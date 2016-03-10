angular.module('museum.controllers')

.controller('menuController', function($scope, $rootScope,$state,$ionicSideMenuDelegate,$ionicHistory) {
    $scope.$on("$ionicView.beforeEnter", function() {
        if(!$rootScope.currentUser){
            //$state.go('login');
        }
    });
    ionic.Platform.ready(function(){
       if(!$rootScope.currentUser){
            //$state.go('login');
        }
    });
    
    $rootScope.menuItems = [
        {
              text:"Anslut till grupp",
              icon:"ion-ios-people",
              onClick:"joinClick()"  
        },
        {
              text:"Hitta museum",
              icon:"ion-android-map",
              onClick:"museumClick()"  
        },
        {
              text:"Mina quiz",
              icon:"ion-android-map",
              onClick:"quizClick()"  
        }
    ];
    $scope.leftMenuClick = function (){
        if($rootScope.backEnabled){
            $ionicHistory.goBack();
        }else{
            $ionicSideMenuDelegate.toggleLeft();
        }
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
        $ionicSideMenuDelegate.toggleLeft();
        $state.go('app.museums');
    }
    $scope.quizClick = function(){
        $ionicSideMenuDelegate.toggleLeft();
        $state.go('app.quiz');
    }
});