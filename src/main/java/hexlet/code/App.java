package hexlet.code;

import picocli.CommandLine;

import java.nio.file.Path;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {

    @CommandLine.Option(names = {"-f", "--format"}, paramLabel = "format", description = "output format [default: stylish]")
    private String format = "";

    @CommandLine.Parameters(paramLabel = "filepath1", description = "path to first file")
    private Path filepath1;

    @CommandLine.Parameters(paramLabel = "filepath2", description = "path to second file")
    private Path filepath2;

    @Override
    public Integer call() throws Exception {
        System.out.println(Differ.generate(filepath1, filepath2));
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
