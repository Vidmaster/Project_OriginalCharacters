angular.module('search', [])
	.controller('SearchController', function($scope, $location, $http, searchService) {
		var self = this;
		$scope.tacos = "yes";
		$scope.results = searchService.results;
		$scope.searchTerm = searchService.searchTerm;
		var search = function() {
			console.log('called search');
			self.loading = true;
			searchService.search($scope.searchText);
//			var path = '/api/stories?title=' + $scope.searchText;
//			console.log('searching for stories at ' + path);
//			$http.get(path).then(function(response) {
//				console.log('search response');
//				console.log(response);
//				$scope.results = response.data;
//				self.loading = false;
//				$location.path("/search-results");
//			});
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
		self.search = function(value) {
			self.searchTerm = value;
			var path = '/api/stories?title=' + value;
			console.log('searching for stories at ' + path);
			$http.get(path).then(function(response) {
				console.log('search response');
				console.log(response);
				self.results = response.data;
				$location.path("/search-results");
			});
		};
		
	}
			
	);