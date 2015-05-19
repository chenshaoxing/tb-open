var Login = {
    globalVariable:{
        ajaxSuccess:"success"
    },
    url:{
    login:"/login"
    },
    ejs:{


    },
    fn:{

        login:function(){
            var userName = $("#login-name").val();
            var password = $("#login-pass").val();
            var params = {};
            params.user = userName;
            params.pass = password;
            common.fn.ajaxSync(Login.url.login,params,callback);
            function callback(data){
                if(data.success){

                    location.href = "/main";
                }else{
                    common.fn.showInfoMessages("提示","用户名或密码错误！");
                }
            }
        }
    }
}
