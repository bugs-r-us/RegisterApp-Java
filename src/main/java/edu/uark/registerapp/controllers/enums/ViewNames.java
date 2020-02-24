package edu.uark.registerapp.controllers.enums;

public enum ViewNames {             //TEMPLATE NAMES ARE
	SIGN_IN("signIn", "/"),
	MAIN_MENU("mainMenu"),
	PRODUCT_DETAIL("productDetail"),
	EMPLOYEE_DETAIL("employeeDetail"),
	PRODUCT_LISTING("productListing");
	
	public String getRoute() {
		return this.route;
	}
	public String getViewName() {
		return this.viewName;
	}

	private String route;
	private String viewName;

	// /signIn
	private ViewNames(final String viewName) {
		this(viewName, "/".concat(viewName));
	}

	private ViewNames(final String viewName, final String route) {
		this.route = route;
		this.viewName = viewName;
	}
}
