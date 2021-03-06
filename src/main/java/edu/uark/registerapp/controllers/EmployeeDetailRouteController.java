package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.employees.EmployeeQuery;
import edu.uark.registerapp.controllers.enums.QueryParameterMessages;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
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


			boolean isElevated = false;
			try{
				isElevated = this.isElevatedUser(activeUserEntity.get());
			}catch(Exception exception){
				isElevated = false;
			}

		if( isElevated || !activeUserExists()) {
			modelAndView = new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName())
				.addObject(ViewModelNames.EMPLOYEE.getValue(), new Employee());
			//not sure if right
		}
		// no active user for current session
		else if(!activeUserEntity.isPresent()) {
			return this.buildInvalidSessionResponse();
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
			return this.buildInvalidSessionResponse();

		} else if (!this.isElevatedUser(activeUserEntity.get())) {
			return this.buildNoPermissionsResponse();
			
		} else { //add try catch stuff
			Employee e = this.employeeQuery.setRecordID(employeeId).execute();

			modelAndView = new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName()).addObject(
				ViewModelNames.EMPLOYEE.getValue(), e);
		}
		// TODO: Query the employee details using the request route parameter
		// TODO: Serve up the page
		// return new ModelAndView(ViewModelNames.EMPLOYEE_TYPES.getValue());
		return modelAndView;
	}

	// Helper methods
	private boolean activeUserExists() {
		return this.activeEmployeeExistsQuery.isPresent();
	}
	
	// Properties
	@Autowired
	private EmployeeQuery employeeQuery;

	@Autowired
	private ActiveEmployeeExistsQuery activeEmployeeExistsQuery;
}