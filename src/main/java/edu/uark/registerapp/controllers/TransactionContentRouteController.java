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
import edu.uark.registerapp.commands.employees.EmployeeSignInCommand;
import edu.uark.registerapp.controllers.enums.QueryParameterMessages;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.api.TransactionContent;
import edu.uark.registerapp.models.api.TransactionContentAdd;
import edu.uark.registerapp.models.api.Employee;

@Controller
@RequestMapping(value = "/")
public class TransactionContentRouteController extends BaseRouteController {

	@RequestMapping(value= "/transactionContent", method = RequestMethod.POST)
	public ModelAndView addTransactionContent(
		TransactionContentAdd transactionContentAdd,
		HttpServletRequest request
	) 
	{
        // transcontentadd has proudct id as string  and quantity as int
        // i made the creaate commmand might work idk
        // need to get the employee  id and the linked transaction  
        //  you'll need to mannually add data to your db for that
        // transactionquery works with finding the trans by the employee  see transactionroutecontroller

		String sessionID = request.getSession(true).getId();
		TransactionContentAdd t = transactionContentAdd;

		try{
			// this.employeeSignInCommand.setapiEmployeeSignIn(employeeSignIn).setSessionKey(sessionID).execute();
			return new ModelAndView(
						REDIRECT_PREPEND.concat(ViewNames.MAIN_MENU.getRoute()));
		}catch (Exception exception){
			return new ModelAndView(
						REDIRECT_PREPEND.concat(ViewNames.SIGN_IN.getRoute()))
						.addObject(ViewModelNames.ERROR_MESSAGE.getValue(), QueryParameterMessages.SIGN_IN_ERROR.getMessage());
		}
	}

	@Autowired
	private ActiveEmployeeExistsQuery activeEmployeeExistsQuery;
	@Autowired
	private EmployeeSignInCommand employeeSignInCommand;
}

