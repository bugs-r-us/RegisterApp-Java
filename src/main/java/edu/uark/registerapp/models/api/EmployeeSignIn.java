package edu.uark.registerapp.models.api;

public class EmployeeSignIn extends ApiResponse {

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