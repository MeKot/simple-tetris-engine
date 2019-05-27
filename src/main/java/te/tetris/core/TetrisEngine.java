package te.tetris.core;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import te.tetris.core.domain.Shape;
import te.tetris.core.domain.TetrisPiece;

public class TetrisEngine {
    private static final int GRID_WIDTH = 10;

    public LinkedList<boolean[]> generateResultingGrid(List<TetrisPiece> pieces) {
        LinkedList<boolean[]> grid = new LinkedList<>();

        for (TetrisPiece piece : pieces) {
            int[][] adjustedCoordinates = getAdjustedCoordinates(piece);

            ListIterator<boolean[]> gridIterator = grid.listIterator();
            traverseDownToNextPiecePosition(gridIterator, adjustedCoordinates);
            insertPiece(gridIterator, adjustedCoordinates);
        }

        // It is more efficient (and readable) to process all possible line clears at once
        // in O(n) complexity since removing from a linked list is O(1) complexity.
        grid.removeIf(this::isLineClear);

        Utils.printTetrisGrid(grid);

        return grid;
    }

    //TODO: Determine if this is truly necessary
    private void ensureGridHasEnoughSpaceForPiece(LinkedList<boolean[]> grid, TetrisPiece piece) {
        while (grid.size() < piece.getShape().getCoordinates().length) {
            grid.addFirst(emptyLine());
        }
    }

    /**
     * Finds next piece's position by traversing down the grid line-by-line, comparing all the next
     * piece's coordinates to the current line and halting if any coordinates are already set (i.e.
     * a block is already present there).
     *
     * Most blocks take up more than a single line (Q, Z, S, etc.) so this method merely acts as an
     * approximation of whether we're at the next piece's position or not, and always ends in one of
     * the following three circumstances:
     *
     * <br/>1) We walked all the way down to the bottom of the grid, encountering no other blocks.
     * We can then simply insert the piece in reverse order from where we are back up the grid.
     *
     * <br/>2) We walked down and encountered a block and stopped right before it.  We can then
     * simply insert the piece in reverse order from where we are back up the grid.
     *
     * <br/>3) We walked down and encountered a block and stopped right <b>on</b> it (i.e. we're
     * currently on top of an existing piece). This is the edge case that causes this method to be
     * an approximation, however it only requires stepping backwards up the grid once before
     * beginning the insertion process.  We could handle this situation in this method fully however
     * doing so would duplicate the efforts that the insertion logic puts forth in {@link
     * #insertPiece(ListIterator, int[][])}
     */
    private void traverseDownToNextPiecePosition(ListIterator<boolean[]> gridIterator, int[][] adjustedCoordinates) {
        boolean isApproximatelyAtNextPosition = false;

        while (gridIterator.hasNext() && !isApproximatelyAtNextPosition) {
            boolean[] line = gridIterator.next();

            for (int[] coordinates : adjustedCoordinates) {
                if (isOverlappingExistingPiece(line, coordinates)) {
                    isApproximatelyAtNextPosition = true;
                }
            }
        }
    }

    //TODO: Add documentation
    private void insertPiece(ListIterator<boolean[]> gridIterator, int[][] pieceCoordinates) {
        int coordinateIndex = pieceCoordinates.length - 1;
        boolean isPieceFullyInserted = false;
        boolean isApproximationHandled = false;

        while (!isPieceFullyInserted) {
            if (gridIterator.hasPrevious()) {
                boolean[] line = gridIterator.previous();

                // The first time we iterate over the coordinates of the piece to insert we have to check if
                // we're overlapping with any existing pieces.  All other iterations can ignore this check.
                if (!isApproximationHandled && isOverlappingExistingPiece(line, pieceCoordinates[coordinateIndex])) {
                    isApproximationHandled = true;
                    continue;
                }

                for (int coordinate : pieceCoordinates[coordinateIndex]) {
                    line[coordinate] = true;
                }

                if (--coordinateIndex < 0) {
                    isPieceFullyInserted = true;
                }
            } else {
                gridIterator.add(emptyLine());
            }
        }
    }

    private boolean isOverlappingExistingPiece(boolean[] line, int[] coordinates) {
        for (int coordinate : coordinates) {
            if (line[coordinate]) return true;
        }
        return false;
    }

    /**
     * Returns the coordinates a piece takes up adjusted over by their {@link TetrisPiece#getPosition()}
     * so they represent the correct coordinates to insert them into the grid at.  <br/><br/>
     *
     * For example a {@link Shape#Q} at position 0 will take up [0, 1] for the first row and [0, 1]
     * for the second row, resulting in:
     *
     * <pre>
     *        □ □
     *        □ □
     *        -------------------
     *        0 1 2 3 4 5 6 7 8 9
     * </pre>
     *
     * However a {@link Shape#Q} at position 3 will take up [3, 4] for the first row and [3, 4] for
     * the second row, resulting in:
     *
     * <pre>
     *              □ □
     *              □ □
     *        -------------------
     *        0 1 2 3 4 5 6 7 8 9
     * </pre>
     */
    protected int[][] getAdjustedCoordinates(TetrisPiece piece) {
        int[][] coordinates = piece.getShape().getCoordinates();
        int[][] adjustedCoordinates = new int[coordinates.length][];

        for (int i = 0; i < adjustedCoordinates.length; i++) {
            int[] adjustedCoordinate = Arrays.copyOf(coordinates[i], coordinates[i].length);

            for (int j = 0; j < adjustedCoordinate.length; j++) {
                adjustedCoordinate[j] += piece.getPosition();
            }

            adjustedCoordinates[i] = adjustedCoordinate;
        }

        return adjustedCoordinates;
    }

    private boolean isLineClear(boolean[] line) {
        for (boolean b : line) {
            if (!b) return false;
        }
        return true;
    }

    private boolean[] emptyLine() {
        return new boolean[GRID_WIDTH];
    }

}

