
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
                $rootScope.currentUser = response.data;
                $rootScope.currentUser.password = password;
                $location.path('/admin');
                console.log("registerSuccess for: ", response.data);
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
                $rootScope.currentUser.quiz.push(response.data);
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
                url     : 'http://localhost:8080/museum/webresources/quiz/'+id,
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
            data.points = points+'';
            data.options = [
                {
                    "text"  : opt1
                },
                {
                    "text"  : opt2
                },
                {
                    "text"  : opt3,
                },
                {
                    "text"  : opt4,
                }
            ];
            for(var i = 0; i<data.options.length; i++){
                if(data.options[i].text == correct){
                    data.correct = data.options.splice(i,1)[0];
                }
            }
            
            //TODO Fix options!
            //data.options.push(opt1);
            
            data.password = $rootScope.currentUser.password;
            data.username = $rootScope.currentUser.username;
            console.log(data);
            $http({
                method  : 'POST',
                url     : 'http://localhost:8080/museum/webresources/quiz/'+$rootScope.currentUser.activeQuiz+'/questions',
                data    : data
            }).then(function successCallback(response){
                console.log("posted question: ", response);
            },  function errorCallback(response) {
                console.log("could not post question : ", response);
            });
        },
        getQuestions: function(id){
            
            return $http({
                method  : 'GET',
                url     : 'http://localhost:8080/museum/webresources/quiz/'+id+'/questions'
            });
        }
    }
    
    var update = function(user){
        
    }
}); 