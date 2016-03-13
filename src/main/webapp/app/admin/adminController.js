
museumApp.controller('adminController',['$scope','$rootScope','$location','apiService', function($scope,$rootScope,$location,apiService){
    if($rootScope.currentUser === undefined){
        $location.path('/login');
    }else{
    
        $scope.quizzes = $rootScope.currentUser.quiz;

        $scope.saveId = function(quiz_id){
            $rootScope.currentUser.activeQuiz = quiz_id;
        };
        $rootScope.signOut = apiService.signOut;
    }
}]);

