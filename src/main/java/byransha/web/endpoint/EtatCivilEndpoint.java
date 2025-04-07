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

public class EtatCivilEndpoint extends NodeEndpoint<BBGraph> {

    public EtatCivilEndpoint(BBGraph db) {
        super(db);
    }

    @Override
    public String getDescription() {
        return "Endpoint to retrieve EtatCivil data.";
    }

    @Override
    public EndpointResponse exec(ObjectNode input, User user, WebServer webServer, HttpsExchange exchange, BBGraph node)
            throws Throwable {
        var personNode = node.findNode(Person.class);
        if (personNode == null || personNode.etatCivil == null) {
            return new EndpointHtmlResponse(404, "text/html", "<h1>EtatCivil not found</h1>".getBytes());
        }
    
        String etatCivilHtml = htmlNode.generateHtmlForNode(personNode.etatCivil);
    
        return new EndpointHtmlResponse(200, "text/html", etatCivilHtml.getBytes());
    }

}