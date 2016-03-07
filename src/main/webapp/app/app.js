/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var museumApp = angular.module('museumApp', ['ngRoute']);

museumApp.config(['$routeProvider', 
    function($routeProvider){
        $routeProvider.
                when('/login',{
                    templateUrl: 'partials/login.html',
                    controller: 'MuseumAppCtrl'
                }).
                otherwise({
                    redirectTo: '/'
                });
    }]);

museumApp.controller('MuseumAppCtrl', function($scope) {
   $scope.names = [
       { 'name' : 'Sebastian'},
       { 'name' : 'Simon'},
       { 'name' : 'Victor'}
       
       
   ] 
});