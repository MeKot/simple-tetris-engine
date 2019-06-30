package te.tetris.core;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import te.tetris.core.domain.TetrisPiece;
import te.tetris.core.io.InputOutputFileMapper;
import te.tetris.core.io.InputToTetrisPieceTransformer;

public class TetrisEngine {
    private InputToTetrisPieceTransformer inputTransformer;
    private InputOutputFileMapper inputOutputFileMapper;
    private TetrisGridGenerator gridGenerator;

    public TetrisEngine(File inputFile, File outputFile) {
        this.inputOutputFileMapper = new InputOutputFileMapper(inputFile, outputFile);
        this.inputTransformer = new InputToTetrisPieceTransformer();
        this.gridGenerator = new TetrisGridGenerator();
    }

    public void run() throws IOException {
        inputOutputFileMapper.mapAllInputToOutput(inputLine -> {
            List<TetrisPiece> pieces = inputTransformer.transform(inputLine);
            LinkedList<boolean[]> grid = gridGenerator.generate(pieces);

            return String.valueOf(grid.size());
        });
    }
}