angular.module('user', [])
	.component('userEdit', {
		templateUrl: '/js/user/user-edit.template.html'
	})
	.controller('EditUserController', function($scope, $location, $routeParams, auth, userService) {
		var self = this;
		self.loading = true;
		self.userId = $routeParams.userId;
		userService.readUser(self.userId, function() {
			self.user = userService.user;
			$scope.formData = self.user;
			self.loading = false;
		});
		
		$scope.formData = {};
		
		var edit = function(valid) {
			console.log('edit yay');
			console.log($scope.formData);
			userService.updateUser($scope.formData, function() {
				console.log('callback invoked double yay');
				if (userService.error) {
					  $scope.message = userService.message;
					  $scope.error = true;
				  } else {
					  $scope.loading = false;
					  console.log('going home');
					  $location.path('/home');
				  }
				  $scope.loading = false;
			});
		};
		
		$scope.edit = edit;

	})
	.component('userUpdatePass', {
		templateUrl: '/js/user/user-update-pass.template.html'
	})
	.controller('UpdatePasswordController', function($scope, $location, $routeParams, userService) {
		var self = this;
		self.userId = $routeParams.userId;
		$scope.formData = { 'id': self.userId};
		
		$scope.updatePassword = function(valid) {
			$scope.loading = true;
			console.log('update password');
			console.log($scope.formData);
			userService.updateUserPassword($scope.formData, function() {
				if (userService.error) {
					$scope.error = true;
					$scope.message = userService.message;
				} else {
					$scope.message = userService.message;
					$location.path("/home");
				}
				$scope.loading = false;
			});
		}
		
	})
	.service('userService', function($http) {
		var self = this;
		
		self.readUser = function(userId, callback) {
			$http.get('/api/users/' + userId).then(function (response) {
				self.user = response.data;
				callback && callback();
			});
		};
		
		self.updateUser = function(data, callback) {
			console.log('update user service');
			console.log(data);
			var path = '/api/users/' + data.id;
			
			$http({
				method: 'PUT',
				url: path,
				data: $.param(data),
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			}).success(function(response) {
				console.log('post success');
			    console.log(response);
			    console.log(data);
			    self.message = "User updated successfully";
			    self.createdId = data.id;
			    callback && callback();
			}).error(function(response) {
				console.log('post error');
				console.log(response);
				self.message = "An error occurred. Please try again.";
				self.error = true;
				callback && callback();
			});
			
		};
		
		self.updateUserPassword = function(data, callback) {
			console.log('update user password');
			console.log(data);
			self.error = false;
			var path = '/api/users/' + data.id + '/updatePass';
			
			$http({
				method: 'PUT',
				url: path,
				data: $.param(data),
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			}).success(function(response) {
				console.log('post success');
			    console.log(response);
			    console.log(data);
			    self.message = "Password updated successfully";
			    self.error = false;
			    callback && callback();
			}).error(function(response) {
				console.log('post error');
				console.log(response);
				self.message = "An error occurred. Please try again.";
				self.error = true;
				callback && callback();
			});
			
		};
	});