package byransha.web.endpoint;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.BBGraph;
import byransha.User;
import byransha.labmodel.model.v0.Person;
import byransha.web.EndpointHtmlResponse;
import byransha.web.EndpointResponse;
import byransha.web.NodeEndpoint;
import byransha.web.WebServer;

public class PersonEndpoint extends NodeEndpoint<BBGraph> {

    public PersonEndpoint(BBGraph db) {
        super(db);
    }

    @Override
    public String getDescription() {
        return "Endpoint to retrieve Person data from the graph.";
    }

    @Override
    public EndpointResponse exec(ObjectNode input, User user, WebServer webServer, HttpsExchange exchange, BBGraph node)
            throws Throwable {
        var personNode = node.findNode(Person.class);
        if (personNode == null) {
            return new EndpointHtmlResponse(404, "text/html", "<h1>Person not found</h1>".getBytes());
        }

        String personHtml = htmlNode.generateHtmlForNode(personNode);
        return new EndpointHtmlResponse(200, "text/html", personHtml.getBytes());
    }

}