package edu.uark.registerapp.models.api;


public class Test {

    private TransactionContent transactionContent;
    private Product product;

    public Test (TransactionContent transactionContent, Product product)
    {
        this.transactionContent=transactionContent;
        this.product= product;
    }
    public Product getProduct()
    {
        return this.product;
    }
    public TransactionContent getTransactionContent()
    {
        return this.transactionContent;
    }


 

    
   


}