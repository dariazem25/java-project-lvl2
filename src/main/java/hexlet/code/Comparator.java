package hexlet.code;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Comparator {

    public static List<Map<String, Object>> compare(Map<String, Object> data1, Map<String, Object> data2) {
        List<Map<String, Object>> result = new ArrayList<>();
        Set<String> setOfKeys = new TreeSet<>(data1.keySet());
        setOfKeys.addAll(data2.keySet());

        for (String key : setOfKeys) {
            Map<String, Object> comparedData = new LinkedHashMap<>();
            comparedData.put("key", key);
            if (!data2.containsKey(key)) {
                comparedData.put("oldValue", data1.get(key));
                comparedData.put("status", "deleted");
            } else if (!data1.containsKey(key)) {
                comparedData.put("newValue", data2.get(key));
                comparedData.put("status", "added");
            } else if (Objects.equals(data2.get(key), data1.get(key))) {
                comparedData.put("oldValue", data1.get(key));
                comparedData.put("status", "unchanged");
            } else if (!Objects.equals(data2.get(key), data1.get(key))) {
                comparedData.put("oldValue", data1.get(key));
                comparedData.put("newValue", data2.get(key));
                comparedData.put("status", "changed");
            }
            result.add(comparedData);
        }
        return result;
    }
}
