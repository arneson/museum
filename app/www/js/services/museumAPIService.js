angular.module('museum.services')

.factory('museumAPIService', function($http,$rootScope) {
    var at;
    var url = 'http://192.168.0.113:8080/museum/webresources';
    var userId;
    return{
        login:function(at,callback){
            $http.post(url+'/visitor/login',{"access_token":at}).success(function(user){
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
        }
    }
});