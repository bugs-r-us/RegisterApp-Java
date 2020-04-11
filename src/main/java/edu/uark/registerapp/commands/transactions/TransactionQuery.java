//QUERIES FOR THE TRANSACTION OF THE EMPLOYEE
package edu.uark.registerapp.commands.transactions;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.api.Transaction;
import edu.uark.registerapp.models.entities.TransactionEntity;
import edu.uark.registerapp.models.repositories.TransactionRepository;

@Service
public class TransactionQuery implements ResultCommandInterface<Transaction> {
	@Override
	public Transaction execute() {
		final Optional<TransactionEntity> transactionEntity =
			this.transactionRepository.findByEmployeeId(this.employeeId);
		if (transactionEntity.isPresent()) {
			return new Transaction(transactionEntity.get());
		} else {
			throw new NotFoundException("Not available");
		}
	}

	// Properties
	private UUID employeeId;
	public UUID getEmployeeId() {
		return this.employeeId;
	}
	public TransactionQuery setEmployeeId(final UUID employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	@Autowired
	private TransactionRepository transactionRepository;
}
