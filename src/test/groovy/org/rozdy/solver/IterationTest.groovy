package org.rozdy.solver

import spock.lang.Specification

class IterationTest extends Specification {

    def "Should calculate first iteration"() {
        given:
        def iterationBuilder = new IterationBuilder([[1l, 2l, 3l], [4l, 5l]])

        when:
        def iteration = iterationBuilder.nextIteration(0)

        then:
        iteration == [1l, 4l] as long[]
    }

    def "Should calculate few iterations"() {
        given:
        def iterationBuilder = new IterationBuilder([[1l, 2l, 3l], [4l, 5l]])

        when:
        2.times { iterationBuilder.nextIteration(0) }
        def iteration = iterationBuilder.nextIteration(0)

        then:
        iteration == [2l, 4l] as long[]
    }

    def "Should calculate last iteration"() {
        given:
        def iterationBuilder = new IterationBuilder([[1l, 2l, 3l], [4l, 5l]])

        when:
        5.times { iterationBuilder.nextIteration(0) }
        def iteration = iterationBuilder.nextIteration(0)

        then:
        iteration == [3l, 5l] as long[]
    }

    def "Should skip incorrect iteration"() {
        given:
        def iterationBuilder = new IterationBuilder([[1l, 2l, 3l], [4l, 5l]])

        when:
        iterationBuilder.nextIteration(0)
        def iteration = iterationBuilder.nextIteration(1)

        then:
        iteration == [2l, 4l] as long[]
    }
}
