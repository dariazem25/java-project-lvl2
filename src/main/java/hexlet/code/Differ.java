package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Differ {

    public static String generate(Path path1, Path path2) throws Exception {
        String file1 = Files.readString(path1.toAbsolutePath());
        String file2 = Files.readString(path2.toAbsolutePath());
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map1 = objectMapper.readValue(file1, new TypeReference<>() {
        });
        Map<String, Object> map2 = objectMapper.readValue(file2, new TypeReference<>() {
        });

        List<Map<String, Object>> result = new ArrayList<>();
        Set<String> setOfKeys = new TreeSet<>(map1.keySet());
        setOfKeys.addAll(map2.keySet());

        if (map1.isEmpty() && !map2.isEmpty()) {
            Set<String> setMap2 = new TreeSet<>(map2.keySet());
            for (String key : setMap2) {
                result.add(Map.of("key", key, "newValue", map2.get(key), "status", "added"));
            }
        } else if (map2.isEmpty() && !map1.isEmpty()) {
            Set<String> setMap1 = new TreeSet<>(map1.keySet());
            for (String key : setMap1) {
                result.add(Map.of("key", key, "oldValue", map1.get(key), "status", "deleted"));
            }
        } else {
            for (String key : setOfKeys) {
                if (map2.containsKey(key) && map1.containsKey(key) && map2.get(key).equals(map1.get(key))) {
                    result.add(Map.of("key", key, "oldValue", map1.get(key),
                            "newValue", map2.get(key), "status", "unchanged"));
                } else if (map2.containsKey(key) && map1.containsKey(key) && !(map2.get(key).equals(map1.get(key)))) {
                    result.add(Map.of("key", key, "oldValue", map1.get(key),
                            "newValue", map2.get(key), "status", "changed"));
                } else if (!map2.containsKey(key)) {
                    result.add(Map.of("key", key, "oldValue", map1.get(key),
                            "status", "deleted"));
                } else if (!map1.containsKey(key)) {
                    result.add(Map.of("key", key, "newValue", map2.get(key),
                            "status", "added"));
                }
            }
        }
        return toFormat(result);
    }

    private static String toFormat(List<Map<String, Object>> list) {
        StringBuilder result = new StringBuilder();
        result.append("{");
        for (Map<String, Object> map : list) {
            if (map.get("status").equals("unchanged")) {
                result.append("\n").append("\s").append("\s").append("\s")
                        .append(map.get("key")).append(": ").append(map.get("oldValue"));
            } else if (map.get("status").equals("changed")) {
                result.append("\n").append("\s").append("- ").append(map.get("key"))
                        .append(": ").append(map.get("oldValue"));
                result.append("\n").append("\s").append("+ ").append(map.get("key"))
                        .append(": ").append(map.get("newValue"));
            } else if (map.get("status").equals("deleted")) {
                result.append("\n").append("\s").append("- ").append(map.get("key"))
                        .append(": ").append(map.get("oldValue"));
            } else if (map.get("status").equals("added")) {
                result.append("\n").append("\s").append("+ ").append(map.get("key"))
                        .append(": ").append(map.get("newValue"));
            }
        }
        result.append("\n").append("}");
        return result.toString();
    }
}
