var Rate = {
    globalVariable:{
        ajaxSuccess:"success",
        rateContent:["亲~~欢迎您下次光临小店~~~小店需要亲的大力支持~~·祝亲生活愉快！！",
                     "非常感谢您的光临，希望您在本店选购的商品能给您带来快乐和满意！也许我们不是最好的，但我们一定会尽最大的努力做到更好，让每一位消费者都能真正的享受到网购的实惠和快乐！如果我们有什么地方做得不好，请您多多提提宝贵意见，因为在成长的路上我们需要您的支持和监督，这样我们就能做得更好！谢谢！",
                     "每一个好评都是一份感动，每一个好评都是一种激励。最简单的好评，都给予我们的是动力和坚持，感谢亲的支持！欢迎下次光临！"]
    },
    url:{
        getAlreadyRateOrders:"/rate/already-rate-orders/list",
        getBuyerInfo:"/trade/buyer-info",
        getProductInfo:"/product/info",
        getBatchRateOrders:"/rate/batch-rate-orders/list",
        addBatchRateOrders:"/rate/batch-rate-orders/rate",
        autoRateSetting:"/rate/auto-rate-global-setting" ,
        initAutoRateSetting:"/rate/auto-rate-global-setting/init",
        getRateContentsByUser:"/rate/rate-content/getRateContentsByUser"
    },
    ejs:{
        alreadyRateOrderList:"/script/rate/ejs/already-rate-order-list.ejs",
        batchRateOrderList:"/script/rate/ejs/batch-rate-order-list.ejs",
        buyerInfo:"/script/rate/ejs/showBuyerInfo.ejs",
        batchRateInfo:"/script/rate/ejs/showBatchRateInfo.ejs",
        batchRateResult:"/script/rate/ejs/batchRateResultInfo.ejs",
        autoRateSetting:"/script/rate/ejs/autoRateSetting.ejs",
        showNull:"/script/rate/ejs/showNull.ejs"
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
                   $("#alreadyRateOrderListBody").html('');
                   if(data.data.list){
                       var dataHtml = new EJS({url:Rate.ejs.alreadyRateOrderList}).render({data:data.data});
                       $("#alreadyRateOrderListBody").html(dataHtml);
                   }
                   if(data.data.recordTotalCount){
                       callback(data.data.recordTotalCount);
                   }else{
                       callback(0);
                   }
               }else{
                   if(data.msg == "570"){
                       common.fn.showInfoMessages("提示","该交易号不存在哟!");
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
           location.href = "http://item.taobao.com/item.htm?id="+numId;
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
                    $("#batchRateOrderListBody").html('');
                    if(data.data.list){
                        var dataHtml = new EJS({url:Rate.ejs.batchRateOrderList}).render({data:data.data});
                        $("#batchRateOrderListBody").html(dataHtml);
                    }
                    if(data.data.recordTotalCount){
                        callback(data.data.recordTotalCount);
                    }else{
                        callback(0);
                    }
                }else{
                    if(data.msg == "520"){
                        common.fn.showInfoMessages("提示","该买家昵称不存在噢!");
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
            function readyFun(){
                function cbFun(data){
                    if(data.success){
                        var content = "content";
                        if(data.data){
                            for(var i = data.data.length-1;i>=0;i--){
                                $("#"+content+(i+1)).val(data.data[i].content);
                                $("#"+content+(i+1)).attr("rid",data.data[i].id);
                            }
                        }
                    }
                }
                var params = {};
                common.fn.ajaxSyncNotLoadingDialog(Rate.url.getRateContentsByUser,params,cbFun);
            }
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
            common.fn.showDialog(Rate.ejs.batchRateInfo,params,null,readyFun,null);
        },
        batchRateOrders:function(dialogId){
            function callback(data){
                if(data.success){

                    common.fn.showDialog(Rate.ejs.batchRateResult,data.data,null);
                    Rate.fn.execBatchList();
                    $("#tradeIdsTmp").removeAttr("checked");
                }
            }

            var batchRateForm = common.fn.getFromJsonData("batchRateForm");
            batchRateForm.buyerNick = $("#buyerNick").val();
            batchRateForm.rateStatus = $("#rateStatus").val();
            var tabContent = $(".tab-content");
            if(tabContent && tabContent.length == 1){
                var contents  = $(tabContent[0]).children();
                $(contents).each(function(){
                    if($(this).hasClass("active")){
                        var checkTextarea = $(this).children().get(0);
                        batchRateForm.rid = $(checkTextarea).attr("rid");
                        return;
                    }
                })
            }
            //tabPane.each(function(){
            //    if($(this).hasClass("active")){
            //        $(this).
            //    }
            //})
            //if(textArea){
            //    batchRateForm.content = $(textArea).val();
            //}
            $("#"+dialogId).modal("hide");
            common.fn.ajaxNotLoadingDialog(Rate.url.addBatchRateOrders,batchRateForm,callback);
        },
        readyAutoRateSetting:function(){
            var dataHtml = new EJS({url:Rate.ejs.autoRateSetting}).render({data:{}});
            $("#page-content").html(dataHtml);
        },
        addAutoRateSetting:function(){
            function callback(data){
                if(data.success){
                    $("#"+dialogId).modal("hide");
                    common.fn.showInfoMessages("提示","自动评价配置设置成功!");
                }
            }
            var autoRateSettingForm = $("#autoRateSettingForm").validate({});
            if(autoRateSettingForm.form()){
                var params = common.fn.getFromJsonData("autoRateSettingForm");
                if($("#autoRateStatus").attr("checked")){
                    params.autoRateStatus = true;
                }else{
                    params.autoRateStatus = false;
                }
                if($("#mediumOrPoorRateAlarm").attr("checked")){
                    params.mediumOrPoorRateAlarm = true;
                }else{
                    params.mediumOrPoorRateAlarm = false;
                }
                var dialogId = common.fn.showDialog(Rate.ejs.showNull,{},null,null,null);
                common.fn.ajaxNotLoadingDialog(Rate.url.autoRateSetting,params,callback)
            }
        },
        initAutoRateSetting:function(){
            function callback(data){
                if(data.success){
                    var result = data.data.setting;
                    var dataHtml = new EJS({url:Rate.ejs.autoRateSetting}).render({data:result});
                    $("#page-content").html("");
                    $("#page-content").html(dataHtml);
                    $("input[name='autoRateType']").each(function(){
                        if($(this).val() == result.autoRateType){
                            $(this).attr("checked",true);
                        }
                    })
                    var content = "content";
                    if(data.data.contents){
                        for(var i = data.data.contents.length-1;i>=0;i--){
                            $("#"+content+(i+1)).val(data.data.contents[i].content);
                        }
                    }else{
                        for(var i = Rate.globalVariable.rateContent.length-1;i>=0;i--){
                            $("#"+content+(i+1)).val(Rate.globalVariable.rateContent[i]);
                        }
                    }
                    $("#triggerMode").val(result.triggerMode);
                }
            }
            var params = new Object();
            common.fn.ajax(Rate.url.initAutoRateSetting,params,callback)
        }
    }
}

