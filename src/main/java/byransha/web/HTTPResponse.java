package byransha.web;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

class HTTPResponse {
	int code;
	byte[] content;
	String contentType;

	public HTTPResponse(int i, String contentType, byte[] content) {
		this.code = i;
		this.content = content;
		this.contentType = contentType;
	}


	void send(HttpExchange e) throws IOException {
        try (var output = e.getResponseBody()) {
            try {
                e.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                e.getResponseHeaders().set("Content-type", contentType);
                e.sendResponseHeaders(code, content.length);
                output.write(content);
                output.flush();
            } catch (IOException ex) {
                System.err.println("Error sending response: " + ex.getMessage());
                throw ex;
            }
        } catch (IOException ex) {
            System.err.println("Error closing output stream: " + ex.getMessage());
        }
		// System.out.println("sent: " + code + " content:" + new String(content));
	}
}
