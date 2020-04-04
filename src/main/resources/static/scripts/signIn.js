document.addEventListener("DOMContentLoaded", function(event) {
	// TODO: Anything you want to do when the page is loaded?
	console.log("SigninJS called");
});


function validateForm() {

    var employeeID = document.getElementById("employeeID").value;
    var password = document.getElementById("employeePW").value;
    if(employeeID == '' || isNaN(employeeID)) { 
        displayError("Please provide a valid employee ID");
        return false;
    }
    if(password == '') {
        displayError("Please provide a valid password");
        return false;
    }
    // TODO: Validate the user input
    return true;
    
}