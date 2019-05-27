package te.tetris.core;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import te.tetris.core.domain.Shape;
import te.tetris.core.domain.TetrisPiece;

/**
 * Stateless simulator of {@link TetrisPiece}s being dropped into a grid from a particular position
 * at the top, returning the resulting grid after all pieces have been dropped in.
 *
 * <p></p>Uses a {@link LinkedList} to store the tetris grid and <code>boolean[]</code>s to
 * represent the lines of the grid.  The {@link LinkedList} was chosen to minimize memory overhead,
 * simplify growing the grid, and simplify shrinking the grid (i.e. line clears).  The last case
 * in particular will surpass arrays/lists in runtime as the grid grows sufficiently large since
 * no shifting of the grid is required when removing line clears.
 */
public class TetrisEngine {
    private static final int GRID_WIDTH = 10;

    public LinkedList<boolean[]> generateTetrisGrid(List<TetrisPiece> pieces) {
        LinkedList<boolean[]> grid = new LinkedList<>();

        for (TetrisPiece piece : pieces) {
            int[][] coordinates = convertDimensionsToCoordinates(piece);
            ListIterator<boolean[]> gridIterator = grid.listIterator();

            moveToNextPositionToInsert(gridIterator, coordinates);

            insertPiece(gridIterator, coordinates);
        }

        removeAllLineClears(grid);

        return grid;
    }

    /**
     * Finds the position ti insert the next piece by traversing down the grid line-by-line,
     * comparing all the next piece's coordinates to the current line and halting if any coordinates
     * are already set (i.e. a block is already present there).
     *
     * <p></p> The Algorithm: <br/>
     *
     * Of course we could walk down or up the grid from a particular position to determine whether
     * our piece will fit, however the complexity of that up and down traversal turns out to not be
     * necessary if we instead use an approximation coupled with a final check. <br/>
     *
     * <p></p> The Approximation: <br/>
     *
     * Instead of checking the true coordinates a piece could be positioned at we instead pretend
     * the piece to insert was crushed down to a single line and then check that crushed piece
     * against the current line we are on (e.g. if we are inserting a Z piece we end up checking 4
     * positions on the current line even though, in reality, the Z piece takes up 2 positions on
     * one line and 2 on the other). When we encounter an overlapping piece we know we're
     * approximately at the next piece's position. We then resolve this approximation by checking
     * the bottom most coordinates of the piece to insert.  If those are overlapping we back our
     * iterator up by one line.  If there is no overlap then we know `gridIterator` is already at
     * the position to insert the next piece.
     */
    private void moveToNextPositionToInsert(ListIterator<boolean[]> gridIterator, int[][] peiceCoordinates) {
        boolean isApproximatelyAtNextPosition = false;

        while (gridIterator.hasNext() && !isApproximatelyAtNextPosition) {
            boolean[] line = gridIterator.next();

            for (int[] coordinates : peiceCoordinates) {
                if (isOverlappingExistingPiece(line, coordinates)) {
                    isApproximatelyAtNextPosition = true;
                }
            }

            if (isApproximatelyAtNextPosition) {
                int[] bottomCoordinatesOfPiece = peiceCoordinates[peiceCoordinates.length - 1];
                if (isOverlappingExistingPiece(line, bottomCoordinatesOfPiece)) {
                    gridIterator.previous();
                }
            }
        }
    }

    /**
     * Inserts a piece backwards, starting from where the `gridIterator` is at and moving backwards.
     * If the grid does not contain the necessary space to insert the piece more lines are added at
     * the top.
     */
    private void insertPiece(ListIterator<boolean[]> gridIterator, int[][] pieceCoordinates) {
        int coordinateIndex = pieceCoordinates.length - 1;
        boolean isPieceFullyOnGrid = false;

        while (!isPieceFullyOnGrid) {
            if (gridIterator.hasPrevious()) {
                boolean[] line = gridIterator.previous();

                for (int coordinate : pieceCoordinates[coordinateIndex--]) {
                    line[coordinate] = true;
                }

                if (coordinateIndex < 0) {
                    isPieceFullyOnGrid = true;
                }
            } else {
                gridIterator.add(emptyLine());
            }
        }
    }

    /**
     * @return true if any of the provided coordinates in line are already set to true, false
     * otherwise.
     */
    private boolean isOverlappingExistingPiece(boolean[] line, int[] coordinates) {
        for (int coordinate : coordinates) {
            if (line[coordinate]) return true;
        }
        return false;
    }

    /**
     * Converts the dimensions of a piece to the coordinates to insert the piece into the grid at by
     * shifting each of their values over by the size by {@link TetrisPiece#getPosition()} values.
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
    protected int[][] convertDimensionsToCoordinates(TetrisPiece piece) {
        int[][] dimensions = piece.getShape().getDimensions();
        int[][] coordinates = new int[dimensions.length][];

        for (int i = 0; i < coordinates.length; i++) {
            int[] dimensionsCopy = Arrays.copyOf(dimensions[i], dimensions[i].length);

            for (int j = 0; j < dimensionsCopy.length; j++) {
                dimensionsCopy[j] += piece.getPosition();
            }

            coordinates[i] = dimensionsCopy;
        }

        return coordinates;
    }

    /**
     * Iterates through the entire grid once, removing any lines that are completely full/set.
     *
     * <p></p>Removing elements from a linked list is O(1) complexity so this operation takes O(n).
     */
    private void removeAllLineClears(LinkedList<boolean[]> grid) {
        grid.removeIf( line -> {
            for (boolean position : line) {
                if (!position) return false;
            }

            return true;
        });
    }

    private boolean[] emptyLine() {
        return new boolean[GRID_WIDTH];
    }

}

