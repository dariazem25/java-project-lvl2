package hexlet.code;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Differ {

    public static String generate(Path path1, Path path2) throws Exception {
        Map<String, Object> map1 = Parser.parse(path1);
        Map<String, Object> map2 = Parser.parse(path2);
        List<Map<String, Object>> result = new ArrayList<>();
        Set<String> setOfKeys = new TreeSet<>(map1.keySet());
        setOfKeys.addAll(map2.keySet());

        if (map1.isEmpty() && !map2.isEmpty()) {
            Set<String> setMap2 = new TreeSet<>(map2.keySet());
            for (String key : setMap2) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("key", key);
                map.put("newValue", map2.get(key));
                map.put("status", "added");
                result.add(map);
            }
        } else if (map2.isEmpty() && !map1.isEmpty()) {
            Set<String> setMap1 = new TreeSet<>(map1.keySet());
            for (String key : setMap1) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("key", key);
                map.put("oldValue", map1.get(key));
                map.put("status", "deleted");
                result.add(map);
            }
        } else {
            for (String key : setOfKeys) {
                Map<String, Object> map = new LinkedHashMap<>();
                if (map2.containsKey(key) && map1.containsKey(key) && Objects.equals(map2.get(key), map1.get(key))) {
                    map.put("key", key);
                    map.put("oldValue", map1.get(key));
                    map.put("newValue", map2.get(key));
                    map.put("status", "unchanged");
                } else if (map2.containsKey(key) && map1.containsKey(key)
                        && !(Objects.equals(map2.get(key), map1.get(key)))) {
                    map.put("key", key);
                    map.put("oldValue", map1.get(key));
                    map.put("newValue", map2.get(key));
                    map.put("status", "changed");
                } else if (!map2.containsKey(key)) {
                    map.put("key", key);
                    map.put("oldValue", map1.get(key));
                    map.put("status", "deleted");
                } else if (!map1.containsKey(key)) {
                    map.put("key", key);
                    map.put("newValue", map2.get(key));
                    map.put("status", "added");
                }
                result.add(map);
            }
        }
        return stylish(result);
    }

    private static String stylish(List<Map<String, Object>> list) {
        StringBuilder result = new StringBuilder();
        result.append("{");
        for (Map<String, Object> map : list) {
            if (map.get("status").equals("unchanged")) {
                result.append("\n\s\s\s").append(map.get("key")).append(": ").append(map.get("oldValue"));
            } else if (map.get("status").equals("changed")) {
                result.append("\n\s- ").append(map.get("key")).append(": ").append(map.get("oldValue"));
                result.append("\n\s+ ").append(map.get("key")).append(": ").append(map.get("newValue"));
            } else if (map.get("status").equals("deleted")) {
                result.append("\n\s- ").append(map.get("key")).append(": ").append(map.get("oldValue"));
            } else if (map.get("status").equals("added")) {
                result.append("\n\s+ ").append(map.get("key")).append(": ").append(map.get("newValue"));
            }
        }
        result.append("\n").append("}");
        return result.toString();
    }
}
