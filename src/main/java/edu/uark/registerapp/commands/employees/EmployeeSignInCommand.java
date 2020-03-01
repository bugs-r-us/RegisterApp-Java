package edu.uark.registerapp.commands.employees;

import java.util.Arrays;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

@Service
public class EmployeeSignInCommand implements ResultCommandInterface<Employee> {

//FINISH THIS
    @Override
    public Employee execute()
    {
        this.validateProperties();
        Employee e = this.findEmployee();
        this.createActiveUserEntity();
        return e;
	}

    
//HELPER METHODS
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
        byte[] requestPW = this.apiEmployeeSignIn.getPassword().getBytes();
        boolean passwordMatch=Arrays.equals(queriedEmployee.get().getPassword(),requestPW);
            
        if(doesExist==true && passwordMatch)
        {
            return new Employee(queriedEmployee.get());
        }else
        {
            throw new NotFoundException("Employee");
        }
    }

    @Transactional
    private ActiveUserEntity createActiveUserEntity() {

        EmployeeEntity employeeEntity = this.employeeRepository.findByEmployeeId(Integer.parseInt(this.apiEmployeeSignIn.getEmployeeID())).get();
        Optional<ActiveUserEntity> activeUserEntity = this.activeUserRepository.findByEmployeeId(employeeEntity.getId());

        if(activeUserEntity.isPresent() == true){
            activeUserEntity.get().setSessionKey(this.getSessionKey());
            return this.activeUserRepository.save(activeUserEntity.get());
        }else{
            ActiveUserEntity tmpActiveUserEntity = new ActiveUserEntity(employeeEntity, this.sessionKey);
            return this.activeUserRepository.save(tmpActiveUserEntity);
        }


    // try{
    //     Optional<ActiveUserEntity> activeUserEntity = this.activeUserRepository.findByEmployeeId(UUID.fromString(this.apiEmployeeSignIn.getEmployeeID()));
    //     activeUserEntity.get().setSessionKey(this.getSessionKey());
    //    return this.activeUserRepository.save(activeUserEntity.get());
    // }catch(Exception exc){
    //     EmployeeEntity e = this.employeeRepository.findByEmployeeId(Integer.parseInt(this.apiEmployeeSignIn.getEmployeeID())).get();
    //     ActiveUserEntity tmpActiveUserEntity = new ActiveUserEntity(e, this.sessionKey);
    //     return this.activeUserRepository.save(tmpActiveUserEntity);
    // }
   
    // if(activeUserEntity != null)
    // {
    //    activeUserEntity.get().setSessionKey(this.getSessionKey());
    //    return this.activeUserRepository.save(activeUserEntity.get());
    // }
    // else
    // {
    //     EmployeeEntity e = this.employeeRepository.findByEmployeeId(Integer.parseInt(this.apiEmployeeSignIn.getEmployeeID())).get();
    //     return this.activeUserRepository.save(
	// 		new ActiveUserEntity(
    //             e, this.setSessionKey(this.getSessionKey())));
    // }

    }

// Properties
    private String sessionKey;
    public String getSessionKey() {
		return this.sessionKey;
	}

	public EmployeeSignInCommand setSessionKey(final String sessionKey) {
		this.sessionKey = sessionKey;
		return this;
    }
    
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

    @Autowired
    private ActiveUserRepository activeUserRepository; 

}
