angular.module('museum.services')

.factory('museumAPIService', function($http,$rootScope) {
    return{
        login:function(at,callback){
            callback("hej");
        }
    }
});