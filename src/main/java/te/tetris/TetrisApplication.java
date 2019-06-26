package te.tetris;

import java.io.File;
import java.io.IOException;

import te.tetris.core.TetrisEngine;

/**
 * Entry point to the application.
 */
public class TetrisApplication {

    public static void main(String[] args) throws IOException {
        // Note: normally I'd validate input here, but that is outside the scope of this assignment.
        File inputFile = new File(args[0]);
        File outputFile = new File(args[1]);

        new TetrisEngine(inputFile, outputFile).run();
    }

}
