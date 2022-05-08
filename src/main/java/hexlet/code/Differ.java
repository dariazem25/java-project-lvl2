package hexlet.code;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Differ {

    public static String generate(String path1, String path2, String... formatName) throws Exception {
        String data1 = FileReader.readFile(path1);
        String data2 = FileReader.readFile(path2);

        String extension1 = FileReader.getExtension(path1);
        String extension2 = FileReader.getExtension(path2);

        Map<String, Object> map1 = Parser.parse(data1, extension1);
        Map<String, Object> map2 = Parser.parse(data2, extension2);
        return Formatter.format(compare(map1, map2), formatName);
    }

    private static List<Map<String, Object>> compare(Map<String, Object> map1, Map<String, Object> map2) {
        List<Map<String, Object>> result = new ArrayList<>();
        Set<String> setOfKeys = new TreeSet<>(map1.keySet());
        setOfKeys.addAll(map2.keySet());

        for (String key : setOfKeys) {
            Map<String, Object> map = new LinkedHashMap<>();
            if (map2.containsKey(key) && map1.containsKey(key) && Objects.equals(map2.get(key), map1.get(key))) {
                map.put("key", key);
                map.put("oldValue", map1.get(key));
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
        return result;
    }
}
