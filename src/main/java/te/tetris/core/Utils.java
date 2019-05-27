package te.tetris.core;

import java.util.LinkedList;
import java.util.List;

public final class Utils {
    private static final char TETRIS_BLOCK = 9_633;

    private Utils() {}

    public static void printTetrisGrid(LinkedList<boolean[]> grid) {
        System.out.println();

        grid.forEach( line -> {
            StringBuilder rowAsString = new StringBuilder("| ");
            for (boolean value : line) {
                rowAsString.append(value ? TETRIS_BLOCK : " ")
                        .append(" ");
            }
            System.out.println(rowAsString.append("|").toString());
        });

        // Indexes of grid positions for easy visualization
        System.out.println("  -------------------  ");
        System.out.println("  0 1 2 3 4 5 6 7 8 9\n");
    }

}
