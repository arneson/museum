museumApp.controller('createQuizController',
    ['$scope','$location', 'apiService', '$rootScope','apiService',
    
    function($scope, $location, apiService, $rootScope, apiService){
    $scope.init = function(){
      
      if($rootScope.currentUser.quiz !== undefined){
          var l = $rootScope.currentUser.quiz.length; 

        for(var i=0; i < l; i++){
            if($rootScope.currentUser.quiz[i].id === $rootScope.currentUser.activeQuiz){
                $scope.quiz = $rootScope.currentUser.quiz[i];
            } 
        }
        apiService.getQuestions($rootScope.currentUser.activeQuiz).then(
                function(res){
                    console.log("this is res", res.data);
                    $scope.questions = res.data;
                },function(err){
                    
                });

      }
        
    };
    
    $scope.routeToQuestion = function(question){
        var address;
        
        if(question){
            $rootScope.currentUser.activeQuestion = question;
            address = $location.url() + '/question/' + question.id;
        }else{
            $rootScope.currentUser.activeQuestion=undefined;
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
        backToAdminPage()
        
    };
    
    $scope.cancel = function(){
        $rootScope.currentUser.activeQuiz = "";
        backToAdminPage()
    };
    
    function backToAdminPage(){
        $location.path('admin');
    }
    
}]);
