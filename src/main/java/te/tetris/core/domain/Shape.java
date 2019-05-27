package te.tetris.core.domain;

/**
 * Represents the kinds of tetris shapes that can be parsed from the command line and the dimensions
 * of how much space they take up on our tetris grid (written as though they were inserted at
 * position 0).
 *
 * For example: a {@link #Q} has the dimensions [[0,1],[0,1]] because they take up 4 places on the
 * grid in a square formation:
 * <pre>
 *     □ □
 *     □ □
 * </pre>
 */
public enum Shape {
    /**
     * Represents the following tetris shape:
     *
     * <pre>
     *      □ □
     *      □ □
     * </pre>
     */
    Q(new int[][]{new int[]{0, 1}, new int[]{0, 1}}),

    /**
     * Represents the following tetris shape:
     *
     * <pre>
     *      □ □ □ □
     * </pre>
     */
    I(new int[][]{new int[]{0, 1, 2, 3},}),

    /**
     * Represents the following tetris shape:
     *
     * <pre>
     *      □ □
     *        □ □
     * </pre>
     */
    Z(new int[][]{new int[]{0, 1}, new int[]{1, 2}}),

    /**
     * Represents the following tetris shape:
     *
     * <pre>
     *        □ □
     *      □ □
     * </pre>
     */
    S(new int[][]{new int[]{1, 2}, new int[]{0, 1}}),

    /**
     * Represents the following tetris shape:
     *
     * <pre>
     *      □ □ □
     *        □
     * </pre>
     */
    T(new int[][]{new int[]{0, 1, 2}, new int[]{1}}),

    /**
     * Represents the following tetris shape:
     *
     * <pre>
     *       □
     *       □
     *       □ □
     * </pre>
     */
    L(new int[][]{new int[]{0}, new int[]{0}, new int[]{0, 1}}),

    /**
     * Represents the following tetris shape:
     *
     * <pre>
     *         □
     *         □
     *       □ □
     * </pre>
     */
    J(new int[][]{new int[]{1}, new int[]{1}, new int[]{0, 1}});

    private int[][] dimensions;

    Shape(int[][] dimensions) {
        this.dimensions = dimensions;
    }

    public int[][] getDimensions() {
        return dimensions;
    }

    @Override
    public String toString() {
        return name();
    }
}
