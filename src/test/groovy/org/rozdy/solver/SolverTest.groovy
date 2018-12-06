package org.rozdy.solver

import org.rozdy.model.Board
import spock.lang.Specification

class SolverTest extends Specification {

    def "Should solve simple boards with one island"() {
        given:
        def board = new Board(width, heigth)
        board.addRequiredIsland(islandSize, islandI, islandJ)

        when:
        Solver.solve(board)

        then:
        board.isComplete()

        where:
        width | heigth | islandSize | islandI | islandJ
        2     | 2      | 1          | 0       | 0
        3     | 3      | 3          | 0       | 0
    }

    def "Should solve different boards with two islands"() {
        given:
        def board = new Board(width, heigth)
        board.addRequiredIsland(islandSize, islandI, islandJ)
        board.addRequiredIsland(island2Size, island2I, island2J)

        when:
        Solver.solve(board)

        then:
        board.isComplete()

        where:
        width | heigth | islandSize | islandI | islandJ | island2Size | island2I | island2J
        3     | 2      | 1          | 0       | 0       | 2           | 0        | 2
        4     | 4      | 5          | 0       | 0       | 5           | 2        | 2
    }

    def "Should solve board with one long island"() {
        given:
        def board = new Board(width, heigth)
        board.addRequiredIsland(islandSize, islandI, islandJ)

        when:
        Solver.solve(board)

        then:
        board.isComplete()

        where:
        width | heigth | islandSize | islandI | islandJ
//        3     | 3      | 5          | 0       | 0
//        3     | 3      | 7          | 0       | 0
        3     | 3      | 9          | 0       | 0
    }
}
