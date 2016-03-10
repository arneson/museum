museumApp.controller('registerController',['$scope','$http','$location', function($scope,$http,$location){
    $scope.register = function(){
        var data = {
            "name"      : $scope.name,
            "username"  : $scope.username,
            "password"  : $scope.password,
            "email"     : $scope.email
        }
        $http({
            method: 'POST',
            url: 'http://localhost:8080/Museum/webresources/museum/signup',
            data: data
        }).then(function successCallback(response){
            $location.path('#/admin');
            console.log("registerSuccess for: ", response);
        },  function errorCallback(response) {
            console.log("registerFail for: " + response);
        });
    };
}]);