museumApp.controller('loginController',['$scope','apiService', function($scope, apiService){
    $scope.login = function(){
        apiService.login($scope.username, $scope.password);
    };
}]);