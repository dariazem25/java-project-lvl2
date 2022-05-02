package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class Parser {

    public static Map<String, Object> parse(Path path) throws Exception {
        String file = FileReader.readFile(path);
        ObjectMapper mapper;
        String extension = FileReader.getExtension(path);

        if (extension.equals("json")) {
            mapper = new ObjectMapper();
        } else if (extension.equals("yml")) {
            mapper = new ObjectMapper(new YAMLFactory());
        } else {
            throw new RuntimeException("Invalid or unsupported extension");
        }
        return !file.isEmpty() ? mapper.readValue(file, new TypeReference<>() {
        }) : new LinkedHashMap<>();
    }
}
