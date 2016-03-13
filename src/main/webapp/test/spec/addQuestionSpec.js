/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

describe('addQuestionController', function(){
        var $controller;
    var rootScope, location, apiService, printSer;
    var location;
    var scope;
    
    beforeEach(module('museumApp'));
    

    beforeEach(inject(function(_$controller_){
        $controller = _$controller_;
    }));
    /*beforeEach(inject(function(_$rootScope_, _$controller_, _$location_, _printService_, _apiService_){
        $controller = _$controller_;
        rootScope = _$rootScope_;
        scope = rootScope.new();
        location = _$location_;
        apiService = _apiService_;
        printSer = _printService_;
    }));*/
    
    describe('$scope.init', function() {
       it('goes back to login screen if no active user', function() {
           var $scope = {};
           var controller = $controller('addQuestionController', {$scope: $scope, apiService: apiService});
           $scope.init();
           expect($location.path().toBe('/login'));
       }); 
    });
});
