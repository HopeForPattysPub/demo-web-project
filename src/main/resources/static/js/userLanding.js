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
    
       
      $http.get("/getUserNotifications/" + UN[1])
      .success(function(data){
         
         $scope.items = data;
      });
      
     
      
      $http.get("/steamTopPage")
      .success(function(data){
         
         $scope.TopList = data;
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
	$scope.addItem = function(itemID) {
		var username = getUserName();
		var notifyPrice = window.prompt("Notification Price", 0);
		$http.post("/addUserNotification/" + username + "/" + itemID + "/" + notifyPrice)
			.success(function(data) {
				$scope.pageOutput = data;
				location.reload();
				
							
			})
			.error(function(data,status) {
				$scope.pageOutput = 'Error';
				
			});
	}

	$scope.deleteItem = function(itemID) {
		var username = getUserName();
		$http.delete("/deleteNotification/" + username + "/" + itemID)
			.success(function(data) {
				$scope.pageOutput = data;
                location.reload();
			})
			.error(function(data,status) {
				$scope.pageOutput = 'Error';
			});
	}

	$scope.updateItem = function(itemID) {
		var username = getUserName();
		var notifyPrice = window.prompt("New Price", 0);
		$http.post("/updateNotification/" + username + "/" + itemID + "/" + notifyPrice)
			.success(function(data) {
				$scope.pageOutput = data;
                location.reload();
			})
			.error(function(data,status) {
				$scope.pageOutput = 'Error';
			});
	}
});

