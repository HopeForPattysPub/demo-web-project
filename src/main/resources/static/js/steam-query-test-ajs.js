// This is the version used for the HTML home-ajs.html with AngularJS
// This is the trending technology
var SteamQueryTest = angular.module('SteamQueryTest', ['ngSanitize']);

SteamQueryTest.controller('SteamInfoPage', function ($scope, $http) {
  
  $scope.parseQuery = function() {
     $http.get("/parser/steam/query/" + $scope.query, {headers: { 'Accept': 'application/html' }})
	  	.success(function(data) {
          $scope.QueryList = data;
	  	})
	  	.error(function(data,status) {
	  		$scope.pageOutput = 'Error';
	  	});
  }

});