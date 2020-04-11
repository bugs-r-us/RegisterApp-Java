package edu.uark.registerapp.commands.transactions;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.api.TransactionContent;
import edu.uark.registerapp.models.entities.TransactionContentEntity;
import edu.uark.registerapp.models.repositories.TransactionContentRepository;

@Service
public class TransactionContentUpdateCommand implements ResultCommandInterface<TransactionContent> {
    @Override
    public TransactionContent execute() {
        this.validateProperties();
        
        final Optional<TransactionContentEntity> transactionContentEntity = 
            this.transactionContentRepo.findById(this.id);
            if(!transactionContentEntity.isPresent()) {
                throw new NotFoundException("Transaction Content");
            }
            this.apiTCA = transactionContentEntity.get().synchronize(this.apiTCA);
            this.transactionContentRepo.save(transactionContentEntity.get());
            return this.apiTCA;
    }

    private void validateProperties() {
        // not sure if necesarry
        // or if this whole class is necessary
        // but im testing sumn..
    }

    // Properties
    private UUID id;

    public UUID getID() {
        return this.id;
    }

    public TransactionContentUpdateCommand setID(final UUID id) {
        this.id = id;
        return this;
    }

    private TransactionContent apiTCA;
    public TransactionContent getapiTCA() {
        return this.apiTCA;
    }
    public TransactionContentUpdateCommand setapiTCA(final TransactionContent apiTCA) {
        this.apiTCA = apiTCA;
        return this;
    }
    @Autowired
    private TransactionContentRepository transactionContentRepo;
}