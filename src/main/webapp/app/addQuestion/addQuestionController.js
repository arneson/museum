museumApp.controller('addQuestionController',['$scope','$rootScope','apiService', function($scope,$rootScope,apiService){
    $scope.question = {};
    $scope.submit = function() {
        //send question here
        apiService.addQuestion($rootScope.currentUser.activeQuiz, 
                                $scope.question.question,
                                $scope.question.points,
                                $scope.question.correctAns,
                                $scope.question.opt1,
                                $scope.question.opt2,
                                $scope.question.opt3,
                                $scope.question.opt4);
        //Empty the question array
        //$scope.question = {};
    };
}]);    