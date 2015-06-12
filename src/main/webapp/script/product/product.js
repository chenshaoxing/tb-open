var Product = {
    globalVariable:{
        ajaxSuccess:"success"
    },
    url:{
        list:"/product/list",
        jdList:"/product/jd-list",
        addJd:"/product/add-jd",
        updateJd:"/product/update-jd",
        deleteJd:"/product/delete-jd"
    },
    ejs:{
        getList:"/script/product/ejs/list.ejs",
        getJdList:"/script/product/ejs/jd-list.ejs",
        addJd:"/script/product/ejs/showAddJd.ejs",
        getRelationJdList:"/script/product/ejs/jd-list-relation.ejs",
        relationJd:"/script/product/ejs/relation-jd.ejs"
    },
    fn:{
        execList:function(){
            common.fn.pagination(1,'Product.fn.getList','searchProductListPagination');
        },
        getList:function(currentPage,callback){
           var params = new Object();
            params = common.fn.getFromJsonData("searchProductForm");
            params.currentPage = currentPage;
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
            common.fn.pagination(1,'Product.fn.getJdList','searchJdProductListPagination');
        },
        getJdList:function(currentPage,callback){
            var params = new Object();
            params = common.fn.getFromJsonData("searchJdProductForm");
            params.currentPage = currentPage;
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
            common.fn.pagination(1,'Product.fn.getRelationJdList','searchJdProductListPagination');
        },
        getRelationJdList:function(currentPage,callback){
            var params = new Object();
            params = common.fn.getFromJsonData("searchJdProductForm");
            params.currentPage = currentPage;
            params.pageSize = common.globalVariable.pageSize;
            common.fn.ajax(Product.url.jdList,params,callBack);
            function callBack(data){
                if(data.success){
                    $("#searchJdProductListBody").html('');
                    if(data.data.list){
                        var dataHtml = new EJS({url:Product.ejs.getRelationJdList}).render({data:data.data});
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
        relationJd:function(dialogId){
            var jdId = $("input[type='radio']:checked").val();
            var numIid = $("#numIid").val();
            var differenceOfPrices = $("#differenceOfPrices").val();
            var tbPrice = $("#tbPrice").val();
            var params = {};
            params.jdId = jdId;
            params.numIid = numIid;
            params.tbPrice = tbPrice;
            params.differenceOfPrices = differenceOfPrices;
        }
    }
}

