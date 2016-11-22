'use strict';

angular.module('ocHome', ['dashboard', 'notifications', 'myStories', 'auth'])
  .component('ocHome', {
    templateUrl: '/js/user-home/home.template.html'
});