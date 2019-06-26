package te.tetris.core;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import te.tetris.core.domain.TetrisPiece;
import te.tetris.core.io.InputOutputFileHandler;
import te.tetris.core.io.InputToTetrisPieceTransformer;

public class TetrisEngine {
    private final InputToTetrisPieceTransformer inputTransformer;
    private final InputOutputFileHandler fileHandler;
    private final TetrisGridGenerator gridGenerator;

    public TetrisEngine(File inputFile, File outputFile) {
        this.fileHandler = new InputOutputFileHandler(inputFile, outputFile);
        this.inputTransformer = new InputToTetrisPieceTransformer();
        this.gridGenerator = new TetrisGridGenerator();
    }

    public void run() throws IOException {
        fileHandler.processLineFromInput(inputLine -> {
            List<TetrisPiece> pieces = inputTransformer.transform(inputLine);

            LinkedList<boolean[]> grid = gridGenerator.generate(pieces);

            return String.valueOf(grid.size());
        });
    }

}