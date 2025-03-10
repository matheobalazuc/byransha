package byransha.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.ArrayList;
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

import byransha.BBGraph;
import byransha.BNode;
import byransha.Byransha;
import byransha.JVMNode;
import byransha.ListNode;
import byransha.Log;
import byransha.OSNode;
import byransha.User;
import byransha.web.endpoint.AllOuts;
import byransha.web.endpoint.Authenticate;
import byransha.web.endpoint.CurrentNode;
import byransha.web.endpoint.Endpoints;
import byransha.web.endpoint.Jump;
import byransha.web.endpoint.Kill;
import byransha.web.endpoint.NodeEndpoints;
import byransha.web.endpoint.NodeIDs;
import byransha.web.endpoint.Nodes;
import byransha.web.view.AllViews;
import byransha.web.view.CharExampleXY;
import byransha.web.view.CharacterDistribution;
import byransha.web.view.ModelDOTView;
import byransha.web.view.ModelGraphivzSVGView;
import byransha.web.view.SourceView;
import byransha.web.view.ToStringView;
import labmodel.model.v0.Picture;
import labmodel.model.v0.view.LabView;
import labmodel.model.v0.view.StructureView;
import toools.reflect.ClassPath;
import toools.text.TextUtilities;

/**
 * https://syncagio.medium.com/how-to-setup-java-httpsserver-and-keystore-eb74a8bd89d
 */

public class WebServer extends BNode {
	public static void main(String[] args) throws Exception {
		var argList = List.of(args);
		var argMap = new HashMap<String, String>();
		argList.stream().map(a -> a.split("=")).forEach(a -> argMap.put(a[0], a[1]));
		BBGraph g = loadG(argMap);
		int port = Integer.valueOf(argMap.getOrDefault("-port", "8080"));
		new WebServer(g, port);
	}

	public static BBGraph loadG(Map<String, String> argMap)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException, IOException {
		System.out.println("loading DB");

		if (argMap.containsKey("-dbDirectory")) {
			var d = new File(argMap.get("-dbDirectory"));
			return (BBGraph) Class.forName(Files.readString(new File(d, "dbClass.txt").toPath()))
					.getConstructor(File.class).newInstance(d);
		} else if (argMap.containsKey("-dbClass")) {
			return (BBGraph) Class.forName(argMap.get("-dbClass")).getConstructor().newInstance();
		} else {
			return new BBGraph(new File(System.getProperty("user.home") + "/." + BBGraph.class.getPackageName()));
		}
	}

	static ObjectMapper mapper = new ObjectMapper();

	final JVMNode jvm;
	final Byransha byransha;
	final OSNode operatingSystem;

	List<User> nbRequestsInProgress = Collections.synchronizedList(new ArrayList<>());
	public Map<String, EndPoint> endpoints = new HashMap<>();

	private HttpsServer httpsServer;
	public final List<Log> logs = new ArrayList<>();

