var department = {
    globalVariable:{
        ajaxSuccess:"success"
    },
    url:{
        delete:"/department/delete",
        save:"/department/save",
        findAll:"/department/findAll",
        findByParams:"/department/findByParams",
        getById:"/department/getById"
    },
    ejs:{
        addDep:"/script/department/ejs/addDep.ejs",
        depList:"/script/department/ejs/depList.ejs"

    },
    fn:{
        findAll:function(selectElementId){
            function callback(data){
                if(data.success){
                    $("#"+selectElementId).html("");
                    $("#"+selectElementId).append("<option value=''>请选择</option>")
                    for(var i=0;i<data.data.list.length;i++){
                        $("#"+selectElementId).append("<option value='"+data.data.list[i].id+"'>"+data.data.list[i].name+"</option>");
                    }
                }
            }
            common.fn.ajaxSync(department.url.findAll,{},callback);
        },
        execQuery:function(){
            common.fn.pagination(1,'department.fn.findByParams','depListPagination');
        },
        findByParams:function(currentPage,callback){
            var params = {};
            params.currentPage = currentPage;
            params.pageSize = common.globalVariable.pageSize;
            if($("#name").val()){
                params.name =  $("#name").val();
            }
            common.fn.ajax(department.url.findByParams,params,callBack);
            function callBack(data){
                if(data.success){
                    var dataHtml = new EJS({url:department.ejs.depList}).render({data:data.data});
                    $("#depListBody").html('');
                    $("#depListBody").html(dataHtml);
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
                employee.fn.findAllSelect("leaderId");
            }
            common.fn.showDialog(department.ejs.addDep,{"title":"添加部门"},null,readyFun,null);
        },

        save:function(dialogId){
            function callback(data){
                if(data.success){
                    $("#"+dialogId).modal("hide");
                    common.fn.showInfoMessages("添加部门","添加成功！");
                    department.fn.execQuery();
                }else{
                    common.fn.showInfoMessages("","部门名称重复！");
                }
            }
            var depForm = $("#depForm").validate({});
            if(depForm.form()) {
                var params = common.fn.getFromJsonData("depForm");
                common.fn.ajax(department.url.save,params,callback);
            }
        },
        delete:function(id){
            function callback(data){
                if(data.success){
                    common.fn.showInfoMessages("删除","删除成功！");
                    department.fn.execQuery();
                }else{
                    common.fn.showInfoMessages("删除","该部门下面还有员工，不能删除！");
                }
            }
            function del(){
                var params = {};
                params.id = id;
                common.fn.ajax(department.url.delete,params,callback);
            }
            common.fn.showConfirmMessage("删除","确认删除该部门吗？",del,null);
        },
        getById:function(id){
            function callback(data){
                if(data.success){
                    data.data.title = "更新部门";
                    common.fn.showDialog(department.ejs.addDep,data.data,null,null,null);
                }
            }
            var params = {};
            params.id = id;
            common.fn.ajax(department.url.getById,params,callback);
        }
    }
}