package byransha.web;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsExchange;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

import byransha.BNode;
import byransha.Byransha;
import byransha.DB;
import byransha.JVMNode;
import byransha.ListNode;
import byransha.Log;
import byransha.OSNode;
import byransha.User;
import byransha.web.endpoint.Authenticate;
import byransha.web.endpoint.CurrentNode;
import byransha.web.endpoint.Help;
import byransha.web.endpoint.Jump;
import byransha.web.endpoint.Kill;
import labmodel.model.v0.AcademiaDB;
import toools.io.Cout;
import toools.reflect.ClassPath;
import toools.text.TextUtilities;

/**
 * https://syncagio.medium.com/how-to-setup-java-httpsserver-and-keystore-eb74a8bd89d
 */

public class WebServer extends BNode {
	public static void main(String[] args) throws Exception {
		new WebServer(args);
	}

	static ObjectMapper mapper = new ObjectMapper();

	JVMNode jvm = new JVMNode();
	Byransha byransha = new Byransha();
	OSNode operatingSystem = new OSNode();
	DB db = DB.instance;

	List<User> nbRequestsInProgress = Collections.synchronizedList(new ArrayList<>());
	public Map<String, EndPoint> endpoints = new HashMap<>();

	private HttpsServer httpsServer;
	public final List<Log> logs = new ArrayList<>();

	static {
		View.views.add(new LogsView());
	}

	public WebServer(String[] args) throws Exception {
		registerEndpoint(new Jump());
		registerEndpoint(new Help());
		registerEndpoint(new Kill());
		registerEndpoint(new Authenticate());
		registerEndpoint(new CurrentNode());

		for (var v : View.views.l) {
			registerEndpoint(v);
		}

		try {
			Files.write(new File(WebServer.class.getPackageName() + "-classpath.lst").toPath(),
					ClassPath.retrieveSystemClassPath().toString().getBytes());
		} catch (IOException err) {
		}

		Map<String, String> argMap = new HashMap<>();
		Arrays.stream(args).map(a -> a.split("=")).forEach(a -> argMap.put(a[0], a[1]));

		System.out.println("loading DB");
		if (argMap.containsKey("-dbDirectory")) {
			var d = new File(argMap.get("-dbDirectory"));
			DB.instance = (DB) Class.forName(Files.readString(new File(d, "dbClass.txt").toPath()))
					.getConstructor(File.class).newInstance(d);
		} else {
			DB.instance = (DB) Class.forName(argMap.getOrDefault("-dbClass", AcademiaDB.class.getName()))
					.getConstructor().newInstance();
		}

		DB.instance.accept(this);

		int port = Integer.valueOf(argMap.getOrDefault("-port", "8080"));
		System.out.println("starting HTTP server on port " + port);

		httpsServer = HttpsServer.create(new InetSocketAddress(port), 0);
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
		httpsServer.setExecutor(Executors.newCachedThreadPool());
		httpsServer.start();
	}

	@Override
	public void forEachOut(BiConsumer<String, BNode> consumer) {
		super.forEachOut(consumer);
		consumer.accept("views", View.views);
		consumer.accept("active users", activeUsers());
	}

	public static ListNode<User> activeUsers() {
		ListNode<User> activeUsers = new ListNode<>();

		DB.instance.forEachNode(n -> {
			if (n instanceof User u && u.session != null && u.session.isValid()) {
				activeUsers.add(u);
			}
		});

		return activeUsers;
	}

	private void registerEndpoint(EndPoint e) {
		endpoints.put(e.name(), e);
	}

	static final File buildDir = new File(System.getProperty("user.home"), "frontend/Project_DS4H");

