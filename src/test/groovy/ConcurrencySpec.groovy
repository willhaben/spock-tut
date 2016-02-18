import spock.lang.Specification
import spock.lang.Timeout
import spock.util.concurrent.PollingConditions

class ConcurrencySpec extends Specification {

    @Timeout(5)
    def 'I should fail if I run for more than 5 seconds'() {
      expect:
        Thread.sleep(10000)
    }

    def 'using polling to be sure that we do not use more time than needed'() {
      given:
        PollingConditions pollingConditions = new PollingConditions()
        DocumentStore documentStore = new DocumentStore()

      when:
        documentStore.addDoc('a nice document')

      then:
        pollingConditions.within(2, {
            documentStore.documents.size() == 1
        })
    }
}
