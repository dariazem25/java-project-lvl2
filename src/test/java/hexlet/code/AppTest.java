package hexlet.code;

import com.fasterxml.jackson.core.JsonParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppTest {

    @Test
    void partiallyDifferentFiles() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "file1.json");
        Path path2 = Paths.get("src", "test", "resources", "file2.json");
        String expected = "{"
                + "\n" + " - follow: false"
                + "\n" + "   host: hexlet.io"
                + "\n" + " - proxy: 123.234.53.22"
                + "\n" + " - timeout: 50"
                + "\n" + " + timeout: 20"
                + "\n" + " + verbose: true"
                + "\n" + "}";
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void sameFiles() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "file1.json");
        Path path2 = Paths.get("src", "test", "resources", "file1.json");
        String expected = "{"
                + "\n" + "   follow: false"
                + "\n" + "   host: hexlet.io"
                + "\n" + "   proxy: 123.234.53.22"
                + "\n" + "   timeout: 50"
                + "\n" + "}";
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void completelyDifferentFiles() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "file1.json");
        Path path2 = Paths.get("src", "test", "resources", "file3.json");
        String expected = "{"
                + "\n" + " - follow: false"
                + "\n" + " + follow: true"
                + "\n" + " - host: hexlet.io"
                + "\n" + " + host: yandex.io"
                + "\n" + " - proxy: 123.234.53.22"
                + "\n" + " + proxy: 255.255.255.255"
                + "\n" + " - timeout: 50"
                + "\n" + " + timeout: 100"
                + "\n" + "}";
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void differentTypes() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "file2.json");
        Path path2 = Paths.get("src", "test", "resources", "file4.json");
        String expected = "{"
                + "\n" + " - host: hexlet.io"
                + "\n" + " + host: [hexlet.io, hexlet.io]"
                + "\n" + "   timeout: 20"
                + "\n" + "   verbose: true"
                + "\n" + "}";
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void emptyFiles() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "file5.json");
        Path path2 = Paths.get("src", "test", "resources", "file5.json");
        String expected = "{" + "\n" + "}";
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void incorrectFormat() {
        Path path1 = Paths.get("src", "test", "resources", "file2.json");
        Path path2 = Paths.get("src", "test", "resources", "file6.json");
        assertThrows(JsonParseException.class, () -> Differ.generate(path1, path2));
    }

    @Test
    void differentExtensions() {
        Path path1 = Paths.get("src", "test", "resources", "file1.json");
        Path path2 = Paths.get("src", "test", "resources", "file7.txt");
        assertThrows(RuntimeException.class, () -> Differ.generate(path1, path2));
    }

    @Test
    void fileIsAbsent() {
        Path path1 = Paths.get("src", "test", "resources", "file1.json");
        Path path2 = Paths.get("src", "test", "resources", "file8.json");
        assertThrows(NoSuchFileException.class, () -> Differ.generate(path1, path2));
    }

    @Test
    void partiallyDifferentFilesYaml() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "file8.yml");
        Path path2 = Paths.get("src", "test", "resources", "file9.yml");
        String expected = "{"
                + "\n" + " - follow: false"
                + "\n" + "   host: hexlet.io"
                + "\n" + " - proxy: 123.234.53.22"
                + "\n" + " - timeout: 50"
                + "\n" + " + timeout: 20"
                + "\n" + " + verbose: true"
                + "\n" + "}";
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }
}
