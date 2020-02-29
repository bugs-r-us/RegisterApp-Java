package edu.uark.registerapp.commands.employees;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
        
// TODO Auto-generated method stub
@Service
public class ActiveEmployeeExistsQuery implements ResultCommandInterface<EmployeeSignIn> {

    @Autowired
    private EmployeeRepository employeeRepository;
   

    //TODO: IDK WHAT TO RETURN
    @Override
    public EmployeeSignIn execute() {

      if(this.employeeRepository.existsByIsActive(true))
       {
           return new EmployeeSignIn();
           
       }else{
        throw new NotFoundException("User");
       }
    }
    public boolean isPresent()
    {
        if(this.employeeRepository.existsByIsActive(true))
            return true;
        else
            return false;
    }


}

   