package byransha.web.endpoint;

import java.lang.reflect.Modifier;
import java.util.Set;

import org.reflections.Reflections;

import byransha.BBGraph;
import byransha.BNode;
import byransha.User;
import byransha.web.EndpointHtmlResponse;
import byransha.web.EndpointResponse;
import byransha.web.NodeEndpoint;
import byransha.web.WebServer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpsExchange;

public class AutoEndpointGenerator {

    public static void generateEndpoints(BBGraph graph, WebServer webServer) {
        // Scan the package containing the models
        Reflections reflections = new Reflections("byransha.labmodel.model.v0");

        // Find all classes that inherit from BNode
        Set<Class<? extends BNode>> modelClasses = reflections.getSubTypesOf(BNode.class);

        System.out.println("Detected model classes:");
        for (Class<? extends BNode> modelClass : modelClasses) {
            System.out.println(" - " + modelClass.getName());
        }

        for (Class<? extends BNode> modelClass : modelClasses) {
            // Skip abstract classes
            if (Modifier.isAbstract(modelClass.getModifiers())) {
                System.out.println("Skipping abstract class: " + modelClass.getSimpleName());
                continue;
            }

            // Dynamically create an endpoint for each class
            String endpointName = modelClass.getSimpleName().toLowerCase(); // Use the model name as the URL
            System.out.println("Generating endpoint for: " + modelClass.getSimpleName() + " at /" + endpointName);
            webServer.addEndpoint(new AutoGeneratedEndpoint<>(graph, modelClass, endpointName));
        }
    }

    // Generic class for automatically generated endpoints
    public static class AutoGeneratedEndpoint<T extends BNode> extends NodeEndpoint<BBGraph> {
        private final Class<T> modelClass;
        private final String endpointName;

        public AutoGeneratedEndpoint(BBGraph graph, Class<T> modelClass, String endpointName) {
            super(graph);
            this.modelClass = modelClass;
            this.endpointName = endpointName;
        }

        public Class<T> getModelClass() {
            return modelClass;
        }

        @Override
        public String getDescription() {
            return "Endpoint for " + modelClass.getSimpleName();
        }

        @Override
        public String name() {
            return endpointName; // Returns the endpoint name based on the model
        }

        @Override
        public EndpointResponse exec(ObjectNode input, User user, WebServer webServer, HttpsExchange exchange, BBGraph graph)
                throws Throwable {
            // Find a node corresponding to the class
            T node = graph.find(modelClass, n -> true);

            if (node == null) {
                return new EndpointHtmlResponse(404, "text/html", ("<h1>" + modelClass.getSimpleName() + " not found</h1>").getBytes());
            }

            // Generate the HTML for the node
            String html = AutoHtmlNodeGenerator.generateHtmlForNode(node);
            return new EndpointHtmlResponse(200, "text/html", html.getBytes());
        }
    }
}