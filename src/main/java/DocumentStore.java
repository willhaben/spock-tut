import java.util.ArrayList;
import java.util.List;

public class DocumentStore {
    private List<String> documents = new ArrayList<String>();

    public void addDoc(final String doc) {
        Thread thread = new Thread() {
            public void run(){
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    // ignore
                }
                documents.add(doc);
            }
        };

        thread.start();
    }

    public List<String> getDocuments() {
        return documents;
    }
}
