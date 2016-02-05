// This is the version used for the HTML home-ajs.html with AngularJS
// This is the trending technology
var recoveryScreen = angular.module('recoveryScreen', ['ngSanitize']);

recoveryScreen.controller('recoveryInfoPage', function ($scope, $http) {
  
  $scope.recoveryTest = function() {
	
	  var un = $scope.userNameToSearch;
	  var email = $scope.emailToSearch;
	  
	  
	  if(document.getElementById("userName").value == '')
	  {
	     alert("Username cannot be empty");
	  }
	  else if(document.getElementById("userEmail").value == '')
     {
        alert("Email cannot be empty");
     }
	  else
	  {
	       $http.get("/recovery/" + un + "/" + email)
            
            .success(function(data) {
               if(data == 0)
               {
                  alert("Combination of Username and Email do not match");
                  document.getElementById("userEmail").value = ''
                  
               }
               else if (data == 1)
               {
                  alert("Email has been sent. Follow instructions.");
                  window.location = "http://localhost:8080/login.html";
               }
              
            })
            .error(function(data,status) {
               $scope.pageOutput = "Error";
            });
	  }
	  	  
	  
  }

});