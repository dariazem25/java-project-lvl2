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

    public static String generate(Path path1, Path path2, String... formatName) throws Exception {
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
        return Formatter.format(result, formatName);
    }
}
