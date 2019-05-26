package te.tetris;

import java.io.File;
import java.io.IOException;
import java.util.List;

import te.tetris.core.InputOutputFileHandler;
import te.tetris.core.InputToTetrisPieceTransformer;
import te.tetris.core.TetrisEngine;
import te.tetris.core.domain.TetrisPiece;

public class TetrisApplication {

    public static void main(String[] args) throws IOException {
        new TetrisApplication(new File(args[0]), new File(args[1])).run();
    }

    private final InputOutputFileHandler fileHandler;
    private final InputToTetrisPieceTransformer inputTransformer;
    private final TetrisEngine tetrisEngine;

    public TetrisApplication(File inputFile, File outputFile) {
        this.fileHandler = new InputOutputFileHandler(inputFile, outputFile);
        this.inputTransformer = new InputToTetrisPieceTransformer();
        this.tetrisEngine = new TetrisEngine();
    }

    public void run() throws IOException {
        fileHandler.eachLineOfInput(inputLine -> {
            List<TetrisPiece> pieces = inputTransformer.transform(inputLine);
            int gridHeight = tetrisEngine.runSimulationThenFindResultingGridHeight(pieces);
            return String.valueOf(gridHeight);
        });
    }

}
