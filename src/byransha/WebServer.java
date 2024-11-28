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

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import toools.text.TextUtilities;

public class WebServer {

	static class Response {
		int code;
		String s;
		String contentType;

		void send(HttpExchange e) throws IOException {
			OutputStream output = e.getResponseBody();
			e.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

			e.getResponseHeaders().set("Content-type", contentType);
			e.sendResponseHeaders(code, s.length());
			output.write(s.getBytes());
			output.flush();
			output.close();
			System.out.println("sent: " + s);
		}
	}

	public static void main(String[] args) throws IOException {
		User user = null;
		var node = new GOBMNode();
		DB.defaultDB.accept(node);
		GOBMNode currentNode = DB.defaultDB.root;

		var httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
		httpServer.createContext("/", e -> {
			var response = new Response();

			try {
				URI uri = e.getRequestURI();
				List<String> path = path(uri.getPath());
				System.out.println(path);
				var context = path.getFirst();

				if (false){//user == null) {
					response.contentType = "text/html";
					response.s = "<html>login page";
				} else if (context.equals("html")) {
					response.contentType = "text/html";
					response.s = "<html>logged : " + user + "\nNODE VIEWS";
				} else if (context.equals("api")) {
					Map<String, String> query = query(uri.getQuery());
					response.contentType = "text/json";
					response.s = currentNode.compliantViews().stream().map(v -> {
                                try {
                                    return v.toJSONNode(currentNode, user);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }).toList()
							.toString();
				} else {
					response.contentType = "text/plain";
					response.s = "Error: " + context;
				}
			} catch (Throwable err) {
				response.contentType = "text/plain";
				response.s = "Error: " + err;
				err.printStackTrace();
			}

			response.send(e);
		});

		httpServer.setExecutor(Executors.newCachedThreadPool());
		httpServer.start();
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
