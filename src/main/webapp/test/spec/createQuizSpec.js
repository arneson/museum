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
    var mockApiService;
    
    beforeEach(module('museumApp'));
    beforeEach(module(function($provide){
         mockApiService = {
               addQuiz: jasmine.createSpy()
           };
        $provide.value('apiService', mockApiService);
    }));
    
    beforeEach(inject(function(_$controller_, $location ){
        $controller = _$controller_;
        location = $location; 
    }));
    

   
   describe('cancel', function() {
        beforeEach(inject(function($rootScope){
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
    
    describe('submitQuiz', function(){
        beforeEach(inject(function($rootScope){
            scope = $rootScope.$new();
            rootScope = $rootScope;
            rootScope.currentUser = 'someUsr';
            scope = {
                'quiz':{
                    'name': 'Some quiz',
                    'points': '20',
                    'description': 'Some description'
                }
            };
            
            var controller = $controller('createQuizController', {$scope: scope, $location: location, apiService: mockApiService});
        }));
        
        it('Should call apiService.addQuiz', function(){
            scope.submitQuiz();
            expect(mockApiService.addQuiz).toHaveBeenCalled();
        });
    });
});

