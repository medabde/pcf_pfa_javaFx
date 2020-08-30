package app.models;

public class CommandeUpdate {
    private String productName;
    private int productQuantity;

    public CommandeUpdate(String productName, int productQuantity) {
        this.productName = productName;
        this.productQuantity = productQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }
}
