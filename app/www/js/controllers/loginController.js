angular.module('museum.controllers')

.controller('loginController', function($scope, $rootScope,$state,museumAPIService) {
    ionic.Platform.ready(function(){
        if($rootScope.currentUser!==undefined){
            $state.go('app.main');
        }
    });

    $scope.loginClick = function(){
        if(window.device){
            facebookConnectPlugin.login(["public_profile,user_friends,email"],
                loginSuccess,
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
        museumAPIService.login(at,loginSuccess);
    };
    function loginSuccess(user){
        $rootScope.currentUser = user;
        $state.go('app.main');
        console.log(user);
    };
});