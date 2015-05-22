var Rate = {
    globalVariable:{
        ajaxSuccess:"success"
    },
    url:{
        getAlreadyRateOrders:"/rate/already-rate-orders/list",
        getBuyerInfo:"/trade/buyer-info",
        getProductInfo:"/product/info",
        getBatchRateOrders:"/rate/batch-rate-orders/list",
        addBatchRateOrders:"/rate/batch-rate-orders/rate"
    },
    ejs:{
        alreadyRateOrderList:"/script/rate/ejs/already-rate-order-list.ejs",
        batchRateOrderList:"/script/rate/ejs/batch-rate-order-list.ejs",
        buyerInfo:"/script/rate/ejs/showBuyerInfo.ejs",
        batchRateInfo:"/script/rate/ejs/showBatchRateInfo.ejs",
        batchRateResult:"/script/rate/ejs/batchRateResultInfo.ejs"
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
        },
        checkAll:function(element){
            var check = element.checked;
            $("input[name='tradeIds']").each(function(){
                $(this).attr("checked",check);
            })
        },
        showBatchRateInfoDialog:function(selectOrAll){
            var params  = new Object();
            if(selectOrAll == 'select'){
                var tradeIds = "";
                $("input[name='tradeIds']:checked").each(function(){
                    tradeIds+=$(this).val()+",";
                })
                if(tradeIds){
                    tradeIds = tradeIds.substr(0,tradeIds.length-1);
                    params.tradeIds = tradeIds;
                }else{
                    common.fn.showInfoMessages("提示","请至少选择一条记录!");
                    return;
                }
            }

            params.selectOrAll = selectOrAll;
            common.fn.showDialog(Rate.ejs.batchRateInfo,params,null,null,null);
        },
        batchRateOrders:function(dialogId){
            function callback(data){
                if(data.success){
                    $("#"+dialogId).modal("hide");
                    common.fn.showDialog(Rate.ejs.batchRateResult,data.data,null);
                    Rate.fn.execBatchList();
                }
            }
            var batchRateForm = common.fn.getFromJsonData("batchRateForm");
            batchRateForm.buyerNick = $("#buyerNick").val();
            batchRateForm.rateStatus = $("#rateStatus").val();
            common.fn.ajax(Rate.url.addBatchRateOrders,batchRateForm,callback);
        }
    }
}

