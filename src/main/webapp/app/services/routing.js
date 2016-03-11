/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

museumApp.config(['$routeProvider', 
    function($routeProvider){
        $routeProvider.
                when('/login',{
                    templateUrl : 'app/login/index.html',
                    controller  : 'loginController'
                }).
                when('/register',{
                    templateUrl : 'app/register/index.html',
                    controller  : 'registerController'
                }).
                when('/admin',{
                    templateUrl : 'app/admin/index.html',
                    controller  : 'adminController'
                }).
                when('/createQuiz',{
                    templateUrl : 'app/createQuiz/index.html',
                    controller  : 'createQuizController'
                }).
                when('/manageQuiz/:quizId/addQuestion',{
                    templateUrl : 'app/addQuestion/index.html',
                    controller  : 'addQuestionController'
                }).
                when('/manageQuiz/:quizId',{
                    templateUrl : 'app/createQuiz/index.html',
                    controller  : 'createQuizController'
                }).
                when('/manageQuiz/:quizId/question/:questionId',{
                    templateUrl: 'app/addQuestion/index.html',
                    controller: 'addQuestionController'
                }).        
                otherwise({
                    redirectTo  : '/login'
                });
    }]);


