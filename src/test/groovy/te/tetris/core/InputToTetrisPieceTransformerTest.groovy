package te.tetris.core

import spock.lang.Specification
import spock.lang.Subject
import te.tetris.core.domain.Shape
import te.tetris.core.domain.TetrisPiece

class InputToTetrisPieceTransformerTest extends Specification {

    @Subject
    InputToTetrisPieceTransformer inputToTetrisPieceTransformer = []

    def "can handle lines with no commas"() {
        expect:
            inputToTetrisPieceTransformer.transform("Q4") == [new TetrisPiece(Shape.Q, 4)]
    }

    def "can handle every possible shape in a line"() {
        given: 'a list of pieces built from every available shape'
            List<TetrisPiece> expectedPieces = []
            Shape.values().eachWithIndex { Shape shape, int index ->
                expectedPieces << new TetrisPiece(shape, index)
            }

        and: 'a line representing the input for all of those pieces'
            String line = expectedPieces
                    .collect({ piece -> "${piece.shape.inputCharacter}${piece.position}" })
                    .join(",")

        expect: 'that transforming that line yields the expected pieces'
            inputToTetrisPieceTransformer.transform(line) == expectedPieces
    }

}
