angular.module('search', [])
	.controller('SearchController', function($scope, $window, $location, $http, searchService) {
		var self = this;

		$scope.results = searchService.results;
		$scope.searchTerm = searchService.searchTerm;
		var search = function() {
			console.log('called search');
			$scope.loading = true;
			searchService.search($scope.searchText, function() {
				console.log('callback');
				$scope.loading = false;
				$scope.results = searchService.results;
				$scope.searchTerm = searchService.searchTerm;
				//$location.path("/search-results");
				$window.location.href="#!/search-results";
			});
		};
		$scope.search = search;
		
	})
	.component('searchResults', {
		templateUrl: "/js/search/search-results.template.html"
	})
	.service('searchService', function($http, $location) {
		var self = this;
		
		self.results = [];
		self.searchTerm = "";
		self.search = function(value, callback) {
			self.searchTerm = value;
			var path = '/api/search?value=' + value;
			console.log('searching for stories at ' + path);
			$http.get(path).then(function(response) {
				console.log('search response');
				console.log(response);
				self.results = response.data;
				callback && callback();
			});
		};
		
	}
			
	);