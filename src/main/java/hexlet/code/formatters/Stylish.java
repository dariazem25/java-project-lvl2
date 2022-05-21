package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class Stylish {

    public static String format(List<Map<String, Object>> comparedData) {
        StringBuilder result = new StringBuilder();
        result.append("{");
        for (Map<String, Object> data : comparedData) {
            if (data.get("status").equals("unchanged")) {
                result.append("\n\s\s\s\s").append(data.get("key")).append(": ").append(data.get("oldValue"));
            } else if (data.get("status").equals("changed")) {
                result.append("\n\s\s- ").append(data.get("key")).append(": ").append(data.get("oldValue"));
                result.append("\n\s\s+ ").append(data.get("key")).append(": ").append(data.get("newValue"));
            } else if (data.get("status").equals("deleted")) {
                result.append("\n\s\s- ").append(data.get("key")).append(": ").append(data.get("oldValue"));
            } else if (data.get("status").equals("added")) {
                result.append("\n\s\s+ ").append(data.get("key")).append(": ").append(data.get("newValue"));
            }
        }
        result.append("\n").append("}");
        return result.toString();
    }
}
