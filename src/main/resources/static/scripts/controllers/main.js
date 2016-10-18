'use strict';

//angular.module('originalCharactersApp')
//	.controller('MainCtrl', function ($scope, $http, dashboardService, storiesService, config) {
//		var dashboardEndpoint = "/dashboard";
//		$scope.dashboardLoading = true;
//		
//		dashboardService.getDashboard()
//			.then(function(response) {
//				$scope.resposne = response;
//				$scope.dashboard = response.data;
//				if (response.status !== 200) {
//					$scope.dashboardErrorOccurred = true;
//				} else if (!response.data.length) {
//					$scope.noDashboardContent = true;
//				}
//			}, function (error) {
//				$scope.response = error;
//		}).finally(function() {
//			$scope.dashboardLoading = false;
//		});
//		// Do other stuff here
//		
//		};
//	});
