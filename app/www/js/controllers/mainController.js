angular.module('museum.controllers')

.controller('mainController', function($scope, $rootScope,$state) {
    $scope.$on("$ionicView.beforeEnter", function() {

    });
    $scope.scanClick = function(){
        cordova.plugins.barcodeScanner.scan(
            function (result) {
                if(result.text.substring(0,14)=="museumsjakten:"){
                    var id = result.text.substring(14);
                    $state.go('question',{"questionId":id});
                }
            }, 
            function (error) {
                alert("Scanning failed: " + error);
            }
        );
    }
    $scope.joinClick = function(){
        cordova.plugins.barcodeScanner.scan(
            function (result) {
                if(result.text.substring(0,14)=="museumsjakten:group:"){
                    var id = result.text.substring(14);
                    $state.go('app.group',{"groupId":id});
                }
            }, 
            function (error) {
                alert("Scanning failed: " + error);
            }
        );
    }
    $scope.museumClick = function(){
        $state.go('app.museums');
    }
});