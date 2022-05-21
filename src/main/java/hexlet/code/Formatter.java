package hexlet.code;

import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Formatter {

    public static String format(List<Map<String, Object>> comparedData, String formatName) throws IOException {
        if (formatName.equals("stylish")) {
            return Stylish.format(comparedData);
        } else if (formatName.equals("plain")) {
            return Plain.format(comparedData);
        } else if (formatName.equals("json")) {
            return Json.format(comparedData);
        }
        throw new RuntimeException("Incorrect format value");
    }
}
