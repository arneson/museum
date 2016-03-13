angular.module('museum.controllers')

.controller('mainController', function($scope, $rootScope,$state) {
    $scope.$on("$ionicView.beforeEnter", function() {
        $rootScope.backEnabled = false;
    });
    var scanning = false;
    $scope.scanClick = function(){
        if(!scanning){
            scanning =true;
            cordova.plugins.barcodeScanner.scan(
                function (result) {
                    scanning =false;
                    if(result.text.substring(0,14)=="museumsjakten:"){
                        var id = result.text.substring(14);
                        $state.go('question',{"questionId":id});
                    }
                }, 
                function (error) {
                    scanning =false;
                    alert("Scanning failed: " + error);
                }
            );
        }
    }
});