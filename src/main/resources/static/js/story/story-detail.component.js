angular.
  module('storyDetail').
  component('storyDetail', {
    templateUrl: '/js/story/story-detail.template.html',
    controller: ['$http', '$routeParams', 'auth', 
      function StoryDetailController($http, $routeParams, auth) {
    	var self = this;
        self.storyId = $routeParams.storyId;
        if (auth && auth.user && auth.user.principal) {
            self.currentUser = auth.user.principal.id;
        } else {
        	self.currentUser = -1;
        }
        
        $http.get('/api/stories/' + $routeParams.storyId).then(function (response) {
        	self.story = response.data;
            if (self.story) {
            	$http.get('/api/users/' + self.story.owner).then(function (response) {
            		self.story.owner = response.data;
            	});
            }
        });

      }
    ]
  });