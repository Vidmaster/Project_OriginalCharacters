'use strict';

angular.
  module('dashboardApp').
  component('latestStories', {
    templateUrl: '/scripts/dashboard/dashboard.template.html',
    controller: function PhoneListController($http) {
    	var self = this;
    	
    	$http.get('/api/dashboard').then(function(response) {
    		self.stories = response.data;
    	});
    }
});