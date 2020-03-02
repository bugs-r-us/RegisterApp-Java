package edu.uark.registerapp.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.products.ProductsQuery;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.enums.EmployeeClassification;

@Controller
@RequestMapping(value = "/productListing")
public class ProductListingRouteController extends BaseRouteController  {
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showProductListing(final HttpServletRequest request) {
		ModelAndView modelAndView =
			new ModelAndView(ViewNames.PRODUCT_LISTING.getViewName());


		final Optional<ActiveUserEntity> activeUserEntity = this.getCurrentUser(request);

		if (EmployeeClassification.isElevatedUser(activeUserEntity.get().getClassification())){
			modelAndView.addObject("isElevated", true);
		}else{
			modelAndView.addObject("isElevated", false);
		}

		try {
			modelAndView.addObject( ViewModelNames.PRODUCTS.getValue(), this.productsQuery.execute());

		} catch (final Exception e) {
			modelAndView.addObject(
				ViewModelNames.ERROR_MESSAGE.getValue(),
				e.getMessage());
			modelAndView.addObject(
				ViewModelNames.PRODUCTS.getValue(),
				(new Product[0]));
		}
		
		return modelAndView;
	}

	// Properties
	@Autowired
	private ProductsQuery productsQuery;
}
