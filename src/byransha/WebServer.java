package byransha;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import toools.text.TextUtilities;

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
			OutputStream output = e.getResponseBody();
			e.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

			e.getResponseHeaders().set("Content-type", contentType);
			e.sendResponseHeaders(code, content.length);
			output.write(content);
			output.flush();
			output.close();
			System.out.println("sent: " + code + " content:" + new String(content));
		}
	}

	static User user = null;
	static GOBMNode currentNode = DB.defaultDB.root;

	public static void main(String[] args) throws IOException {
		initDB(args);

		var httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
		httpServer.createContext("/", e -> {
			Response response;

			try {
				URI uri = e.getRequestURI();
				Map<String, String> query = query(uri.getQuery());

				if (query.containsKey("auth")) {
					user = auth(query.get("user"), query.get("password"));

					if (user != null) {
						response = new Response(200, "text/html", "Welcome " + user.name
								+ "! Start <a href='http://localhost:8080/?node'>navigating</a>");
					} else {
						response = new Response(403, "text/plain", "Access denied");
					}
				} else if (user == null) {
					response = new Response(403, "text/html",
							"<html>use the following URL to authenticate: <a href='?auth&user=user&password=test'>here</a>");
				} else if (query.containsKey("node")) {
					var id = query.get("node");
					currentNode = id == null ? DB.defaultDB.root : DB.defaultDB.findByID(id);

					if (currentNode == null) {
						response = new Response(404, "text/plain", "no such node: " + id);
					} else {

						var viewName = query.get("view");

						if (viewName == null) {
							ObjectNode root = new ObjectNode(null);
							root.set("id", new TextNode(currentNode.id()));
							ArrayNode viewsNode = new ArrayNode(null);
							root.set("views", viewsNode);

							for (var v : currentNode.compliantViews()) {
								viewsNode.add(v.toJSONNode(currentNode, user));
							}

							response = new Response(200, "text/json", root.toString());
						} else {
							var v = currentNode.compliantViews().get(Integer.valueOf(viewName));
							response = new Response(200, v.contentType(), v.content(currentNode, user));
						}
					}
				} else {
					response = new Response(200, "text/html",
							new String(WebServer.class.getResource("app.html").openStream().readAllBytes()));
				}

				response.send(e);
			} catch (Throwable err) {
				response = new Response(500, "text/plain", "" + err);
				err.printStackTrace();
			}
		});

		httpServer.setExecutor(Executors.newSingleThreadExecutor());
		httpServer.start();
	}

	private static void initDB(String[] args) {
		var node = new GOBMNode();
		DB.defaultDB.accept(node);
	}

	private static User auth(String u, String p) {
		var m = new HashMap<String, String>();
		m.put("user", "test");
		m.put("admin", "test");

		if (m.containsKey(u)) {
			if (m.get(u).equals(p)) {
				return new User(u, u.equals("admin"));
			} else {
				return null;
			}
		} else {
			return null;
		}
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
}
