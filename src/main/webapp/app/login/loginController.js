museumApp.controller('loginController',['$scope','$http','$location', function($scope,$http,$location){
    $scope.login = function(){
        var userData = {
            "username": $scope.username,
            "password": $scope.password
        };
        console.log("this is userData", userData);
        $http({
            method: 'POST',
            url: 'http://localhost:8080/Museum/webresources/museum/login',
            data: userData
        }).then(function successCallback(response){
            $location.path('/admin');
            console.log("loginsuccess for: ", response);
        },  function errorCallback(response) {
            console.log("loginfail for: ", response);
        });
    };
}]);