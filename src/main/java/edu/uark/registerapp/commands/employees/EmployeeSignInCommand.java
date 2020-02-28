package edu.uark.registerapp.commands.employees;

import java.util.Arrays;
import java.util.Optional;

import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnauthorizedException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;



@Service
public class EmployeeSignInCommand implements ResultCommandInterface<Employee> {


    @Override
    public Employee execute()
    {
        this.validateProperties();
        this.findEmployee();


        

        //public EmployeeEntity employeeEntity= this.employeeRepository.setId(this.getId());;

        
        if()
        {

        }
        else{
            // this.ActiveUserRepository.save(this.EmployeeEntity);
            // this.setSessionKey(getSessionKey());
            return new Employee();
        }

	}

    

//HELP METHODS
private void validateProperties() {
    if ((StringUtils.isBlank(this.apiEmployeeSignIn.getEmployeeID()))&&
        (((StringUtils.isBlank(this.apiEmployeeSignIn.getPassword()))))) {
        throw new UnprocessableEntityException("Employee");
    }
}
private Employee findEmployee(){
    final Optional<EmployeeEntity> queriedEmployee=
        this.employeeRepository.findByEmployeeId(Integer.parseInt(this.apiEmployeeSignIn.getEmployeeID()));
        
    boolean doesExist= this.employeeRepository.
                            existsByEmployeeId(Integer.parseInt(this.apiEmployeeSignIn.getEmployeeID()));
    
                            //password from request and database
    byte[] requestPW = this.employeeSignin.getPassword().getBytes();
    boolean passwordMatch=Arrays.equals(queriedEmployee.get().getPassword(),requestPW);
        if(doesExist==true && passwordMatch)
        {
            // public Employee synchronize(final Employee apiEmployee) {
                return new Employee(queriedEmployee.get());
        }
        else
        {
           throw new NotFoundException("Employee");
        }
}

@Transactional
private ActiveUserEntity createActiveUserEntity() {

}

	// Properties
    private String sessionKey;
    public EmployeeSignIn employeeSignin;

    public String getSessionKey() {
		return this.sessionKey;
	}

	public EmployeeSignInCommand setSessionKey(final String sessionKey) {
		this.sessionKey = sessionKey;
		return this;
    }
    
    //COPIED
    private EmployeeSignIn apiEmployeeSignIn;
	public EmployeeSignIn getapiEmployeeSignIn() {
		return this.apiEmployeeSignIn;
	}
	public EmployeeSignInCommand setapiEmployeeSignIn(final EmployeeSignIn apiEmployeeSignIn) {
		this.apiEmployeeSignIn = apiEmployeeSignIn;
		return this;
    }
    
	@Autowired
    private EmployeeRepository employeeRepository;

}