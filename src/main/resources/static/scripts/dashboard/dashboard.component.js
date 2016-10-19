'use strict';

angular.
  module('dashboard').
  component('latestStories', {
    templateUrl: '/scripts/dashboard/dashboard.template.html',
    controller: ['$http', 
      function LatestStoriesController($http) {
    	var self = this;
    	
    	$http.get('/api/dashboard').then(function(response) {
    		self.stories = response.data;
    	});
    }]
});