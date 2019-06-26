package te.tetris.core.io;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import te.tetris.core.domain.Shape;
import te.tetris.core.domain.TetrisPiece;

/**
 * Transforms a lines of input from a file into {@link List}s of {@link TetrisPiece}s. <br/>
 *
 * Note: validation of input from the file falls outside the scope of this application so it is
 * explicitly ignored. Exceptions will surely be thrown if invalid input is provided.
 */
public class InputToTetrisPieceTransformer {

    public List<TetrisPiece> transform(String inputLine) {
        return Arrays.stream(inputLine.split(","))
                .map(this::toTetrisPiece)
                .collect(Collectors.toList());
    }

    private TetrisPiece toTetrisPiece(String letterAndDigit) {
        return new TetrisPiece(
                Shape.valueOf(letterAndDigit.substring(0, 1)),
                Integer.parseInt(letterAndDigit.substring(1, 2))
        );
    }

}
