angular.module('museum.controllers')

.controller('museumsController', function($scope, $rootScope,$state,$stateParams,museumAPIService) {
    $scope.$on("$ionicView.beforeEnter", function() {
        $rootScope.backEnabled = true;
        museumAPIService.getMuseums(museumsCallback);
    });
    $scope.backClick = function(option){
        $state.go('app.main');
    }
    function museumsCallback(museums){
        $scope.museums = museums;
    }
});