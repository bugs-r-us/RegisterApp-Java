package edu.uark.registerapp.controllers;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.employees.EmployeeQuery;
import edu.uark.registerapp.commands.employees.EmployeeSignInCommand;
import edu.uark.registerapp.controllers.enums.QueryParameterMessages;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.api.Employee;

@Controller
@RequestMapping(value = "/")
public class SignInRouteController extends BaseRouteController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showSignIn(@RequestParam final Map<String, String> queryParameters) {

		if(this.activeEmployeeExistsQuery.isPresent())
		{
			ModelAndView modelAndView = new ModelAndView(ViewNames.SIGN_IN.getViewName());

			String employeeId = queryParameters.get("employeeId");
			if (employeeId != null){
				//http://localhost:8080/?employeeId=00582 figure out

				modelAndView.addObject("employeeId", employeeId);
			}

			// Check for error message / code
			// This can be simplified...
			String errorMsg = queryParameters.get(ViewModelNames.ERROR_MESSAGE.getValue());
			if (errorMsg == null)
				try {
					errorMsg = QueryParameterMessages.mapMessage(Integer.parseInt(queryParameters.
							get(QueryParameterNames.ERROR_CODE.getValue())));	
				} catch (Exception e) { }
		
			modelAndView.addObject(ViewModelNames.ERROR_MESSAGE.getValue(), errorMsg);
			
			return modelAndView;
		}
		else
		{
			 ModelAndView modelAndView = new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName())
				.addObject(ViewModelNames.EMPLOYEE.getValue(), new Employee());
			return modelAndView;
		}
		
	}

	@RequestMapping(value= "/signIn", method = RequestMethod.POST)
	public ModelAndView performSignIn(
		// TODO: Define an object that will represent the sign in request and add it as a parameter here
		EmployeeSignIn employeeSignIn,
		HttpServletRequest request
	) 
	{
		String sessionID = request.getSession(true).getId();
		EmployeeSignIn es = employeeSignIn;

		try{
			this.employeeSignInCommand.setapiEmployeeSignIn(employeeSignIn).setSessionKey(sessionID).execute();
			return new ModelAndView(
						REDIRECT_PREPEND.concat(ViewNames.MAIN_MENU.getRoute()));
		}catch (Exception exception){
			return new ModelAndView(
						REDIRECT_PREPEND.concat(ViewNames.SIGN_IN.getRoute()))
						.addObject(ViewModelNames.ERROR_MESSAGE.getValue(), QueryParameterMessages.SIGN_IN_ERROR.getMessage());
		}

		// try{
		// Employee employee = employeeQuery.
		// = this.employeeSignInCommand.setapiEmployeeSignIn(employeeSignIn).
		// 						  .findEmployee();
	
		// new ActiveUserEntity(employee, request.getRequestedSessionId());
	
		// return new ModelAndView(
		// 	REDIRECT_PREPEND.concat(
		// 		ViewNames.MAIN_MENU.getRoute()));
		// }
		// catch(final Exception e)
		// {
		// 	//TODO: SEND BACK AN ERROR MESSAGE 	
		// 	return new ModelAndView(
		// 		REDIRECT_PREPEND.concat(
		// 			ViewNames.SIGN_IN.getRoute()));
		// }

	}
	@Autowired
	private EmployeeQuery employeeQuery;
	@Autowired
	private ActiveEmployeeExistsQuery activeEmployeeExistsQuery;
	@Autowired
	private EmployeeSignInCommand employeeSignInCommand;
}

