package edu.uark.registerapp.models.api;

public class TransactionContentAdd extends ApiResponse{

    public TransactionContentAdd(String productId, int quantity) {

		this.productId = productId;
		this.quantity = quantity;
    }
    public TransactionContentAdd()
    {
        this.productId = " ";
        this.quantity = 0;
    }



    //EMPLOYEE ID
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

    //PASSWORD ID
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



}