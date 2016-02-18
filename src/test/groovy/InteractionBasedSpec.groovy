import spock.lang.Specification

class InteractionBasedSpec extends Specification {
    def pub = new Publisher()
    def sub1 = Mock(Subscriber) // first way to create a mock
    Subscriber sub2 = Mock()    // second way to create a mock, mock's type is inferred
    Subscriber sub3 = Mock(Subscriber)    // ...and you could do both, just to be on the safe side :-)

    def setup() {
        pub.subscribers << sub1 << sub2 << sub3
    }

    def "delivers events to all subscribers"() {
      when:
        pub.send("event")

      then:
        1 * sub1.receive("event")
        1 * sub2.receive("event")
        (1..9) * sub3.receive("event1")
    }

    def 'groovy has a nice way of specifying that _any_ mock object can receive _anything_'() {
      when:
        pub.send("${new Random().nextInt()}")

      then:
        2 * _.receive(_)
    }

    def "can cope with misbehaving subscribers"() {
      given:
        sub1.receive(_) >> { throw new Exception() }  // a stub! with a closure!!

      when:
        pub.send("event1")
        pub.send("event2")

      then:
        pub.sentMessages == 3
        2 * sub2.receive("event1")
        2 * sub3.receive("event2")
    }

    def 'stubbing works too'() {
      given:
        sub1.receive(_) >> "ok"  // returns a fixed value
        sub2.receive('foo') >> "bar"

      when:
        String result = pub.send("foo")

      then:
        result.equals("bar")
    }

    def 'and we can return different values in the stubbed response'() {
      given:
        sub1.receive(_) >>> ["ok", "error", "ok"] // returns a sequence of values

      when:
        pub.send("Barca")
        String result = pub.send("Real Madrid")
        pub.send("Barca")

      then:
        result.equals("ok")
    }

    def 'we can even return a computed stubbed response'() {
      given:
        sub1.receive(_) >> { String message -> message.size() > 3 ? "ok" : "fail" }

      when:
        String result1 = pub.send("Barca")
        String result2 = pub.send("RM")

      then:
        result1.equals("fail")
        result2.equals("ok")
    }

    def 'we can explicitly declare a Stub to make it easier to read'() {
      given:
        def sub4 = Stub(Subscriber) {
            receive("message1") >> "ok"
            receive("message2") >> "fail"
        }
        pub.subscribers << sub4

      when:
        String result = pub.send("foo")

      then:
        result.equals("ok")
    }

    def 'a spy on a real object can be handy as well'() {
      given:
        def subscriberSpy = Spy(SubscriberImpl)
        subscriberSpy.receive(_) >> "I Spy"
        pub.subscribers << subscriberSpy

      when:
        String res = pub.send("hello world")

      then:
        res.equals("a message from the real implementation")
        subscriberSpy.justAMethod().equals('it\'s just a method')
    }
}

