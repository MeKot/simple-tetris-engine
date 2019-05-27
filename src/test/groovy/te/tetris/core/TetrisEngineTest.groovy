package te.tetris.core

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll
import te.tetris.core.domain.TetrisPiece
import te.tetris.core.domain.Shape

class TetrisEngineTest extends Specification {

    @Subject
    TetrisEngine tetrisEngine = []

    List<TetrisPiece> pieces
    
    @Unroll("#shape.name() by itself results in #height height")
    def "can handle the simplest scenario: a single piece"() {
        given:
            pieces = [new TetrisPiece(shape, 0)]

        expect:
            tetrisEngine.generateResultingGrid(pieces).size() == height

        where:
            shape   || height
            Shape.Q || 2
            Shape.Z || 2
            Shape.S || 2
            Shape.T || 2
            Shape.I || 1
            Shape.L || 3
            Shape.J || 3
    }

    def "can handle multiple, non-overlapping pieces"() {
        given:
            pieces = [
                    new TetrisPiece(Shape.Q, 0),
                    new TetrisPiece(Shape.T, 4)
            ]

        expect:
            tetrisEngine.generateResultingGrid(pieces).size() == 2
    }

    def "can can handle two pieces of the same shape that need to stack"() {
        given:
            pieces = [
                    new TetrisPiece(Shape.Q, 0),
                    new TetrisPiece(Shape.Q, 1),
            ]

        expect:
            tetrisEngine.generateResultingGrid(pieces).size() == 4
    }

    def "can handle 2 pieces of different shape that need to stack"() {
        given:
            pieces = [
                    new TetrisPiece(Shape.I, 0),
                    new TetrisPiece(Shape.J, 0)
            ]

        expect:
            tetrisEngine.generateResultingGrid(pieces).size() == 4
    }

    def "can handle 3 pieces where the third interlocks between the previous two"() {
        given:
            pieces = [
                    new TetrisPiece(Shape.Q, 0),
                    new TetrisPiece(Shape.Q, 3),
                    new TetrisPiece(Shape.T, 1)
            ]

        expect:
            tetrisEngine.generateResultingGrid(pieces).size() == 3
    }

    def "can handle example 1 from requirements document"() {
        given:
            pieces = [
                    new TetrisPiece(Shape.I, 0),
                    new TetrisPiece(Shape.I, 4),
                    new TetrisPiece(Shape.Q, 8),
            ]

        expect:
            tetrisEngine.generateResultingGrid(pieces).size() == 1
    }

    def "can handle example 2 from requirements document"() {
        given:
            pieces = [
                    new TetrisPiece(Shape.T, 1),
                    new TetrisPiece(Shape.Z, 3),
                    new TetrisPiece(Shape.I, 4),
            ]

        expect:
            tetrisEngine.generateResultingGrid(pieces).size() == 4
    }

    def "can handle example 3 from requirements document"() {
        given:
            pieces = [
                    new TetrisPiece(Shape.Q, 0),
                    new TetrisPiece(Shape.I, 2),
                    new TetrisPiece(Shape.I, 6),
                    new TetrisPiece(Shape.I, 0),
                    new TetrisPiece(Shape.I, 6),
                    new TetrisPiece(Shape.I, 6),
                    new TetrisPiece(Shape.Q, 2),
                    new TetrisPiece(Shape.Q, 4),
            ]

        expect:
            tetrisEngine.generateResultingGrid(pieces).size() == 3
    }

    def "getAdjustedCoordinates() returns the position-adjusted coordinates for a piece"() {
        given:
            TetrisPiece squarePiece = new TetrisPiece(Shape.Q, 3)
            int[][] copyOfOriginalCoordinates = [[0, 1], [0, 1]]

        when:
            int[][] adjustedCoordinates = tetrisEngine.getAdjustedCoordinates(squarePiece)

        then: 'the coordinates are all adjusted by the position of the piece'
            adjustedCoordinates == [[3, 4], [3, 4]] as int[][]

        and: 'we did not mutate the original coordinates for the square'
            squarePiece.shape.coordinates == copyOfOriginalCoordinates
    }

}
