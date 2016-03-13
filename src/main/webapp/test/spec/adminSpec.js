/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
describe('addQuestionController', function(){
        var $controller;
    var rootScope, location, apiService, printSer;
    var location;
    var $scope;
    var mockApiService;
    
    beforeEach(module('museumApp'));
    
    beforeEach(inject(function(_$controller_, $location ){
        $controller = _$controller_;
        location = $location;
        
    }));
    
    describe('No user', function() {
       beforeEach(function(){
            $scope = {};
            
       });
       
       it('goes back to login screen', function() {
           var controller = $controller('adminController', {$scope: $scope, $location: location});
           expect(location.path()).toBe('/login');
       });
   });
});

