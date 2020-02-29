package edu.uark.registerapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.uark.registerapp.commands.activeUsers.ActiveUserDeleteCommand;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.entities.EmployeeEntity;

@RestController
@RequestMapping(value = "/api")
public class SignInRestController extends BaseRestController {
	@RequestMapping(value="/signOut", method = RequestMethod.DELETE)
	public @ResponseBody ApiResponse removeActiveUser(
		final HttpServletRequest request
	) {

	
		this.activeUserDeleteCommand.setSessionKey(request.getSession().getId()).execute();
		
		return (new ApiResponse())
			.setRedirectUrl(ViewNames.SIGN_IN.getRoute());
	}

	@Autowired
	private ActiveUserDeleteCommand activeUserDeleteCommand;
	
}
