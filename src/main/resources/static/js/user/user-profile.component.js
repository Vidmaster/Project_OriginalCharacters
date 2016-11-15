angular.
  module('userProfile').
  component('userProfile', {
    templateUrl: '/js/user/user-profile.template.html',
    controller: ['$http', '$routeParams',
      function UserProfileController($http, $routeParams) {
    	var self = this;
        this.userId = $routeParams.userId;
        
        $http.get('/api/users/' + $routeParams.userId).then(function (response) {
        	self.user = response.data;
        	self.user.facebookId = null;
        	self.user.password = null;
        	self.user.salt = null;
        	
        	if (self.user.id) {
            	$http.get('/api/users/' + self.story.owner + '/stories').then(function (response) {
            		self.user.stories = response.data;
            	});
            }
        });
      }
    ]
  });