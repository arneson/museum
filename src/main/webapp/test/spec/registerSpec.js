/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

describe('registerController', function(){
    var $controller;
    var rootScope;
    var scope;
    var mockApiService;
   
    
    beforeEach(module('museumApp'));
    
    beforeEach(module(function($provide){
         mockApiService = {
               signUp: jasmine.createSpy()
           };
        $provide.value('apiService', mockApiService);
    }));
    
    beforeEach(inject(function(_$controller_){
        $controller = _$controller_;
    }));
    describe('Register', function() {
           beforeEach(inject(function($rootScope){
            //$scope = {};
            scope = $rootScope.$new();
            rootScope = $rootScope;
            var controller = $controller('registerController', {$scope: scope, $location: location, apiService: mockApiService});
       }));
        it('should call apiService.signUp', function(){
            scope.register();
            expect(mockApiService.signUp).toHaveBeenCalled();
        });
    });
    
});
