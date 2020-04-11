package edu.uark.registerapp.commands.transactions;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.ConflictException;
import edu.uark.registerapp.models.api.Transaction;
import edu.uark.registerapp.models.entities.TransactionEntity;
import edu.uark.registerapp.models.repositories.TransactionRepository;

@Service
public class TransactionCreateCommand implements ResultCommandInterface<Transaction> {
	@Override
	public Transaction execute() {

		final TransactionEntity createdTransactionEntity = this.createTransactionEntity();

		// Synchronize information generated by the database upon INSERT.
		this.apiTransaction.setTransactionId(createdTransactionEntity.getTransactionId()); 
		this.apiTransaction.setCreatedOn(createdTransactionEntity.getCreatedOn());

		return this.apiTransaction;
    }

	@Transactional
	private TransactionEntity createTransactionEntity() {
		final Optional<TransactionEntity> queriedTransactionEntity =
			this.transactionRepository
				.findByEmployeeId(this.apiTransaction.getEmployeeId());

		if (queriedTransactionEntity.isPresent()) {
			throw new ConflictException("Employee ID");
		}

		return this.transactionRepository.save(
			new TransactionEntity(apiTransaction));
	}

	// Properties
	private Transaction apiTransaction;
	public Transaction getApiTransaction() {
		return this.apiTransaction;
	}
	public TransactionCreateCommand setApiTransaction(final Transaction apiTransaction) {
		this.apiTransaction = apiTransaction;
		return this;
	}

	@Autowired
	private TransactionRepository transactionRepository;
}
