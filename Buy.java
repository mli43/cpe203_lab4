import java.util.Map;
import java.util.List;
import java.util.LinkedList;

public class Buy{
    private String sessionId;
    private String productId;
    private double price;
    private int quantity;

    public Buy(String sessionId, String productId, int price, int quantity){
        this.sessionId = sessionId;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getProductId() {
        return productId;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }


}