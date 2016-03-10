museumApp.controller('createQuizController',
    ['$scope','$location', 'apiService', '$rootScope',
    
    function($scope, $location, apiService, $rootScope){
    //$scope.questions = 
    
    $scope.init = function(){
      console.log('fucked up shit');
      var l = $rootScope.currentUser.quiz.length; 
      
      for(var i=0; i < l; i++){
          if($rootScope.currentUser.quiz[i].id === $rootScope.currentUser.activeQuiz){
              $scope.quiz = $rootScope.currentUser.quiz[i];
          } 
      }
    };
    
    $scope.routeToQuestion = function(question_id){
        var address;
        
        if(question_id){
            address = $location.url() + '/question/' + question_id;
        }else{
            address = $location.url() + '/addQuestion';
        }
        
        $location.path(address); 
    };
    
    $scope.submitQuiz = function(){
        var url = $location.url();
        console.log(url);
        if(url.indexOf('manageQuiz') > -1){
            console.log('edit');
            apiService.editQuiz($scope.quiz.name, 
                    $scope.quiz.points +'', 
                    $scope.quiz.description, 
                    $rootScope.currentUser.activeQuiz);
        }else{
            console.log('create');
            apiService.addQuiz($scope.quiz.name, $scope.quiz.points, $scope.quiz.description);
        }
    };
    
    $scope.cancel = function(){
        $rootScope.currentUser.activeQuiz = "";
        $location.path('admin');
    };
    
}]);
