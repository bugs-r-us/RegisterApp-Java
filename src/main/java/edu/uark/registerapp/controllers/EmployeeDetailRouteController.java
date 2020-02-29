package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.management.Query;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.employees.EmployeeQuery;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.controllers.enums.QueryParameterMessages;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.ActiveUserEntity;

@Controller
@RequestMapping(value = "/employeeDetail")
public class EmployeeDetailRouteController extends BaseRouteController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView start(
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {
		ModelAndView modelAndView;
		final Optional<ActiveUserEntity> activeUserEntity =
			this.getCurrentUser(request);
			// might need something else here..
		if(this.isElevatedUser(activeUserEntity.get()) || this.employeeQuery.isEREmpty()) {
			modelAndView = new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName()); 
			//not sure if right
		}
		else if(!activeUserEntity.isPresent()) {
			modelAndView = new ModelAndView("redirect:/" + ViewNames.PRODUCT_DETAIL.getViewName())
				.addObject(ViewModelNames.ERROR_MESSAGE.getValue(), QueryParameterMessages.NOT_DEFINED
					.getMessage()); //not sure how correct this is..
		}
		else {
			modelAndView = new ModelAndView("redirect:/" + ViewNames.MAIN_MENU.getViewName())
				.addObject(ViewModelNames.ERROR_MESSAGE.getValue(), QueryParameterMessages.NOT_DEFINED
					.getMessage()); // not sure if correct
		}
		// TODO: Logic to determine if the user associated with the current session
		//  is able to create an employee
		// return new ModelAndView(ViewModelNames.EMPLOYEE_TYPES.getValue());
		return modelAndView;
	}

	@RequestMapping(value = "/{employeeId}", method = RequestMethod.GET)
	public ModelAndView startWithEmployee(
		@PathVariable final UUID employeeId,
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {
		final ModelAndView modelAndView;
		final Optional<ActiveUserEntity> activeUserEntity =
			this.getCurrentUser(request);

		if (!activeUserEntity.isPresent()) {
			modelAndView = new ModelAndView("redirect:/" + ViewNames.SIGN_IN.getViewName())
				.addObject(ViewModelNames.ERROR_MESSAGE.getValue(), QueryParameterMessages.NOT_DEFINED
					.getMessage());
			// return this.buildInvalidSessionResponse();
		} else if (!this.isElevatedUser(activeUserEntity.get())) {
			modelAndView = new ModelAndView("redirect:/" + ViewNames.MAIN_MENU.getViewName()).addObject(
				ViewModelNames.ERROR_MESSAGE.getValue(), QueryParameterMessages.NOT_DEFINED.getMessage()); //error message
			// return this.buildNoPermissionsResponse();
		} else { //add try catch stuff
			modelAndView = new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName()).addObject(
				ViewModelNames.EMPLOYEE_TYPES.getValue(), this.employeeQuery
					.setRecordID(employeeId).execute());
		}
		// TODO: Query the employee details using the request route parameter
		// TODO: Serve up the page
		// return new ModelAndView(ViewModelNames.EMPLOYEE_TYPES.getValue());
		return modelAndView;
	}

	// Helper methods
	private boolean activeUserExists() {
		// TODO: Helper method to determine if any active users Exist
		return true;
	}
	// Properties
	@Autowired
	private EmployeeQuery employeeQuery;
}