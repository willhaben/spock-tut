import spock.lang.FailsWith
import spock.lang.Ignore
import spock.lang.Specification

class StateBasedSpec extends Specification {

    def setupSpec() {
        // something common for the whole specification
    }

    def cleanupSpec() {
        // something common for the whole specification
    }

    def setup() {
        println 'starting a test...'
    }

    def cleanup() {
        println 'finished a test...'
    }

    def 'finding the maximum of a small and a big number'() {
      given:
        int smallNumber = 1
        int bigNumber = 5

      when:
        int result = Math.max(smallNumber, bigNumber)

      then:
        result == smallNumber
    }


    def 'finding the maximum of two numbers, using the expect block for purely functional methods'() {
      expect:
        Math.max(1, 2) == 3
    }

    def "compare sets"() {
      setup: 'you could also put labels in here, but probably of limited use'
        Set set1 = ['Fred', 'Wilma', 'Dino', 'Dino']
        //can also be declared as:
        //def set1 = ['Fred', 'Wilma', 'Dino', 'Dino'] as Set
        def set2 = ['Wilma', 'Dino', 'Barney']

      expect: 'the sets to be equal'
        set1 == set2
    }

    def 'exceptions are handled nicely'() {
      when:
        ['a', 'b'].get(42)

      then:
        thrown(NullPointerException)
    }

    @FailsWith(IndexOutOfBoundsException)
    def 'if you have a bug, you could write a test and annotate it with FailsWith and fix the bug with a given test'() {
      when:
        ['a', 'b'].get(42)

      then:
        true
    }

    @Ignore
    def 'hashmap accepts null key'() {
      given:
        def map = new HashMap()

      when:
        map.put(null, 'elem')

      then:
        notThrown(NullPointerException)
    }
}
