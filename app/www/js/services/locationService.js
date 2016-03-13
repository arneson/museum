angular.module('museum.services')

.factory('locationService', function($rootScope, $ionicPopup) {
    
    return {
        getPosition: function(cb) {

            var geolocationSuccess = function(position) {
                var gps ={
                    lat: position.coords.latitude,
                    long: position.coords.longitude,
                };
                $rootScope.GPSposition = gps;
                if(cb){
                    cb(null,gps);
                }
            };
            var geolocationError = function(error) {
                $ionicPopup.show({
                    title: 'Please turn on location services.',
                    buttons: [{
                        text: 'Ok',
                        type: 'buttonMultiple',
                    }]

                });
                callback(error);
            };

            navigator.geolocation.getCurrentPosition(geolocationSuccess, geolocationError);

        }
    }
});