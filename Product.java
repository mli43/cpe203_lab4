class Product{
    private final String productId;
    private double price;
    private double avgPrice;
    private double totalPrice;

    public Product(String productId, double price){
        this.price = price;
        this.productId = productId;
        this.totalPrice = 0;
    }

    public String getProductId() {
        return productId;
    }

    public double getPrice() {
        return price;
    }

    public double getTotalPrice() {return totalPrice;}

    public void setPrice(double newPrice){
        totalPrice += newPrice;
        price = newPrice;
    }

}