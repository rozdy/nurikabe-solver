package org.rozdy.model

import spock.lang.Specification

class IslandTest extends Specification {

    def "Should generate successful island"() {
        given:
        def board = BoardBuilder.board().withWidth(2).withHeight(2).withIsland(2, 0, 0).build()
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
        def board = BoardBuilder.board().withWidth(2).withHeight(2).withIsland(2, 0, 0).build()
        def cell = board.cells[1][1]

        when:
        def rivers = cell.getConnectedRivers()

        then:
        rivers.size() == 2
    }
}
