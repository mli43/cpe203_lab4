import java.util.Map;
import java.util.List;
import java.util.LinkedList;

public class Buy{
    private String sessionId;
    private Map<String, int> products;
    private int quantity;

    public Buy(String sessionId, Map<String, int> products, int quantity){
        this.sessionId = sessionId;
        this.products = products;
        this.quantity = quantity;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Map<String, int> getProducts() {
        return products;
    }

    public int getQuantity() {
        return quantity;
    }


}