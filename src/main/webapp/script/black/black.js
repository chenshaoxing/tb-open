var Black = {
    globalVariable:{
        ajaxSuccess:"success"
    },
    url:{
        add:"/black/add",
        getList:"/black/getList",
        delete:"/black/delete"
    },
    ejs:{
        getList:"/script/black/ejs/blackList.ejs"
    },
    fn:{
        execList:function(){
            common.fn.pagination(1,'Black.fn.getList','blackListPagination');
        },
        getList:function(currentPage,callback){
           var params = new Object();
            params = common.fn.getFromJsonData("blackForm");
            params.currentPage = currentPage;
            params.pageSize = common.globalVariable.pageSize;
            common.fn.ajax(Black.url.getList,params,callBack);
           function callBack(data){
               if(data.success){
                   $("#blackListBody").html('');
                   if(data.data.list){
                       var dataHtml = new EJS({url:Black.ejs.getList}).render({data:data.data});
                       $("#blackListBody").html(dataHtml);
                   }
                   if(data.data.recordTotalCount){
                       callback(data.data.recordTotalCount);
                   }else{
                       callback(0);
                   }
               }
           }

       },    // ejsUrl,data,size,readyFun,dialogCloseFun
       add:function(){
           function callback(data){
               if(data.success){
                   common.fn.showInfoMessages("提示","添加成功")   ;
                   $("#buyerNick").val("");
                   Black.fn.execList();
               }
           }
           var params = new Object();
           var blackForm = $("#blackForm").validate({});
           if(blackForm.form()){
               params = common.fn.getFromJsonData("blackForm");
               common.fn.ajax(Black.url.add,params,callback);
           }
       },
        delete:function(id){
            function callback(data){
                if(data.success){
                    common.fn.showInfoMessages("提示","删除成功")   ;
                    Black.fn.execList();
                }
            }
            common.fn.showConfirmMessage("删除","确定删除该黑名单?",function(){
                var params = new Object();
                params.id = id;
                common.fn.ajax(Black.url.delete,params,callback);
            })

        }
    }
}

