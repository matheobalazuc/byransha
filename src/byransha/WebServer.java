package byransha;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsExchange;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

import labmodel.model.v0.AcademiaDB;
import toools.exceptions.ExceptionUtilities;
import toools.io.Cout;
import toools.net.SSHParms;
import toools.reflect.ClassPath;
import toools.text.TextUtilities;

/**
 * https://syncagio.medium.com/how-to-setup-java-httpsserver-and-keystore-eb74a8bd89d
 */

public class WebServer {

	static class Response {
		int code;
		byte[] content;
		String contentType;

		public Response(int i, String contentType, byte[] content) {
			this.code = i;
			this.content = content;
			this.contentType = contentType;
		}

		public Response(int i, String contentType, String content) {
			this(i, contentType, content.getBytes());
		}

		void send(HttpExchange e) throws IOException {
			var output = e.getResponseBody();
			e.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
			e.getResponseHeaders().set("Content-type", contentType);
			e.sendResponseHeaders(code, content.length);
			output.write(content);
			output.flush();
			output.close();
			// System.out.println("sent: " + code + " content:" + new String(content));
		}
	}

	static ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) throws Exception {
		if (false && System.getProperty("user.name").equals("lhogie")) {
			rsyncBinaries();
		}

		Map<String, String> argMap = new HashMap<>();
		Arrays.stream(args).map(a -> a.split("=")).forEach(a -> argMap.put(a[0], a[1]));

		System.out.println("loading DB");
		if (argMap.containsKey("-dbDirectory")) {
			var d = new File(argMap.get("-dbDirectory"));
			DB.defaultDB = (DB) Class.forName(Files.readString(new File(d, "dbClass.txt").toPath()))
					.getConstructor(File.class).newInstance(d);
		} else {
			DB.defaultDB = (DB) Class.forName(argMap.getOrDefault("-dbClass", AcademiaDB.class.getName()))
					.getConstructor().newInstance();
		}

		int port = Integer.valueOf(argMap.getOrDefault("-port", "8080"));
		System.out.println("starting HTTP server on port " + port);

		var httpsServer = HttpsServer.create(new InetSocketAddress(port), 0);
		httpsServer.setHttpsConfigurator(new HttpsConfigurator(getSslContext()) {
			@Override
			public void configure(HttpsParameters params) {
				try {
					SSLContext context = getSSLContext();
					SSLEngine engine = context.createSSLEngine();
					params.setNeedClientAuth(false);
					params.setCipherSuites(engine.getEnabledCipherSuites());
					params.setProtocols(engine.getEnabledProtocols());
					SSLParameters sslParameters = context.getSupportedSSLParameters();
					params.setSSLParameters(sslParameters);
				} catch (Exception ex) {
					System.out.println("Failed to create HTTPS port");
				}
			}
		});

		httpsServer.createContext("/", http -> process((HttpsExchange) http).send(http));
		httpsServer.setExecutor(Executors.newSingleThreadExecutor());
		httpsServer.start();
	}

	public static Response process(HttpsExchange https) {
		try {
			// get what is received by POST (encrypted if HTTPS is used)
			ObjectNode inputJson = inputJSON(https);
			User user = getUser(inputJson, https);

			if (user == null) {
				return new Response(403, "text/plain", "No user authentified. Access denied");
			} else {
				if (user.session == null) { // first connexion, send frontend
					user.session = https.getSSLSession();
					return nour("index.html");
				}

				var path = https.getRequestURI().getPath();

				if (!path.isEmpty()) {
					return nour(path);
				}

				var node = node(inputJson.get("node"), user);

				if (node == null) {
					return new Response(404, "text/plain", "no such node");
				}

				user.stack.push(node);
				var viewNameNode = inputJson.get("view");

				if (viewNameNode == null) {// no request for a specific view
					ObjectNode root = new ObjectNode(null);
					root.set("id", new TextNode("" + node.id()));
					root.set("username", new TextNode(user.name.get()));
					root.set("can read", new TextNode("" + node.canSee(user)));
					root.set("can write", new TextNode("" + node.canSee(user)));
					ArrayNode viewsNode = new ArrayNode(null);
					root.set("views", viewsNode);
					var views = node.compliantViews();
					views.forEach(v -> viewsNode.add(v.toJSONNode(node, user, v.sendContentByDefault)));
					return new Response(200, "text/json", root.toString());
				} else {
					var v = node.compliantViews().get(Integer.valueOf(viewNameNode.asText()));
					return new Response(200, v.contentType(), v.content(node, user));
				}
			}
		} catch (Throwable err) {
			err.printStackTrace();
			return new Response(500, "text/plain", "" + ExceptionUtilities.toString(err));
		}
	}

	private static Response nour(String path) throws MalformedURLException, IOException {
		var url = "https://raw.githubusercontent.com/NourElBazzal/Project_DS4H/refs/heads/gh-pages/" + path;
		System.out.println("downloading " + url);
		var data = new URL(url).openStream().readAllBytes();
		return new Response(200, "text/html", data);
	}

	private static User getUser(ObjectNode inputJson, HttpsExchange https) {
		var userNameNode = inputJson.get("username");

		if (userNameNode == null) {
			return DB.defaultDB.findUser(https.getSSLSession());
		} else {
			var passwordNode = inputJson.get("password");
			return passwordNode == null ? null : auth(userNameNode.asText(), passwordNode.asText());
		}
	}

	private static BNode node(JsonNode idNode, User u) {
		if (idNode == null) {
			return u.currentNode() != null ? u.currentNode() : AcademiaDB.defaultDB.root;
		} else {
			var nodeID = idNode.asText();

			if (nodeID.equals("random")) {
				return AcademiaDB.defaultDB.nodes.random();
			} else if (nodeID.equals("previous")) {
				return u.stack.isEmpty() ? null : u.stack.pop();
			} else {
				return AcademiaDB.defaultDB.findByID(Integer.valueOf(nodeID));
			}
		}
	}

	private static void rsyncBinaries() throws IOException {
		var ssh = new SSHParms();
		ssh.host = "dronic.i3s.unice.fr";
		ssh.username = "hogie";
		Cout.debugSuperVisible("Syncing to " + ssh.host);
		ClassPath.retrieveSystemClassPath().rsyncTo(ssh, "byransha", out -> System.out.println(out),
				err -> System.err.println(err));

		ssh.host = "bastion.i3s.unice.fr";
		ssh.username = "hogie";
		Cout.debugSuperVisible("Syncing to " + ssh.host);
		ClassPath.retrieveSystemClassPath().rsyncTo(ssh, "public_html/software/byransha",
				out -> System.out.println(out), err -> System.err.println(err));
	}

	private static ObjectNode inputJSON(HttpExchange http) throws IOException {
		var postData = http.getRequestBody().readAllBytes();
		ObjectNode inputJson = postData.length > 0 ? (ObjectNode) mapper.readTree(postData) : new ObjectNode(null);

		// adds the URL parameters from the query string to the JSON
		var query = query(http.getRequestURI().getQuery());
		query.entrySet().forEach(e -> inputJson.set(e.getKey(), new TextNode(e.getValue())));

		return inputJson;
	}

	private static User auth(String username, String p) {
		for (var n : DB.defaultDB.nodes.l) {
			if (n instanceof User u && u.accept(username, p)) {
				return u;
			}
		}

		return null;
	}

	static Map<String, String> query(String s) {
		Map<String, String> query = new HashMap<>();

		if (s != null && !s.isEmpty()) {
			for (var e : TextUtilities.split(s, '&')) {
				var a = e.split("=");
				query.put(a[0], a.length == 2 ? a[1] : null);
			}
		}

		return query;
	}

//	static String filename = "/Users/lhogie/a/job/i3s/tableau_de_bord/self-signed-certificate/keystore.jks";

	private static SSLContext getSslContext() throws Exception {
		var keyStore = KeyStore.getInstance("JKS");
//			InputStream fis = new FileInputStream(filename);
		var fis = WebServer.class.getResourceAsStream("keystore.jks");
		var password = "password".toCharArray();
		keyStore.load(fis, password);

		var keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		keyManagerFactory.init(keyStore, password);
		var sslContext = SSLContext.getInstance("TLS");
		sslContext.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());
		return sslContext;
	}
}
