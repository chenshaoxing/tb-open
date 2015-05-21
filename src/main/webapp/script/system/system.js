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
                }
            }
            common.fn.ajaxNotLoadingDialog(System.url.getSellerInfo,{},callback)
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