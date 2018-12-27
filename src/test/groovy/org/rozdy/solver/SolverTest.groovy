package org.rozdy.solver

import org.rozdy.model.Board
import spock.lang.Ignore
import spock.lang.Specification

class SolverTest extends Specification {

    def "Should solve simple boards with one island"() {
        given:
        def board = new Board(width, heigth)
        board.addRequiredIsland(islandSize, islandI, islandJ)
        def solver = new Solver(board)

        when:
        solver.solve(solver.findCorrectIterations())

        then:
        board.isComplete()

        where:
        width | heigth | islandSize | islandI | islandJ
        2     | 2      | 1          | 0       | 0
        3     | 3      | 3          | 0       | 0
    }

    def "Should solve different boards with two islands"() {
        given:
        def board = new Board(heigth, width)
        board.addRequiredIsland(islandSize, islandI, islandJ)
        board.addRequiredIsland(island2Size, island2I, island2J)
        def solver = new Solver(board)

        when:
        solver.solve(solver.findCorrectIterations())

        then:
        board.isComplete()

        where:
        width | heigth | islandSize | islandI | islandJ | island2Size | island2I | island2J
        3     | 2      | 1          | 0       | 0       | 2           | 0        | 2
        4     | 4      | 5          | 0       | 0       | 5           | 2        | 2
    }

    def "Should solve hard board"() {
        given:
        def board = new Board(7, 7)
        board.addRequiredIsland(2, 2, 0)
        board.addRequiredIsland(3, 1, 1)
        board.addRequiredIsland(4, 5, 2)
        board.addRequiredIsland(3, 4, 3)
        board.addRequiredIsland(2, 2, 5)
        board.addRequiredIsland(5, 3, 6)
        def solver = new Solver(board)

        when:
        solver.solve(solver.findCorrectIterations())

        then:
        board.isComplete()
    }

    @Ignore("It's very slow")
    def "Should solve board with one long island"() {
        given:
        def board = new Board(3, 3)
        board.addRequiredIsland(islandSize, 0, 0)
        def solver = new Solver(board)

        when:
        solver.solve(solver.findCorrectIterations())

        then:
        board.isComplete()

        where:
        islandSize << [5, 7, 9]
    }

    def "Should calculate correct iterations"() {
        given:
        def board = new Board(3, 3)
        board.addRequiredIsland(3, 0, 0)
        def solver = new Solver(board)

        when:
        def correctIterations = solver.findCorrectIterations()

        then:
        correctIterations.size() == 1
        correctIterations.get(0).size() == 5
    }

    def "Should remove bad seeds while recheck"() {
        given:
        def board = new Board(3, 3)
        board.addRequiredIsland(3, 0, 0)
        board.addRequiredIsland(1, 2, 1)
        def solver = new Solver(board)
        def correctIterations = solver.findCorrectIterations()
        def correctIterationsSize = correctIterations.get(0).size()

        when:
        def actual = solver.recheckCorrectIterations(correctIterations)

        then:
        actual.get(0).size() != correctIterationsSize
    }

    def "Should remove bad seeds while recheck 2x2"() {
        given:
        def board = new Board(4, 4)
        board.addRequiredIsland(2, 1, 1)
        board.addRequiredIsland(2, 2, 2)
        def solver = new Solver(board)
        def correctIterations = solver.findCorrectIterations()

        when:
        def actual = solver.recheckCorrectIterations(correctIterations)

        then:
        actual.get(0).size() == 2
        actual.get(1).size() == 2
    }

    def "Should remove bad seeds while recheck with rivers not connected"() {
        given:
        def board = new Board(3, 2)
        board.addRequiredIsland(2, 1, 1)
        def solver = new Solver(board)
        def correctIterations = solver.findCorrectIterations()

        when:
        def actual = solver.recheckCorrectIterations(correctIterations)

        then:
        actual.get(0).size() == 2
    }
}
