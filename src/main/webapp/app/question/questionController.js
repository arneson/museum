museumApp.controller('questionController',['$scope', function($scope){
        $scope.questions = [{
                text: 'Hur många lingon finns det i världen?'
        },{
                text: 'Hur många lingon finns det i världen?'
        },{
                text: 'Hur många lingon finns det i världen?'
        },{
                text: 'Hur många lingon finns det i världen?'
        },{
                text: 'Hur många lingon finns det i världen?'
        }];
                
        $scope.answerOptions = [{
                text: 'answer1',
                isCorrect: false
        },{
                text: 'answer2',
                isCorrect: false
        },{
                text: 'answer3',
                isCorrect: false
        },{
                text: 'answer4',
                isCorrect: true
        },{
                text: 'answer5',
                isCorrect: false
        }];
}]);

museumApp.directive('question', [function(){
        return {
          restrict: 'E',
          templateUrl: 'app/question/questionTmpl.html',
          controller: 'questionController'
        };
}]);