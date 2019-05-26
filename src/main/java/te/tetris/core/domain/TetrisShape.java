package te.tetris.core.domain;

import java.util.StringJoiner;

/**
 * Represents the kinds of tetris shapes that can be parsed from the command line.
 *
 * @see <a href="https://tetris.fandom.com/wiki/Tetromino">Official Tetris piece names</a>
 */
public enum TetrisShape {
    SQUARE('Q'),
    LINE('I'),
    Z('Z'),
    S('S'),
    T('T'),
    L('L'),
    J('J');

    private char inputCharacter;

    TetrisShape(char character) {
        this.inputCharacter = character;
    }

    public char getInputCharacter() {
        return inputCharacter;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TetrisShape.class.getSimpleName() + "(", ")")
                .add("inputCharacter=" + inputCharacter)
                .toString();
    }
}
