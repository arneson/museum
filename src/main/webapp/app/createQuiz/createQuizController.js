museumApp.controller('createQuizController',['$scope', '$route','$location', 'apiService', function($scope, $route, $location, apiService){
    //$scope.questions = 
    
    $scope.quiz = {};
    
    $scope.routeToQuestion = function(id){
        var address;
        
        if(id){
            address = $location.url() + '/question/' + id;
        }else{
            address = $location.url() + '/addQuestion';
        }
        
        $location.path(address); 
    };
    
    $scope.submitQuiz = function(){
        apiService.addQuiz($scope.quiz.title, $scope.quiz.points, $scope.quiz.desc);
    };
    
}]);
