angular.module('museum.services')

.factory('museumAPIService', function($http,$rootScope) {
    var at;
    var url = 'http://172.20.10.2:8080/museum/webresources';
    var userId;
    return{
        login:function(at,callback){
            $http.post(url+'/visitor/login',{"access_token":at}).success(function(user){
                $http.defaults.headers.common.access_token = at;
                at = at;
                userId = user.id;
                callback(user);
            });
        },
        getQuestion:function(id,callback){
            $http.get(url+'/question/'+id).success(function(question){
                callback(question);
            });
        },
        answerQuestion:function(questionId,optionId,callback){
            $http.post(url+'/question/'+questionId+'/answer',
                    {"visitor_id":userId,"answer_id":optionId})
                .success(callback);
        },
        getMuseums:function(callback){
            $http.get(url+'/museum/')
                .success(callback);
        },
        refreshPoints:function(callback){
            $http.get(url+'/visitor/'+userId+'/points')
                .success(callback);
        }
    }
});