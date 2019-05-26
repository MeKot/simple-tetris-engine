package te.tetris;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Function;
import java.util.stream.Stream;

public class InputOutputFileHandler {

    private final File inputFile;
    private final File outputFile;

    public InputOutputFileHandler(File inputFile, File outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public void eachLineOfInput(Function<String, String> inputProcessor) throws IOException {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(outputFile))) {
            try (Stream<String> lines = Files.lines(inputFile.toPath())) {
                lines.map(inputProcessor)
                        .forEach(output -> writeToOutputFile(fileWriter, output));
            }
        }
    }

    private void writeToOutputFile(BufferedWriter outputFileWriter, String output) {
        try {
            outputFileWriter.write(output + System.lineSeparator());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
