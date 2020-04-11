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
public class TransactionUpdateCommand implements ResultCommandInterface<Transaction>{
    @Override
    public Transaction execute() {
        this.validateProperties();
        
        final Optional<TransactionEntity> transactionEntity =
            this.transactionRepo.findById(this.id);
            // idk about this if statement...
            if(!transactionEntity.isPresent()) {
                throw new NotFoundException("Transaction");
            }
            this.apiTransaction = transactionEntity.get().synchronize(this.apiTransaction);
            this.transactionRepo.save(transactionEntity.get());
            return this.apiTransaction;
    }

    private void validateProperties() {
        // not sure yet, but its error checking stuff
    }

    // Properties
    private UUID id;

    public UUID getID() {
        return this.id;
    }

    public TransactionUpdateCommand setID(final UUID id) {
        this.id = id;
        return this;
    }

    private Transaction apiTransaction;
    public Transaction getApiTransaction() {
        return this.apiTransaction;
    }
    public TransactionUpdateCommand setApiTransaction(final Transaction apiTransaction) {
        this.apiTransaction = apiTransaction;
        return this;
    }
    @Autowired
    private TransactionRepository transactionRepo;
}