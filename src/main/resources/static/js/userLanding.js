// This is the version used for the HTML home-ajs.html with AngularJS
// This is the trending technology
var UserLanding = angular.module('UserLanding', ['ngSanitize']);
//var UN = String(location.search).split("userName=");
var UN = ["", ""];
UN[1] = getUserName();

function getUserName() {
	var ca = document.cookie.split(';');
	for(var i=0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1);
		if(c.indexOf("gameTrackerUser=") == 0) return c.substring("gameTrackerUser=".length, c.length);
	}
	return "$%notset%$";
}

UserLanding.controller('UserLandingPage', function ($scope, $http) {
  
  $scope.welcomeUser = function() {
	  
	  var username = getUserName();
	  if(username == "$%notset%$") window.location.replace("/");
	  
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

