package hexlet.code;

import picocli.CommandLine;

@CommandLine.Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App {
    @CommandLine.Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format = "";

    @CommandLine.Parameters(description = "path to first file")
    private String filepath1;

    @CommandLine.Parameters(description = "path to second file")
    private String filepath2;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
