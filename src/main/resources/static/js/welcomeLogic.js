// This is the version used for the HTML home-ajs.html with AngularJS
// This is the trending technology
var welcomeScreen = angular.module('WelcomeScreen', ['ngSanitize']);

welcomeScreen.controller('welcomeInfopage', function ($scope, $http) {

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
  }

});