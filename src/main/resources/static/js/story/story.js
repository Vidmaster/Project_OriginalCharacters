angular.module('storyDetail')
  .component('storyNew', {
	  templateUrl: '/js/story/story-new.template.html'
  }).controller('StoryController', function($scope, $location, auth, storyService) {
	  var self = this;
	  $scope.formData = { visible: true, inviteOnly: false };
	  console.log('story controller');
	  
	  var newStory = function(valid) {
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
	  
	  
  }).service('storyService', function($http, $location) {
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
				self.message = "An error occurred";
				self.error = true;
				callback && callback();
			});
//			$http.post(path).then(function(response) {
//				console.log(response);
//				self.createdId = response.data.id;
//				callback && callback();
//			});
		};
		
		self.editStory = function(data) {
			
		};
  });