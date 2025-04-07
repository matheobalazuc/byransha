package byransha.web.endpoint;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

import byransha.BBGraph;
import byransha.User;
import byransha.labmodel.model.v0.Office;
import byransha.web.EndpointHtmlResponse;
import byransha.web.EndpointResponse;
import byransha.web.NodeEndpoint;
import byransha.web.WebServer;

public class OfficeEndpoint extends NodeEndpoint<BBGraph> {

    public OfficeEndpoint(BBGraph db) {
        super(db);
    }

    @Override
    public String getDescription() {
        return "Endpoint to retrieve Office data.";
    }

    @Override
    public EndpointResponse exec(ObjectNode input, User user, WebServer webServer, HttpsExchange exchange, BBGraph node)
            throws Throwable {
        var officeNode = node.findNode(Office.class);
        if (officeNode == null) {
            return new EndpointHtmlResponse(404, "text/html", "<h1>Office not found</h1>".getBytes());
        }

        String officeHtml = htmlNode.generateHtmlForNode(officeNode);
        return new EndpointHtmlResponse(200, "text/html", officeHtml.getBytes());
    }

}