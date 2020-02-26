package edu.uark.registerapp.commands.employees;

import java.util.UUID;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

//Im literally just copying productquery for right now
@Service
public class EmployeeQuery implements ResultCommandInterface<Employee>{
    @Override
    public Employee execute() {
        final Optional<EmployeeEntity> employeeEntity =
            this.employeeRepository.findById(this.recordID);
            if (employeeEntity.isPresent()) {
            return new Employee(employeeEntity.get());
        } else {
            throw new NotFoundException("Employee");
        }
    }

    // Properties
    private UUID recordID;
    public UUID getRecordID() {
        return this.recordID;
    }
    public EmployeeQuery setRecordID(final UUID recordID) {
        this.recordID = recordID;
        return this;
    }

    @Autowired
    private EmployeeRepository employeeRepository;

}