package te.tetris.core;

import te.tetris.core.domain.Shape;
import te.tetris.core.domain.TetrisPiece;

/**
 * Generates coordinates to insert a Tetris piece at.
 */
public class TetrisPieceCoordinatesGenerator {

    /**
     * Converts the dimensions of a Tetris piece to the coordinates to insert the piece into the
     * grid at by returning a copy of the dimensions all shifted over by the value of
     * {@link TetrisPiece#getPosition()}.
     * <br/><br/>
     *
     * For example a {@link Shape#Q} at position 0 has the dimensions [0, 1] on the first row and
     * [0, 1] for the second row.  In this case the dimensions of the piece are already the
     * coordinates. When inserted into the grid they would appear as:
     *
     * <pre>
     *        □ □
     *        □ □
     *        -------------------
     *        0 1 2 3 4 5 6 7 8 9
     * </pre>
     *
     * However a {@link Shape#Q} at position 3 has the same dimensions but the coordinates would be
     * adjusted by 3 so they would result in [3, 4] for the first row and [3, 4] for the second row,
     * which would appear on the grid as:
     *
     * <pre>
     *              □ □
     *              □ □
     *        -------------------
     *        0 1 2 3 4 5 6 7 8 9
     * </pre>
     */
    public int[][] generate(TetrisPiece piece) {
        int[][] dimensions = piece.getShape().getDimensions();
        int[][] coordinates = new int[dimensions.length][];

        for (int row = 0; row < dimensions.length; row++) {
            coordinates[row] = new int[dimensions[row].length];

            for (int col = 0; col < dimensions[row].length; col++) {
                coordinates[row][col] = dimensions[row][col] + piece.getPosition();
            }
        }

        return coordinates;
    }

}
