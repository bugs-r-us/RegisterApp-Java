package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.models.api.Transaction;
import edu.uark.registerapp.commands.transactions.TransactionQuery;
import edu.uark.registerapp.controllers.enums.QueryParameterMessages;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.enums.EmployeeClassification;

@Controller
@RequestMapping(value = "/mainMenu")
public class MainMenuRouteController extends BaseRouteController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView start(
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {
		// Check for error message / code
		// This can be simplified...
		String errorMsg = queryParameters.get(ViewModelNames.ERROR_MESSAGE.getValue());
		if (errorMsg == null)
			try {
				errorMsg = QueryParameterMessages.mapMessage(Integer.parseInt(queryParameters.
						get(QueryParameterNames.ERROR_CODE.getValue())));	
			} catch (Exception e) { }

		final Optional<ActiveUserEntity> activeUserEntity = this.getCurrentUser(request);
		
		
		if (!activeUserEntity.isPresent()) {
			return this.buildInvalidSessionResponse();
		}
		
		ModelAndView modelAndView =
			this.setErrorMessageFromQueryString(
				new ModelAndView(ViewNames.MAIN_MENU.getViewName()),
				queryParameters);
			

		modelAndView.addObject(ViewModelNames.ERROR_MESSAGE.getValue(), errorMsg);

		if (EmployeeClassification.isElevatedUser(activeUserEntity.get().getClassification())){
			modelAndView.addObject("isElevated", true);
		}else{
			modelAndView.addObject("isElevated", false);
		}
		this.transactionQuery.setEmployeeId(activeUserEntity.get().getEmployeeId());
		try {
			Transaction t = this.transactionQuery.execute(); 
			modelAndView.addObject("start", "Update Transaction");
			modelAndView.addObject("viewCurrent", true);

		  }
		  catch(Exception e) {
			modelAndView.addObject("start", "Start Transaction");
			modelAndView.addObject("viewCurrent", false);
		  }

		modelAndView.addObject(
			ViewModelNames.IS_ELEVATED_USER.getValue(),
			true);
		
		return modelAndView;
	}

	@Autowired
	private TransactionQuery transactionQuery;

}