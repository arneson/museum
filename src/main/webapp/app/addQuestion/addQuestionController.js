museumApp.controller('addQuestionController',['$scope','$rootScope','$location','apiService','printService', function($scope,$rootScope,$location,apiService,printService){
    $scope.question = {};
    $scope.init= function(){
        if($rootScope.currentUser.activeQuestion !== undefined){
            $scope.submit = update;
            $scope.question.question = $rootScope.currentUser.activeQuestion.question;
            $scope.question.points = $rootScope.currentUser.activeQuestion.points;
            $scope.question.options = $rootScope.currentUser.activeQuestion.options;
            for(var i = 0; i<$scope.question.options.length;i++){
                if($scope.question.options[i].text===$rootScope.currentUser.activeQuestion.correctOption.text){
                    $scope.question.correctIndex = i;
                }
            }
            $scope.question.id=$rootScope.currentUser.activeQuestion.id;
            
        }else{
            $scope.submit = add;
            $scope.question = {
                options:[
                    {text:"",correct:false},
                    {text:"",correct:false},
                    {text:"",correct:false},
                    {text:"",correct:false}
                ]
            }
        }
    };
    if($rootScope.currentUser){
        $scope.init();
    }else{
        $location.go('/login');
    }
    $scope.printQR = function(){
        printService.PrintImage($scope.question.id);
    }
    function add() {
        //send question here
        apiService.addQuestion(
            $rootScope.currentUser.activeQuiz, 
            $scope.question.question,
            $scope.question.points,
            $scope.question.options,
            $scope.question.correctIndex
            ,function(questionId){$scope.question.id=questionId}
        );
        //Empty the question array
        //$scope.question = {};
    };
    function update() {
        //send question here
        apiService.updateQuestion(
            $scope.question.id,
            $scope.question.question,
            $scope.question.points,
            $scope.question.options,
            $scope.question.correctIndex
        ,function(question){$scope.question.id=question.id});
        //Empty the question array
        //$scope.question = {};
    };
}]);    