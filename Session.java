import java.util.List;
import java.util.LinkedList;
import java.util.Map;

class Session{
    private final String sessionId;
    private List<Product> products;
    private Map<Session, List<Product>> viewedProducts;
    private Map<Session, List<Product>> boughtProducts;

    public Session(String sessionId, List<Product> products){
        this.sessionId = sessionId;
        this.products = products;
        this.viewedProducts = new HashMap<>();
        this.boughtProducts = new HashMap<>();
    }

}
