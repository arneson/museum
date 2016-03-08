/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

museumApp.config(['$routeProvider', 
    function($routeProvider){
        $routeProvider.
                when('/login',{
                    templateUrl: 'app/login/index.html',
                    controller: 'loginController'
                }).
                when('/admin',{
                    templateUrl: 'app/admin/index.html',
                    controller: 'adminController'
                }).
                when('/createQuiz',{
                    templateUrl: 'app/creteQuiz/index.html',
                    controller: 'createQuizController'
                }).
                when('/addQuestion',{
                    templateUrl: 'app/addQuestion/index.html',
                    controller: 'addQuestionController'
                }).
                otherwise({
                    redirectTo: '/'
                });
    }]);


