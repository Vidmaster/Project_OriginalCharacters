angular.module('myStories', [])
  .component('myStories', {
	 templateUrl: '/js/user-home/my-stories.template.html',
	 controller: ['$http', 'auth', 
	              function LatestStoriesController($http, $scope) {
	            	var self = this;
	            	self.loading = true;
	            	
	            	$http.get('/api/dashboard/' + $scope.user.principal.id).then(function(response) {
	            		console.log(response);
	            	
	            		self.stories = response.data.stories;
	            		self.characters = response.data.characters;
	            		self.contributions = response.data.contributions;
	            		
	            		self.contributions.forEach(function(contribution) {
	            			$http.get('/api/stories/' + contribution.story).then(function(response) {
	            				contribution.story = response.data;
	            			});
	            		});
	            		
	            		self.loading = false;
	            	}, function(response, $window) {
	            		console.log('error getting user dashboard');
	            		console.log(response);
	            		self.loading = false
	            		self.message = "You are not authorized to access this page. Please log in.";
	            	});
	            }]
  });