	private HTTPResponse process(HttpsExchange https) {
		try {
			ObjectNode inputJson = grabInputFromURLandPOST(https);
			final var inputJson2sendBack = inputJson.deepCopy();

			User user = DB.instance.findUser(https.getSSLSession());
//			System.out.println("found user from session : " + user);

			if (user == null) {
				user = new User("user", "test", false);
				user.session = https.getSSLSession();
				user.stack.push(DB.instance); 
			}

			var path = https.getRequestURI().getPath();

			if (path.startsWith("/api")) {
				nbRequestsInProgress.add(user);

				var endpointName = inputJson.remove("endpoint").asText();
				Cout.debugSuperVisible(endpointName);
				var endpoint = endpoints.get(endpointName);

				if (endpoint == null)
					throw new IllegalStateException("no endpoint specified");

				EndpointResponse result = endpoint.exec(inputJson, user, this, https);

				if (inputJson.size() > 0)
					throw new IllegalArgumentException("parms unused: " + inputJson.toPrettyString());

				var root = new ObjectNode(null);
				root.set("endpoint name", new TextNode(endpointName));
				root.set("session ID", new TextNode(Long.toHexString(Math.abs(https.getSSLSession().hashCode()))));

				if (user != null) {
					root.set("username", new TextNode(user.name.get()));
				}

				root.set("backend version", new TextNode(Byransha.VERSION));
				long uptimeMs = ManagementFactory.getRuntimeMXBean().getUptime();
				root.set("uptime", new TextNode(Duration.ofMillis(uptimeMs).toString()));
				root.set("request", inputJson2sendBack);

				if (result != null) {
					root.set("result", result.toJson());
				}

				return new HTTPResponse(200, "text/json", root.toPrettyString());
			} else {
				var file = new File(buildDir, path);

				if (!file.exists() || !file.isFile()) {
					file = new File(buildDir, "index.html");
				}

				// System.out.println("serving " + file);
				return new HTTPResponse(200, mimeType(file.getName()), Files.readAllBytes(file.toPath()));
			}
		} catch (

		Throwable err) {
			err.printStackTrace();
			var n = new ObjectNode(null);
			n.set("error class", new TextNode(err.getClass().getName()));
			n.set("message", new TextNode(err.getMessage()));
			var a = new ArrayNode(null);

			for (var e : err.getStackTrace()) {
				var se = new ObjectNode(null);
				se.set("line number", new IntNode(e.getLineNumber()));
				se.set("class name", new TextNode(e.getClassName()));
				se.set("method name", new TextNode(e.getMethodName()));
				a.add(se);
			}

			n.set("stack trace", a);

			return new HTTPResponse(500, "text/plain", n.toPrettyString());
		} finally {
			nbRequestsInProgress.remove(https);
		}
	}

	static String mimeType(String url) {
		if (url.endsWith(".html") || url.endsWith(".htm")) {
			return "text/html";
		} else if (url.endsWith(".js") || url.endsWith(".jsx")) {
			return "text/javascript";
		} else if (url.endsWith(".jpg") || url.endsWith(".jpeg")) {
			return "image/jpeg";
		} else if (url.endsWith(".png")) {
			return "image/png";
		} else if (url.endsWith(".svg")) {
			return "image/svg+xml";
		} else if (url.endsWith(".css")) {
			return "text/css";
		} else {
			return null;
		}
	}

	private static ObjectNode grabInputFromURLandPOST(HttpExchange http) throws IOException {
		var postData = http.getRequestBody().readAllBytes();
		ObjectNode inputJson = postData.length > 0 ? (ObjectNode) mapper.readTree(postData) : new ObjectNode(null);

		// adds the URL parameters from the query string to the JSON
		var query = query(http.getRequestURI().getQuery());
		query.entrySet().forEach(e -> inputJson.set(e.getKey(), new TextNode(e.getValue())));

		return inputJson;
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

	private SSLContext getSslContext() throws Exception {
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

	public void log(String msg) {
		logs.add(new Log(new Date(), msg));
	}

	public static class Info extends JSONView<WebServer> {
		@Override
		protected JsonNode jsonData(WebServer n, User u) {
			var r = new ObjectNode(null);
			r.set("#request NOW", new TextNode("" + n.nbRequestsInProgress.size()));
			r.set("#requests", new TextNode("" + n.nbRequestsInProgress.stream().map(uu -> uu.name.get()).toList()));
			r.set("active users", new TextNode("" + n.activeUsers().l.stream().map(uu -> uu.name.get()).toList()));
			r.set("timeout", new TextNode("" + n.httpsServer.getHttpsConfigurator().getSSLContext()
					.getClientSessionContext().getSessionTimeout()));
			r.set("cache size", new TextNode("" + n.httpsServer.getHttpsConfigurator().getSSLContext()
					.getClientSessionContext().getSessionCacheSize()));
			r.set("SSL protocol",
					new TextNode("" + n.httpsServer.getHttpsConfigurator().getSSLContext().getProtocol()));
			return r;
		}

		@Override
		protected String jsonDialect() {
			return "nodeinfo";
		}
	}

	public static class LogsView extends JSONView<WebServer> {
		@Override
		protected JsonNode jsonData(WebServer n, User u) {
			var r = new ArrayNode(null);
			n.logs.forEach(l -> {
				var lr = new ObjectNode(null);
				lr.set("date", new TextNode(l.date.toLocaleString()));
				lr.set("message", new TextNode(l.msg));
				r.add(lr);
			});
			return r;
		}

		@Override
		protected String jsonDialect() {
			return "logs";
		}
	}
}
