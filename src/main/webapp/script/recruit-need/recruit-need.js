var need = {
    globalVariable:{
        ajaxSuccess:"success"
    },
    url:{
        save:"/recruit-need/save",
        findByParams:"/recruit-need/findByParams" ,
        delete:"/recruit-need/delete",
        findById:"/recruit-need/findById",
        findAll:"/recruit-need/findAll"
    },
    ejs:{
        addNeed:"/script/recruit-need/ejs/addNeed.ejs",
        needList:"/script/recruit-need/ejs/needList.ejs"
    },
    fn:{
        findAll:function(selectElementId){
            function callback(data){
                if(data.success){
                    $("#"+selectElementId).html("");
                    $("#"+selectElementId).append("<option value=''>请选择</option>")
                    for(var i=0;i<data.data.length;i++){
                        $("#"+selectElementId).append("<option value='"+data.data[i].id+"'>"+data.data[i].id+"</option>");
                    }
                }
            }
            common.fn.ajaxSync(need.url.findAll,{},callback);
        },
        execQuery:function(){
            common.fn.pagination(1,'need.fn.findByParams','needListPagination');
        },
        findByParams:function(currentPage,callback){
            var params = common.fn.getFromJsonData("queryNeedForm");
            if(params.queryDepId){
                params.depId = params.queryDepId;
                delete params.queryDepId;
            }
            if(params.queryNeedStatus){
                params.needStatus = params.queryNeedStatus;
                delete params.queryNeedStatus;
            }
            params.currentPage = currentPage;
            params.pageSize = common.globalVariable.pageSize;
            common.fn.ajax(need.url.findByParams,params,callBack);
            function callBack(data){
                if(data.success){
                    var dataHtml = new EJS({url:need.ejs.needList}).render({data:data.data});
                    $("#needListBody").html('');
                    $("#needListBody").html(dataHtml);
                    if(data.data.recordTotalCount){
                        callback(data.data.recordTotalCount);
                    }else{
                        callback(0);
                    }
                }
            }
        },
        showSaveDialog:function(){
            function readyFun(){
                employee.fn.findAllSelect("empId");
                department.fn.findAll("depId");
            }
            common.fn.showDialog(need.ejs.addNeed,{"title":"添加招聘需求"},null,readyFun,null);
        },

        save:function(dialogId){
            function callback(data){
                if(data.success){
                    $("#"+dialogId).modal("hide");
                    common.fn.showInfoMessages("招聘需求","保存成功！");
                    need.fn.execQuery();
                }
            }
            var depForm = $("#needForm").validate({});
            if(depForm.form()) {
                var params = common.fn.getFromJsonData("needForm");
                common.fn.ajax(need.url.save,params,callback);
            }
        },
        deleteById:function(id){
            var params = {};
            params.id = id;
            function callback(data){
                if(data.success){
                    common.fn.showInfoMessages("提示","删除成功！");
                    need.fn.execQuery();
                }else{
                    common.fn.showInfoMessages("提示","删除失败！和应聘人有关系,不能删除，请先删除应聘人信息后再进行删除!");
                }
            }
            common.fn.showConfirmMessage("提示","确定删除该招聘需求?",exec,null);
            function exec(){
                common.fn.ajax(need.url.delete,params,callback);
            }
        },
        getById:function(id){
            function callback(data){
                if(data.success){
                    function readyFun(){
                        employee.fn.findAllSelect("empId");
                        department.fn.findAll("depId");
                        $("#empId").val(data.data.recruitEmp.id);
                        $("#depId").val(data.data.department.id);
                        $("#recruitStatus").val(data.data.recruitStatus);
                    }
                    data.data.title = "更新招聘信息";
                    common.fn.showDialog(need.ejs.addNeed,data.data,null,readyFun,null);
                }
            }
            var params = {};
            params.id = id;
            common.fn.ajax(need.url.findById,params,callback);
        }
    }
}