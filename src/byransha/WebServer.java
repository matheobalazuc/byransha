package byransha;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import org.jcp.xml.dsig.internal.dom.Utils;

import toools.text.TextUtilities;

public class WebServer {

	public static void main(String[] args) {
		User user = null;

		var httpServer = HttpServer.create(new InetSocketAddress(5643), 0);
		httpServer.createContext("/", e -> {
			OutputStream output = e.getResponseBody();

			try {
				URI uri = e.getRequestURI();
				InputStream input = "POST".equals(e.getRequestMethod()) ? e.getRequestBody() : null;
				// is.close();
				System.out.println(uri.getPath());
				List<String> path = Utils.path(uri.getPath());
				System.out.println(path);
				Map<String, String> query = Utils.query(uri.getQuery());
				e.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
			} catch (Throwable err) {
				try {
					System.err.println("The following error will be sent to the Web client");
					err.printStackTrace();
					Utils.singleHTTPResponse(HttpURLConnection.HTTP_INTERNAL_ERROR, "text/plain",
							TextUtilities.exception2string(err).getBytes(), e, output);
					logError(err.getMessage());
				} catch (IOException ee) {
					// ee.printStackTrace();
				}
			} finally {
				output.close();
			}
		});

		httpServer.setExecutor(new ThreadPoolExecutor());
		httpServer.start();

	}
}
