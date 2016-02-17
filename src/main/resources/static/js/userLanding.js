// This is the version used for the HTML home-ajs.html with AngularJS
// This is the trending technology
var UserLanding = angular.module('UserLanding', ['ngSanitize']);

UserLandng.controller('UserLandingPage', function ($scope, $http) {
  
  $scope.parseUser = function() {
     alert("Entered in function");
     /*$http.get("/parser/steam/" + $scope.appidToSearch, {headers: { 'Accept': 'application/html' }})
	  	.success(function(data) {
	  		$scope.pageOutput = data;
	  	})
	  	.error(function(data,status) {
	  		$scope.pageOutput = 'Error';
	  	});*/
  }

});