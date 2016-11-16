angular.module('login')
	.controller('login', function($rootScope, $http, $location) {
		console.log('login controller');
		  var self = this;

		  var authenticate = function(credentials, callback) {
			  console.log('called authenticate');
			  console.log(credentials);
		    var headers = credentials ? {authorization : "Basic "
		        + btoa(credentials.username + ":" + credentials.password)
		    } : {};
		    $http.get('/user', {headers : headers}).then(function(response) {
		    	console.log('/user response: ');
		    	console.log(response);
		      if (response.data.name) {
		        $rootScope.authenticated = true;
		      } else {
		        $rootScope.authenticated = false;
		      }
		      callback && callback();
		    }, function() {
		      $rootScope.authenticated = false;
		      callback && callback();
		    });

		  };

		  authenticate();
		  self.credentials = {};
		  self.login = function() {
			  console.log('called login');
		      authenticate(self.credentials, function() {
		        if ($rootScope.authenticated) {
		          $location.path("/");
		          self.error = false;
		        } else {
		          $location.path("/login");
		          self.error = true;
		        }
		      });
		  };
		  
		  self.logout = function() {
			  console.log('called logout');
			  $http.post('logout', {}).finally(function() {
			    $rootScope.authenticated = false;
			    $location.path("/");
			  });
			};
	});