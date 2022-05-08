package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class Plain {

    public static String format(List<Map<String, Object>> list) {
        StringBuilder result = new StringBuilder();
        for (Map<String, Object> map : list) {
            Object oldValue = getValue(map.get("oldValue"));
            Object newValue = getValue(map.get("newValue"));

            if (map.get("status").equals("changed")) {
                getTemplate(result, map.get("key"));
                result.append("' was updated. From ").append(oldValue).append(" to ").append(newValue);
            } else if (map.get("status").equals("deleted")) {
                getTemplate(result, map.get("key"));
                result.append("' was removed");
            } else if (map.get("status").equals("added")) {
                getTemplate(result, map.get("key"));
                result.append("' was added with value: ").append(newValue);
            }
        }
        return result.isEmpty() ? "The files are the same" : result.toString();
    }

    private static void getTemplate(StringBuilder builder, Object key) {
        builder.append(builder.isEmpty() ? "" : "\n").append("Property \'").append(key);
    }

    private static Object getValue(Object value) {
        if (value instanceof List || value instanceof Map) {
            return "[complex value]";
        } else if (value instanceof String) {
            return "'" + value + "'";
        }
        return value;
    }
}
