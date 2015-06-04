var System = {
    globalVariable:{
        ajaxSuccess:"success"
    },
    url:{
        getSellerInfo:"/index/seller-info"
    },
    ejs:{
        updateAccount:"/script/system/ejs/updateAccount.ejs"
    },
    fn:{
        getSellerInfo:function(){
            function callback(data){
                if(data.success){
                    $("#nav-user-photo").attr("src",data.data.avatar);
                    $("#user-info").html("<small>欢迎光临,</small>"+data.data.nick);
                    $.cookie("user-photo",data.data.avatar);
                }
            }
           var nick  = decodeURI($.cookie("name"));
            var userPhoto = $.cookie("user-photo");
            if(!nick || !userPhoto){
                common.fn.ajaxNotLoadingDialog(System.url.getSellerInfo,{},callback)
            }else{
                $("#nav-user-photo").attr("src",userPhoto);
                $("#user-info").html("<small>欢迎光临,</small>"+nick);
            }
        },
        logout:function(){
            $.cookie("id",null);
            location.href='/index.html';
        }

    }
}
$(document).ready(function(){
    System.fn.getSellerInfo();
})