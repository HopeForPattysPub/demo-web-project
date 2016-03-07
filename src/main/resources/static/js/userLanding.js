// This is the version used for the HTML home-ajs.html with AngularJS
// This is the trending technology
var UserLanding = angular.module('UserLanding', ['ngSanitize']);
//var UN = String(location.search).split("userName=");
var UN = ["", ""];
UN[1] = getUserName();

// Wait for the page to load first
window.onload = function() {
	
    //Get a reference to the link on the page
    // with an id of "mylink"
    var a = document.getElementById("logoutlink");

    //Set code to run when the link is clicked
    // by assigning a function to "onclick"
    a.onclick = function() {
  	  document.cookie = "gameTrackerUser" + '=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
  	  location.reload();

      //If you don't want the link to actually 
      // redirect the browser to another page,
      // "google.com" in our example here, then
      // return false at the end of this block.
      // Note that this also prevents event bubbling,
      // which is probably what we want here, but won't 
      // always be the case.
      return false;
    }
}

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
	  
     document.getElementById("Welcome").innerHTML = ('<p style="text-align:left;">' + "Welcome, " + UN[1] + '<span style="float:right;"><a id="logoutlink" href="#">Logout</a></span></p><div style="clear:both"></div>');
    
       
      $http.get("/getUserNotifications/" + UN[1])
      .success(function(data){
         
         $scope.items = data;
      });
      
     
      
      $http.get("/steamTopPage")
      .success(function(data){
         
         $scope.TopList = data;
      });
      
   }
   
   $scope.parseQuery = function() {
     $http.get("/parser/steam/query/" + $scope.query, {headers: { 'Accept': 'application/html' }})
	  	.success(function(data) {
          $scope.QueryList = data;
	  	})
		.error(function(data,status) {
	  		$scope.pageOutput = 'Error';
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

