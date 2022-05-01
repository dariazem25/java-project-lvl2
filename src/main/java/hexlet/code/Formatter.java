package hexlet.code;

import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.util.List;
import java.util.Map;

public class Formatter {

    public static String format(List<Map<String, Object>> list, String... formatName) {
        if (formatName.length == 0) {
            return Stylish.format(list);
        } else if (formatName[0].equals("stylish")) {
            return Stylish.format(list);
        } else if (formatName[0].equals("plain")) {
            return Plain.format(list);
        }
        throw new RuntimeException("Incorrect format value");
    }
}
