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
import edu.uark.registerapp.models.repositories.EmployeeRepository;

@Service
public class EmployeeCreateCommand implements ResultCommandInterface<Employee> {
    @Override
    public Employee execute() {
        // ?
        this.validateProperties();
        
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
        if(isInitialEmployee) { //not sure if belings here either
            // MIGHT GO IN OWN FUNCTION..
            //default to general manager classification
            // that employee classification class ?
        }
    }

    @Transactional 
    private EmployeeEntity createEmployeeEntity() {
        //idk what else to put here yet
        return this.employeeRepository.save(
            new EmployeeEntity(apiEmployee));
    }
    //properites
    private Employee apiEmployee;
    private boolean isInitialEmployee;
    public Employee getApiEmployee() {
        return this.apiEmployee;
    }
    public EmployeeCreateCommand setApiEmpoloyee(final Employee apiEmployee) {
        this.apiEmployee = apiEmployee;
        return this;
    }

    @Autowired
    private EmployeeRepository employeeRepository;
}