package edu.uark.registerapp.commands.employees;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.ConflictException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.enums.EmployeeClassification;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

@Service
public class EmployeeCreateCommand implements ResultCommandInterface<Employee> {
    @Override
    public Employee execute() {
        // ?
        this.validateProperties();

        if(isInitial()) {
        this.apiEmployee.setClassification(EmployeeClassification
            .GENERAL_MANAGER.getClassification());
        }
        final EmployeeEntity createdEmployeeEntity = this.createEmployeeEntity();
        // not sure
        this.apiEmployee.setId(createdEmployeeEntity.getId());
        this.apiEmployee.setCreatedOn(createdEmployeeEntity.getCreatedOn());
        
        return this.apiEmployee;
    }

    //helper ?
    private void validateProperties() {
        if(StringUtils.isBlank(this.apiEmployee.getFirstName())) {
            //not sure if right
            throw new UnprocessableEntityException("firstname");
        }
        if(StringUtils.isBlank(this.apiEmployee.getLastName())) {
            //not sure if right
            throw new UnprocessableEntityException("lastname");
        }
        if(StringUtils.isBlank(this.apiEmployee.getPassword())) {
            //not sure if right
            throw new UnprocessableEntityException("password");
        }
    }

    @Transactional 
    private EmployeeEntity createEmployeeEntity() {
        final Optional<EmployeeEntity> queriedEmployeeEntity = 
            this.employeeRepository
                .findByEmployeeId(
                    Integer.parseInt(this.apiEmployee.getEmployeeId()));
                    //^^PROBABLY NOT RIGHT
        // ^^ FIGURE THAT OUT
        if(queriedEmployeeEntity.isPresent()) {
             throw new ConflictException("employeeid");
        }
        // maybe check for exisiting employeeid
        //idk what else to put here yet
        return this.employeeRepository.save(
            new EmployeeEntity(apiEmployee));
    }
    //properites
    private Employee apiEmployee;
    private boolean isInitialEmployee = false;
    public Employee getApiEmployee() {
        return this.apiEmployee;
    }
    public EmployeeCreateCommand setApiEmpoloyee(final Employee apiEmployee) {
        this.apiEmployee = apiEmployee;
        return this;
    }

    public boolean isInitial() {
        if(employeeRepository.count() == 0) {
            isInitialEmployee = true;
        }
        return isInitialEmployee;
    }

    @Autowired
    private EmployeeRepository employeeRepository;
}