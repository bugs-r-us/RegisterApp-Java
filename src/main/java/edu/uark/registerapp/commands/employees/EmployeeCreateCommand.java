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
        this.validateProperties();

        if(isInitial()) {
        this.apiEmployee.setClassification(EmployeeClassification
            .GENERAL_MANAGER.getClassification());
        }
        final EmployeeEntity createdEmployeeEntity = this.createEmployeeEntity();
        // not sure
        // this.apiEmployee.setId(createdEmployeeEntity.getId());
        // this.apiEmployee.setCreatedOn(createdEmployeeEntity.getCreatedOn());
        
        return new Employee(createdEmployeeEntity);
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
        byte[] password = this.apiEmployee.getPassword().getBytes();
    
        EmployeeEntity eE = new EmployeeEntity(apiEmployee);
        eE.setPassword(password);

        return this.employeeRepository.save(
            eE);
    }
    //properites
    private Employee apiEmployee;
    public Employee getApiEmployee() {
        return this.apiEmployee;
    }
    public EmployeeCreateCommand setApiEmpoloyee(final Employee apiEmployee) {
        this.apiEmployee = apiEmployee;
        return this;
    }

    public boolean isInitial() {
        if(!employeeRepository.existsByIsActive(true)) {
            return true;
        }
        return false;
    }

    @Autowired
    private EmployeeRepository employeeRepository;
}