var Rate = {
    globalVariable:{
        ajaxSuccess:"success"
    },
    url:{
        getAlreadyRateOrders:"/rate/already-rate-orders/list"

    },
    ejs:{
        alreadyRateOrderList:"/script/rate/ejs/already-rate-order-list.ejs"
//        addEmp:"/script/employee/ejs/addEmp.ejs"
    },
    fn:{
        execList:function(){
            common.fn.pagination(1,'Rate.fn.getAlreadyRateOrders','alreadyRateOrderListPagination');
        },
        getAlreadyRateOrders:function(currentPage,callback){
           var params = new Object();
            var isBuyerGiveMe;
            var getAlreadyRateOrderForm = $("#getAlreadyRateOrderForm").validate({});
            if(getAlreadyRateOrderForm.form()){
                params = common.fn.getFromJsonData("getAlreadyRateOrderForm");
                params.currentPage = currentPage;
                params.pageSize = common.globalVariable.pageSize;
                isBuyerGiveMe = params.isBuyerGiveMe;
                common.fn.ajax(Rate.url.getAlreadyRateOrders,params,callBack);
            }
           function callBack(data){
               if(data.success){
                  data.data.isBuyerGiveMe = isBuyerGiveMe;
                  var dataHtml = new EJS({url:Rate.ejs.alreadyRateOrderList}).render({data:data.data});
                  $("#alreadyRateOrderListBody").html('');
                  $("#alreadyRateOrderListBody").html(dataHtml);
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
$(document).ready(function(){
    Rate.fn.execList();
    $("#tradeId").keydown(function(e){
        if(!e)e=window.event;
        if((e.keyCode||e.which)==13){
            e.preventDefault();
            Rate.fn.execList()
        }
    });
})
