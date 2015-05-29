var common ={
    globalVariable:{
        pageSize:10,
        userType:"",
        username:"",
        userId:0
    },
    fn:{
        init:function(){
            common.globalVariable.username = $.cookie("name");
            common.globalVariable.userId = $.cookie("id");
//            if(!$.cookie("id")){
//                location.href="/index.html";
//            }
//            $("#topUsernameArea").html('<a href="/my_info/" title="点击修改个人信息">' + common.globalVariable.username + '</a>');
        },
        /**
         * @param url   请求地址
         * @param data  请求参数
         * @param successFun  请求成功回调函数
         */
        ajax: function(url,data,successFun,currentPage,errorFun){
            var maskId = common.fn.showLoadingStatus(null);
            $.ajax({
                type: "POST",
                url: url,
                data: data,
                cache:false,
                dataType: "json",
                success: function (data) {
                    common.fn.closeLoadingStatus(maskId);
                    if(currentPage){
                        successFun(data,currentPage);
                    }else{
                        successFun(data);
                    }
                },
                error: function (data) {
                    common.fn.closeLoadingStatus(maskId);
                    if(errorFun){
                        errorFun();
                    }else{
//                        if(data){
//                            common.fn.showInfoMessages("错误",data.responseText);
//                            $("pre").css("max-height","300px");
//                        }
                        common.fn.showInfoMessages("错误", "通讯失败,请稍后刷新页面!");
                    }
                }
            });
        },
        /**
         * @param url   请求地址
         * @param data  请求参数
         * @param successFun  请求成功回调函数
         */
        ajaxNotLoadingDialog: function(url,data,successFun,currentPage,errorFun){
            $.ajax({
                type: "POST",
                url: url,
                data: data,
                cache:false,
                dataType: "json",
                success: function (data) {
                    if(currentPage){
                        successFun(data,currentPage);
                    }else{
                        successFun(data);
                    }
                },
                error: function () {
                    if(errorFun){
                        errorFun();
                    }else{
                        common.fn.showInfoMessages("错误", "通讯失败,请稍后刷新页面!");
                    }
                }
            });
        },
        /**
         * @param url   请求地址
         * @param data  请求参数
         * @param successFun  请求成功回调函数
         */
        ajaxSyncNotLoadingDialog: function(url,data,successFun,currentPage,errorFun){
            $.ajax({
                type: "POST",
                url: url,
                data: data,
                cache:false,
                dataType: "json",
                async:false,
                success: function (data) {
                    if(currentPage){
                        successFun(data,currentPage);
                    }else{
                        successFun(data);
                    }
                },
                error: function () {
                    if(errorFun){
                        errorFun();
                    }else{
                        common.fn.showInfoMessages("错误", "通讯失败,请稍后刷新页面!");
                    }
                }
            });
        },
        //ajax post  json请求       url：请求地址 ；  data:请求参数  ；successFun:请求成功回调函数
        ajaxSync: function(url,data,successFun,errorFun,isShowDialog){
            if(isShowDialog){
                var maskId = common.fn.showLoadingStatus(null);
            }
            $.ajax({
                type: "POST",
                url: url,
                data: data,
                async:false,
                cache:false,
                dataType: "json",
                success: function (data) {
                    if(isShowDialog){
                        common.fn.closeLoadingStatus(maskId);
                    }
                    successFun(data);
                },
                error: function (data) {
                    if(isShowDialog){
                        common.fn.closeLoadingStatus(maskId);
                    }
                    if(errorFun){
                        errorFun();
                    }else{
//                        if(data){
//                            common.fn.showInfoMessages("错误",data.responseText);
//                            $("pre").css("max-height","300px");
//                        }
                        common.fn.showInfoMessages("错误", "通讯失败,请稍后刷新页面!");
                    }
                }
            });
        },
        ajaxGet: function(url,data,successFun,errorFun){
            var maskId = common.fn.showLoadingStatus(null);
            $.ajax({
                type: "GET",
                url: url,
                cache:false,
                data: data,
                dataType: "json",
                success: function (data) {
                    common.fn.closeLoadingStatus(maskId);
                    successFun(data);
                },
                error: function (data) {
                    common.fn.closeLoadingStatus(maskId);
                    if(errorFun){
                        errorFun();
                    }else{
//                        if(data){
//                            common.fn.showInfoMessages("错误",data.responseText);
//                            $("pre").css("max-height","300px");
//                        }
                        common.fn.showInfoMessages("错误", "通讯失败,请稍后刷新页面!");
                    }
                }
            });
        },
        /**
         * ajax 跨域 请求
         * @param url
         * @param data
         * @param callback
         * @param errorCallback
         */
        ajaxCrossDomain:function(url,data,callback,errorCallback){
            $.ajax({
                type: "POST",
                url: url,
                data: data,
                cache:false,
                jsonp:"jsoncallback",
                dataType: "jsonp",
                success: function (data) {
                    callback(data);
                },
                error: function (data) {
                    if(errorCallback){
                        errorCallback();
                    }else{
                        common.fn.showInfoMessages("错误", "通讯失败,请稍后刷新页面!");
                    }
                }
            });
        },
        showDialog:function (ejsUrl,data,size,readyFun,dialogCloseFun){
            var randomDialogId = parseInt(Math.random()*1000000);
            data.randomDialogId = randomDialogId;
            var dataHtml = new EJS({url:ejsUrl}).render({data:data});
            var dialog = $(dataHtml);
            dialog.attr('id',randomDialogId);
            if(size){
                if(size.width){
                    dialog.css("width",size.width);
                }
                if(size.height){
                    dialog.css("height",size.height);
                }
            }
            $("body").append(dialog);

            //对话框关闭后，清理资源
            $("#"+randomDialogId).on('hidden.bs.modal', function () {
                if(dialogCloseFun){
                    dialogCloseFun();
                }
                $("#"+randomDialogId).addClass("hide");
                $("#"+randomDialogId).remove();
            });
            $("#"+randomDialogId).modal({
                keyboard: false,
                show:true,
                backdrop:'static'
            });
            $(".close").css("display",'none');
            if(readyFun){
                $("#"+randomDialogId).on("shown.bs.modal",function(){
                    readyFun();
                })
            }
        },
        /**
         *  显示确认对话框
         * @param showCancel 是否显示取消按钮
         * @param title 标题
         * @param message
         */
        showConfirmMessage: function (title, message, callback,closeCallBack) {
            var randomDialogId = parseInt(Math.random()*1000000);
            var data = {};
            data.title = title;
            data.message = message;
            data.randomDialogId = randomDialogId;
            var infoDialogHTML = new EJS({url:'/script/common/confirmDialog.ejs'}).render({data:data});
            var infoDialog = $(infoDialogHTML);
            infoDialog.attr('id',randomDialogId);
            $("body").append(infoDialog);

            //对话框关闭后，清理资源
            $("#"+randomDialogId).on('hidden', function () {
                $("#"+randomDialogId).remove();
            })
            $("#"+randomDialogId).on('shown.bs.modal', function () {
                $("#subBtn"+randomDialogId).bind("click",callback);
                $("#closeBtn"+randomDialogId).bind("click",closeCallBack);
            })
            $("#"+randomDialogId).modal({
                keyboard: false,
                show:true,
                backdrop:'static'
            });
            $("#"+randomDialogId).find(".close").css("display",'none');
        },
        //组装form 数据为json 格式数据 用于新增和更新数据
        getFromJsonData : function(formId){
            var jsonData = {};
            var fields = $("#"+formId).serializeArray();
            jQuery.each(fields, function(i, field){
                if(field.value != ''){
                    jsonData[field.name]= $.trim(field.value);
                }
            });
            return jsonData;
        },
        /**
         * 分页工具栏
         * @param callbackFunctionName 分页回调函数的全名，该函数完成数据分页显示的数据列表,
         *                              为兼容异步请求模式。该函数的型参格式为 function(pageIndex,callback)，该callback函数在异步请求响应时调用
         * @targetContainer 分页的工具栏将要放置的目标容器ID
         * @maxPageCount 最大的页数，可选。主要用于判断用户输入的页数不能超过次数
         */
        pagination:function(pageIndex,callbackFunctionName,targetContainer,maxPageCount){
            var pageIndex = ~~pageIndex;
            if(pageIndex<1){
                pageIndex = 1;
            }
            if(pageIndex>maxPageCount){
                pageIndex = maxPageCount;
            }

            //匿名回调函数，基于数据记录总数构建分页工具栏，并显示在页面指定的容器中
            function callback(recordCount){
                var pageHTML='';
                var recordIndexBegin = 0;
                var recordIndexEnd = 0;
                recordIndexBegin = (pageIndex - 1) * common.globalVariable.pageSize + 1;
                recordIndexEnd = recordIndexBegin + common.globalVariable.pageSize - 1;

                if (recordIndexEnd > recordCount) {
                    recordIndexEnd = recordCount;
                }
                if(!recordCount){
                    recordCount=0;
                }
                var pageCount = parseInt(recordCount/common.globalVariable.pageSize);
                if(recordCount%common.globalVariable.pageSize>0){
                    pageCount++;
                }

                var data = {};
                data.recordCount = recordCount;
                data.pageSize = common.globalVariable.pageSize;
                data.pageCount = pageCount;
                data.currentPage = pageIndex;
                data.recordIndexBegin = recordIndexBegin;
                data.recordIndexEnd = recordIndexEnd;
                data.pagination = callbackFunctionName;
                data.targetContainer = targetContainer;
                var randomPagerId = "page_"+parseInt(Math.random()*1000000);
                data.id = randomPagerId;

                pageHTML = new EJS({url:'/script/common/pagination.ejs'}).render({data:data});
                $("#"+targetContainer).html('');
                $("#"+targetContainer).html(pageHTML);
            }
            eval(callbackFunctionName+'('+pageIndex+',callback)');
        },
        showErrorMessages:function(errorCode,errorMessage,description){
            var data = {};
            data.errorCode = errorCode;
            data.errorMessage = errorMessage;
            data.description = description;
            var errorDialogHTML = new EJS({url:'/script/common/errorDialog.ejs'}).render({data:data});
            var errorDialog = $(errorDialogHTML);
            var randomDialogId = parseInt(Math.random()*1000000);
            errorDialog.attr('id',randomDialogId);
            $("body").append(errorDialog);

            //对话框关闭后，清理资源
            $("#"+randomDialogId).on('hidden', function () {
                $("#"+randomDialogId).remove();
            })
            $("#"+randomDialogId).modal('show');
            $(".close").css("display",'none');
        },
        showInfoMessages:function(title,message){
            var data = {};
            data.title = title;
            data.message = message;
            var infoDialogHTML = new EJS({url:'/script/common/infoDialog.ejs'}).render({data:data});
            var infoDialog = $(infoDialogHTML);
            var randomDialogId = parseInt(Math.random()*1000000);
            infoDialog.attr('id',randomDialogId);
            $("body").append(infoDialog);

            //对话框关闭后，清理资源
            $("#"+randomDialogId).on('hidden', function () {
                $("#"+randomDialogId).remove();
            })
            $("#"+randomDialogId).modal('show');
            $(".close").css("display",'none');
        },
        showInfoMessages:function(title,message, callback){
            var data = {};
            data.title = title;
            data.message = message;
            var infoDialogHTML = new EJS({url:'/script/common/infoDialog.ejs'}).render({data:data});
            var infoDialog = $(infoDialogHTML);
            var randomDialogId = parseInt(Math.random()*1000000);
            infoDialog.attr('id',randomDialogId);
            $("body").append(infoDialog);

            $("#"+randomDialogId).on('hidden.bs.modal', function () {
                if(callback){
                    callback();
                }
                $("#"+randomDialogId).remove();
            })
            $("#"+randomDialogId).modal('show');
            $(".close").css("display",'none');
        },
        //给跳页输入框增加回车事件     跳页input框id ,总页面隐藏字段Id，验证通过请求后台的方法
        jumpToPageAddEnterKeyEvent:function(pageNumInput,callbackFunctionName,targetContainer,recordTotalCount,maxPageCount){
            $(pageNumInput).keydown(function(e){
                if(!e)e=window.event;
                if((e.keyCode||e.which)==13){
                    e.preventDefault();
                    var pageIndex =$(pageNumInput).val();
                    if(pageIndex>maxPageCount){
                        pageIndex = maxPageCount;
                    }
                    common.fn.pagination(pageIndex,callbackFunctionName,targetContainer,recordTotalCount);
                }
            })
        },
        digitInput:function(element,event){
            //8：退格键、46：delete、37-40： 方向键
            //48-57：小键盘区的数字、96-105：主键盘区的数字
            //189、109：小键盘区和主键盘区的负号
            var e = event || window.event; //IE、FF下获取事件对象
            var cod = event.charCode||event.keyCode; //IE、FF下获取键盘码
            //小数点处理

            if(cod!=8 && cod != 46 && (cod<37 || cod>40) && (cod<48 || cod>57) && (cod<96 || cod>105)) notValue(event);
            function notValue(e){
                e.preventDefault ? e.preventDefault() : e.returnValue=false;
            }
        },
        formatDateTime:function (obj) {
            var myDate = new Date(obj);
            var year = myDate.getFullYear();
            var month = ("0" + (myDate.getMonth() + 1)).slice(-2);
            var day = ("0" + myDate.getDate()).slice(-2);
            var h = ("0" + myDate.getHours()).slice(-2);
            var m = ("0" + myDate.getMinutes()).slice(-2);
            var s = ("0" + myDate.getSeconds()).slice(-2);
//            var mi = ("00" + myDate.getMilliseconds()).slice(-3);
            return year + "-" + month + "-" + day +" "+h+":"+m+":"+s;
        },
        formatDate:function (obj) {
            var myDate = new Date(obj);
            var year = myDate.getFullYear();
            var month = ("0" + (myDate.getMonth() + 1)).slice(-2);
            var day = ("0" + myDate.getDate()).slice(-2);
//            var mi = ("00" + myDate.getMilliseconds()).slice(-3);
            return year + "-" + month + "-" + day
        },
        formatMonth:function (obj) {
            var myDate = new Date(obj);
            var year = myDate.getFullYear();
            var month = ("0" + (myDate.getMonth() + 1)).slice(-2);
            //var day = ("0" + myDate.getDate()).slice(-2);
//            var mi = ("00" + myDate.getMilliseconds()).slice(-3);
            return year + "-" + month;
        },
        /**
         * 采用Ajax的方式上传文件
         * @param fileInputId  input元素的id
         * @param url 接收上传数据的服务端URL
         * @param succeedCallBack 当上传成功后回调的函数
         * @param failedCallBack 当上传失败后的回调函数
         */
        ajaxFileUpload:function(fileInputId,url,succeedCallBack,failedCallBack, targetContainer)
        {
            var maskId = common.fn.showLoadingStatus(targetContainer);
            $.ajaxFileUpload
            (
                {
                    url:url,//用于文件上传的服务器端请求地址
                    secureuri:false,//一般设置为false
                    fileElementId:fileInputId,//文件上传空间的id属性  <input type="file" id="file" name="file" />
                    dataType: 'json',//返回值类型 一般设置为json
                    success: function (data, status)  //服务器成功响应处理函数
                    {
                        common.fn.closeLoadingStatus(maskId);
                        if(typeof(data.errorCode) != 'undefined')
                        {
                            failedCallBack(data.errorCode,data.errorMessage)
                        }else{
                            succeedCallBack(data)
                        }
                    },
                    error: function (data, status, e)//服务器响应失败处理函数
                    {
                        common.fn.closeLoadingStatus(maskId);
                        failedCallBack(status,"服务器错误")
                    }
                }
            )
        },
        /**
         * 打开或关闭加载进度提示框
         * @param targetElemenId 进度框所覆盖的元素Id，可选。如果没有该参数默认对整个页面进行遮罩
         */
        showLoadingStatus : function(targetElementId){
            var randomId = "mask_"+parseInt(Math.random()*1000000);
            var loadingStatus = new EJS({url:'/script/common/loadingStatus.ejs'}).render({targetMaskId:randomId});
            var mask = $(loadingStatus);
            mask.attr('id',randomId);
            if(targetElementId){
                mask.height($("#"+targetElementId).outerHeight());
                mask.width($("#"+targetElementId).outerWidth());
                $("#"+targetElementId).append(mask);
            }else{
                mask.height($("body").outerHeight());
                mask.width($("body").outerWidth());
                $("body").append(mask);
            }
            return randomId;
        },
        closeLoadingStatus: function (targetMaskId) {
            $("#" + targetMaskId).remove();
        } ,
        /**
         * 将秒数换成时分秒格式
         **/
        formatSeconds:function (value) {
                var seconds = parseInt(value);// 秒
                var minutes = 0;// 分
                var hours = 0;// 小时
                if(seconds >= 60) {
                    minutes = parseInt(seconds/60);
                    seconds = parseInt(seconds%60);
                    if(minutes >= 60) {
                        hours = parseInt(minutes/60);
                        minutes = parseInt(minutes%60);
                       }
                   }
                var result = "";
                if(hours > 0) {
                    result = ""+parseInt(hours)+"小时"+parseInt(minutes)+"分"+ parseInt(seconds) + "秒";
                }
                else{
                    if(minutes > 0) {
                        result = parseInt(minutes)+"分"+ parseInt(seconds) + "秒";
                    }
                    else{
                        result = "" + parseInt(seconds) + "秒";
                    }
                }
                return result;
                }


    }
}

$(document).ready(function(){
    common.fn.init();
})
