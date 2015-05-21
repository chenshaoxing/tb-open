var Rate = {
    globalVariable:{
        ajaxSuccess:"success"
    },
    url:{
        getAlreadyRateOrders:"/rate/already-rate-orders/list",
        getBuyerInfo:"/trade/buyer-info",
        getProductInfo:"/product/info",
        getBatchRateOrders:"/rate/batch-rate-orders/list"
    },
    ejs:{
        alreadyRateOrderList:"/script/rate/ejs/already-rate-order-list.ejs",
        batchRateOrderList:"/script/rate/ejs/batch-rate-order-list.ejs",
        buyerInfo:"/script/rate/ejs/showBuyerInfo.ejs"
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
        },    // ejsUrl,data,size,readyFun,dialogCloseFun
        showBuyerInfo:function(tradeId){
            function callback(data){
                if(data.success){
                    if(data.data){
                        common.fn.showDialog(Rate.ejs.buyerInfo,data.data,null,null,null);
                    }else{
                        common.fn.showInfoMessages("提示","该订单的买家信息不存在!");
                    }
                }
            }
            var params = new Object();
            params.tradeId = tradeId;
            common.fn.ajax(Rate.url.getBuyerInfo,params,callback);
        },
        getProductUrl:function(numId){
            function callback(data){
                if(data.success){
                    if(data.data){
                        var a = $("<a href='"+data.data.detailUrl+"' target='_blank'>Apple</a>").get(0);
                        var e = document.createEvent('MouseEvents');
                        e.initEvent('click', true, true);
                        a.dispatchEvent(e);
                    }else{
                        common.fn.showInfoMessages("提示","该商品不存在!");
                    }
                }
            }
            var params  = new Object();
            params.numId = numId;
            common.fn.ajaxNotLoadingDialog(Rate.url.getProductInfo,params,callback);
        },
        getBatchRateOrders:function(currentPage,callback){
            var params = new Object();
            var getBatchRateOrderForm = $("#getBatchRateOrderForm").validate({});
            if(getBatchRateOrderForm.form()){
                params = common.fn.getFromJsonData("getBatchRateOrderForm");
                params.currentPage = currentPage;
                params.pageSize = common.globalVariable.pageSize;
                common.fn.ajax(Rate.url.getBatchRateOrders,params,callBack);
            }
            function callBack(data){
                if(data.success){
                    var dataHtml = new EJS({url:Rate.ejs.batchRateOrderList}).render({data:data.data});
                    $("#batchRateOrderListBody").html('');
                    $("#batchRateOrderListBody").html(dataHtml);
                    if(data.data.recordTotalCount){
                        callback(data.data.recordTotalCount);
                    }else{
                        callback(0);
                    }
                }
            }
        },
        execBatchList:function(){
            common.fn.pagination(1,'Rate.fn.getBatchRateOrders','batchRateOrderListPagination');
        }
    }
}

