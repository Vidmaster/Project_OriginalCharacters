'use strict';

angular.module('dashboardApp')
	.factory('dashboardService', function ($http) {
		return {
			getDashboardContents: function() {
				return $http({
					method: 'GET',
					url: "/api/dashboard"
				}).then(function successCallback(response) {
					if (config.DEBUG) { console.log('Success: ' + response); }
					return response;
				}, function errorCallback(response) {
					if (config.DEBUG) { console.log('Error: ' + response); }
					return response;
				});
			}
		}
	});