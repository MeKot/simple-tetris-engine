package te.tetris;

import java.io.File;
import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        File inputFile = new File(args[0]);
        File outputFile = new File(args[1]);

        new TetrisEngine(inputFile, outputFile).run();
    }

}
