angular.
  module('userProfile').
  component('userProfile', {
    templateUrl: '/js/user/user-profile.template.html',
    controller: ['$http', '$routeParams', 'auth',
      function UserProfileController($http, $routeParams, auth) {
    	var self = this;
        self.userId = $routeParams.userId;
        
        if (auth && auth.user && auth.user.principal) {
            self.currentUser = auth.user.principal.id;
        } else {
        	self.currentUser = -1;
        }
        
        $http.get('/api/users/' + $routeParams.userId).then(function (response) {
        	console.log(response);
        	self.user = response.data;
        	
        	if (self.user.id) {
            	$http.get('/api/users/' + self.user.id + '/stories').then(function (response) {
            		console.log(response);
            		self.user.stories = response.data;
            		
            	});
            }
        });
      }
    ]
  });