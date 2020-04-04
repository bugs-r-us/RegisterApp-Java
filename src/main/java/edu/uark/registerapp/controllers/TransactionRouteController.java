package edu.uark.registerapp.controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.transactions.TransactionContentsQuery;
import edu.uark.registerapp.commands.transactions.TransactionQuery;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Transaction;
import edu.uark.registerapp.models.api.TransactionContent;
import edu.uark.registerapp.models.entities.ActiveUserEntity;


@Controller
@RequestMapping(value = "/transactionView")
public class TransactionRouteController extends BaseRouteController  {
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showTransaction(final HttpServletRequest request) {
		ModelAndView modelAndView =
			new ModelAndView(ViewNames.TRANSACTION_VIEW.getViewName());

		final Optional<ActiveUserEntity> activeUserEntity = this.getCurrentUser(request);

        this.transactionQuery.setEmployeeId(activeUserEntity.get().getEmployeeId());
        Transaction t = this.transactionQuery.execute(); 

        List<TransactionContent> content = this.transactionContentsQuery.setTransactionId(t.getTransactionId()).execute();

		if (content.isEmpty()){
			modelAndView.addObject("emptyCart", true);
		}else{
			modelAndView.addObject("emptyCart", false);
		}

		try {
			// modelAndView.addObject( ViewModelNames.TRANSACTION.getValue(), this.transactionQuery.execute());

			modelAndView.addObject( ViewModelNames.TRANSACTION.getValue(), content);

		} catch (final Exception e) {
			modelAndView.addObject(ViewModelNames.ERROR_MESSAGE.getValue(),e.getMessage());
			modelAndView.addObject(ViewModelNames.TRANSACTION.getValue(),(new Transaction[0]));
		}
		
		return modelAndView;
	}

	// Properties
    @Autowired
    private TransactionQuery transactionQuery;
    @Autowired
    private TransactionContentsQuery transactionContentsQuery;
}
