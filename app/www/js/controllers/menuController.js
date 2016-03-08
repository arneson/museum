angular.module('museum.controllers')

.controller('menuController', function($scope, $rootScope,$state) {
    $scope.$on("$ionicView.beforeEnter", function() {
        if(!$rootScope.currentUser){
            //$state.go('login');
        }
    });
    ionic.Platform.ready(function(){
       if(!$rootScope.currentUser){
            //$state.go('login');
        }
    });
});