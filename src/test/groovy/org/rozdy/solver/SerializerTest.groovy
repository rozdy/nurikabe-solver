package org.rozdy.solver

import spock.lang.Specification

class SerializerTest extends Specification {

    def "Should write and read correct iterations"() {
        given:
        def correctIterations = [[0L, 1L], [7L, 12L, 10001L]]
        def filename = "test"

        when:
        SeedsSerializer.write(correctIterations, filename)
        def actual = SeedsSerializer.read(filename)
        new File(filename).delete()

        then:
        correctIterations == actual
    }
}
