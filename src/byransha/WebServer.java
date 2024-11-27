package byransha;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import toools.text.TextUtilities;

public class WebServer {

	static class Response {
		int code = 404;
		String content = "default content";
		String contentType = "text/html";

		public Response(int i, String contentType, String content) {
			this.code = i;
			this.content = content;
			this.contentType = contentType;
		}

		void send(HttpExchange e) throws IOException {
			OutputStream output = e.getResponseBody();
			e.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

			e.getResponseHeaders().set("Content-type", contentType);
			e.sendResponseHeaders(code, content.length());
			output.write(content.getBytes());
			output.flush();
			output.close();
			System.out.println("sent: " + code + " content:" + content);
		}
	}

	static User user = null;
	static GOBMNode currentNode = DB.defaultDB.root;

	public static void main(String[] args) throws IOException {
		var node = new GOBMNode();
		DB.defaultDB.accept(node);
		DB.defaultDB.root = node;

		var httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
		httpServer.createContext("/", e -> {
			Response response;

			try {
				URI uri = e.getRequestURI();
				Map<String, String> query = query(uri.getQuery());

				if (query.containsKey("auth")) {
					user = auth(query.get("user"), query.get("password"));

					if (user != null) {
						response = new Response(200, "text/html", "Welcome " + user.name + "! Start <a href='http://localhost:8080/?node'>navigatin</a>");
					} else {
						response = new Response(404, "text/plain", "Access denied");
					}
				} else if (user == null) {
					response = new Response(403, "text/html",
							"<html>use the following URL to authenticate: <a href='?auth&user=user&password=test'>here</a>");
				} else if (query.containsKey("node")) {
					var id = query.get("node");
					currentNode = id == null ? DB.defaultDB.root : DB.defaultDB.findByID(query.get("node"));

					if (currentNode == null) {
						response = new Response(404, "text/plain", "no such node: " + id);
					} else {
						ObjectNode root = new ObjectNode(null);
						root.set("id", new TextNode(node.id()));
						ArrayNode viewsNode = new ArrayNode(null);
						root.set("views", viewsNode);

						for (var v : currentNode.compliantViews()) {
							viewsNode.add(v.toJSONNode(currentNode, user));
						}

						response = new Response(200, "text/json", root.toString());
					}
				} else {
					response = new Response(200, "text/html", "HTML");
				}
			} catch (Throwable err) {
				response = new Response(500, "text/plain", "" + err);
				err.printStackTrace();
			}

			response.send(e);
		});

		httpServer.setExecutor(Executors.newCachedThreadPool());
		httpServer.start();
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

	static void singleHTTPResponse(int returnCode, String mimeType, byte[] bytes, HttpExchange e, OutputStream os)
			throws IOException {
		e.getResponseHeaders().set("Content-type", mimeType);
		e.sendResponseHeaders(returnCode, bytes.length);
		os.write(bytes);
	}

	static List<String> path(String s) {
		if (s == null) {
			return null;
		}

		if (s.startsWith("/")) {
			s = s.substring(1);
		}

		if (s.endsWith("/")) {
			s = s.substring(0, s.length() - 1);
		}

		s = s.replaceAll("//", "/");

		return s.isEmpty() ? null : TextUtilities.split(s, '/');
	}

	static String removeOrDefault(Map<String, String> map, String k, String defaultValue, Set<String> validKeys) {
		var r = map.remove(k);

		if (r == null)
			r = defaultValue;

		if (validKeys != null && !validKeys.contains(r))
			throw new IllegalArgumentException(
					r + " is not a valid value for '" + k + "'. Valid values are: " + validKeys);

		return r;
	}

	static Map<String, String> query(String s) {
		Map<String, String> query = new HashMap<>();

		if (s != null && !s.isEmpty()) {
			for (String queryEntry : TextUtilities.split(s, '&')) {
				String[] a = queryEntry.split("=");

				if (a.length == 2) {
					query.put(a[0], a[1]);
				} else {
					query.put(a[0], null);
				}
			}
		}

		return query;
	}

}
