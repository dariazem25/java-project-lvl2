package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {

    public static String readFile(String textPath) throws IOException {
        Path path = Paths.get(textPath);
        return Files.readString(path.toAbsolutePath());
    }

    public static String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
