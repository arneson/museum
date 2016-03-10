
museumApp.controller('adminController',['$scope','$rootScope','$location', function($scope,$rootScope,$location){
    console.log($rootScope.currentUser);
    if($rootScope.currentUser == undefined){
        $location.path('/login');
    }
    
    $scope.quizzes = $rootScope.currentUser.quiz;
    
    
    
}]);

