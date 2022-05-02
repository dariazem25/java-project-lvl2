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
    void sameJsonFilesDefaultFormat() throws Exception {
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
    void sameYamlFilesDefaultFormat() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "file8.yml");
        Path path2 = Paths.get("src", "test", "resources", "file8.yml");
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
    void differentValueTypes() throws Exception {
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
    void emptyJsonFiles() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "file5.json");
        Path path2 = Paths.get("src", "test", "resources", "file5.json");
        String expected = "{" + "\n" + "}";
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void emptyJsonFilesPlainFormat() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "file5.json");
        Path path2 = Paths.get("src", "test", "resources", "file5.json");
        String expected = "The files are the same";
        String actual = Differ.generate(path1, path2, "plain");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void emptyYamlFiles() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "empty.yml");
        Path path2 = Paths.get("src", "test", "resources", "empty.yml");
        String expected = "{" + "\n" + "}";
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void emptyYamlFilesPlainFormat() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "empty.yml");
        Path path2 = Paths.get("src", "test", "resources", "empty.yml");
        String expected = "The files are the same";
        String actual = Differ.generate(path1, path2, "plain");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void firstJsonFileEmpty() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "file5.json");
        Path path2 = Paths.get("src", "test", "resources", "file1.json");
        String expected = """
                {
                 + follow: false
                 + host: hexlet.io
                 + proxy: 123.234.53.22
                 + timeout: 50
                }""";
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void firstJsonFileEmptyPlainFormat() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "file5.json");
        Path path2 = Paths.get("src", "test", "resources", "file1.json");
        String expected = """
                Property 'follow' was added with value: false
                Property 'host' was added with value: 'hexlet.io'
                Property 'proxy' was added with value: '123.234.53.22'
                Property 'timeout' was added with value: 50""";
        String actual = Differ.generate(path1, path2, "plain");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void secondJsonFileEmpty() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "file1.json");
        Path path2 = Paths.get("src", "test", "resources", "file5.json");
        String expected = """
                {
                 - follow: false
                 - host: hexlet.io
                 - proxy: 123.234.53.22
                 - timeout: 50
                }""";
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void secondJsonFileEmptyPlainFormat() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "file1.json");
        Path path2 = Paths.get("src", "test", "resources", "file5.json");
        String expected = """
                Property 'follow' was removed
                Property 'host' was removed
                Property 'proxy' was removed
                Property 'timeout' was removed""";
        String actual = Differ.generate(path1, path2, "plain");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void firstYamlFileEmpty() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "empty.yml");
        Path path2 = Paths.get("src", "test", "resources", "file8.yml");
        String expected = """
                {
                 + follow: false
                 + host: hexlet.io
                 + proxy: 123.234.53.22
                 + timeout: 50
                }""";
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void firstYamlFileEmptyPlainFormat() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "empty.yml");
        Path path2 = Paths.get("src", "test", "resources", "file8.yml");
        String expected = """
                Property 'follow' was added with value: false
                Property 'host' was added with value: 'hexlet.io'
                Property 'proxy' was added with value: '123.234.53.22'
                Property 'timeout' was added with value: 50""";
        String actual = Differ.generate(path1, path2, "plain");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void secondYamlFileEmpty() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "file8.yml");
        Path path2 = Paths.get("src", "test", "resources", "empty.yml");
        String expected = """
                {
                 - follow: false
                 - host: hexlet.io
                 - proxy: 123.234.53.22
                 - timeout: 50
                }""";
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void secondYamlFileEmptyPlainFormat() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "file8.yml");
        Path path2 = Paths.get("src", "test", "resources", "empty.yml");
        String expected = """
                Property 'follow' was removed
                Property 'host' was removed
                Property 'proxy' was removed
                Property 'timeout' was removed""";
        String actual = Differ.generate(path1, path2, "plain");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void incorrectFileStructure() {
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
    void fileAbsent() {
        Path path1 = Paths.get("src", "test", "resources", "file1.json");
        Path path2 = Paths.get("src", "test", "resources", "file8.json");
        assertThrows(NoSuchFileException.class, () -> Differ.generate(path1, path2));
    }

    @Test
    void compareYamlFilesDefaultFormat() throws Exception {
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
    void compareJsonFilesNestedStructures() throws Exception {
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
    void compareYamlFilesNestedStructures() throws Exception {
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

    @Test
    void compareJsonFilesPlainFormat() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "nested1.json");
        Path path2 = Paths.get("src", "test", "resources", "nested2.json");
        String expected = """
                Property 'chars2' was updated. From [complex value] to false
                Property 'checked' was updated. From false to true
                Property 'default' was updated. From null to [complex value]
                Property 'id' was updated. From 45 to null
                Property 'key1' was removed
                Property 'key2' was added with value: 'value2'
                Property 'numbers2' was updated. From [complex value] to [complex value]
                Property 'numbers3' was removed
                Property 'numbers4' was added with value: [complex value]
                Property 'obj1' was added with value: [complex value]
                Property 'setting1' was updated. From 'Some value' to 'Another value'
                Property 'setting2' was updated. From 200 to 300
                Property 'setting3' was updated. From true to 'none'""";
        String actual = Differ.generate(path1, path2, "plain");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void compareYamlFilesPlainFormat() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "nested1.yml");
        Path path2 = Paths.get("src", "test", "resources", "nested2.yml");
        String expected = """
                Property 'chars2' was updated. From [complex value] to false
                Property 'checked' was updated. From false to true
                Property 'default' was updated. From null to [complex value]
                Property 'id' was updated. From 45 to null
                Property 'key1' was removed
                Property 'key2' was added with value: 'value2'
                Property 'numbers2' was updated. From [complex value] to [complex value]
                Property 'numbers3' was removed
                Property 'numbers4' was added with value: [complex value]
                Property 'obj1' was added with value: [complex value]
                Property 'setting1' was updated. From 'Some value' to 'Another value'
                Property 'setting2' was updated. From 200 to 300
                Property 'setting3' was updated. From true to 'none'""";
        String actual = Differ.generate(path1, path2, "plain");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void compareJsonFilesStylishFormat() throws Exception {
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
        String actual = Differ.generate(path1, path2, "stylish");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void compareYamlFilesStylishFormat() throws Exception {
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
        String actual = Differ.generate(path1, path2, "stylish");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void incorrectFormat() {
        Path path1 = Paths.get("src", "test", "resources", "nested1.json");
        Path path2 = Paths.get("src", "test", "resources", "nested2.json");
        assertThrows(RuntimeException.class, () -> Differ.generate(path1, path2, "fff"));
    }

    @Test
    void sameJsonFilesPlainFormat() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "file1.json");
        Path path2 = Paths.get("src", "test", "resources", "file1.json");
        String expected = "The files are the same";
        String actual = Differ.generate(path1, path2, "plain");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void sameYamlFilesPlainFormat() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "file8.yml");
        Path path2 = Paths.get("src", "test", "resources", "file8.yml");
        String expected = "The files are the same";
        String actual = Differ.generate(path1, path2, "plain");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void compareJsonFilesJsonFormat() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "nested1.json");
        Path path2 = Paths.get("src", "test", "resources", "nested2.json");
        String actual = Differ.generate(path1, path2, "json");
        String expected = FileReader.readFile(Paths.get("src", "test", "resources", "jsonFormat.json"));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void compareYamlFilesYamlFormat() throws Exception {
        Path path1 = Paths.get("src", "test", "resources", "nested1.yml");
        Path path2 = Paths.get("src", "test", "resources", "nested2.yml");
        String actual = Differ.generate(path1, path2, "json");
        String expected = FileReader.readFile(Paths.get("src", "test", "resources", "jsonFormat.json"));
        Assertions.assertEquals(expected, actual);
    }
}
