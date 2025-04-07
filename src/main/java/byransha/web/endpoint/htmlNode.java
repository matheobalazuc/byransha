package byransha.web.endpoint;

import java.lang.reflect.Field;

import byransha.BNode;
import byransha.ListNode;

public class htmlNode {

    public static String generateHtmlForNode(BNode node) {
        if (node == null) {
            return generateHtmlDocument("<p>No data available for this node.</p>", "No Data View");
        }

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<div class=\"container\">");
        htmlBuilder.append("<ul class=\"list\">");

        for (Field field : node.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(node);
                String fieldName = field.getName();

                htmlBuilder.append("<li class=\"list-item\">");
                htmlBuilder.append("<strong>").append(fieldName).append(":</strong> ");
                if (value instanceof BNode) {
                    for (Field subField : value.getClass().getDeclaredFields()) {
                        subField.setAccessible(true);
                        Object subValue = subField.get(value);
                        String subFieldName = subField.getName();
                        htmlBuilder.append("<br><strong>").append(subFieldName).append(":</strong> ");
                        htmlBuilder.append(subValue != null ? subValue.toString() : "<em>N/A</em>");
                    }
                } else {
                    htmlBuilder.append(value != null ? value.toString() : "<em>N/A</em>");
                }
                htmlBuilder.append("</li>");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        htmlBuilder.append("</ul>");
        htmlBuilder.append("</div>");

        String title = node.getClass().getSimpleName() + " View";
        return generateHtmlDocument(htmlBuilder.toString(), title);
    }

    private static String generateHtmlDocument(String bodyContent, String title) {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<!DOCTYPE html>");
        htmlBuilder.append("<html lang=\"en\">");
        htmlBuilder.append("<head>");
        htmlBuilder.append("<meta charset=\"UTF-8\">");
        htmlBuilder.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        htmlBuilder.append("<title>").append(title).append("</title>");
        htmlBuilder.append("<style>");
        htmlBuilder.append("body { font-family: Arial, sans-serif; background-color: #f8f9fa; margin: 0; padding: 0; }");
        htmlBuilder.append(".container { max-width: 1200px; margin: 20px auto; padding: 10px; background-color: #ffffff; border-radius: 4px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); overflow: hidden; }");
        htmlBuilder.append(".list { display: flex; flex-wrap: wrap; list-style: none; padding: 0; margin: 0; }");
        htmlBuilder.append(".list-item { flex: 1 1 calc(33.33% - 10px); padding: 8px 10px; border-bottom: 1px solid #ddd; font-size: 1rem; color: #555; }");
        htmlBuilder.append(".list-item:last-child { border-bottom: none; }");
        htmlBuilder.append("strong { color: #000; }");
        htmlBuilder.append("</style>");
        htmlBuilder.append("</head>");
        htmlBuilder.append("<body>");
        htmlBuilder.append("<h1>").append(title).append("</h1>"); 
        htmlBuilder.append(bodyContent);
        htmlBuilder.append("</body>");
        htmlBuilder.append("</html>");
        return htmlBuilder.toString();
    }
}