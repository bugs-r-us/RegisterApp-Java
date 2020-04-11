package edu.uark.registerapp.controllers;

import java.util.UUID;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import edu.uark.registerapp.commands.activeUsers.ValidateActiveUserCommand;
import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.employees.EmployeeCreateCommand;
import edu.uark.registerapp.commands.employees.EmployeeQuery;
import edu.uark.registerapp.commands.employees.EmployeeUpdateCommand;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.controllers.enums.QueryParameterMessages;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.enums.EmployeeClassification;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

@RestController
@RequestMapping(value = "/api/employee")
public class EmployeeRestController extends BaseRestController {
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody ApiResponse createEmployee(
		@RequestBody final Employee employee,
		final HttpServletRequest request,
		final HttpServletResponse response
	) {

		boolean isInitialEmployee = employeeRepository.existsByIsActive(true);
		ApiResponse canCreateEmployeeResponse;

		// there are users
		if(isInitialEmployee){
			try {
				// get active user
				final Optional<ActiveUserEntity> activeUserEntity = activeUserRepository.findBySessionKey(request.getSession().getId());

				// not an active user
				if (activeUserEntity == null){
					response.setStatus(HttpStatus.FOUND.value());

					return(new ApiResponse())
					.setRedirectUrl(ViewNames.SIGN_IN.getRoute().concat(
						this.buildInitialQueryParameter(QueryParameterNames.ERROR_CODE.getValue(), 
						QueryParameterMessages.SESSION_NOT_ACTIVE.getKeyAsString())));	
				}
				// is active user
				else {
					if (!EmployeeClassification.isElevatedUser(activeUserEntity.get().getClassification())){
						response.setStatus(HttpStatus.FOUND.value());
						return(new ApiResponse())
							.setRedirectUrl(ViewNames.MAIN_MENU.getRoute().concat(
								this.buildInitialQueryParameter(QueryParameterNames.ERROR_CODE.getValue(),
								QueryParameterMessages.NO_PERMISSIONS_TO_VIEW.getKeyAsString())));
					}
					// not init 
					else{
						final Employee createdEmployee = this.employeeCreateCommand.setApiEmpoloyee(employee).execute();
						
						createdEmployee
						.setRedirectUrl(
							ViewNames.EMPLOYEE_DETAIL.getRoute().concat("/" + createdEmployee.getId().toString()));

						return createdEmployee.setIsInitialEmployee(isInitialEmployee);
					}
				}
				// canCreateEmployeeResponse =
				// 	this.redirectUserNotElevated(request, response);
			} catch (final NotFoundException e) {
				isInitialEmployee = true;
				canCreateEmployeeResponse = new ApiResponse();
			}

		}

		// if (!canCreateEmployeeResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
		// 	return canCreateEmployeeResponse;
		// }

		// TODO: Create an employee;
		final Employee createdEmployee = this.employeeCreateCommand.setApiEmpoloyee(employee).execute();;
		
		createdEmployee
				.setRedirectUrl(
					ViewNames.SIGN_IN.getRoute().concat(
						this.buildInitialQueryParameter(
							QueryParameterNames.EMPLOYEE_ID.getValue(),
							createdEmployee.getEmployeeId())));

		return createdEmployee.setIsInitialEmployee(isInitialEmployee);


		// boolean isInitialEmployee = false;
		// ApiResponse canCreateEmployeeResponse;
 
		// final ActiveUserEntity activeUserEntity = this.validateActiveUserCommand
		// .setSessionKey(request.getSession().getId()).execute();

			
		// try {
		// 	// if employees exist and current user not elevated
		// 	if(!employeeCreateCommand.isInitial() && EmployeeClassification.isElevatedUser(employee.getClassification())) {
		// 		response.setStatus(HttpStatus.FOUND.value());
		// 		return(new ApiResponse())
		// 			.setRedirectUrl(ViewNames.MAIN_MENU.getRoute().concat(
		// 				this.buildInitialQueryParameter(QueryParameterNames.ERROR_CODE.getValue(),
		// 				QueryParameterMessages.NO_PERMISSIONS_TO_VIEW.getKeyAsString())));

		// 	}
		// 	// no active user for current session
		// 	if(activeUserEntity == null) { //validate activ euser command
		// 		// this.redirectSessionNotActive(response);
		// 		// this also kinda seemed right ?
		// 		response.setStatus(HttpStatus.FOUND.value());
		// 		return(new ApiResponse())
		// 			.setRedirectUrl(ViewNames.SIGN_IN.getRoute().concat(
		// 				this.buildInitialQueryParameter(QueryParameterNames.ERROR_CODE.getValue(), 
		// 				QueryParameterMessages.SESSION_NOT_ACTIVE.getKeyAsString())));
		// 	}

		// 	// TODO: Query if any active employees exist
		// 	// somwwhere in here an employee is created
		// 	canCreateEmployeeResponse =
		// 		this.redirectUserNotElevated(request, response);
		// } catch (final NotFoundException e) {
		// 	isInitialEmployee = true;
		// 	canCreateEmployeeResponse = new ApiResponse();
		// }

		// if (!canCreateEmployeeResponse.getRedirectUrl().equals(StringUtils.EMPTY)) { 
		// 	return canCreateEmployeeResponse;
		// }

		// // TODO: Create an employee;
		// final Employee createdEmployee = new Employee();
		// // if first employee
		// if (isInitialEmployee) {
		// 	createdEmployee
		// 		.setRedirectUrl(
		// 			ViewNames.SIGN_IN.getRoute().concat(
		// 				this.buildInitialQueryParameter(
		// 					QueryParameterNames.EMPLOYEE_ID.getValue(),
		// 					createdEmployee.getEmployeeId())));
		// } else {
		// 	return this.employeeCreateCommand.setApiEmpoloyee(employee).execute();
		// 	// maybe ?
		// }

		// return createdEmployee.setIsInitialEmployee(isInitialEmployee);
	}

