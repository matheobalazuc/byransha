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