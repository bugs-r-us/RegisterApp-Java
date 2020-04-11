package edu.uark.registerapp.commands.transactions;

import java.util.UUID;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.api.TransactionContent;
import edu.uark.registerapp.models.entities.TransactionContentEntity;
import edu.uark.registerapp.models.repositories.TransactionContentRepository;

@Service
public class TransactionContentQuery implements ResultCommandInterface<TransactionContent> {
	@Override
	public TransactionContent execute() {
        final Optional<TransactionContentEntity> transactionContentEntity = this.transactionContentRepository.findById(this.transactionContentId);
		if (transactionContentEntity.isPresent()) {
			return new TransactionContent(transactionContentEntity.get());
		} else {
			throw new NotFoundException("Product");
		}
	}

    // Properties
	private UUID transactionContentId = new UUID(0, 0);
	public UUID getTransactionId() {
		return this.transactionContentId;
	}
	public TransactionContentQuery setTransactionContentId(final UUID transactionContentId) {
		this.transactionContentId = transactionContentId;
		return this;
	}

    @Autowired
	private TransactionContentRepository transactionContentRepository;
}