	@RequestMapping(value = "/{employeeId}", method = RequestMethod.PATCH)
	public @ResponseBody ApiResponse updateEmployee(
		@PathVariable final UUID employeeId,
		@RequestBody final Employee employee,
		final HttpServletRequest request,
		final HttpServletResponse response
	) {

		final ApiResponse elevatedUserResponse =
			this.redirectUserNotElevated(request, response);
		if (!elevatedUserResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
			return elevatedUserResponse;
		}

		// TODO: Update the employee
		// not an active user for the session
		// try catch method for 
		if(employee.getIsActive() == false) {
			response.setStatus(HttpStatus.FOUND.value());
			return(new ApiResponse())
				.setRedirectUrl(ViewNames.SIGN_IN.getRoute().concat(
					this.buildInitialQueryParameter(QueryParameterNames.ERROR_CODE.getValue(),
					QueryParameterMessages.SESSION_NOT_ACTIVE.getKeyAsString())));
					// fix error message thing for all of these
		}
		else if(!EmployeeClassification.isElevatedUser(employee.getClassification())) { // current user not elevated
			response.setStatus(HttpStatus.FOUND.value());
			return(new ApiResponse())
				.setRedirectUrl(ViewNames.MAIN_MENU.getRoute().concat(
					this.buildInitialQueryParameter(QueryParameterNames.ERROR_CODE.getValue(),
					QueryParameterMessages.SESSION_NOT_ACTIVE.getKeyAsString())));
		}
		else {
			return this.employeeUpdateCommand.setApiEmployee(employee).execute();
			// this probably isnt right
		}

		// return employee;
	}

	// Properties
	@Autowired 
	private EmployeeCreateCommand employeeCreateCommand;

	@Autowired
	private EmployeeUpdateCommand employeeUpdateCommand;

	@Autowired
	private ValidateActiveUserCommand validateActiveUserCommand;

	@Autowired
	private EmployeeQuery employeeQuery;

	@Autowired
	private ActiveEmployeeExistsQuery activeEmployeeExistsQuery;

	@Autowired
	private ActiveUserRepository activeUserRepository;

	@Autowired
	private EmployeeRepository employeeRepository;
}
