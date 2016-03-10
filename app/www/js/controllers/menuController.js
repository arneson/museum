angular.module('museum.controllers')

.controller('menuController', function($scope, $rootScope,$state) {
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
              onClick:joinClick  
        },
        {
              text:"Hitta museum",
              icon:"ion-android-map",
              onClick:museumClick  
        },
        {
              text:"Mina quiz",
              icon:"ion-android-map",
              onClick:quizClick  
        }
    ]
});