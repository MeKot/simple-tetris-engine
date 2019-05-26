package te.tetris.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Reads from an input file, passes that line to some function for processing, and then writes the
 * result of that function to the output file.
 *
 * The input file could, in theory, be very large so it is streamed in line-by-line rather than read
 * in all at once.  All file handles are closed when the input file is completely processed or when
 * an exception is thrown from within the input processing function.
 */
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

    /**
     * Wrapper necessary to handle any potential exceptions that could occur while writing to the
     * output file.  Had I been able to use external libraries I would have opted for vavr's
     * functional Try class here.
     */
    private void writeToOutputFile(BufferedWriter outputFileWriter, String output) {
        try {
            outputFileWriter.write(output + System.lineSeparator());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
