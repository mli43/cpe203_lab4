class View{
    private String productId;
    private double price;

    public View(String productId, double price){
        this.productId = productId;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getProductId() {
        return productId;
    }
}