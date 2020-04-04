package edu.uark.registerapp.models.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import edu.uark.registerapp.models.entities.TransactionEntity;
/*
transaction id
employee id
total
transactions status

SHOULD BE DONE?  

 */
public class Transaction extends ApiResponse {
	private UUID id;
	public UUID getTransactionId() {
		return this.id;
	}
	public Transaction setTransactionId(final UUID id) {
		this.id = id;
		return this;
    }
    
    private UUID employeeId;
	public UUID getEmployeeId() {
		return this.employeeId;
	}
	public Transaction setEmployeeId(final UUID id) {
		this.employeeId = id;
		return this;
	}

	private float total;

	public float getTotal() {
		return this.total;
	}

	public Transaction setTotal(final float total) {
		this.total = total;
		return this;
	}

	private int transactionstatus;

	public int gettransactionstatus() {
		return this.transactionstatus;
	}

	public Transaction settransactionstatus(final int status) {
		this.transactionstatus = status;
		return this;
	}

	private String createdOn;

	public String getCreatedOn() {
		return this.createdOn;
	}

	public Transaction setCreatedOn(final String createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public Transaction setCreatedOn(final LocalDateTime createdOn) {
		this.createdOn =
			createdOn.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

		return this;
	}

	

	public Transaction() {
		super();

		this.id =new UUID(0, 0);
		this.employeeId = new UUID(0, 0);
        this.total = 0;
		this.transactionstatus=0;
		this.setCreatedOn(LocalDateTime.now());

	}

	public Transaction(final TransactionEntity transactionEntity) {
		super(false);

		this.id =transactionEntity.getTransactionId();
		this.employeeId = transactionEntity.getEmployeeId();
        this.total = transactionEntity.getTotal();
		this.transactionstatus=transactionEntity.gettransactionstatus();
		this.setCreatedOn(transactionEntity.getCreatedOn());
	}
}
