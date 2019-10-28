import java.util.List;
import java.util.LinkedList;
import java.util.Map;

class Session{
    private final String sessionId;
    private Map<Product, int> viewedProducts;
    private Map<Product, int> boughtProducts;

    public Session(String sessionId, Map<Product, int> viewdProducts, Map<Product, int> boughtProducts){
        this.sessionId = sessionId;
        this.viewedProducts = new HashMap<>();
        this.boughtProducts = new HashMap<>();
    }


}
