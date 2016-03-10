
museumApp.factory('apiService', function($rootScope,$http,$location){
   var baseUrl = 'http://localhost:8080/museum/webresources/museum';
    return {
        
        login: function(username, password){
            var data = {};
            data.username = username;
            data.password = password;
            $http({
                method  : 'POST',
                url     : baseUrl + '/login',
                data    : data
            }).then(function successCallback(response){
                $rootScope.currentUser = response.data;
                $rootScope.currentUser.password = password;
                $location.path('/admin');
                console.log("loginsuccess for: ", response.data);
                console.log("currentUser is", $rootScope.currentUser);
            },  function errorCallback(response) {
                console.log("loginfail for: ", response);
            });
        },
        
        signUp: function(name, username, password, email){
            var data = {};
            data.name = name;
            data.username = username;
            data.password = password;
            data.email = email;
            $http({
                method  : 'POST',
                url     : baseUrl + '/signup',
                data    : data
            }).then(function successCallback(response){
                $rootScope.currentUser = response;
                $rootScope.currentUser.password = password;
                $location.path('/admin');
                console.log("registerSuccess for: ", response);
            },  function errorCallback(response) {
                console.log("registerFail for: ", response);
            });
        },
        
        addQuiz: function(name, points, description){
            var data = {};
            data.name = name;
            data.points = points;
            data.description = description;
            data.password = $rootScope.currentUser.password;
            data.username = $rootScope.currentUser.username;
            console.log("This is quiz: ", data);
            $http({
                method  : 'POST',
                url     : baseUrl + '/'+$rootScope.currentUser.id+'/quizzes',
                data    : data
            }).then(function successCallback(response){
                $rootScope.currentUser.quiz.push(response);
                console.log("posted quiz: ", response);
            },  function errorCallback(response) {
                console.log("could not post quiz : ", response);
            });
        },
        editQuiz: function(name, points, description, id){
            var data = {};
            data.name = name;
            data.points = points;
            data.description = description;
            data.password = $rootScope.currentUser.password;
            data.username = $rootScope.currentUser.username;
            console.log("This is quiz: ", data);
            $http({
                method  : 'PUT',
                url     : 'http://localhost:8080/museum/webresources/' +id+'/quizzes',
                data    : data
            }).then(function successCallback(response){
                console.log("Updated quiz: ", response);
            },  function errorCallback(response) {
                console.log("could not update quiz : ", response);
            });
        },
        addQuestion: function(id, question, points, correct, opt1, opt2, opt3, opt4){
            var data = {};
            data.question = question;
            data.points = points;
            data.options = [];
            var text = {};
            text.text = opt1;
            data.options.push(text);
            console.log(data.options);
            //TODO Fix options!
            //data.options.push(opt1);
            
            data.password = $rootScope.currentUser.password;
            data.username = $rootScope.currentUser.username;
            $http({
                method  : 'POST',
                url     : baseUrl + '/'+$rootScope.currentUser.id+'/quizzes',
                data    : data
            }).then(function successCallback(response){
                console.log("posted quiz: ", response);
            },  function errorCallback(response) {
                console.log("could not post quiz : ", response);
            });
        }     
    }
    
    var update = function(user){
        
    }
}); 