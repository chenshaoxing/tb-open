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

       },
        execNeutralList:function(){
            common.fn.pagination(1,'Rate.fn.getAlreadyNeutralRateOrders','alreadyNeutralRateOrderListPagination');
        },
        getAlreadyNeutralRateOrders:function(currentPage,callback){
            var params = new Object();
            var isBuyerGiveMe = "true";
            params.currentPage = currentPage;
            params.pageSize = common.globalVariable.pageSize;
            params.isBuyerGiveMe = isBuyerGiveMe;
            params.result ="neutral";
            common.fn.ajax(Rate.url.getAlreadyRateOrders,params,callBack);
            function callBack(data){
                if(data.success){
                    data.data.isBuyerGiveMe = isBuyerGiveMe;
                    var dataHtml = new EJS({url:Rate.ejs.alreadyRateOrderList}).render({data:data.data});
                    $("#alreadyNeutralRateOrderListBody").html('');
                    $("#alreadyNeutralRateOrderListBody").html(dataHtml);
                    if(data.data.recordTotalCount){
                        callback(data.data.recordTotalCount);
                    }else{
                        callback(0);
                    }
                }
            }
        },
        execBadList:function(){
            common.fn.pagination(1,'Rate.fn.getAlreadyBadRateOrders','alreadyBadRateOrderListPagination');
        },
        getAlreadyBadRateOrders:function(currentPage,callback){
            var params = new Object();
            var isBuyerGiveMe = "true";
            params.currentPage = currentPage;
            params.pageSize = common.globalVariable.pageSize;
            params.isBuyerGiveMe = isBuyerGiveMe;
            params.result ="bad";
            common.fn.ajax(Rate.url.getAlreadyRateOrders,params,callBack);
            function callBack(data){
                if(data.success){
                    data.data.isBuyerGiveMe = isBuyerGiveMe;
                    var dataHtml = new EJS({url:Rate.ejs.alreadyRateOrderList}).render({data:data.data});
                    $("#alreadyBadRateOrderListBody").html('');
                    $("#alreadyBadRateOrderListBody").html(dataHtml);
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

