var adminService = angular.module('adminService', ['ngResource']);

adminService.factory('adminService', function($resource){
   return $resource('http;//somebackend/:admin', {admin: '@admin'} );
});