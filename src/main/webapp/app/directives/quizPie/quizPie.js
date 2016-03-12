museumApp.directive('quizpie',[function(){
    return {
          restrict: 'E',
          templateUrl: 'app/directives/quizPie/quizPieTmpl.html',
          controller: 'createQuizController'
        };
}]);
