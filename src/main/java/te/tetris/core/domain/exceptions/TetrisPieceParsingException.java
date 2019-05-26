package te.tetris.core.domain.exceptions;

import te.tetris.core.domain.TetrisShape;
import te.tetris.core.domain.TetrisPiece;

/**
 * Represents a line from input that could not be parsed into an instance of {@link TetrisPiece}.
 *
 * According to the constraints of this assignment it should not be possible for this exception to
 * occur so it extends {@link RuntimeException} to please the type system and simplify it's use in
 * {@link te.tetris.core.InputToTetrisPieceTransformer}.
 */
public class TetrisPieceParsingException extends RuntimeException {

    public TetrisPieceParsingException(char input) {
        super("Could not find a value in " + TetrisShape.class.getSimpleName() + " that corresponds to '" + input + "'.");
    }

    public TetrisPieceParsingException() {
        super("Encountered input that was not long enough to parse into an instance of " + TetrisPiece.class.getSimpleName());
    }

}