	public WebServer(BBGraph g, int port) throws Exception {
		super(g);
		jvm = new JVMNode(g);
		byransha = new Byransha(g);
		operatingSystem = new OSNode(g);
		registerEndpoint(new Jump(g));
		registerEndpoint(new Endpoints(g));
		registerEndpoint(new Kill(g));
		registerEndpoint(new Authenticate(g));
		registerEndpoint(new CurrentNode(g));
		registerEndpoint(new NodeIDs(g));
		registerEndpoint(new Nodes(g));
		registerEndpoint(new EndpointCallDistributionView(g));
		registerEndpoint(new Info(g));
		registerEndpoint(new LogsView(g));

		registerEndpoint(new AllOuts(g));
		registerEndpoint(new BasicView(g));
		registerEndpoint(new CharacterDistribution(g));
		registerEndpoint(new CharExampleXY(g));
		registerEndpoint(new User.UserView(g));
		registerEndpoint(new BBGraph.GraphView(g));
		registerEndpoint(new OSNode.View(g));
		registerEndpoint(new JVMNode.View(g));
		registerEndpoint(new BNode.GraphView(g));
		registerEndpoint(new ModelGraphivzSVGView(g));
		registerEndpoint(new Nav2(g));
		registerEndpoint(new OutNodeDistribution(g));
		registerEndpoint(new Picture.V(g));
		registerEndpoint(new AllViews(g));
		registerEndpoint(new LabView(g));
		registerEndpoint(new ModelDOTView(g));
		registerEndpoint(new SourceView(g));
		registerEndpoint(new ToStringView(g));
		registerEndpoint(new StructureView(g));
		registerEndpoint(new NodeEndpoints(g));

		try {
			Path classPathFile = new File(Byransha.class.getPackageName() + "-classpath.lst").toPath();
			System.out.println("writing " + classPathFile);
			Files.write(classPathFile, ClassPath.retrieveSystemClassPath().toString().getBytes());
		} catch (IOException err) {
			err.printStackTrace();
		}

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
					ex.printStackTrace();
				}
			}
		});

		httpsServer.createContext("/", http -> processRequest((HttpsExchange) http).send(http));
		httpsServer.setExecutor(Executors.newCachedThreadPool());
		httpsServer.start();
	}

	public List<NodeEndpoint> compliantEndpoints(BNode n) {
		List<NodeEndpoint> r = new ArrayList<>();

		for (var e : endpoints.values()) {
			if (e instanceof NodeEndpoint v && v.getTargetNodeType().isAssignableFrom(n.getClass())) {
				r.add(v);
			}
		}

		Collections.sort(r, (a, b) -> a.getTargetNodeType().isAssignableFrom(b.getTargetNodeType()) ? 1 : -1);
		return r;
	}

	@Override
	public void forEachOut(BiConsumer<String, BNode> consumer) {
		super.forEachOut(consumer);
		consumer.accept("active users", activeUsers());
	}

	public ListNode<User> activeUsers() {
		ListNode<User> activeUsers = new ListNode<>(graph);

		graph.forEachNode(n -> {
			if (n instanceof User u && u.session != null && u.session.isValid()) {
				activeUsers.add(u);
			}
		});

		return activeUsers;
	}

	private void registerEndpoint(EndPoint e) {
		if (endpoints.containsKey(e.name()))
			throw new IllegalStateException(e.name());

		endpoints.put(e.name(), e);
	}

	static final File buildDir = new File(System.getProperty("user.home"), "frontend/Project_DS4H");

	private HTTPResponse processRequest(HttpsExchange https) {
		try {
			ObjectNode inputJson = grabInputFromURLandPOST(https);
			final var inputJson2sendBack = inputJson.deepCopy();

			User user = graph.findUser(https.getSSLSession());
//			System.out.println("found user from session : " + user);

			if (user == null) {
				user = new User(graph, "user", "test");
				user.session = https.getSSLSession();
				user.stack.push(graph.root());
			}

			var path = https.getRequestURI().getPath();

			if (path.equals("/api") || path.equals("/api/")) {
				nbRequestsInProgress.add(user);

				var response = new ObjectNode(null);
				response.set("session ID", new TextNode(Long.toHexString(Math.abs(https.getSSLSession().hashCode()))));
				response.set("backend version", new TextNode(Byransha.VERSION));
				long uptimeMs = ManagementFactory.getRuntimeMXBean().getUptime();
				response.set("uptime", new TextNode(Duration.ofMillis(uptimeMs).toString()));

				if (!inputJson2sendBack.isEmpty())
					response.set("request", inputJson2sendBack);

				if (user != null) {
					response.set("username", new TextNode(user.name.get()));
				}

				var endpointNode = inputJson.remove("endpoint");
				long startTimeNs = System.nanoTime();

				if (endpointNode != null) {
					var endpoint = endpoints.get(endpointNode.asText());
					response.set("endpoint name", endpointNode);

					if (endpoint == null) {
						throw new IllegalArgumentException("no such endpoint: " + endpointNode.asText());
					} else {
						EndpointResponse<?> result = endpoint.exec(inputJson, user, this, https);
						endpoint.nbCalls++;
						endpoint.timeSpentNs += System.nanoTime() - startTimeNs;

						if (inputJson.remove("raw") != null) {
							if (inputJson.size() > 0)
								throw new IllegalArgumentException("parms unused: " + inputJson.toPrettyString());

							return new HTTPResponse(200, result.contentType, result.toRawText().getBytes());
						} else {
							response.set("result", result.toJson());
						}
					}
				}

				response.set("durationNs", new TextNode("" + (System.nanoTime() - startTimeNs)));

				if (inputJson.size() > 0)
					throw new IllegalArgumentException("parms unused: " + inputJson.toPrettyString());

				return new HTTPResponse(200, "text/json", response.toPrettyString().getBytes());
			} else {
				var file = new File(buildDir, path);

				if (!file.exists() || !file.isFile()) {
					file = new File(buildDir, "index.html");
				}

				System.out.println("serving " + file);
				return new HTTPResponse(200, mimeType(file.getName()), Files.readAllBytes(file.toPath()));
			}
		} catch (Throwable err) {
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
			return new HTTPResponse(500, "text/plain", n.toPrettyString().getBytes());
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
		// gets the date from POST
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
		InputStream fis = new FileInputStream("./src/byransha/web/keystore.jks");
		//var fis = WebServer.class.getResourceAsStream("keystore.jks");
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

	public static class Info extends NodeEndpoint<WebServer> {

		public Info(BBGraph db) {
			super(db);
		}

		@Override
		public EndpointResponse exec(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange,
				WebServer n) {
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
			return new EndpointJsonResponse(r, "nodeinfo");
		}
	}

	public static class LogsView extends NodeEndpoint<WebServer> {
		public LogsView(BBGraph db) {
			super(db);
			// TODO Auto-generated constructor stub
		}

		@Override
		public EndpointResponse exec(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange,
				WebServer n) {
			var r = new ArrayNode(null);
			n.logs.forEach(l -> {
				var lr = new ObjectNode(null);
				lr.set("date", new TextNode(l.date.toLocaleString()));
				lr.set("message", new TextNode(l.msg));
				r.add(lr);
			});
			return new EndpointJsonResponse(r, "logs");
		}
	}

	public static class EndpointCallDistributionView extends EndPoint {
		public EndpointCallDistributionView(BBGraph db) {
			super(db);
			// TODO Auto-generated constructor stub
		}

		@Override
		public EndpointResponse exec(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange) {
			var d = new Byransha.Distribution();
			webServer.endpoints.values().forEach(e -> d.addXY(e.name(), e.nbCalls));
			return new EndpointJsonResponse(d.toJson(), "logs");
		}
	}

}
