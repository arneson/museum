
museumApp.controller('adminController',['$scope','$rootScope','$location', function($scope,$rootScope,$location){
    //console.log($rootScope.currentUser);
    if($rootScope.currentUser === undefined){
        $location.path('/login');
    }else{
    
        $scope.quizzes = $rootScope.currentUser.quiz;

        $scope.saveId = function(quiz_id){
            $rootScope.currentUser.activeQuiz = quiz_id;
        };
    }
}]);

