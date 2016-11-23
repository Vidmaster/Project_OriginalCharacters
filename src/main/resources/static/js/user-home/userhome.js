'use strict';

angular.module('ocHome', ['dashboard', 'notifications', 'myStories', 'auth'])
  .component('ocHome', {
    templateUrl: '/js/user-home/home.template.html',
    controller: 'HomeController'
  })
  .controller('HomeController', function($scope, $window, auth) {
	  $scope.user = {};
	  auth.authenticate();
	  if (!auth.authenticated) {
		  console.log('Auth failure in home controller');
		  $scope.message = "Please log in";
		  $window.location.href = "/#!/login";
	  }
	  var user = auth.user.principal
	  $scope.user = user;
	  
  });