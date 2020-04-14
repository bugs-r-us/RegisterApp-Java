//DELETE AN ITEM
package edu.uark.registerapp.commands.transactions;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.api.TransactionContent;
import edu.uark.registerapp.models.entities.TransactionContentEntity;
import edu.uark.registerapp.models.repositories.TransactionContentRepository;

@Service
public class TransactionContentDeleteCommand implements VoidCommandInterface {
    @Transactional
    @Override
    public void execute() {
        
        final Optional<TransactionContentEntity> transactionContentEntity = 
            this.transactionContentRepo.findById(this.id);
            // idk about this if statement...
            if(!transactionContentEntity.isPresent()) {
                throw new NotFoundException("Transaction Content");
            }
            
            this.transactionContentRepo.delete(transactionContentEntity.get());
    }

    // Properties
    private UUID id;

    public UUID getID() {
        return this.id;
    }

    public TransactionContentDeleteCommand setID(final UUID id) {
        this.id = id;
        return this;
    }
    @Autowired
    private TransactionContentRepository transactionContentRepo;
}