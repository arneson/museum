museumApp.controller('addQuestionController',['$scope', function($scope){
    $scope.question = {};
    $scope.submit = function() {
        //send question here
        
        //Empty the question array
        $scope.question = {};
    };
}]);    