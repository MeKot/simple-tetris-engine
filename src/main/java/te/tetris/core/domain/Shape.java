package te.tetris.core.domain;

import java.util.StringJoiner;

/**
 * Represents the kinds of tetris shapes that can be parsed from the command line and the dimensions
 * of how much space they take up on our tetris grid.
 *
 * For example: a {@link #Q} has the dimensions [[0,1],[0,1]] because they take up 4 places on
 * the grid in a square formation:
 * <pre>
 *     □ □
 *     □ □
 * </pre>
 */
public enum Shape {
    /**
     * <pre>
     *      □ □
     *      □ □
     * </pre>
     */
    Q("Q", new int[][]{new int[]{0, 1}, new int[]{0, 1}}),

    /**
     * <pre>
     *      □ □ □ □
     * </pre>
     */
    I("I", new int[][]{new int[]{0, 1, 2, 3},}),

    /**
     * <pre>
     *      □ □
     *        □ □
     * </pre>
     */
    Z("Z", new int[][]{new int[]{0, 1}, new int[]{1, 2}}),

    /**
     * <pre>
     *        □ □
     *      □ □
     * </pre>
     */
    S("S", new int[][]{new int[]{1, 2}, new int[]{0, 1}}),

    /**
     * <pre>
     *      □ □ □
     *        □
     * </pre>
     */
    T("T", new int[][]{new int[]{0, 1, 2}, new int[]{1}}),

    /**
     * <pre>
     *       □
     *       □
     *       □ □
     * </pre>
     */
    L("L", new int[][]{new int[]{0}, new int[]{0}, new int[]{0, 1}}),

    /**
     * <pre>
     *         □
     *         □
     *       □ □
     * </pre>
     */
    J("J", new int[][]{new int[]{1}, new int[]{1}, new int[]{0, 1}});

    private String inputCharacter;
    private int[][] coordinates;

    Shape(String character, int[][] coordinates) {
        this.inputCharacter = character;
        this.coordinates = coordinates;
    }

    public String getInputCharacter() {
        return inputCharacter;
    }

    public int[][] getCoordinates() {
        return coordinates;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Shape.class.getSimpleName() + "(", ")")
                .add("inputCharacter=" + inputCharacter)
                .toString();
    }
}
