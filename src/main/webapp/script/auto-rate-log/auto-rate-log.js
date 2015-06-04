var AutoLog = {
    globalVariable:{
        ajaxSuccess:"success"
    },
    url:{
        getList:"/auto-rate-log/getList"
    },
    ejs:{
        getList:"/script/auto-rate-log/ejs/auto-rate-log.ejs"
    },
    fn:{
        execList:function(){
            common.fn.pagination(1,'AutoLog.fn.getList','autoRateLogListPagination');
        },
        getList:function(currentPage,callback){
           var params = new Object();
            var blackForm = $("#autoRateLogForm").validate({});
            if(blackForm.form()){
                params = common.fn.getFromJsonData("autoRateLogForm");
                params.currentPage = currentPage;
                params.pageSize = common.globalVariable.pageSize;
                common.fn.ajax(AutoLog.url.getList,params,callBack);
            }

           function callBack(data){
               if(data.success){
                   $("#autoRateLogListBody").html('');
                   if(data.data.list){
                       var dataHtml = new EJS({url:AutoLog.ejs.getList}).render({data:data.data});
                       $("#autoRateLogListBody").html(dataHtml);
                   }
                   if(data.data.recordTotalCount){
                       callback(data.data.recordTotalCount);
                   }else{
                       callback(0);
                   }
               }
           }
       }
    }
}

