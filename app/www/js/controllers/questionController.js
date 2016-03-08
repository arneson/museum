angular.module('museum.controllers')

.controller('questionController', function($scope, $rootScope,$state,$stateParams,museumAPIService) {
    $scope.$on("$ionicView.beforeEnter", function() {
        if($stateParams.questionId){
            fetchQuestion($stateParams.questionId);
        }else{
            $state.go('login');
        }
    });
    $scope.answerClick = function(option){
        if($scope.answerSet){
        }else{
            $scope.answerSet = true;
            if(option.id==$scope.question.correctOption.id){
                option.correct = true;
            }
            option.clicked = true;
            museumAPIService.answerQuestion($scope.question.id,option.id,answerCallback);
        }
    }
    $scope.backClick = function(option){
        $state.go('app.main');
    }
    function answerCallback(response){
        console.log(response);
    }
    function fetchQuestion(id){
        museumAPIService.getQuestion(id,questionReceived);
    }
    function questionReceived(question){
        $scope.question = question;
    }
});