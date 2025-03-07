package byransha.web.endpoint;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.BBGraph;
import byransha.User;
import byransha.web.EndPoint;
import byransha.web.EndpointResponse;
import byransha.web.EndpointTextResponse;
import byransha.web.WebServer;

public class Kill extends EndPoint {

	public Kill(BBGraph db) {
		super(db);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EndpointResponse exec(ObjectNode in, User user, WebServer webServer, HttpsExchange http) throws Throwable {
		new Thread(() -> {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
			System.exit(0);
		}).start();

		return new EndpointTextResponse("exiting...", "text/plain");
	}
}
