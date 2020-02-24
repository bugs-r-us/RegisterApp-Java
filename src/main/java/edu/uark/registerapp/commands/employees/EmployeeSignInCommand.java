package edu.uark.registerapp.commands.employees;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.UnauthorizedException;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;



@Service
public class EmployeeSignInCommand implements ResultCommandInterface<EmployeeEntity> {
    @Override
    public EmployeeEntity execute()
    {
        //Validate the incoming Employee request object
        
        return 
    }
    // Properties
    private String sessionKey;
    private EmployeeSignIn employeeSignIn;
    //FINISH UP HERE

	public String getSessionKey() {
		return this.sessionKey;
	}

	public EmployeeSignInCommand setSessionKey(final String sessionKey) {
		this.sessionKey = sessionKey;
		return this;
	}

	@Autowired
	private EmployeeRepository employeeRepository;
}