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
        String expected = """
                {
                 - follow: false
                   host: hexlet.io
                 - proxy: 123.234.53.22
                 - timeout: 50
                 + timeout: 20
                 + verbose: true
                }""";
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void sameFiles() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "file1.json");
        Path path2 = Paths.get("src", "test", "resources", "file1.json");
        String expected = """
                {
                   follow: false
                   host: hexlet.io
                   proxy: 123.234.53.22
                   timeout: 50
                }""";
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void completelyDifferentFiles() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "file1.json");
        Path path2 = Paths.get("src", "test", "resources", "file3.json");
        String expected = """
                {
                 - follow: false
                 + follow: true
                 - host: hexlet.io
                 + host: yandex.io
                 - proxy: 123.234.53.22
                 + proxy: 255.255.255.255
                 - timeout: 50
                 + timeout: 100
                }""";
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void differentTypes() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "file2.json");
        Path path2 = Paths.get("src", "test", "resources", "file4.json");
        String expected = """
                {
                 - host: hexlet.io
                 + host: [hexlet.io, hexlet.io]
                   timeout: 20
                   verbose: true
                }""";
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
    void compareYamlFiles() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "file8.yml");
        Path path2 = Paths.get("src", "test", "resources", "file9.yml");
        String expected = """
                {
                 - follow: false
                   host: hexlet.io
                 - proxy: 123.234.53.22
                 - timeout: 50
                 + timeout: 20
                 + verbose: true
                }""";
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void compareJsonFilesNested() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "nested1.json");
        Path path2 = Paths.get("src", "test", "resources", "nested2.json");
        String expected = """
                {
                   chars1: [a, b, c]
                 - chars2: [d, e, f]
                 + chars2: false
                 - checked: false
                 + checked: true
                 - default: null
                 + default: [value1, value2]
                 - id: 45
                 + id: null
                 - key1: value1
                 + key2: value2
                   numbers1: [1, 2, 3, 4]
                 - numbers2: [2, 3, 4, 5]
                 + numbers2: [22, 33, 44, 55]
                 - numbers3: [3, 4, 5]
                 + numbers4: [4, 5, 6]
                 + obj1: {nestedKey=value, isNested=true}
                 - setting1: Some value
                 + setting1: Another value
                 - setting2: 200
                 + setting2: 300
                 - setting3: true
                 + setting3: none
                }""";
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void compareYamlFilesNested() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "nested1.yml");
        Path path2 = Paths.get("src", "test", "resources", "nested2.yml");
        String expected = """
                {
                   chars1: [a, b, c]
                 - chars2: [d, e, f]
                 + chars2: false
                 - checked: false
                 + checked: true
                 - default: null
                 + default: [value1, value2]
                 - id: 45
                 + id: null
                 - key1: value1
                 + key2: value2
                   numbers1: [1, 2, 3, 4]
                 - numbers2: [2, 3, 4, 5]
                 + numbers2: [22, 33, 44, 55]
                 - numbers3: [3, 4, 5]
                 + numbers4: [4, 5, 6]
                 + obj1: {nestedKey=value, isNested=true}
                 - setting1: Some value
                 + setting1: Another value
                 - setting2: 200
                 + setting2: 300
                 - setting3: true
                 + setting3: none
                }""";
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }
}