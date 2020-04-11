package edu.uark.registerapp.commands.transactions;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.models.api.TransactionContent;
import edu.uark.registerapp.models.entities.TransactionContentEntity;
import edu.uark.registerapp.models.repositories.TransactionContentRepository;

@Service
public class TransactionContentsQuery implements ResultCommandInterface<List<TransactionContent>> {
	@Override
	public List<TransactionContent> execute() {
        final LinkedList<TransactionContent> transactionContents = new LinkedList<TransactionContent>();

		for (final TransactionContentEntity transactionContentEntity : transactionContentRepository.findAll()) {
            UUID id = transactionContentEntity.getTransactionId();
            boolean t = transactionContentEntity.getTransactionId().equals(this.transactionId);
            if (transactionContentEntity.getTransactionId().equals(this.transactionId)){
                transactionContents.addLast(new TransactionContent(transactionContentEntity));    
            }
		}
		
		return transactionContents;
	}

    // Properties
	private UUID transactionId = new UUID(0, 0);
	public UUID getTransactionId() {
		return this.transactionId;
	}
	public TransactionContentsQuery setTransactionId(final UUID transactionId) {
		this.transactionId = transactionId;
		return this;
	}

    @Autowired
	private TransactionContentRepository transactionContentRepository;
}
