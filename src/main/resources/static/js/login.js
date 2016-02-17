// This is the version used for the HTML home-ajs.html with AngularJS
// This is the trending technology
var LoginScreen = angular.module('LoginScreen', ['ngSanitize']);

LoginScreen.controller('loginInfoPage', function ($scope, $http) {
  
  $scope.logInTest = function() {
	
	  var un = $scope.userNameToSearch;
	  var pw = $scope.passWordToSearch;
	  var pwString = String(pw);
	  
	  if(document.getElementById("passWord").value == '')
	  {
	     alert("Password cannot be empty");
	  }
	  else if(document.getElementById("userName").value == '')
     {
        alert("Username cannot be empty");
     }
	  else
	  {
	       var hash = CryptoJS.SHA1(pwString);
	       $http.get("/login/" + hash + "/" + un)
            
            .success(function(data) {
               if(data == 0)
               {
                  alert("Combination of Username and password do not match");
                  document.getElementById("passWord").value = ''
               }
               else if (data == 1)
               {
                  alert("Login successful");
                  $http.get("http://localhost:8080/" + un + "/userLanding")
                  
                     .success(function(data) {
                        alert("User Name is: data");
                     });
                     .error(function(data,status) {
                        $scope.pageOutput = "Error";
                     });
                  //window.location.href = ("http://localhost:8080/" + un + "/userLanding");
               }
              
            })
            .error(function(data,status) {
               $scope.pageOutput = "Error";
            });
	  }
	  	  
	  
  }

});