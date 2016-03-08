angular.module('museum.controllers')

.controller('questionController', function($scope, $rootScope,$state,$stateParams,museumAPIService) {
    $scope.$on("$ionicView.beforeEnter", function() {
        if($stateParams.questionId){
            fetchQuestion($stateParams.questionId);
        }else{
            $state.go('login');
        }
    });
    function fetchQuestion(id){
        museumAPIService.getQuestion(id,questionReceived);
    }
    function questionReceived(question){
        $scope.question = question;
    }
});