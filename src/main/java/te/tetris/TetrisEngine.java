package te.tetris;

import java.io.File;
import java.io.IOException;

public class TetrisEngine {

    private final InputOutputFileHandler fileHandler;

    public TetrisEngine(File inputFile, File outputFile) {
        this.fileHandler = new InputOutputFileHandler(inputFile, outputFile);
    }

    public void run() throws IOException {
        fileHandler.eachLineOfInput(this::processInputFromFile);
    }

    protected String processInputFromFile(String line) {
        return line;
    }
}
