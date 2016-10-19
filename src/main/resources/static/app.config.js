'use strict';

angular.module('originalCharactersApp')
	.config(['$locationProvider', '$routeProvider', 
	  function config($locationProvider, $routeProvider) {
		$locationProvider.hashPrefix('!');
		
		$routeProvider.when('/', {
			template: '<oc-index></oc-index>'
		}).
		when('/home', {
			template: '<oc-home></oc-home>'
		}).
		when('/register', {
			controller: 'RegisterController',
			template: '<oc-register></oc-register>',
			controllerAs: 'rc'
		}).
		when('/story/:storyId', {
			template: '<story-detail></story-detail>'
		}).
		when('/user/:userId', {
			template: '<user-profile></user-profile>'
		}).
		otherwise('/');
	}
]);

// /home for logged in user, /register, /character/:characterId, /searchResults, /createStory, /write?