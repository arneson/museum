angular.module('museum.services')

.factory('museumAPIService', function($http,$rootScope) {
    var at;
    var url = 'http://192.168.0.113:8080/Museum/webresources';
    var fbid;
    return{
        login:function(at,callback){
            $http.post(url+'/visitor/login',{"access_token":at}).success(function(user){
                at = at;
                callback(user);
            });
        },
        getQuestion:function(id,callback){
            $http.get(url+'/question/'+id).success(function(question){
                callback(question);
            });
        }
    }
});