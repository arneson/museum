museumApp.factory('printService', function($rootScope,$http){
    return {
            PrintImage:function(questionId) {
            Pagelink = "about:blank";
            var pwa = window.open(Pagelink, "_new");
            pwa.document.open();
            pwa.document.write(ImagetoPrint(questionId));
            pwa.document.close();
        }
    };
    function ImagetoPrint(questionId) {
        return "<html><head><script>function step1(){\n" +
            "setTimeout('step2()', 10);}\n" +
            "function step2(){window.print();window.close()}\n" +
            "</scri" + "pt></head><body onload='step1()'>\n" +
            "<img src='/museum/webresources/question/" + questionId + "/qr' /></body></html>";
          }
});
