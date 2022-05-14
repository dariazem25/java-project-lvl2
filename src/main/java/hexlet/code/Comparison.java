package hexlet.code;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Comparison {

    public static List<Map<String, Object>> compare(Map<String, Object> map1, Map<String, Object> map2) {
        List<Map<String, Object>> result = new ArrayList<>();
        Set<String> setOfKeys = new TreeSet<>(map1.keySet());
        setOfKeys.addAll(map2.keySet());

        for (String key : setOfKeys) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("key", key);
            if (!map2.containsKey(key)) {
                map.put("oldValue", map1.get(key));
                map.put("status", "deleted");
            } else if (!map1.containsKey(key)) {
                map.put("newValue", map2.get(key));
                map.put("status", "added");
            } else if (Objects.equals(map2.get(key), map1.get(key))) {
                map.put("oldValue", map1.get(key));
                map.put("status", "unchanged");
            } else if (!Objects.equals(map2.get(key), map1.get(key))) {
                map.put("oldValue", map1.get(key));
                map.put("newValue", map2.get(key));
                map.put("status", "changed");
            }
            result.add(map);
        }
        return result;
    }
}
