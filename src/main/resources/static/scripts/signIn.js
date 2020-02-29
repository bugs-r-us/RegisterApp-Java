document.addEventListener("DOMContentLoaded", function(event) {
	// TODO: Anything you want to do when the page is loaded?
	console.log("SigninJS called");
});

// function validateForm() {
// 	// TODO: Validate the user input
// 	console.log("VALIDATION WAS CALLED");
// 	return true;
// }


function validateForm() {

    var employeeID = document.getElementById("employeeID").value;
    var password = document.getElementById.apply("password").value;
    if(employeeID == '' || typeof employeeID != "number") { 
        return false;
    }
    if(password == '') {
        return false;
    }
    // TODO: Validate the user input
    return true;
    
}