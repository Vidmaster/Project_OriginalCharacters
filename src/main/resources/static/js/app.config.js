'use strict';

angular.module('originalCharactersApp')
	.config(['$locationProvider', '$routeProvider', '$httpProvider',
	  function config($locationProvider, $routeProvider, $httpProvider) {
		$locationProvider.hashPrefix('!');
		
		$routeProvider.when('/', {
			template: '<oc-index></oc-index>'
		}).
		when('/home', {
			template: '<oc-home></oc-home>'
		}).
		when('/register', {
			controller: 'RegistrationController',
			template: '<oc-register></oc-register>',
			controllerAs: 'rc'
		}).
		when('/login', {
			template: '<oc-login></oc-login>'
		}).
		when('/story/:storyId', {
			template: '<story-detail></story-detail>'
		}).
		when('/user/:userId', {
			template: '<user-profile></user-profile>'
		}).
		otherwise('/');
		
		$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
	}
]);

// /home for logged in user, /character/:characterId, /searchResults, /createStory, /write?