import java.util.List;
import java.util.LinkedList;

class Customer{

    private String CustomerId;
    private Session sessions;

    public Customer(String id, Session sessions){
        this.CustomerId = id;
        this.sessions = sessions;
    }

}
