museumApp.factory('printService', function($rootScope,$http){
    return {
            PrintImage:function(questionId) {
            Pagelink = "about:blank";
            var pwa = window.open(Pagelink, "_new");
            pwa.document.open();
            pwa.document.write(ImagetoPrint(questionId));
            //pwa.document.close();
        }
    };
    function ImagetoPrint(questionId) {
        return "<html><head><script>function step1(){\n" +
            "setTimeout('step2()', 10);}\n" +
            "function step2(){window.print();window.close()}\n" +
            "</scri" + "pt>\n"+
            "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css' integrity='sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7' crossorigin='anonymous'>\n"+
            "<style>img{display:block;} .center-content{margin-left:auto; margin-right:auto;} h4{font-family: 'Open Sans', 'Helvetica', sans-serif; text-align:center;}\n"+
            ".well{background-color: #5cb85c; border-radius:20px;}</style>\n"+
            "<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,700' rel='stylesheet' type='text/css'>\n"+
            "</head><body onload='step1()'>\n" +
            
            "<img class='center-content' width='300' src='img/icon_w_text.png'/>\n" +
            "<h4 class='center-content'>Pssst... Om du inte har appen, skanna QR-koden i förstoringsglaset...</h4>\n"+
            "<div class='well well-sm col-md-6 col-md-offset-3'>\n"+
                "<img class='center-content' width='600' src='/museum/webresources/question/" + questionId + "/qr' /></body></html>"+
            "</div>\n";
            
    }
});
