angular.module('museum.controllers')

.controller('loginController', function($scope, $rootScope,$state,museumAPIService) {
    ionic.Platform.ready(function(){
        if($scope.currentUser){
            $state.go('app.main');
        }
    });

    $scope.loginClick = function(){
        if(window.device){
            facebookConnectPlugin.login(["public_profile,user_friends,email"],
                app.fbLoginSuccess,
                function (error) { alert("" + error) }
            );
        }else{
            FB.login(function (res) {
                if (res.status == "connected" && res.authResponse) {
                    FBlogin(res.authResponse.accessToken);
                }
            }, { scope: 'email,public_profile,user_friends' });
        }
    };
    function FBlogin(at){
        museumAPIService.login(at,function(res){console.log(at)})
    };
});