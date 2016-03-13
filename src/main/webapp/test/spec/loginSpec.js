/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

describe('loginController', function(){
    var $controller;
    var rootScope, location;
    var location;
    var scope;
    var mockApiService;
   
    
    beforeEach(module('museumApp'));
    
    beforeEach(module(function($provide){
         mockApiService = {
               login: jasmine.createSpy()
           };
        $provide.value('apiService', mockApiService);
    }));
    
    beforeEach(inject(function(_$controller_, $location ){
        $controller = _$controller_;
        location = $location; 
    }));
    describe('Login', function() {
           beforeEach(inject(function($rootScope){
            //$scope = {};
            scope = $rootScope.$new();
            rootScope = $rootScope;
            var controller = $controller('loginController', {$scope: scope, $location: location, apiService: mockApiService});
       }));
        it('should call apiService.login', function(){
            scope.login();
            expect(mockApiService.login).toHaveBeenCalled();
        });
    });
    
    describe('With user', function() {
        beforeEach(inject(function($rootScope){
            //$scope = {};
            scope = $rootScope.$new();
            rootScope = $rootScope;
           
       }));
       it('Should change path to /admin', function(){
            rootScope.currentUser = 'someUsr';
            var controller = $controller('loginController', {$scope: scope, $location: location});
            expect(location.path()).toBe('/admin');
       });
    });
});
