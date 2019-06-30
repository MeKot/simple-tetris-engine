package te.tetris.core.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Function;
import java.util.stream.Stream;

public class InputOutputFileMapper {

    private final File inputFile;
    private final File outputFile;

    public InputOutputFileMapper(File inputFile, File outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    /**
     * Calls the provided function on every line of the input, synchronously, and writes
     * the result of it to the output file, in-order.
     *
     * @param inputToOutputMapper the function that maps an input line to an output line
     * @throws IOException if an exception occurs while reading from input file or writing to output file
     */
    public void mapAllInputToOutput(Function<String, String> inputToOutputMapper) throws IOException {
        try (BufferedWriter outputFileWriter = Files.newBufferedWriter(outputFile.toPath())) {
            try (Stream<String> inputLines = Files.lines(inputFile.toPath())) {
                inputLines
                        .map(inputToOutputMapper)
                        .forEach(outputLine -> writeLine(outputLine, outputFileWriter));
            }
        }
    }

    private void writeLine(String line, BufferedWriter fileWriter) {
        try {
            fileWriter.write(line + System.lineSeparator());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
