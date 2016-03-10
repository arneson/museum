museumApp.controller('loginController',['$scope','$http', function($scope,$http){
    $scope.login = function(){
        var data = {
            "username": $scope.username,
            "password": $scope.password
        }
        $http({
            method: 'POST',
            url: 'http://localhost:8080/Museum/webresources/museum/login',
            data: data
        }).then(function successCallback(response){
            
            console.log("loginsuccess for: ", response);
        },  function errorCallback(response) {
            console.log("loginfail for: " + response);
        });
    };
}]);