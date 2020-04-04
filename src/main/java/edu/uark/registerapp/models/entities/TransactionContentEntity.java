package edu.uark.registerapp.models.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import edu.uark.registerapp.models.api.TransactionContent;

@Entity
@Table(name="transactioncontent")
public class TransactionContentEntity {
    @Id
    @Column(name="id", updatable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;

	public UUID getID() {
		return this.id;
	}

	@Column(name = "transactionid")
	private UUID transactionid;

	public UUID getTransactionId() {
		return this.transactionid;
	}
	public TransactionContentEntity setTransactionId(final UUID transactionid) {
		this.transactionid = transactionid;
		return this;
	}

	@Column(name = "productid")
	private UUID productid;

	public UUID getProductID() {
		return this.productid;
	}

	public TransactionContentEntity setProductID(final UUID productid) {
		this.productid = productid;
		return this;
	}

	@Column(name = "quantity")
	private float quantity;

	public float getQuantity() {
		return this.quantity;
	}

	public TransactionContentEntity setQuantity(final float quantity) {
		this.quantity = quantity;
		return this;
    }
    
    @Column(name = "price")
	private double price;

	public double getPrice() {
		return this.price;
	}

	public TransactionContentEntity setPrice(final double price) {
		this.price = price;
		return this;
	}

	
	@Column(name = "createdon", insertable = false, updatable = false)
	@Generated(GenerationTime.INSERT)
	private LocalDateTime createdOn;

	public LocalDateTime getCreatedOn() {
		return this.createdOn;
	}

	public TransactionContent synchronize(final TransactionContent apiTransactionContent) {
        
        this.setTransactionId(apiTransactionContent.getTransactionID());
        this.setProductID(apiTransactionContent.getProductID());
        this.setQuantity(apiTransactionContent.getQuantity());
        this.setPrice(apiTransactionContent.getPrice());

        //DB CREATED THESE  
        apiTransactionContent.setId(this.getID());
        apiTransactionContent.setCreatedOn(this.getCreatedOn());

        return apiTransactionContent;
	}

	public TransactionContentEntity() {
		this.id=new UUID(0, 0);;
        this.transactionid=new UUID(0, 0);
        this.productid=new UUID(0, 0);
        this.quantity=0;
        this.price=0;
		this.createdOn=getCreatedOn();
		
	}

	//WE CAN  MAKE MORE BASED ON WHAT WE NEED
<<<<<<< HEAD
=======
	public TransactionContentEntity(TransactionContent apiTransactionContent) {
		this.id = new UUID(0, 0);;
        this.transactionid = apiTransactionContent.getTransactionID();
        this.productid = apiTransactionContent.getProductID();
        this.quantity = apiTransactionContent.getQuantity();
        this.price = apiTransactionContent.getPrice();
		this.createdOn = getCreatedOn();
		
	}
>>>>>>> master-dev
	
}