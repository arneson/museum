/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
describe('createQuizController', function(){
    var $controller;
    var rootScope;
    var location;
    var scope;
   
    
    beforeEach(module('museumApp'));
    
    beforeEach(inject(function(_$controller_, $location ){
        $controller = _$controller_;
        location = $location; 
    }));
    

   
   describe('cancel', function() {
        beforeEach(inject(function($rootScope){
            //$scope = {};
            scope = $rootScope.$new();
            rootScope = $rootScope;
            rootScope.currentUser = 'someUsr';
            
       }));
        it('Should set location to admin', function() {
            
            var controller = $controller('createQuizController', {$scope: scope, $location: location});
            scope.cancel();
            expect(location.path()).toBe('/admin');
        });
    });
});

