/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

describe('addQuestionController', function(){
    
    beforeEach(module('museumApp'));
    
    var $controller;
    var rootScope;
    var location;
    
    beforeEach(inject(function(_$rootScope_, _$controller_, $location){
        $controller = _$controller_;
        rootScope = _$rootScope_;
        location = $location;
    }));
    
    describe('$scope.init', function() {
       it('goes back to login screen if no active user', function() {
           var $scope = {};
           var controller = $controller('addQuestionController', {$scope: $scope});
           $scope.init();
           expect(location.path().toBe('/login'));
       }); 
    });
});
