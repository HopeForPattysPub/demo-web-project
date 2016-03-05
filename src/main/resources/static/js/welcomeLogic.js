// This is the version used for the HTML home-ajs.html with AngularJS
// This is the trending technology
var welcomeScreen = angular.module('WelcomeScreen', ['ngSanitize']);

function getUserName() {
	var ca = document.cookie.split(';');
	for(var i=0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1);
		if(c.indexOf("gameTrackerUser=") == 0) return c.substring("gameTrackerUser=".length, c.length);
	}
	return "$%notset%$";
}

welcomeScreen.controller('welcomeInfopage', function ($scope, $http) {
	
	$scope.usercheck = function() {
		if(getUserName != "$%notset%$") window.location.replace("./userLanding.html");
	}

  $scope.parseSteamJSON = function() {
	  $http.get("/parser/steam/" + $scope.appidToSearch, {headers: { 'Accept': 'application/html' }})
	  	.success(function(data) {
	  		$scope.pageOutput = data;
	  		//368500 - Assassin's Creed appid
	  		//248330 - Dino Run DX appid
	  	})
	  	.error(function(data,status) {
	  		$scope.pageOutput = 'Error';
	  	});
	   location.reload(true);
  }

});