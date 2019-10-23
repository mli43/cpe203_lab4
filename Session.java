import java.util.List;
import java.util.LinkedList;

class Session{
    private final String sessionId;
    private List<Product> products;

    public Session(String sessionId, List<Product> products){
        this.sessionId = sessionId;
        this.products = products;
    }

}
