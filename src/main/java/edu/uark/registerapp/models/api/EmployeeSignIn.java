package edu.uark.registerapp.models.api;

import edu.uark.registerapp.models.entities.EmployeeEntity;

public class EmployeeSignIn extends ApiResponse {

    //CONSTRUCTOR if they don't exist
    public EmployeeSignIn() {

		this.employeeID = this.getEmployeeID();
		this.password = this.getPassword();
    }
    //if they do exist
    // public EmployeeSignIn(EmployeeEntity employeeEntity) {

	// 	this.employeeID = this.employeeEntity.getID();
	// 	this.password = this.employeeEntity.getPassword();
    // }

    //EMPLOYEE ID
    private String employeeID;
    public EmployeeSignIn setEmployeeID(final String employeeID)
    {
        this.employeeID=employeeID;
        return this;
    }
    public String getEmployeeID()
    {
        return this.employeeID;
    }

    //PASSWORD ID
    private String password;
    public EmployeeSignIn setPassword(final String password)
    {
        this.password=password;
        return this;
    }
    public String getPassword()
    {
        return this.password;
    }

}