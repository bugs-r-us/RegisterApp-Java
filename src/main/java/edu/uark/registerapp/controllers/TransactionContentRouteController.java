package edu.uark.registerapp.controllers;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import edu.uark.registerapp.models.api.Transaction;
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
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.repositories.TransactionContentRepository;
import edu.uark.registerapp.commands.transactions.TransactionContentCreateCommand;
import edu.uark.registerapp.commands.transactions.TransactionContentUpdateCommand;
import edu.uark.registerapp.commands.transactions.TransactionContentsQuery;
import edu.uark.registerapp.commands.transactions.TransactionQuery;

@Controller
@RequestMapping(value = "/")
public class TransactionContentRouteController extends BaseRouteController {

	@RequestMapping(value = "/transactionContent", method = RequestMethod.POST)
	public ModelAndView addTransactionContent(TransactionContentAdd transactionContentAdd, HttpServletRequest request) {
		// transcontentadd has proudct id as string and quantity as int
		// i made the creaate commmand might work idk
		// need to get the employee id and the linked transaction
		// you'll need to mannually add data to your db for that
		// transactionquery works with finding the trans by the employee see
		// transactionroutecontroller
		final TransactionContent tc = new TransactionContent();
		final Optional<ActiveUserEntity> activeUserEntity = this.getCurrentUser(request);

		boolean test = false; // testing dis

		// link each row to transaction
		this.transactionQuery.setEmployeeId(activeUserEntity.get().getEmployeeId());
		Transaction t = this.transactionQuery.execute();
		TransactionContentAdd tca = transactionContentAdd;
		this.transactionContentsQuery.setTransactionId(t.getTransactionId());

		List<TransactionContent> tc1 = this.transactionContentsQuery.execute();
		try {
			// see if product already exists in db and update quantity and price
			for (TransactionContent tc2 : tc1) {
				if (UUID.fromString(tca.getProductId()).equals(tc2.getProductID())) {
					test = true;
					int quantity = tca.getQuantity() + tc2.getQuantity();
					tc2.setQuantity(quantity);
					float price = tc2.getPrice() + (tca.getPrice() * tca.getQuantity());
					tc2.setPrice(price);
					transactionContentUpdate.setID(tc2.getId()).setapiTCA(tc2).execute();
				}
			}
			// if !exist, add to db
			if (test == false) {
				tc.setProductID(UUID.fromString(tca.getProductId()));
				tc.setQuantity(tca.getQuantity());
				tc.setPrice(tca.getPrice() * tca.getQuantity()); // made price = price * quantity
				tc.setTransactionID(t.getTransactionId());
				transactionContentCreate.setApiTransactionContent(tc).execute();
			}

			return new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.MAIN_MENU.getRoute()));
		} catch (Exception exception) {
			return new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.SIGN_IN.getRoute())).addObject(
					ViewModelNames.ERROR_MESSAGE.getValue(), QueryParameterMessages.SIGN_IN_ERROR.getMessage());
		}
	}

	@Autowired
	private ActiveEmployeeExistsQuery activeEmployeeExistsQuery;
	@Autowired
	private EmployeeSignInCommand employeeSignInCommand;
	@Autowired
	private TransactionQuery transactionQuery;
	@Autowired
	private TransactionContentCreateCommand transactionContentCreate;
	@Autowired
	private TransactionContentsQuery transactionContentsQuery;
	@Autowired
	private TransactionContentRepository transactionContentRepository;
	@Autowired
	private TransactionContentUpdateCommand transactionContentUpdate;
}
