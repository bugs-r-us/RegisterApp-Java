package edu.uark.registerapp.models.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import edu.uark.registerapp.models.entities.TransactionContentEntity;

/*
SHOULD BE DONE
  id uuid NOT NULL DEFAULT gen_random_uuid(),
  transactionid uuid NOT NULL DEFAULT CAST('00000000-0000-0000-0000-000000000000' AS uuid),
  productid uuid NOT NULL DEFAULT CAST('00000000-0000-0000-0000-000000000000' AS uuid),
  quantity decimal(15, 4) NOT NULL DEFAULT(0),
  price bigint NOT NULL DEFAULT(0),
  createdon timestamp without time zone NOT NULL DEFAULT now(),
*/
public class TransactionContent extends ApiResponse {
    private UUID id;
    private UUID transactionid;
    private UUID productid;
    private float quantity;
    private double price;
    private String createdOn;
    

    //ID 
	public UUID getId() {
		return this.id;
	}
	public TransactionContent setId(final UUID id) {
		this.id = id;
		return this;
	}

	//TransactionID
	public UUID getTransactionID() {
		return this.transactionid;
	}

	public TransactionContent setTransactionID(final UUID transactionid) {
		this.transactionid = transactionid;
		return this;
	}

	//PRODUCT ID
	public UUID getProductID() {
		return this.productid;
	}

	public TransactionContent setProductID(final UUID productid) {
		this.productid = productid;
		return this;
    }
    
    // QUANTITY
	public float getQuantity() {
		return this.quantity;
	}

	public TransactionContent setQuantity(final float quantity) {
		this.quantity = quantity;
		return this;
    }
    
    // PRICE
	public double getPrice() {
		return this.price;
	}

	public TransactionContent setPrice(final double price) {
		this.price = price;
		return this;
	}
	
    //CREATED  ON
	public String getCreatedOn() {
		return this.createdOn;
	}

	public TransactionContent setCreatedOn(final String createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public TransactionContent setCreatedOn(final LocalDateTime createdOn) {
		this.createdOn =
			createdOn.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

		return this;
	}

	public TransactionContent() {
        super();
        
        this.id=new UUID(0, 0);;
        this.transactionid=new UUID(0, 0);
        this.productid=new UUID(0, 0);
        this.quantity=0;
        this.price=0;

		this.setCreatedOn(LocalDateTime.now());
	}

	public TransactionContent(final TransactionContentEntity TransactionContentEntity) {
        super(false);
        
        this.id=TransactionContentEntity.getID();
        this.transactionid=TransactionContentEntity.getTransactionID();
        this.productid= TransactionContentEntity.getProductID();
        this.quantity=TransactionContentEntity.getQuantity();
        this.price=TransactionContentEntity.getPrice();

		this.setCreatedOn(TransactionContentEntity.getCreatedOn());
	}
}
