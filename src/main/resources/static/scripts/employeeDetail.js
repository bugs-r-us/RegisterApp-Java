let hideEmployeeSavedAlertTimer = undefined;

document.addEventListener("DOMContentLoaded", () => {
	getSaveActionElement().addEventListener("click", saveActionClick)
});

function getSaveActionElement() {
	return document.getElementById("saveButton");

}
// Save
function saveActionClick(event) {
	if (!validateSave()) {
		return;
	}

  const saveActionElement = event.target;
  saveActionElement.disabled = true;

  const employeeID = getEmployeeID();
  const employeeIsDefined = ((employeeID != null) && (employeeID.trim() !== ""));
  const saveActionUrl = ("/api/employee/"
  + (employeeIsDefined ? employeeID : ""));
  const saveEmployeeRequest = {
    id: employeeID,
    firstName: getFirstName(),
    lastName: getLastName(),
	password: getPassword(),
	classification: getEmployeeType()
    // type: getEmployeeType()
  };

  if (employeeIsDefined) {
    ajaxPatch(saveActionUrl, saveEmployeeRequest, (callbackResponse) => {
      saveActionElement.disabled = false;

      if (isSuccessResponse(callbackResponse)) {
        displayEmployeeSavedAlertModal();
        window.location.href = response.redirect;
      }
    });
  } else {
    ajaxPost(saveActionUrl, saveEmployeeRequest, (callbackResponse) => {
      saveActionElement.disabled = false;

      if (isSuccessResponse(callbackResponse)) {
        displayEmployeeSavedAlertModal();
        console.log(callbackResponse)
        window.location.href = callbackResponse.data.redirectUrl;

        /*if ((callbackResponse.data != null)
          && (callbackResponse.data.id != null)
          && (callbackResponse.data.id.trim() !== "")) {

          document.getElementById
        }*/
      }
    });
  }

  /*
  if new employee, use ajaxPost
  if existing employee, use ajaxPatch
  */


};

function validateSave() {
	const fname = getFirstName();
	if ((fname == null) || (fname.trim() === "")) {
		displayError("Please provide a valid first name.");
	getFirstNameElement().focus();
	getFirstNameElement().select();
		return false;
	}

  const lname = getLastName();
	if ((lname == null) || (lname.trim() === "")) {
		displayError("Please provide a valid last name.");
	getLastNameElement().focus();
	getLastNameElement().select();
		return false;
	}

  const password = getPassword();
  if ((password == null) || (password.trim() === "")) {
	displayError("Please provide a valid password");
	getPasswordElement().focus();
	getPasswordElement().select();
	return false;
  }

  const confirmPassword = getConfirmPassword();
  if ((confirmPassword == null) || (confirmPassword.trim() === "")) {
	displayError("Please provide a valid confirm password");
	getConfirmPasswordElement().focus();
	getConfirmPasswordElement().select();
	return false;
  }

  if (confirmPassword !== password) {
	displayError("Please make sure that passwords match each other");
	getConfirmPasswordElement().focus();
	getConfirmPasswordElement().select();
	return false;
  }

	return true;
}

function displayEmployeeSavedAlertModal() {
	if (hideEmployeeSavedAlertTimer) {
		clearTimeout(hideEmployeeSavedAlertTimer);
	}

	const savedAlertModalElement = getSavedAlertModalElement();
	savedAlertModalElement.style.display = "none";
	savedAlertModalElement.style.display = "block";

	hideEmployeeSavedAlertTimer = setTimeout(hideEmployeeSavedAlertModal, 1200);
}

function hideEmployeeSavedAlertModal() {
	if (hideEmployeeSavedAlertTimer) {
		clearTimeout(hideEmployeeSavedAlertTimer);
	}
  
		getSavedAlertModalElement().style.display = "none";
	}

	function displayEmployeeSavedAlertModal() {
		if (hideEmployeeSavedAlertTimer) {
			clearTimeout(hideEmployeeSavedAlertTimer);
		}
	  
		const savedAlertModalElement = getSavedAlertModalElement();
		savedAlertModalElement.style.display = "none";
		savedAlertModalElement.style.display = "block";
	  
		hideEmployeeSavedAlertTimer = setTimeout(hideEmployeeSavedAlertModal, 1200);
	  }
// End save

//Getters and setters
function getSavedAlertModalElement() {
	return document.getElementById("employeeSavedAlertModal");
}

function getEmployeeIdElement() {
	return document.getElementById("employeeId");
  }
  
  function getEmployeeID() {
	return getEmployeeIdElement().value;
  }


  function getFirstNameElement() {
	return document.getElementById("employeeFirstName");
  }
  
  function getFirstName() {
	return getFirstNameElement().value;
  }
  
  function getLastNameElement() {
	return document.getElementById("employeeLastName");
  }
  
  function getLastName() {
	return getLastNameElement().value;
  }
  
  function getPasswordElement() {
	return document.getElementById("employeePassword");
  }
  
  function getPassword() {
	return getPasswordElement().value;
  }
  
  function getConfirmPasswordElement() {
	return document.getElementById("employeeConfirmPassword");
  }
  
  function getConfirmPassword(){
	return getConfirmPasswordElement().value;
  }
  
  function getEmployeeTypeElement() {
	return document.getElementById("type");
  }
  
  function getEmployeeType() {
	return getEmployeeTypeElement().value;
  }
//End getters and setters
