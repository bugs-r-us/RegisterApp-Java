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
import edu.uark.registerapp.commands.products.ProductQuery;
import edu.uark.registerapp.controllers.enums.QueryParameterMessages;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.api.TranasctionIncrementDecrement;
import edu.uark.registerapp.models.api.TransactionContent;
import edu.uark.registerapp.models.api.TransactionContentAdd;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.repositories.TransactionContentRepository;
import edu.uark.registerapp.commands.transactions.TransactionContentCreateCommand;
import edu.uark.registerapp.commands.transactions.TransactionContentDeleteCommand;
import edu.uark.registerapp.commands.transactions.TransactionContentQuery;
import edu.uark.registerapp.commands.transactions.TransactionContentUpdateCommand;
import edu.uark.registerapp.commands.transactions.TransactionUpdateCommand;
import edu.uark.registerapp.commands.transactions.TransactionContentsQuery;
import edu.uark.registerapp.commands.transactions.TransactionQuery;

@Controller
@RequestMapping(value = "/")
public class TransactionContentRouteController extends BaseRouteController {

	@RequestMapping(value = "/transactionContent", method = RequestMethod.POST)
	public ModelAndView addTransactionContent(TransactionContentAdd transactionContentAdd, HttpServletRequest request) {
		final TransactionContent tc = new TransactionContent();
		final Optional<ActiveUserEntity> activeUserEntity = this.getCurrentUser(request);
		
		
		// link each row to transaction
		this.transactionQuery.setEmployeeId(activeUserEntity.get().getEmployeeId());
		Transaction t = this.transactionQuery.execute();
		TransactionContentAdd tca = transactionContentAdd;
		this.transactionContentsQuery.setTransactionId(t.getTransactionId());

		List<TransactionContent> tc1 = this.transactionContentsQuery.execute();

		boolean test = false;
		float price = tca.getPrice() * tca.getQuantity();; // made price = price * quantity
		int quantity = tca.getQuantity();
		try {
			// see if product already exists in db and update quantity and price
			// TODO: check that this works for other transaction db tuples
			for (TransactionContent tc2 : tc1) {
				if (UUID.fromString(tca.getProductId()).equals(tc2.getProductID())) {
					test = true;
					tc2.setQuantity(quantity + tc2.getQuantity());
					tc2.setPrice(tc2.getPrice() + price);
					transactionContentUpdate.setID(tc2.getId()).setapiTCA(tc2).execute();
				}
			}
			// if !exist, add to db
			if (test == false) {
				tc.setProductID(UUID.fromString(tca.getProductId()));
				tc.setQuantity(quantity);
				price = tca.getPrice() * tca.getQuantity();
				tc.setPrice(price);
				tc.setTransactionID(t.getTransactionId());
				transactionContentCreate.setApiTransactionContent(tc).execute();
			}
			updateTotal(t, price);

			return new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.MAIN_MENU.getRoute()));
		} catch (Exception exception) {
			return new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.SIGN_IN.getRoute())).addObject(
					ViewModelNames.ERROR_MESSAGE.getValue(), QueryParameterMessages.SIGN_IN_ERROR.getMessage());
		}
	}

	@RequestMapping(value = "/increment", method = RequestMethod.POST)
	public ModelAndView transactionContentIncrement(TranasctionIncrementDecrement tranasctionIncrementDecrement, HttpServletRequest request) {
		UUID transactionContentID = UUID.fromString(tranasctionIncrementDecrement.getTransactionContentID());
		TransactionContent tc = this.transactionContentQuery.setTransactionContentId(transactionContentID).execute();
		Product p = this.productQuery.setProductId(tc.getProductID()).execute();

		final Optional<ActiveUserEntity> activeUserEntity = this.getCurrentUser(request);
		this.transactionQuery.setEmployeeId(activeUserEntity.get().getEmployeeId());
		Transaction t = this.transactionQuery.execute();
		
		int newQuantity = tc.getQuantity() + 1;
		float newPrice = newQuantity * p.getPrice();
		float difference = p.getPrice();
		tc.setQuantity(newQuantity);
		tc.setPrice(newPrice);

		transactionContentUpdate.setID(transactionContentID).setapiTCA(tc).execute();

		updateTotal(t, difference);

		return new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.TRANSACTION_VIEW.getRoute()));
	}


	@RequestMapping(value = "/decrement", method = RequestMethod.POST)
	public ModelAndView transactionContentDecrement(TranasctionIncrementDecrement tranasctionIncrementDecrement, HttpServletRequest request) {
		UUID transactionContentID = UUID.fromString(tranasctionIncrementDecrement.getTransactionContentID());
		TransactionContent tc = this.transactionContentQuery.setTransactionContentId(transactionContentID).execute();
		Product p = this.productQuery.setProductId(tc.getProductID()).execute();

		final Optional<ActiveUserEntity> activeUserEntity = this.getCurrentUser(request);
		this.transactionQuery.setEmployeeId(activeUserEntity.get().getEmployeeId());
		Transaction t = this.transactionQuery.execute();
		
		int newQuantity = tc.getQuantity() - 1;
		float newPrice = newQuantity * p.getPrice();
		float difference = -1 * p.getPrice();
		tc.setQuantity(newQuantity);
		tc.setPrice(newPrice);

		transactionContentUpdate.setID(transactionContentID).setapiTCA(tc).execute();

		updateTotal(t, difference);

		return new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.TRANSACTION_VIEW.getRoute()));
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public ModelAndView transactionContentRemove(TranasctionIncrementDecrement tranasctionIncrementDecrement, HttpServletRequest request) {
		UUID transactionContentID = UUID.fromString(tranasctionIncrementDecrement.getTransactionContentID());
		TransactionContent tc = this.transactionContentQuery.setTransactionContentId(transactionContentID).execute();
		
		try {
			transactionContentDelete.setID(tc.getId()).execute();
			return new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.TRANSACTION_VIEW.getRoute()));
		} catch (Exception exception) {
			return new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.SIGN_IN.getRoute())).addObject(
					ViewModelNames.ERROR_MESSAGE.getValue(), QueryParameterMessages.SIGN_IN_ERROR.getMessage());
		}
		
	}
	//helper method because needed for conditionals
	public void updateTotal(Transaction t, float price) {
		//updates the total of tht transaction
		float total = t.getTotal() + price;
		t.setTotal(total);
		transactionUpdate.setID(t.getTransactionId()).setApiTransaction(t).execute();

	}
	@Autowired
	private TransactionQuery transactionQuery;
	@Autowired
	private ProductQuery productQuery;
	@Autowired
	private TransactionContentCreateCommand transactionContentCreate;
	@Autowired
	private TransactionContentsQuery transactionContentsQuery;
	@Autowired
	private TransactionContentQuery transactionContentQuery;
	@Autowired
	private TransactionContentUpdateCommand transactionContentUpdate;
	@Autowired
	private TransactionUpdateCommand transactionUpdate;
	@Autowired
	private TransactionContentDeleteCommand transactionContentDelete;
}
