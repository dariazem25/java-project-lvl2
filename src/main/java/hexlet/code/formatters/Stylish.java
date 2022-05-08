package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class Stylish {

    public static String format(List<Map<String, Object>> list) {
        StringBuilder result = new StringBuilder();
        result.append("{");
        for (Map<String, Object> map : list) {
            if (map.get("status").equals("unchanged")) {
                result.append("\n\s\s\s\s").append(map.get("key")).append(": ").append(map.get("oldValue"));
            } else if (map.get("status").equals("changed")) {
                result.append("\n\s\s- ").append(map.get("key")).append(": ").append(map.get("oldValue"));
                result.append("\n\s\s+ ").append(map.get("key")).append(": ").append(map.get("newValue"));
            } else if (map.get("status").equals("deleted")) {
                result.append("\n\s\s- ").append(map.get("key")).append(": ").append(map.get("oldValue"));
            } else if (map.get("status").equals("added")) {
                result.append("\n\s\s+ ").append(map.get("key")).append(": ").append(map.get("newValue"));
            }
        }
        result.append("\n").append("}");
        return result.toString();
    }
}
