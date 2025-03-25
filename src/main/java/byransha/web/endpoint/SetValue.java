package byransha.web.endpoint;

import byransha.*;
import byransha.web.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

public class SetValue extends NodeEndpoint<BNode> {

    public SetValue(BBGraph g) {
        super(g);
    }

    @Override
    public EndpointJsonResponse exec(ObjectNode in, User user, WebServer webServer, HttpsExchange exchange, BNode currentNode) throws Throwable {
        var targetID = requireParm(in, "target").asInt();
        var target = node(targetID);
        if (target instanceof StringNode) {
            var value = requireParm(in, "value").asText();
            ((StringNode) target).set(value);
        }
        else if(target instanceof BooleanNode){
            var value = requireParm(in, "value").asBoolean();
            ((BooleanNode) target).set(value);
        }
        return new CurrentNode(graph).exec(in, user, webServer, exchange, target);
    }
}
