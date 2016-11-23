angular.module('notifications', [])
  .component('ocNotifications', {
	 templateUrl: '/js/user-home/notifications.template.html',
	 controller: ['$http', 'auth', 
				              function LatestStoriesController($http, $scope) {
			     	var self = this;
			     	self.loading = true;
			     	
			     	$http.get('/api/user/' + $scope.user.principal.id + '/notifications').then(function(response) {
			     		console.log(response);
			     	
			     		self.notifications = response.data.notifications;
			     		self.loading = false;
			     	}, function(response, $window) {
			     		console.log('error getting notifications');
			     		console.log(response);
			     		self.loading = false
			     		self.message = "You are not authorized to access this page. Please log in.";
			     	});
			     }]
  });