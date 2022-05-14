package hexlet.code;

import com.fasterxml.jackson.core.JsonParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.NoSuchFileException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppTest {

    private final String stylish = "stylish";
    private final String plain = "plain";
    private final String json = "json";

    @Test
    void partiallyDifferentFiles() throws Exception {
        String path1 = "src/test/resources/file1.json";
        String path2 = "src/test/resources/file2.json";
        String expected = FileReader.readFile("src/test/resources/expected/expected1.txt");
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void sameJsonFilesDefaultFormat() throws Exception {
        String path = "src/test/resources/file1.json";
        String expected = FileReader.readFile("src/test/resources/expected/expected2.txt");
        String actual = Differ.generate(path, path);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void sameYamlFilesDefaultFormat() throws Exception {
        String path = "src/test/resources/file8.yml";
        String expected = FileReader.readFile("src/test/resources/expected/expected2.txt");
        String actual = Differ.generate(path, path);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void completelyDifferentFiles() throws Exception {
        String path1 = "src/test/resources/file1.json";
        String path2 = "src/test/resources/file3.json";
        String expected = FileReader.readFile("src/test/resources/expected/expected3.txt");
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void differentValueTypes() throws Exception {
        String path1 = "src/test/resources/file2.json";
        String path2 = "src/test/resources/file4.json";
        String expected = FileReader.readFile("src/test/resources/expected/expected4.txt");
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void emptyJsonFiles() throws Exception {
        String path = "src/test/resources/file5.json";
        String expected = "{" + "\n" + "}";
        String actual = Differ.generate(path, path);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void emptyJsonFilesPlainFormat() throws Exception {
        String path = "src/test/resources/file5.json";
        String expected = "The files are the same";
        String actual = Differ.generate(path, path, plain);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void emptyYamlFiles() throws Exception {
        String path = "src/test/resources/empty.yml";
        String expected = "{" + "\n" + "}";
        String actual = Differ.generate(path, path);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void emptyYamlFilesPlainFormat() throws Exception {
        String path = "src/test/resources/empty.yml";
        String expected = "The files are the same";
        String actual = Differ.generate(path, path, plain);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void firstJsonFileEmpty() throws Exception {
        String path1 = "src/test/resources/file5.json";
        String path2 = "src/test/resources/file1.json";
        String expected = FileReader.readFile("src/test/resources/expected/expected5.txt");
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void firstJsonFileEmptyPlainFormat() throws Exception {
        String path1 = "src/test/resources/file5.json";
        String path2 = "src/test/resources/file1.json";
        String expected = FileReader.readFile("src/test/resources/expected/expected6.txt");
        String actual = Differ.generate(path1, path2, plain);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void secondJsonFileEmpty() throws Exception {
        String path1 = "src/test/resources/file1.json";
        String path2 = "src/test/resources/file5.json";
        String expected = FileReader.readFile("src/test/resources/expected/expected7.txt");
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void secondJsonFileEmptyPlainFormat() throws Exception {
        String path1 = "src/test/resources/file1.json";
        String path2 = "src/test/resources/file5.json";
        String expected = FileReader.readFile("src/test/resources/expected/expected8.txt");
        String actual = Differ.generate(path1, path2, plain);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void firstYamlFileEmpty() throws Exception {
        String path1 = "src/test/resources/empty.yml";
        String path2 = "src/test/resources/file8.yml";
        String expected = FileReader.readFile("src/test/resources/expected/expected5.txt");
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void firstYamlFileEmptyPlainFormat() throws Exception {
        String path1 = "src/test/resources/empty.yml";
        String path2 = "src/test/resources/file8.yml";
        String expected = FileReader.readFile("src/test/resources/expected/expected6.txt");
        String actual = Differ.generate(path1, path2, plain);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void secondYamlFileEmpty() throws Exception {
        String path1 = "src/test/resources/file8.yml";
        String path2 = "src/test/resources/empty.yml";
        String expected = FileReader.readFile("src/test/resources/expected/expected7.txt");
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void secondYamlFileEmptyPlainFormat() throws Exception {
        String path1 = "src/test/resources/file8.yml";
        String path2 = "src/test/resources/empty.yml";
        String expected = FileReader.readFile("src/test/resources/expected/expected8.txt");
        String actual = Differ.generate(path1, path2, plain);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void incorrectFileStructure() {
        String path1 = "src/test/resources/file2.json";
        String path2 = "src/test/resources/file6.json";
        assertThrows(JsonParseException.class, () -> Differ.generate(path1, path2));
    }

    @Test
    void differentExtensions() {
        String path1 = "src/test/resources/file1.json";
        String path2 = "src/test/resources/file7.txt";
        assertThrows(RuntimeException.class, () -> Differ.generate(path1, path2));
    }

    @Test
    void fileAbsent() {
        String path1 = "src/test/resources/file1.json";
        String path2 = "src/test/resources/file8.json";
        assertThrows(NoSuchFileException.class, () -> Differ.generate(path1, path2));
    }

    @Test
    void compareYamlFilesDefaultFormat() throws Exception {
        String path1 = "src/test/resources/file8.yml";
        String path2 = "src/test/resources/file9.yml";
        String expected = FileReader.readFile("src/test/resources/expected/expected1.txt");
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void compareJsonFilesNestedStructures() throws Exception {
        String path1 = "src/test/resources/nested1.json";
        String path2 = "src/test/resources/nested2.json";
        String expected = FileReader.readFile("src/test/resources/expected/expected9.txt");
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void compareYamlFilesNestedStructures() throws Exception {
        String path1 = "src/test/resources/nested1.yml";
        String path2 = "src/test/resources/nested2.yml";
        String expected = FileReader.readFile("src/test/resources/expected/expected9.txt");
        String actual = Differ.generate(path1, path2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void compareJsonFilesPlainFormat() throws Exception {
        String path1 = "src/test/resources/nested1.json";
        String path2 = "src/test/resources/nested2.json";
        String expected = FileReader.readFile("src/test/resources/expected/expected10.txt");
        String actual = Differ.generate(path1, path2, plain);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void compareYamlFilesPlainFormat() throws Exception {
        String path1 = "src/test/resources/nested1.yml";
        String path2 = "src/test/resources/nested2.yml";
        String expected = FileReader.readFile("src/test/resources/expected/expected10.txt");
        String actual = Differ.generate(path1, path2, plain);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void compareJsonFilesStylishFormat() throws Exception {
        String path1 = "src/test/resources/nested1.json";
        String path2 = "src/test/resources/nested2.json";
        String expected = FileReader.readFile("src/test/resources/expected/expected9.txt");
        String actual = Differ.generate(path1, path2, stylish);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void compareYamlFilesStylishFormat() throws Exception {
        String path1 = "src/test/resources/nested1.yml";
        String path2 = "src/test/resources/nested2.yml";
        String expected = FileReader.readFile("src/test/resources/expected/expected9.txt");
        String actual = Differ.generate(path1, path2, stylish);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void incorrectFormat() {
        String path1 = "src/test/resources/nested1.json";
        String path2 = "src/test/resources/nested2.json";
        assertThrows(RuntimeException.class, () -> Differ.generate(path1, path2, "fff"));
    }

    @Test
    void sameJsonFilesPlainFormat() throws Exception {
        String path = "src/test/resources/file1.json";
        String expected = "The files are the same";
        String actual = Differ.generate(path, path, plain);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void sameYamlFilesPlainFormat() throws Exception {
        String path = "src/test/resources/file8.yml";
        String expected = "The files are the same";
        String actual = Differ.generate(path, path, plain);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void compareJsonFilesJsonFormat() throws Exception {
        String path1 = "src/test/resources/nested1.json";
        String path2 = "src/test/resources/nested2.json";
        String actual = Differ.generate(path1, path2, json);
        String expected = FileReader.readFile("src/test/resources/jsonFormat.json");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void compareYamlFilesYamlFormat() throws Exception {
        String path1 = "src/test/resources/nested1.yml";
        String path2 = "src/test/resources/nested2.yml";
        String actual = Differ.generate(path1, path2, json);
        String expected = FileReader.readFile("src/test/resources/jsonFormat.json");
        Assertions.assertEquals(expected, actual);
    }
}
