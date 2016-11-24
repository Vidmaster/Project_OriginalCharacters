'use strict';

angular.module('originalCharactersApp')
	.config(['$locationProvider', '$routeProvider', '$httpProvider',
	  function config($locationProvider, $routeProvider, $httpProvider) {
		$locationProvider.hashPrefix('!');
		
		$routeProvider.when('/', {
			template: '<oc-index></oc-index>',
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
		when('/story/:storyId/edit', {
			template: '<story-edit></story-edit>',
			controller: 'StoryController',
			controllerAs: 'sc'
		}).
		when('/story', {
			template: '<story-new></story-new>',
			controller: 'StoryController',
			controllerAs: 'sc'
		}).
		when('/character', {
			template: '<character-new></character-new>',
			controller: 'CharacterController',
			controllerAs: 'cc'
		}).
		when('/character/:characterId', {
			template: '<character-detail></character-detail>'
		}).
		when('/character/:characterId/edit', {
			template: '<character-edit></character-edit>'
		}).
		when('/user/:userId', {
			template: '<user-profile></user-profile>'
		}).
		when('/user/:userId/edit', {
			template: '<user-edit></user-edit>',
			controller: 'UserController'
		}).
		when('/search-results', {
			template: '<search-results></search-results>'
		}).
		otherwise('/');
		
		$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
	}
])
	.controller('app', function($scope, $window, auth) {
		$scope.user = auth.user;
		
		$scope.logout = function() {
			auth.clear();
			$scope.user = null;
			$scope.authenticated = auth.authenticated;
			$window.location.href="/";
		};
		
		auth.authenticate(null,function() {
			console.log('app controller');
			console.log(auth)
			$scope.user = auth.user;
			$scope.authenticated = auth.authenticated;
		});
	})
	.run(function(auth) {
	    auth.init('/home', '/login', '/logout');
	});

// /home for logged in user, /character/:characterId, /searchResults, /createStory, /write?