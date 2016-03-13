/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
describe('adminController', function(){
    var $controller;
    var rootScope, location;
    var location;
    var scope;
   
    
    beforeEach(module('museumApp'));
    
    beforeEach(inject(function(_$controller_, $location ){
        $controller = _$controller_;
        location = $location; 
    }));
    
    describe('No user', function() {
       beforeEach(function(){
            scope = {};
       });
       
       it('goes back to login screen', function() {
           var controller = $controller('adminController', {$scope: scope, $location: location});
           expect(location.path()).toBe('/login');
       });
   });
   
   describe('With user', function() {
        beforeEach(inject(function($rootScope){
            //$scope = {};
            scope = $rootScope.$new();
            rootScope = $rootScope;
            rootScope.currentUser = 'someUsr';
            rootScope.currentUser.activeQuiz = '1';
       }));
        it('Should set quizzes to currentUser.quiz', function() {
            rootScope.currentUser.quiz = "some";
            var quiz = rootScope.currentUser.quiz;
            var controller = $controller('adminController', {$scope: scope, $location: location});
            expect(scope.quiz).toBe(quiz);
        });
    });
});

