angular.module('storyDetail')
  .component('storyNew', {
	  templateUrl: '/js/story/story-new.template.html'
  })
  .component('storyEdit', {
	  templateUrl: '/js/story/story-edit.template.html'
  })
  .controller('StoryController', function($scope, $location, auth, storyService) {
	  var self = this;
	  $scope.formData = { visible: true, inviteOnly: false };
	  console.log('story controller');
	  
	  var newStory = function(valid) {
		  if (!valid) {
			  $scope.error = true;
			  $scope.message = "The form contains invalid data. Please correct it and try again.";
		  }
		  
		  $scope.loading = true;
		  console.log('new story');
		  console.log($scope.formData);
		  var user = auth.user.principal;
		  $scope.formData.owner = user.id;
		  
		  storyService.newStory($scope.formData, function() {
			  console.log('callback invoked, hooray');
			  if (storyService.error) {
				  $scope.message = storyService.message;
				  $scope.error = true;
			  } else {
				  $scope.loading = false;
				  console.log('going to id ' + storyService.createdId);
				  $location.path('/story/' + storyService.createdId);
			  }
			  $scope.loading = false;
		  });
		  
	  }
	  $scope.newStory = newStory;
	  
	  
  })
  .controller('EditStoryController', function($scope, $location, $routeParams, auth, storyService) {
	  var self = this;
	  self.loading = true;
	  self.storyId = $routeParams.storyId;
	  storyService.readStory(self.storyId, function() {
		  self.story = storyService.story;
		  $scope.formData = self.story;
		  self.loading = false;
	  });
	  
	  var updateStory = function(valid) {
		  if (!valid) {
			  $scope.error = true;
			  $scope.message = "The form contains invalid data. Please correct it and try again.";
		  }
		  
		  console.log('update story');
		  console.log($scope.formData);
		  
		  storyService.editStory($scope.formData, function() {
			  console.log('callback invoked, hooray');
			  if (storyService.error) {
				  $scope.message = storyService.message;
				  $scope.error = true;
			  } else {
				  $scope.loading = false;
				  console.log('going to id ' + storyService.editedId);
				  $location.path('/story/' + storyService.editedId);
			  }
			  $scope.loading = false;
		  });
	  }
	  
	  $scope.updateStory = updateStory;
  })
  .service('storyService', function($http) {
	  var self = this;
	  
	  self.newStory = function(data, callback) {
			var path = '/api/stories';
			console.log('Creating new story...');
			console.log(data);
			self.error = false;
			self.message = "";
			
			$http({
				method: 'POST',
				url: '/api/stories',
				data: $.param(data),
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			}).success(function(response) {
				console.log('post success');
			    console.log(response);
			    self.message = "Story created successfully";
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
		
		self.editStory = function(data, callback) {
			var path = '/api/stories/' + data.id;
			console.log('Updating story at ' + path);
			console.log(data);
			self.error = false;
			self.message = "";
			self.editedId = data.id;
			
			$http({
				method: 'PUT',
				url: path,
				data: $.param(data),
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			}).success(function(response) {
				console.log('post success');
			    console.log(response);
			    console.log(data);
			    self.message = "Story updated successfully";
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
		
		self.readStory = function(id, callback) {
			console.log('reading story ' + id);
			$http.get('/api/stories/' + id).then(function (response) {
	        	self.story = response.data;
	        	callback && callback();
	        });
		};
  });