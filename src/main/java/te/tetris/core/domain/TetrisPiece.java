package te.tetris.core.domain;

import java.util.StringJoiner;

/**
 * Represents a {@link Shape} and a position at which said shape will be dropped into the
 * tetris grid.
 */
public class TetrisPiece {
    private final Shape shape;
    private final int position;

    public TetrisPiece(Shape shape, int position) {
        this.shape = shape;
        this.position = position;
    }

    public Shape getShape() {
        return shape;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TetrisPiece)) return false;
        TetrisPiece that = (TetrisPiece) o;
        return this.position == that.position && this.shape == that.shape;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TetrisPiece.class.getSimpleName() + "(", ")")
                .add("shape=" + shape)
                .add("position=" + position)
                .toString();
    }
}
