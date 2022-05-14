package hexlet.code;

import java.util.Map;

public class Differ {

    public static String generate(String path1, String path2, String formatName) throws Exception {
        String data1 = FileReader.readFile(path1);
        String data2 = FileReader.readFile(path2);

        String extension1 = FileReader.getExtension(path1);
        String extension2 = FileReader.getExtension(path2);

        Map<String, Object> map1 = Parser.parse(data1, extension1);
        Map<String, Object> map2 = Parser.parse(data2, extension2);
        return Formatter.format(Comparator.compare(map1, map2), formatName);
    }
}
