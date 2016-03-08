angular.module('museum.controllers')

.controller('mainController', function($scope, $rootScope,$state) {
    $scope.$on("$ionicView.beforeEnter", function() {

    });
    $scope.scanClick = function(){
        cordova.plugins.barcodeScanner.scan(
            function (result) {
                if(result.text.substring(0,14)=="museumsjakten:"){
                    var id = result.text.substring(14);
                    $state.go('app.question',{"questionId":id});
                }
            }, 
            function (error) {
                alert("Scanning failed: " + error);
            }
        );
    }
});