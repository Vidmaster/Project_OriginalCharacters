'use strict';

angular.
  module('dashboardApp').
  component('latestStories', {
    templateUrl: '/scripts/dashboard/dashboard-template.html',
    controller: function PhoneListController($http) {
      this.stories = [
        {
          id: 1,
          title: 'Placeholder',
          description: 'Its a story',
          genre: 'things'
        }, {
          id: 2,
          title: 'Placeholder 2',
          description: 'A story.',
        	  genre: 'stuff'
        }, {
          id: 3,
          title: 'Placeholder 3',
          description: 'Another story',
          genre: 'what'
        }
      ];
 }});

//This is kind of working but not really    	  
//$http({
//		method: 'GET',
//		url: "/api/dashboard"
//	}).then(function successCallback(response) {
//		console.log('Success: ' + response);
//		return response.data;
//	}, function errorCallback(response) {
//		console.log('Error: ' + response);
//		return response.data;
//	});
//}	  