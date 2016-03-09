angular.module('museum.controllers')

.controller('museumsController', function($scope, $rootScope,$state,$stateParams,museumAPIService) {
    $scope.$on("$ionicView.beforeEnter", function() {
        
    });
    $scope.backClick = function(option){
        $state.go('app.main');
    }
});