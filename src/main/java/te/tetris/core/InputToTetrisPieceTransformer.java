package te.tetris.core;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import te.tetris.core.domain.TetrisShape;
import te.tetris.core.domain.TetrisPiece;
import te.tetris.core.domain.exceptions.TetrisPieceParsingException;

/**
 * Transforms a line of input into a {@link List} of type {@link TetrisPiece}.
 *
 * Although the constraints of the assignment specifically state that input validation is
 * unnecessary I could very easily provide incorrect values in tests that could be hard to track
 * down. So for the sake of simplifying testing and completeness I have added a few places where a
 * {@link TetrisPieceParsingException} can be thrown.
 */
public class InputToTetrisPieceTransformer {

    public List<TetrisPiece> transform(String inputLine) {
        if (inputLine.length() < 2) throw new TetrisPieceParsingException();

        return Arrays.stream(inputLine.split(","))
                .map(this::toTetrisPiece)
                .collect(Collectors.toList());
    }

    private TetrisPiece toTetrisPiece(String letterAndDigit) {
        char letter = letterAndDigit.charAt(0);
        char digit = letterAndDigit.charAt(1);

        return new TetrisPiece(parseShape(letter), Character.digit(digit, 10));
    }

    private TetrisShape parseShape(char letter) {
        return Arrays.stream(TetrisShape.values())
                .filter(s -> s.getInputCharacter() == letter)
                .findFirst()
                .orElseThrow(() -> new TetrisPieceParsingException(letter));
    }
}
