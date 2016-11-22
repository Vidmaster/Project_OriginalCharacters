angular.module('myStories', [])
  .component('myStories', {
	 templateUrl: '/js/user-home/my-stories.template.html',
	 controller: ['$http', 
	              function LatestStoriesController($http, $scope) {
	            	var self = this;
	            	self.loading = true;
	            	$http.get('/api/dashboard').then(function(response) {
	            		self.stories = response.data;
	            		self.loading = false;
	            	});
	            }]
  });