angular.module('character', [])
	.component('characterNew', {
		templateUrl: '/js/character/create-character.template.html'
	})
	.component('characterDetail', {
		templateUrl: '/js/character/character-detail.template.html'
	})
	.component('characterEdit', {
		templateUrl: '/js/character/character-edit.template.html'
	})
	.controller('CharacterController', function($scope, $location, characterService) {
		var self = this;
		$scope.formData = {};
		console.log('create character controller');
		
		var newCharacter = function(valid) {
			console.log('create character');
			console.log($scope.formData);
			
			characterService.newCharacter($scope.formData, function() {
				$scope.loading = false;
				if (characterService.error) {
					$scope.error = true;
					$scope.message = characterService.message;
				} else {
					$scope.error = false;
					$location.path("/character/" + characterService.createdId);
				}
			});
		}
		
		$scope.newCharacter = newCharacter;
	})
	.controller('ViewCharacterController', function($scope, $routeParams, auth, characterService) {
		$scope.loading = true;
		
		if (auth && auth.user && auth.user.principal) {
            $scope.currentUser = auth.user.principal.id;
        } else {
        	$scope.currentUser = -1;
        }
		
		characterService.readCharacter($routeParams.characterId, function() {
			console.log('callback');
			if (characterService.error) {
				$scope.error = true;
				$scope.message = characterService.message;
			} else {
				$scope.error = false;
				console.log('character: ');
				console.log(characterService.character);
				$scope.character = characterService.character;
			}
			$scope.loading = false;
		});
	})
	.controller('EditCharacterController', function($scope, $routeParams, $location, characterService) {
		var self = this;
		console.log('edit character controller');
		
		self.loading = true;
		  self.characterId = $routeParams.characterId;
		  characterService.readCharacter(self.characterId, function() {
			  self.character = characterService.character;
			  $scope.formData = self.character;
			  self.loading = false;
		  });
		
		var updateCharacter = function(valid) {
			$scope.loading = true;
			console.log('update character');
			characterService.updateCharacter($scope.formData, function() {
				console.log('callback');
				$scope.loading = false;
				$location.path('/character/' + $scope.formData.id);
			});
		};
		
		$scope.updateCharacter = updateCharacter;
		
	})
	.service('characterService', function($http) {
		var self = this;
		console.log('character service');
		
		self.newCharacter = function(data, callback) {
			console.log(data);
			var path = '/api/characters'

			$http({
				method: 'POST',
				url: path,
				data: $.param(data),
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			}).success(function(response) {
				console.log('post success');
			    console.log(response);
			    self.error = false;
			    self.message = "Character created successfully";
			    self.createdId = response.id;
			    callback && callback();
			}).error(function(response) {
				console.log('post error');
				console.log(response);
				self.message = "An error occurred. Please try again.";
				self.error = true;
				callback && callback();
			});
		};
		
		self.updateCharacter = function(data, callback) {
			console.log(data);
			var path = '/api/characters/' + data.id;
			console.log(path);
			
			$http({
				method: 'PUT',
				url: path,
				data: $.param(data),
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			}).success(function(response) {
				console.log('post success');
			    console.log(response);
			    self.error = false;
			    self.message = "Character updated successfully";
			    callback && callback();
			}).error(function(response) {
				console.log('post error');
				console.log(response);
				self.message = "An error occurred. Please try again.";
				self.error = true;
				callback && callback();
			});
			
			callback && callback();
		};
		
		self.deleteCharacter = function(data, callback) {
			
		};
		
		self.tagCharacter = function(data, callback) {
			
		};
		
		self.readCharacter = function(data, callback) {
			console.log('view character ' + data);
			$http.get('/api/characters/' + data).then(function (response) {
				self.character = response.data;
				if (self.character) {
					$http.get('/api/users/' + self.character.owner).then(function (response) {
						self.character.dude = response.data;
						callback && callback();
					});
				} else {
					callback && callback();
				}

			});
		};

	});