package edu.uark.registerapp.commands.employees;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.joran.conditional.ElseAction;
import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
        // TODO Auto-generated method stub
@Service
public class ActiveEmployeeExistsQuery implements ResultCommandInterface<EmployeeSignIn> {

    private Object EmployeeRepository;

    @Override
    public EmployeeSignIn execute() {

        boolean pplExists= ((edu.uark.registerapp.models.repositories.EmployeeRepository) this.EmployeeRepository)
                .existsByIsActive(true);

       if(pplExists)
       {
           //WILL HAVE TO CHANGE LATER
           return new EmployeeSignIn();
           
       }else{
        throw new NotFoundException("User");
       }

       
    }

}

   