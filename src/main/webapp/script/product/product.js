var Product = {
    globalVariable:{
        ajaxSuccess:"success",
        currentPage:1,
        currentPageTow:1,
        currentPageThree:1
    },
    url:{
        list:"/product/list",
        jdList:"/product/jd-list",
        addJd:"/product/add-jd",
        updateJd:"/product/update-jd",
        deleteJd:"/product/delete-jd",
        relationJd:"/product/relation-jd",
        getRelationJd:"/product/get-relation-jd" ,
        cancelJdRelation:"/product/cancel-jd-relation"
    },
    ejs:{
        getList:"/script/product/ejs/list.ejs",
        getJdList:"/script/product/ejs/jd-list.ejs",
        addJd:"/script/product/ejs/showAddJd.ejs",
        getRelationJdList:"/script/product/ejs/jd-list-relation.ejs",
        relationJd:"/script/product/ejs/relation-jd.ejs",
        relationJdList:"/script/product/ejs/relation-jd-list.ejs",
        showGetRelation:"/script/product/ejs/showGetRelationJd.ejs"
    },
    fn:{
        execList:function(){
            common.fn.pagination(Product.globalVariable.currentPage,'Product.fn.getList','searchProductListPagination');
        },
        getList:function(currentPage,callback){
           var params = new Object();
            params = common.fn.getFromJsonData("searchProductForm");
            Product.globalVariable.currentPage = currentPage;
            params.currentPage = Product.globalVariable.currentPage;
            params.pageSize = common.globalVariable.pageSize;
            common.fn.ajax(Product.url.list,params,callBack);
           function callBack(data){
               if(data.success){
                   $("#searchProductListBody").html('');
                   if(data.data.list){
                       var dataHtml = new EJS({url:Product.ejs.getList}).render({data:data.data});
                       $("#searchProductListBody").html(dataHtml);
                   }
                   if(data.data.recordTotalCount){
                       callback(data.data.recordTotalCount);
                   }else{
                       callback(0);
                   }
               }
           }

       },
        execJdList:function(){
            common.fn.pagination(Product.globalVariable.currentPageTow,'Product.fn.getJdList','searchJdProductListPagination');
        },
        getJdList:function(currentPage,callback){
            var params = new Object();
            params = common.fn.getFromJsonData("searchJdProductForm");
            Product.globalVariable.currentPageTow = currentPage;
            params.currentPage = Product.globalVariable.currentPageTow;
            params.pageSize = common.globalVariable.pageSize;
            common.fn.ajax(Product.url.jdList,params,callBack);
            function callBack(data){
                if(data.success){
                    $("#searchJdProductListBody").html('');
                    if(data.data.list){
                        var dataHtml = new EJS({url:Product.ejs.getJdList}).render({data:data.data});
                        $("#searchJdProductListBody").html(dataHtml);
                    }
                    if(data.data.recordTotalCount){
                        callback(data.data.recordTotalCount);
                    }else{
                        callback(0);
                    }
                }
            }
        },
        execJdRelationList:function(){
            common.fn.pagination(Product.globalVariable.currentPageThree,'Product.fn.getRelationJdList','searchJdProductListPagination');
        },
        getRelationJdList:function(currentPage,callback){
            var params = new Object();
            params = common.fn.getFromJsonData("searchJdProductForm");
            Product.globalVariable.currentPageThree = currentPage;
            params.currentPage = Product.globalVariable.currentPageThree;
            params.pageSize = common.globalVariable.pageSize;
            common.fn.ajaxSync(Product.url.jdList,params,callBack);
            function callBack(data){
                if(data.success){
                    $("#searchJdProductListBody").html('');
                    if(data.data.list){
                        var dataHtml = new EJS({url:Product.ejs.getRelationJdList}).render({data:data.data});
                        $("#searchJdProductListBody").html(dataHtml);
                    }else{
                        $("#searchJdProductListBody").html('<div class="alert alert-success" role="alert">您暂时还没有关注京东宝贝或者关注的宝贝已经和淘宝宝贝关联了</div>');
                    }
                    if(data.data.recordTotalCount){
                        callback(data.data.recordTotalCount);
                    }else{
                        callback(0);
                    }
                }
            }
        },
        showAddJdDialog:function(){
          common.fn.showDialog(Product.ejs.addJd,{},null,null,null);
        },
        showRelationJdDialog:function(numIid,tbPrice){
            function readyFun(){
                Product.fn.execJdRelationList();
                $("#skuId").keydown(function(e){
                    if(!e)e=window.event;
                    if((e.keyCode||e.which)==13){
                        e.preventDefault();
                        Product.fn.execJdRelationList();
                    }
                });
                $('[data-toggle="tooltip"]').tooltip();
            }
            var params  = {};
            params.numIid = numIid;
            params.price = tbPrice;
          common.fn.showDialog(Product.ejs.relationJd,params,null,readyFun,null);
        },
        // ejsUrl,data,size,readyFun,dialogCloseFun
       addJd:function(dialogId){
           function callback(data){
               if(data.success){
                   common.fn.showInfoMessages("提示","添加成功")   ;
                   Product.fn.execJdList();
               }else{
                   common.fn.showInfoMessages("提示","您的黑名单数量已经达到500名的上限了!")   ;
               }
           }
           var params = new Object();
           var addJdForm = $("#addJdForm").validate({});
           if(addJdForm.form()){
               params = common.fn.getFromJsonData("addJdForm");
               $("#"+dialogId).modal("hide");
               common.fn.ajax(Product.url.addJd,params,callback);
           }
        },
        updateJd:function(skuId){
            function callback(data){
                if(data.success) {
                    common.fn.showInfoMessages("提示", "刷新成功");
                    Product.fn.execJdList();
                }
            }
            var params = {};
            params.skuId= skuId;
            common.fn.ajax(Product.url.updateJd,params,callback);
        },
        deleteJd:function(id){
            function callback(data){
                if(data.success){
                    common.fn.showInfoMessages("提示","取消关注成功")   ;
                    Product.fn.execJdList();
                }
            }
            common.fn.showConfirmMessage("取消关注","确定取消该商品的关注?",function(){
                var params = new Object();
                params.id = id;
                common.fn.ajax(Product.url.deleteJd,params,callback);
            })
        },
        relationJd:function(dialogId){  //relationJdForm
            function callback(data){
                if(data.success){
                    common.fn.showInfoMessages("提示","关联成功")   ;
                    Product.fn.execList();
                }
            }
            var paramsP = new Object();
            var relationJdForm = $("#relationJdForm").validate({});
            if(relationJdForm.form()){
                paramsP = common.fn.getFromJsonData("relationJdForm");
                var params = {};
                var jdId = $("input[type='radio']:checked").val();
                if(!jdId){
                    common.fn.showInfoMessages("提示","必须选择一个京东宝贝!") ;
                    return;
                }
                var numIid = $("#numIid").val();
                var differenceOfPrices = $("#differenceOfPrices").val();
                var tbPrice = $("#tbPrice").val();
                params.jdId = jdId;
                params.numIid = numIid;
                params.tbPrice = tbPrice;
                params.differenceOfPrices = differenceOfPrices;
                $("#"+dialogId).modal("hide");
                common.fn.ajax(Product.url.relationJd,params,callback);
            }
        },
        getRelationJdListByNumIid:function(){
            function callback(data){
                if(data.success){
                    $("#relationJdListBody").html('');
                    if(data.data){
                        var dataHtml = new EJS({url:Product.ejs.relationJdList}).render({data:data.data});
                        $("#relationJdListBody").html(dataHtml);
                    }

                }
            }
            var params = {};
            params.numIid = $("#numIid").val();
            common.fn.ajax(Product.url.getRelationJd,params,callback);
        },
        showGetRelationJdListDialog:function(numIid){
            var params = {};
            params.numIid = numIid;
            common.fn.showDialog(Product.ejs.showGetRelation,params,null,Product.fn.getRelationJdListByNumIid,null);
        },
        cancelRelation:function(numIid,jdId){
            function callback(data){
                if(data.success){
                    var dialogId = $("#dialogId").val();
                    $("#"+dialogId).modal("hide");
                    Product.fn.execList();
                }
            }
            var params = {};
            params.numIid = numIid;
            params.jdId = jdId;
            common.fn.ajax(Product.url.cancelJdRelation,params,callback);
        }
    }
}

