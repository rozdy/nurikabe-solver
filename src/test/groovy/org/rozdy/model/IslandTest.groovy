package org.rozdy.model

import spock.lang.Specification

class IslandTest extends Specification {

    def "Should generate successful island"() {
        given:
        def board = new Board(2, 2)
        board.addRequiredIsland(2, 0, 0)
        board.clear()
        def island = board.getIslands().first()

        when:
        def successful = island.generateIsland(seed)

        then:
        successful

        where:
        seed << [2, 3]
    }

    def "Should get connected rivers"() {
        given:
        def board = new Board(2, 2)
        board.addRequiredIsland(2, 0, 0)
        board.clear()
        def cell = board.cells[1][1]

        when:
        def rivers = cell.getConnectedRivers()

        then:
        rivers.size() == 2
    }
}
