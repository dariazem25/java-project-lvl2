package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public class Parser {

    public static Map<String, Object> parse(String data, String type) throws Exception {
        ObjectMapper mapper;

        if (type.equals("json")) {
            mapper = new ObjectMapper();
        } else if (type.equals("yml")) {
            mapper = new ObjectMapper(new YAMLFactory());
        } else {
            throw new RuntimeException("Invalid or unsupported extension");
        }
        return !data.isEmpty() ? mapper.readValue(data, new TypeReference<>() {
        }) : new LinkedHashMap<>();
    }
}
