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
    beforeEach(module(function($provide){
         mockApiService = {
               addQuestion: jasmine.createSpy()
           };
        $provide.value('apiService', mockApiService);
    }));
    

    beforeEach(inject(function(_$controller_, $location){
        $controller = _$controller_;
        location = $location;
    }));
    /*beforeEach(inject(function(_$rootScope_, _$controller_, _$location_, _printService_, _apiService_){
        $controller = _$controller_;
        rootScope = _$rootScope_;
        scope = rootScope.new();
        location = _$location_;
        apiService = _apiService_;
        printSer = _printService_;
    }));*/
    
    describe('No user', function() {
       beforeEach(function(){
            $scope = {};
            
       });
       
       it('goes back to login screen', function() {
           var controller = $controller('addQuestionController', {$scope: $scope,  $location: location, apiService: apiService});
           expect(location.path()).toBe('/login');
       }); 
    });   
    describe('With user', function() {
        beforeEach(function(){
            $scope = {};
            rootScope = {
               'currentUser': 'someUsr'
           };
           var controller = $controller('addQuestionController', {$scope: $scope, $rootScope: rootScope, $location: location});
       }); 
    
       it('calls init and sets options', function(){
           var options = {
                options:[
                    {text:"",correct:false},
                    {text:"",correct:false},
                    {text:"",correct:false},
                    {text:"",correct:false}
                ]
            };  
          expect($scope.question).toEqual(options);
       });
       
       
       it('should call add question', function(){
           rootScope = {
               'currentUser': 'someUsr'
           };
            
           $scope.submit;
           expect(mockApiService.addQuestion).toHaveBeenCalled();
       });
    });
});
