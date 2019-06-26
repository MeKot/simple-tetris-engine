package te.tetris.core;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import te.tetris.core.domain.TetrisPiece;

/**
 * Stateless simulator of {@link TetrisPiece}s being dropped into a grid from a particular position
 * at the top, returning the resulting grid after all pieces have been dropped in.
 *
 * <p></p>Uses a {@link LinkedList} to store the tetris grid and <code>boolean[]</code>s to
 * represent the lines of the grid.  The {@link LinkedList} was chosen to minimize memory overhead,
 * to simplify growing the grid, and to simplify shrinking the grid (i.e. line clears).  The last
 * case in particular will surpass arrays/lists in runtime as the grid grows sufficiently large
 * since no shifting of the grid is required when removing line clears.
 */
public class TetrisGridGenerator {
    private int gridWidth;
    private TetrisPieceCoordinatesGenerator coordinatesGenerator;

    public TetrisGridGenerator() {
        this(10);
    }

    public TetrisGridGenerator(int gridWidth) {
        this.gridWidth = gridWidth;
        this.coordinatesGenerator = new TetrisPieceCoordinatesGenerator();
    }

    public LinkedList<boolean[]> generate(List<TetrisPiece> pieces) {
        LinkedList<boolean[]> grid = new LinkedList<>();

        for (TetrisPiece piece : pieces) {
            int[][] pieceCoordinates = coordinatesGenerator.generate(piece);

            ListIterator<boolean[]> gridIterator = grid.listIterator();

            moveToPositionToInsertPiece(gridIterator, pieceCoordinates);
            insertPiece(gridIterator, pieceCoordinates);
            handleLineClears(gridIterator, pieceCoordinates);
        }

        return grid;
    }

    /**
     * Finds the position to insert the next piece by traversing down the grid line-by-line,
     * comparing all the next piece's coordinates to the current line and halting if any coordinates
     * are already set (i.e. a block is already present there).
     *
     * Since rotation isn't allowed in this simulation checking whether a piece fits in a given
     * space reduces to checking whether any coordinate of the piece overlaps with the current line.
     * This logic results in either landing on the correct position for the next piece or
     * overshooting by one, which we then simply backup a line to correct.
     */
    private void moveToPositionToInsertPiece(ListIterator<boolean[]> gridIterator, int[][] pieceCoordinates) {
        boolean atNextPosition = false;

        while (gridIterator.hasNext() && !atNextPosition) {
            boolean[] line = gridIterator.next();

            for (int[] lineCoordinates : pieceCoordinates) {
                if (isOverlappingExistingPiece(line, lineCoordinates)) {
                    atNextPosition = true;
                    break;
                }
            }

            if (atNextPosition && isOneLineTooFarDown(pieceCoordinates, line)) {
                gridIterator.previous();
            }
        }
    }

    private boolean isOneLineTooFarDown(int[][] pieceCoordinates, boolean[] line) {
        int[] bottomCoordinatesOfPiece = pieceCoordinates[pieceCoordinates.length - 1];
        return isOverlappingExistingPiece(line, bottomCoordinatesOfPiece);
    }

    /**
     * Inserts a piece backwards, starting from where the <code>gridIterator</code> is at and moving
     * backwards. If the grid does not contain the necessary space to insert the piece empty lines
     * are added to the top of it.
     */
    private void insertPiece(ListIterator<boolean[]> gridIterator, int[][] pieceCoordinates) {
        int coordinateIndex = pieceCoordinates.length - 1;

        while (coordinateIndex >= 0) {
            if (gridIterator.hasPrevious()) {
                insertPieceBackwards(gridIterator, pieceCoordinates[coordinateIndex--]);
            } else {
                gridIterator.add(new boolean[gridWidth]);
            }
        }
    }

    private void insertPieceBackwards(ListIterator<boolean[]> gridIterator, int[] coordinatesToSet) {
        boolean[] line = gridIterator.previous();

        for (int coordinate : coordinatesToSet) {
            line[coordinate] = true;
        }
    }

    /**
     * @return true if any of the provided coordinates in `line` are already set to true, false
     * otherwise.
     */
    private boolean isOverlappingExistingPiece(boolean[] line, int[] coordinates) {
        for (int coordinate : coordinates) {
            if (line[coordinate]) return true;
        }

        return false;
    }

    /**
     * Revisits the lines of the grid we just inserted our piece into to see if any of those lines
     * are now line-clears (i.e. entire line is set to true).  If they are, they're removed.
     * The complexity of this is effectively O(1).
     */
    private void handleLineClears(ListIterator<boolean[]> gridIterator, int[][] pieceCoordinates) {
        int numRowsToCheck = pieceCoordinates.length;

        while (numRowsToCheck-- > 0) {
            if (isLineClear(gridIterator.next())) {
                gridIterator.remove();
            }
        }
    }

    private boolean isLineClear(boolean[] line) {
        for (boolean position : line) {
            if (!position) return false;
        }

        return true;
    }

}

