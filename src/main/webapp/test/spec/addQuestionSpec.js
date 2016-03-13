/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

describe('addQuestionController', function(){
    var $controller;
    var rootScope;
    var location;
    var $scope;
    var mockApiService;
    var mockUser= {
        currentUser:{
            'name': 'SomeName',
            'activeQuestion': {
                'id': '3',
                'points': '20',
                'options': [
                    {
                        'text':'answer1',
                        'id': '10'
                    },
                    {
                        'text':'answer2',
                        'id': '20'
                    },
                    {
                        'text':'answer3',
                        'id': '30'
                    },
                    {
                        'text':'answer4',
                        'id': '40'
                    }
                ],
                correctOption: {
                    'text': 'answer1',
                    'id': '10'
                }
            }
        }
    }
    
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

    
    describe('No user', function() {
       beforeEach(function(){
            $scope = {};    
       });
       
       it('goes back to login screen', function() {
           var controller = $controller('addQuestionController', {$scope: $scope,  $location: location});
           expect(location.path()).toBe('/login');
       }); 
    });   
    describe('With user', function() {
        beforeEach(function(){
            $scope = {};
            rootScope = {
               'currentUser': 'someUsr'
           };

       }); 
       
       it('should set options to empty texts if no activeQuestion exists ', function(){
           var options = {
                options:[
                    {text:"",correct:false},
                    {text:"",correct:false},
                    {text:"",correct:false},
                    {text:"",correct:false}
                ]
            };    
            var controller = $controller('addQuestionController', {$scope: $scope, $rootScope: rootScope, $location: location});
            expect($scope.question).toEqual(options);
       });
       it('should set scope.question.* to value of  rootScope.currentUser.activeQuestion.*', function(){
           var rootScope = mockUser;
           
           var controller = $controller('addQuestionController', {$scope: $scope, $rootScope: rootScope, $location: location});
           expect($scope.question.question).toBe(rootScope.currentUser.activeQuestion.question);
           expect($scope.question.points).toBe(rootScope.currentUser.activeQuestion.points);
           expect($scope.question.options).toBe(rootScope.currentUser.activeQuestion.options);
           expect($scope.question.id).toBe(rootScope.currentUser.activeQuestion.id);
       });
       it('should call add question when hitting submit', function(){
            var controller = $controller('addQuestionController', {$scope: $scope, $rootScope: rootScope, $location: location, apiService: mockApiService});
            $scope.submit();
            expect(mockApiService.addQuestion).toHaveBeenCalled();
       });
    });
});
