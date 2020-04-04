package edu.uark.registerapp.models.api;

public class TransactionContentAdd extends ApiResponse{

    public TransactionContentAdd(String productId, int quantity, float price) {

		this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
    public TransactionContentAdd()
    {
        this.productId = " ";
        this.quantity = 0;
        this.price = 0;
    }



    //Product ID
    private String productId;
    public TransactionContentAdd setProductId(final String productId)
    {
        this.productId = productId;
        return this;
    }
    public String getProductId()
    {
        return this.productId;
    }

    //Quantity
    private int quantity;
    public TransactionContentAdd setQuantity(final int quantity)
    {
        this.quantity = quantity;
        return this;
    }
    public int getQuantity()
    {
        return this.quantity;
    }

    //Price
    private float price;
    public TransactionContentAdd setPrice(final float price)
    {
        this.price = price;
        return this;
    }
    public float getPrice()
    {
        return this.price;
    }


}