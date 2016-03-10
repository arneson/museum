museumApp.controller('createQuizController',['$scope', '$route','$location',  function($scope, $route,$location){
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
    
    $scope.routeToQuestion = function(id){
        var address;
        
        if(id){
            address = $location.url() + '/question/' + id;
        }else{
            address = $location.url() + '/addQuestion';
        }
        
        $location.path(address); 
    };
    
}]);
