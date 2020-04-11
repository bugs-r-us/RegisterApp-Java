package edu.uark.registerapp.models.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import edu.uark.registerapp.models.entities.TransactionContentEntity;

public interface TransactionContentRepository extends CrudRepository<TransactionContentEntity, UUID> {
    Optional<TransactionContentEntity> findById(UUID id);     
}
