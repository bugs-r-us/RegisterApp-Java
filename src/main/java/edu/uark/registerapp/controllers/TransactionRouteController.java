package edu.uark.registerapp.controllers;


import java.util.Collections;
import java.util.Comparator;
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
import edu.uark.registerapp.commands.transactions.TransactionCreateCommand;
import edu.uark.registerapp.commands.transactions.TransactionCancelCommand;
import edu.uark.registerapp.commands.transactions.TransactionContentDeleteCommand;
import edu.uark.registerapp.commands.transactions.TransactionSubmitTrans;


import edu.uark.registerapp.commands.transactions.TransactionQuery;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Transaction;
import edu.uark.registerapp.models.api.TransactionContent;
import edu.uark.registerapp.models.entities.ActiveUserEntity;


import edu.uark.registerapp.commands.products.ProductQuery;
import edu.uark.registerapp.models.api.Test;


class SortByLookup implements Comparator<Test>{
	public int compare(Test a, Test b){
		return a.getProduct().getLookupCode().compareTo(b.getProduct().getLookupCode());
	}
}


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


		List<Test> tester= new LinkedList <Test>();
		for (TransactionContent c: content )
		{
			tester.add( new Test(c, this.productQuery.setProductId(c.getProductID()).execute()));
		}
		
		Collections.sort(tester, new SortByLookup());		

		if (content.isEmpty()){
			modelAndView.addObject("emptyCart", true);
		}else{
			modelAndView.addObject("emptyCart", false);  
			modelAndView.addObject("listTest", tester);  
			modelAndView.addObject("transTotal", t.getTotal());

			//modelAndView.addObject("productList", tester.getTestProduct());
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

	@RequestMapping(value = "/start", method = RequestMethod.GET)
	public ModelAndView startTransaction(final HttpServletRequest request) {

			
		final Optional<ActiveUserEntity> activeUserEntity = this.getCurrentUser(request);

		this.transactionQuery.setEmployeeId(activeUserEntity.get().getEmployeeId());

		try{
			this.transactionQuery.execute(); 	
		}catch(Exception e){
			// Creates Transaction if one doesn't exist
			Transaction apiTransaction = new Transaction();
			apiTransaction.setEmployeeId(activeUserEntity.get().getEmployeeId());
			apiTransaction.setTotal(0);
			apiTransaction.setStatus(0);

			this.TransactionCreateCommand.setApiTransaction(apiTransaction);
			this.TransactionCreateCommand.execute();
		}
        
		// Redirects to ProductListing page
		return new ModelAndView(
			REDIRECT_PREPEND.concat(ViewNames.PRODUCT_LISTING.getRoute()));
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView deleteTransaction(final HttpServletRequest request) {

		final Optional<ActiveUserEntity> activeUserEntity = this.getCurrentUser(request);
	    this.transactionQuery.setEmployeeId(activeUserEntity.get().getEmployeeId());

		Transaction t = this.transactionQuery.execute(); 
		this.transactionContentsQuery.setTransactionId(t.getTransactionId());
		List<TransactionContent> tc = this.transactionContentsQuery.execute();
		this.TransactionCancelCommand.setTransactionID(t.getTransactionId()).execute();
		
		for(TransactionContent transCon : tc) {
			transactionContentDelete.setID(transCon.getId()).execute();
		}
		return new ModelAndView(
			REDIRECT_PREPEND.concat(ViewNames.MAIN_MENU.getRoute()));
	}

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public ModelAndView submitTransaction(final HttpServletRequest request) {

		final Optional<ActiveUserEntity> activeUserEntity = this.getCurrentUser(request);
	    this.transactionQuery.setEmployeeId(activeUserEntity.get().getEmployeeId());

        Transaction t = this.transactionQuery.execute(); 

		this.TransactionSubmit.setTransactionID(t.getTransactionId()).execute();

		return new ModelAndView(
			REDIRECT_PREPEND.concat(ViewNames.MAIN_MENU.getRoute()));
	}



	// Properties
    @Autowired
	private TransactionQuery transactionQuery;
	@Autowired
	private TransactionCreateCommand TransactionCreateCommand;
	@Autowired
	private TransactionCancelCommand TransactionCancelCommand;
	@Autowired
	private TransactionSubmitTrans TransactionSubmit;
    @Autowired
	private TransactionContentsQuery transactionContentsQuery;
	@Autowired
	private TransactionContentDeleteCommand transactionContentDelete;
	// Properties
	@Autowired
	private ProductQuery productQuery;
}
