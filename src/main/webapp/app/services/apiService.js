
museumApp.factory('apiService', function($rootScope,$http,$location){
   var baseUrl = '/museum/webresources';
    return {
        
        login: function(username, password){
            var data = {};
            data.username = username;
            data.password = password;
            $http({
                method  : 'POST',
                url     : baseUrl + '/museum/login',
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
                url     : baseUrl + '/museum/signup',
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
        
        addQuiz: function(name, points, description,callback){
            var data = {};
            data.name = name;
            data.points = points;
            data.description = description;
            data.password = $rootScope.currentUser.password;
            data.username = $rootScope.currentUser.username;
            console.log("This is quiz: ", data);
            $http({
                method  : 'POST',
                url     : baseUrl + '/museum/'+$rootScope.currentUser.id+'/quizzes',
                data    : data
            }).then(function successCallback(response){
                //fetch the new quiz
                this.getQuiz(response.data).then(function successCallback(response){
                    $rootScope.currentUser.quiz.push(response.data);
                    callback();
                },  function errorCallback(response) {
                    console.log("could not get new quiz : ", response);
                });
                console.log("posted quiz: ", response);
            },  function errorCallback(response) {
                callback();
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
                url     : baseUrl+'/quiz/'+id,
                data    : data
            }).then(function successCallback(response){
                console.log("Updated quiz: ", response);
            },  function errorCallback(response) {
                console.log("could not update quiz : ", response);
            });
        },
        addQuestion: function(id, question, points, options,correctIndex,cb){
            var data = {};
            data.question = question;
            data.points = points+'';
            data.options = options.slice();
            data.correct = data.options.splice(correctIndex,1)[0];
            
            //TODO Fix options!
            //data.options.push(opt1);
            data.username = $rootScope.currentUser.username;
            console.log(data);
            $http({
                method  : 'POST',
                url     : baseUrl+'/quiz/'+$rootScope.currentUser.activeQuiz+'/questions',
                data    : data,
                headers :{"password":$rootScope.currentUser.password}
            }).then(function successCallback(response){
                console.log("posted question: ", response);
                cb(response.data);
            },  function errorCallback(response) {
                console.log("could not post question : ", response);
            });
        },
        updateQuestion: function(id, question, points, options,correctIndex,cb){
            console.log('this is what im doing');
            var data = {};
            data.question = question;
            data.points = points+'';
            data.options = options.slice();
            data.correct = data.options.splice(correctIndex,1)[0];
            
            //TODO Fix options!
            //data.options.push(opt1);
            data.username = $rootScope.currentUser.username;
            console.log(data);
            $http({
                method  : 'PUT',
                url     : baseUrl+'/quiz/'+$rootScope.currentUser.activeQuiz+'/question/'+id,
                data    : data,
                headers :{"password":$rootScope.currentUser.password}
            }).then(function successCallback(response){
                console.log("updated question: ", response);
                cb(response.data);
            },  function errorCallback(response) {
                console.log("could not update question : ", response);
            });
        },
        getQuestions: function(id){
            
            return $http({
                method  : 'GET',
                url     : baseUrl+'/quiz/'+id+'/questions'
            });
        },
        getQuiz: function(id){
            
            return $http({
                method  : 'GET',
                url     : baseUrl+'/quiz/'+id
            });
        },
        getQuizStatistics: function(id){
            
            return $http({
               method   : 'GET',
               url      : baseUrl+'/quiz/' + id     +'/statistics' 
            });
        }
    }
    
    var update = function(user){
        
    }
}); 