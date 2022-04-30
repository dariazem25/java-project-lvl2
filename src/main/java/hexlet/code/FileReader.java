package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReader {

    public static String readFile(Path path) throws IOException {
        return Files.readString(path.toAbsolutePath());
    }

    public static String getExtension(Path path) {
        String fileName = path.toString();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
