package main.java.web.view;

import java.io.PrintWriter;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import main.java.BNode;
import main.java.BBGraph;
import main.java.User;
import main.java.web.DevelopmentView;
import main.java.web.TextOutputEndpoint;
import main.java.web.WebServer;

public class ToStringView extends TextOutputEndpoint<BNode> implements DevelopmentView {

	public ToStringView(BBGraph db) {
		super(db);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String textMimeType() {
		return "text/plain";
	}

	@Override
	protected void print(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange, BNode node,
			PrintWriter pw) {
		pw.print(node.toString());
	}

}