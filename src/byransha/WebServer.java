package byransha;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
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

	public static void main(String[] args) throws IOException {
		User user = null;
		DB db;

		var httpServer = HttpServer.create(new InetSocketAddress(5643), 0);
		httpServer.createContext("/api", e -> {
			OutputStream output = e.getResponseBody();

			try {
				URI uri = e.getRequestURI();
				System.out.println(uri.getPath());
				List<String> path = path(uri.getPath());
				System.out.println(path);
				Map<String, String> query = query(uri.getQuery());
				e.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
			} catch (Throwable err) {
				try {
					System.err.println("The following error will be sent to the Web client");
					err.printStackTrace();
					singleHTTPResponse(HttpURLConnection.HTTP_INTERNAL_ERROR, "text/plain",
							TextUtilities.exception2string(err).getBytes(), e, output);

				} catch (IOException ee) {
					// ee.printStackTrace();
				}
			} finally {
				output.close();
			}
		});

		httpServer.createContext("/html", e -> {
			URI uri = e.getRequestURI();
			System.out.println(uri);
			OutputStream output = e.getResponseBody();

			if (user == null) {
				output.write("login page".getBytes());
			}else {
				e.getResponseHeaders().set("Content-type", "text/html");
				var s= ("logged " + user).getBytes();
				e.sendResponseHeaders(0, s.length);
				output.write(s);
				output.close();
			}
			
			
		});

		httpServer.setExecutor(Executors.newFixedThreadPool(1));
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
