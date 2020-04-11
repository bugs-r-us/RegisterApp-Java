package edu.uark.registerapp.models.api;

public class TranasctionIncrementDecrement {

    public TranasctionIncrementDecrement(String transactionContentID, int quantity) {
        this.transactionContentID = transactionContentID;
        this.quantity = quantity;
    }
    public TranasctionIncrementDecrement()
    {
        this.transactionContentID = " ";
        this.quantity = 0;
    }

    private String transactionContentID;
    public TranasctionIncrementDecrement setTransactionContentID(final String transactionContentID)
    {
        this.transactionContentID = transactionContentID;
        return this;
    }
    public String getTransactionContentID()
    {
        return this.transactionContentID;
    }

    private int quantity;
    public TranasctionIncrementDecrement setQuantity(final int quantity)
    {
        this.quantity = quantity;
        return this;
    }
    public int getQuantity()
    {
        return this.quantity;
    }

}