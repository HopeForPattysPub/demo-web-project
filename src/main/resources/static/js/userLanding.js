// This is the version used for the HTML home-ajs.html with AngularJS
// This is the trending technology
var UserLanding = angular.module('UserLanding', ['ngSanitize']);
var UN = String(location.search).split("userName=");

UserLanding.controller('UserLandingPage', function ($scope, $http) {
  
  $scope.welcomeUser = function() {
     document.getElementById("Welcome").innerHTML = ("Welcome, " + UN[1]);
     alert("in welcome");
     
       
      $http.get("/getUserNotifications/" + UN[1])
      .success(function(data){
         alert("finished");
         $scope.items = data;
      });

            
     
   }
  
  
  $scope.parseUser = function() {
     
     $http.get("/parser/steam/" + $scope.appidToSearch, {headers: { 'Accept': 'application/html' }})
	  	.success(function(data) {
	  		$scope.pageOutput = data;
	  	})
	  	.error(function(data,status) {
	  		$scope.pageOutput = 'Error';
	  	});
  }

});

