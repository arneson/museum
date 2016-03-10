museumApp.controller('questionController',['$scope', '$route','$location',  function($scope, $route,$location){
    $scope.questions = [{
            text: 'Hur många lingon finns det i världen?',
            id: '1'
    },{
            text: 'Fråga nummero dos?',
            id: '2'
    },{
            text: 'Hur många idioter finns det i världen just i detta nu?',
            id: '3'
    },{
            text: 'Question 4?',
            id: '4'
    },{
            text: 'Hur många lingon finns det i världen?',
            id: '5'
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
    
    $scope.getUrl = function(id){
       var address = $location.url() + '/question/' + id;
       $location.path(address); 
    };
    
}]);

museumApp.directive('questions', [function(){
        return {
          restrict: 'E',
          templateUrl: 'app/question/questionTmpl.html',
          controller: 'questionController'
        };
}]);