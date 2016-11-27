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
			template: '<story-edit></story-edit>'
		}).
		when('/story/:storyId/contribute', {
			template: '<contribution-new></contribution-new>'
		}).
		when('/contribution/:contributionId', {
			template: '<contribution-view></contribution-view>'
		}).
		when('/contribution/:contributionId/edit', {
			template: '<contribution-edit></contribution-edit>'
		}).
		when('/story', {
			template: '<story-new></story-new>',
			controller: 'StoryController',
			controllerAs: 'sc'
		}).
		when('/character', {
			template: '<character-new></character-new>'
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
			template: '<user-edit></user-edit>'
		}).
		when('/user/:userId/editPass', {
			template: '<user-update-pass></user-update-pass>'
		}).
		when('/search-results', {
			template: '<search-results></search-results>'
		}).
		otherwise('/');
		
		$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
	}
])
	.controller('app', function($scope, $window, auth) {
		console.log('instantiated app controller');
		if (auth && auth.user) $scope.user = auth.user;
		
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
	})
	.directive("compareTo", function() {
		return {
	        require: "ngModel",
	        scope: {
	            otherModelValue: "=compareTo"
	        },
	        link: function(scope, element, attributes, ngModel) {
	             
	            ngModel.$validators.compareTo = function(modelValue) {
	                return modelValue == scope.otherModelValue;
	            };
	 
	            scope.$watch("otherModelValue", function() {
	                ngModel.$validate();
	            });
	        }
	    };
	});