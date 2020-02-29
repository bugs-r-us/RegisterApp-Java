package edu.uark.registerapp.controllers;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.employees.EmployeeSignInCommand;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.entities.ActiveUserEntity;

@Controller
@RequestMapping(value = "/")
public class SignInRouteController extends BaseRouteController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showSignIn( @RequestParam Map<String, String> queryparams) {
		
		if(this.employeeQuery.isPresent())
		{
			ModelAndView modelAndView = new ModelAndView(ViewNames.SIGN_IN.getViewName());
			return modelAndView;
		}
		else
		{
			ModelAndView modelAndView = new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName());
			return modelAndView;
		}
		
	}

	@RequestMapping(value= "/signIn", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView performSignIn(
		// TODO: Define an object that will represent the sign in request and add it as a parameter here
		EmployeeSignIn employeeSignIn,
		HttpServletRequest request
	) 
	{
		try{
		Employee employee= this.employeeSignInCommand.setapiEmployeeSignIn(employeeSignIn)
								  .findEmployee();
	
		new ActiveUserEntity(employee, request.getRequestedSessionId());
	
		return new ModelAndView(
			REDIRECT_PREPEND.concat(
				ViewNames.MAIN_MENU.getRoute()));
		}
		catch(final Exception e)
		{
			//TODO: SEND BACK AN ERROR MESSAGE 	
			return new ModelAndView(
				REDIRECT_PREPEND.concat(
					ViewNames.SIGN_IN.getRoute()));
		}

	}
	@Autowired
	private ActiveEmployeeExistsQuery employeeQuery;
	@Autowired
	private EmployeeSignInCommand employeeSignInCommand;
}

