angular.module('character', [])
	.component('characterNew', {
		templateUrl: '/js/character/create-character.template.html'
	})
	.component('characterDetail', {
		templateUrl: '/js/character/character-detail.template.html'
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
	.controller('ViewCharacterController', function($scope, $routeParams, characterService) {
		$scope.loading = true;
		characterService.viewCharacter($routeParams.characterId, function() {
			if (characterService.error) {
				$scope.error = true;
				$scope.message = characterService.message;
			} else {
				$scope.error = false;
				$scope.character = characterService.character;
			}
			$scope.loading = false;
		});
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
			    console.log(data);
			    self.message = "Character created successfully";
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
		
		self.updateCharacter = function(data, callback) {
			
		};
		
		self.deleteCharacter = function(data, callback) {
			
		};
		
		self.tagCharacter = function(data, callback) {
			
		};
		
		self.viewCharacter = function(data, callback) {
			console.log('view character ' + data);
			$http.get('/api/characters/' + data).then(function (response) {
				self.character = response.data;
				
				if (self.character) {
					$http.get('/api/users/' + self.character.owner).then(function (response) {
						self.character.owner = response.data;
						callback && callback();
					});
				} else {
					callback && callback();
				}
			});
		};

	});