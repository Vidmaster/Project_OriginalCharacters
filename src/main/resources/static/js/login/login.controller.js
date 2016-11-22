angular.module('login')
	.controller('login', function($rootScope, $http, $location, auth) {
		  var self = this;

		  auth.authenticate();
		  self.credentials = {};
		  self.login = function() {
			  self.dataLoading = true;
			  console.log('called login');
			  auth.authenticate(self.credentials, function() {
			        if (auth.authenticated) {
				          $location.path("/");
				          self.authenticated = true;
				          self.error = false;
				        } else {
				          $location.path("/login");
				          self.error = true;
				        }
		        self.dataLoading = false;
		      });
		  };
		  
		  self.logout = function() {
			  console.log('called logout');
			  auth.logout();
			};
	});