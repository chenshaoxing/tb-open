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
            common.fn.showConfirmMessage("退出","亲确定要退出系统了么?",function(){
                $.cookie("id",null);
                $.cookie("name",null);
                $.cookie("user-photo",null);
                location.href='https://oauth.taobao.com/logoff?client_id=23175152&view=web';
            })
        }

    }
}
$(document).ready(function(){
    System.fn.getSellerInfo();
})