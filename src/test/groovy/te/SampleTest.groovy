package te

import spock.lang.Specification
import spock.lang.Subject

class SampleTest extends Specification {

    @Subject
    Sample sample = []

    def "sample works"() {
        when: 'we run sample'
            int result = sample.runSample()

        then: 'we get the expected value'
            result == 25
    }

}
