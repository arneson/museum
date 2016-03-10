museumApp.controller('registerController',['$scope','apiService', function($scope,apiService){
    $scope.register = function(){
        apiService.signUp($scope.name,$scope.username, $scope.password, $scope.email);

    };
}]);