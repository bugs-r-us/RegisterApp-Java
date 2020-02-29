package edu.uark.registerapp.commands.employees;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

@Service
public class EmployeeUpdateCommand implements ResultCommandInterface<Employee> {
    @Transactional
    @Override
    public Employee execute() {
        this.validateProperties();

        final Optional<EmployeeEntity> employeeEntity =
            this.employeeRepository.findById(this.recordID);
        if(!employeeEntity.isPresent()) {
            throw new NotFoundException("Employee");
        }

        this.apiEmployee = employeeEntity.get().synchronize(this.apiEmployee);
        this.employeeRepository.save(employeeEntity.get());
        // says to return objects updated data not sure if returning
        // entire object is correct ?
        return this.apiEmployee;
    }

    private void validateProperties() {
        if (StringUtils.isBlank(this.apiEmployee.getFirstName())) {
            throw new UnprocessableEntityException("firstname");
        }
        if (StringUtils.isBlank(this.apiEmployee.getLastName())) {
            throw new UnprocessableEntityException("lastname");
            // ^^ look up if thts right like the string parameter
        }
    }
    //Properties
    private UUID recordID; //go back and change all to Id
    public UUID getRecordID() {
        return this.recordID;
    }
    //not sure if needed but is in productupdate
    public EmployeeUpdateCommand setRecordID(final UUID recordID) {
        this.recordID = recordID;
        return this;
    }

    private Employee apiEmployee;
    public Employee getApiEmployee() {
        return this.apiEmployee;
    }
    public EmployeeUpdateCommand setApiEmployee(final Employee apiEmployee) {
        this.apiEmployee = apiEmployee;
        return this;
    }

    @Autowired
    private EmployeeRepository employeeRepository;
} 