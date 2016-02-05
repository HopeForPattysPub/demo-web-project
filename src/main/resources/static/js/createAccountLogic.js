// This is the version used for the HTML home-ajs.html with AngularJS
// This is the trending technology
var newAccountScreen = angular.module('newAccountScreen', ['ngSanitize']);

newAccountScreen.controller('newAccountInfoPage', function ($scope, $http) {
  
  $scope.createAcc = function() {
	
	  var pw1 = $scope.passWordToSearch;
	  var pw2 = $scope.passWordCheck;
	  var pwString = String(pw1);
	  var pwString2 = String(pw2);
	  if(document.getElementById("passWord").value == '')
	  {
	     alert("Password cannot be empty");
	  }
	  else if(pw1 == pw2)
	  {
	       //alert("PW Match");
	       var hash = CryptoJS.SHA1(pwString);
	       //alert(hash);
	       $http.get("/newaccount/" + $scope.emailToSearch + "/" + hash + "/" + $scope.userNameToSearch )
            
            .success(function(data) {
               if(data == 0)
               {
                  alert("That username already taken, try a new one");
                  document.getElementById("passWord").value = '';
                  document.getElementById("passWordCheck").value = '';
                  document.getElementById("userName").value = '';
               }
               else if (data == 1)
               {
                  alert("An account alredy exists for this email.");
                  document.getElementById("passWord").value = '';
                  document.getElementById("passWordCheck").value = '';
                  document.getElementById("userEmail").value = '';
               }
               else
               {
                  alert("Account successful created");
                  window.location = "http://localhost:8080/login.html";
               }               
            })
            .error(function(data,status) {
               $scope.pageOutput = "Error";
            });
	  }
	  else
	  {
	       alert("Passwords don't match");
	       document.getElementById("passWord").value = '';
	       document.getElementById("passWordCheck").value = '';
	  }
	  
	  
	  
  }

});