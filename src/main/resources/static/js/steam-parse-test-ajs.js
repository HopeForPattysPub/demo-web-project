// This is the version used for the HTML home-ajs.html with AngularJS
// This is the trending technology
var SteamParseTest = angular.module('SteamParseTest', ['ngSanitize']);

SteamParseTest.controller('SteamInfoPage', function ($scope, $http) {
  
  $scope.parseSteamJSON = function() {
     alert("Entered in function");
     /*$http.get("/parser/steam/" + $scope.appidToSearch, {headers: { 'Accept': 'application/html' }})
	  	.success(function(data) {
	  		$scope.pageOutput = data;
	  		//368500 - Assassin's Creed appid
	  		//248330 - Dino Run DX appid
	  	})
	  	.error(function(data,status) {
	  		$scope.pageOutput = 'Error';
	  	});*/
  }

});