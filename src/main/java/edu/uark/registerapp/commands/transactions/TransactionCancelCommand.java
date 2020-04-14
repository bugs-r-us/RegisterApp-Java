//CANCEL TRANSACTION
package edu.uark.registerapp.commands.transactions;

import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.uark.registerapp.commands.VoidCommandInterface;

import edu.uark.registerapp.commands.exceptions.NotFoundException;

import edu.uark.registerapp.models.entities.TransactionEntity;
import edu.uark.registerapp.models.repositories.TransactionRepository;

@Service
public class TransactionCancelCommand implements VoidCommandInterface {
    @Transactional
    @Override
	public void execute() {

		final Optional<TransactionEntity> tEntity=this.transactionRepository.findById(this.getTransactionID());
		if(!tEntity.isPresent())
			throw new NotFoundException("transaction");
        this.transactionRepository.delete(tEntity.get());
		
    }

	

	// Properties
	private UUID transactionID;
	public UUID getTransactionID() {
		return this.transactionID;
	}
	public TransactionCancelCommand setTransactionID(final UUID transactionID) {
		this.transactionID = transactionID;
		return this;
	}
	
	@Autowired
	private TransactionRepository transactionRepository;

}
