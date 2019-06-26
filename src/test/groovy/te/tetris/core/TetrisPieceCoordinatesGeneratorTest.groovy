package te.tetris.core

import spock.lang.Specification
import spock.lang.Subject
import te.tetris.core.domain.Shape
import te.tetris.core.domain.TetrisPiece

class TetrisPieceCoordinatesGeneratorTest extends Specification {

    @Subject
    TetrisPieceCoordinatesGenerator transformer = []

    def "convertDimensionsToCoordinates() converts dimensions to coordinates by adding the position to each"() {
        given:
            TetrisPiece squareAt0 = new TetrisPiece(Shape.Q, 0)
            TetrisPiece squareAt3 = new TetrisPiece(Shape.Q, 3)
            int[][] copyOfOriginalCoordinates = [[0, 1], [0, 1]]

        when:
            int[][] squareAt0Coordinates = transformer.generate(squareAt0)
            int[][] squareAt3Coordinates = transformer.generate(squareAt3)

        then: 'the dimensions are all adjusted by the position of the piece'
            squareAt3Coordinates == [[3, 4], [3, 4]] as int[][]

        and: 'the dimensions for the piece at position 0 were already its coordinates'
            squareAt0Coordinates == copyOfOriginalCoordinates

        and: 'we did not mutate the original dimensions for the square at position 3'
            squareAt3.shape.dimensions == copyOfOriginalCoordinates
    }

}
