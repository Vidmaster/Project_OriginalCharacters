angular.
  module('storyDetail').
  component('storyDetail', {
    templateUrl: '/scripts/story-detail/story-detail.template.html',
    controller: ['$http', '$routeParams',
      function StoryDetailController($http, $routeParams) {
    	var self = this;
        this.storyId = $routeParams.storyId;
        
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