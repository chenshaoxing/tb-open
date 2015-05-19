var system = {
    globalVariable:{
        ajaxSuccess:"success"
    },
    url:{
        save:"/account/save"
    },
    ejs:{
        updateAccount:"/script/system/ejs/updateAccount.ejs"
    },
    fn:{
        showDialog:function(){
            common.fn.showDialog(system.ejs.updateAccount,{'title':'修改密码'},null,null,null);
        },
        save:function(dialogId){
            function callback(data){
                if(data.success){
                    $("#"+dialogId).modal("hide");
                    common.fn.showInfoMessages("密码修改","修改成功,请退出后重新登录!");
                }else{
                    common.fn.showInfoMessages("密码修改","原始密码错误！");
                }
            }
            var accountUpdateForm = $("#accountUpdateForm").validate({});
            if(accountUpdateForm.form()) {
                var params = common.fn.getFromJsonData("accountUpdateForm");
                var p = {};
                p.oldPwd = params.oldPwd;
                p.id = $.cookie("id");
                p.newPwd = params.newPwd;
                if(params.newPwd == params.againNewPwd){
                    common.fn.ajax(system.url.save,p,callback);
                }else{
                    common.fn.showInfoMessages("密码修改","两次新密码不相同!");
                }
            }
        },
        logout:function(){
            $.cookie("id",null);
            location.href='/index.html';
        }

    }
}