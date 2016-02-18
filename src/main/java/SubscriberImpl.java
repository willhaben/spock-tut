public class SubscriberImpl implements Subscriber {

    @Override
    public String receive(String event) {
        return "a message from the real implementation";
    }

    public String justAMethod() {
        return "it's just a method";
    }
}
