museumApp.controller('loginController',['$scope','$rootScope','$location','apiService', function($scope,$rootScope ,$location, apiService){
    if($rootScope.currentUser){
        $location.path('/admin');
    }
    $scope.login = function(){
        apiService.login($scope.username, $scope.password);
    };
}]);