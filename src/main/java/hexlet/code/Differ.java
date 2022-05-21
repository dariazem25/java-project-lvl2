package hexlet.code;

import java.util.Map;

public class Differ {
    private static final String STYLISH = "stylish";

    public static String generate(String path1, String path2, String formatName) throws Exception {
        String data1 = FileReader.readFile(path1);
        String data2 = FileReader.readFile(path2);

        String extension1 = FileReader.getExtension(path1);
        String extension2 = FileReader.getExtension(path2);

        Map<String, Object> parsedData1 = Parser.parse(data1, extension1);
        Map<String, Object> parsedData2 = Parser.parse(data2, extension2);
        return Formatter.format(Comparator.compare(parsedData1, parsedData2), formatName);
    }

    public static String generate(String filePath1, String filePath2) throws Exception {
        return generate(filePath1, filePath2, STYLISH);
    }
}
