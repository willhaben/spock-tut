import java.util.ArrayList;
import java.util.List;

public class Publisher {

    List<Subscriber> subscribers = new ArrayList<Subscriber>();
    private int sentMessages;

    String send(String event) {
        sentMessages++;
        String result = "";
        for (Subscriber subscriber : subscribers) {
            try {
                String res = subscriber.receive(event);
                if (res != null) {
                    result += res;
                }
            } catch (Exception e) {
                //ignore
            }
        }
        return result;
    }

    public int getSentMessages() {
        return sentMessages;
    }
}
