angular.module('museum.controllers')

.controller('questionController', function($scope, $rootScope,$state,$stateParams,museumAPIService) {
    $scope.$on("$ionicView.beforeEnter", function() {
        $scope.answerSet = false;
        if($stateParams.questionId){
            $scope.question = null;
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
        museumAPIService.refreshPoints(function(points){
            $rootScope.currentUser.totalPoints = points;
            $state.go('app.main');
        });
    }
    function answerCallback(response){
    }
    function fetchQuestion(id){
        museumAPIService.getQuestion(id,questionReceived);
    }
    function questionReceived(question){
        $scope.question = question;
    }
});