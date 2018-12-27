package org.rozdy.model

import spock.lang.Specification

class BoardTest extends Specification {

    def "Should be complete board 1x1"() {
        given:
        def board = new Board(1, 1)

        when:
        def complete = board.isComplete()

        then:
        complete
    }

    def "Should be complete board 2x2 with one island"() {
        given:
        def board = new Board(2, 2)
        board.addRequiredIsland(size, i, j)
        board.clear()

        when:
        def complete = board.isComplete()

        then:
        complete

        where:
        i | j | size
        0 | 0 | 1
        1 | 0 | 1
        0 | 1 | 1
        1 | 1 | 1
    }

    def "Should be not complete board 2x2 with one not complete island"() {
        given:
        def board = new Board(2, 2)
        board.addRequiredIsland(size, i, j)
        board.clear()

        when:
        def complete = board.isComplete()

        then:
        !complete

        where:
        i | j | size
        0 | 0 | 2
        1 | 0 | 2
        0 | 1 | 2
        1 | 1 | 2
        1 | 1 | 3
    }

    def "Should be complete board 2x2 with one complete island"() {
        given:
        def board = new Board(2, 2)
        board.addRequiredIsland(size, i, j)
        board.clear()
        def island = board.getIslands().first()
        island.addCell(board.cells[i1][j1])

        when:
        def complete = board.isComplete()

        then:
        complete

        where:
        i | j | size | i1 | j1
        0 | 0 | 2    | 0  | 1
        1 | 0 | 2    | 1  | 1
        0 | 1 | 2    | 0  | 0
        1 | 1 | 2    | 0  | 1
    }

    def "Should be complete board 4x2 with one complete island"() {
        given:
        def board = new Board(4, 2)
        board.addRequiredIsland(size, i, j)
        board.clear()
        def island = board.getIslands().first()
        island.addCell(board.cells[i1][j1])

        when:
        def complete = board.isComplete()

        then:
        complete

        where:
        i | j | size | i1 | j1
        1 | 0 | 2    | 2  | 0
        1 | 1 | 2    | 2  | 1
    }

    def "Should be not complete board 4x2 with one complete island"() {
        given:
        def board = new Board(4, 2)
        board.addRequiredIsland(size, i, j)
        board.clear()
        def island = board.getIslands().first()
        island.addCell(board.cells[i1][j1])

        when:
        def complete = board.isComplete()

        then:
        !complete

        where:
        i | j | size | i1 | j1
        0 | 0 | 2    | 0  | 1
        1 | 0 | 2    | 1  | 1
        2 | 0 | 2    | 2  | 1
        3 | 0 | 2    | 3  | 1
    }

    def "Should be not complete board 3x3 with one island"() {
        given:
        def board = new Board(3, 3)
        board.addRequiredIsland(size, i, j)

        when:
        def complete = board.isComplete()

        then:
        !complete

        where:
        i | j | size
        0 | 0 | 3
    }
}
