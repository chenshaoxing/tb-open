 $_GET = (function () {
    var url = window.document.location.href.toString();
    var u = url.split("?");
    if (typeof(u[1]) == "string") {
        u = u[1].split("&");
        var value = {};
        for (var i in u) {
            var j = u[i].split("=");
            value[j[0]] = j[1];
        }
        return value;
    } else {
        return {};
    }
})();
var AJAX ={
    ajax: function(url,params,successFun,errorFun){
        $("#load").html("<img  src='/amaze/assets/cc.gif'>");
        $("#load-title").text("物流信息加载中...");
        $.ajax({
            type: "POST",
            url: url,
            data: params,
            cache:false,
            dataType: "json",
            success: function (data) {
                $("#load").html("");
                $("#load-title").html(params.type+"-"+params.postId);
                successFun(data);
            },
            error: function (data) {
                $("#load").html("");
                $("#load-title").text("物流信息加载失败");
                errorFun();
            }
        });
    }
}

