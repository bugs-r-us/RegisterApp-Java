package edu.uark.registerapp.models.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import edu.uark.registerapp.models.api.Transaction;

@Entity
@Table(name="transaction")
public class TransactionEntity {
    @Id
    @Column(name="id", updatable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;

	public UUID getTransactionId() {
		return this.id;
	}

	@Column(name = "employeeid")
	private UUID employeeId;

	public UUID getEmployeeId() {
		return this.employeeId;
	}

	public TransactionEntity setEmployeeId(final UUID employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	@Column(name = "total")
	private float total;

	public float getTotal() {
		return this.total;
	}

	public TransactionEntity setTotal(final float total) {
		this.total = total;
		return this;
	}

	@Column(name = "transactionstatus")
	private int transactionstatus;

	public int gettransactionstatus() {
		return this.transactionstatus;
	}

	public TransactionEntity settransactionstatus(final int transactionstatus) {
		this.transactionstatus = transactionstatus;
		return this;
	}

	
	@Column(name = "createdon", insertable = false, updatable = false)
	@Generated(GenerationTime.INSERT)
	private LocalDateTime createdOn;

	public LocalDateTime getCreatedOn() {
		return this.createdOn;
	}

	public Transaction synchronize(final Transaction apiTransaction) {
		this.setEmployeeId(apiTransaction.getEmployeeId());
		this.setTotal(apiTransaction.getTotal());
		this.settransactionstatus(apiTransaction.getStatus());

//what the  Transaction  doesn't auto  generate that  the db does
		apiTransaction.setTransactionId(this.getTransactionId());
		apiTransaction.setCreatedOn(this.getCreatedOn());

		return apiTransaction;
	}

	public TransactionEntity() {
		this.id =new UUID(0, 0);
		this.employeeId = new UUID(0, 0);
        this.total = 0;
		this.transactionstatus = 0;
		this.createdOn=getCreatedOn();
	}

	public TransactionEntity(final Transaction apiTransaction) {
		this.id = new UUID(0, 0);
		this.employeeId = apiTransaction.getEmployeeId();
        this.total = apiTransaction.getTotal();
		this.transactionstatus = apiTransaction.getStatus();
		this.createdOn = getCreatedOn();
	}
}