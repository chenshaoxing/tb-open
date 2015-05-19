var depChange = {
    globalVariable:{
        ajaxSuccess:"success"
    },
    url:{
        delete:"/department-change/delete",
        save:"/department-change/save",
        findByParams:"/department-change/findByParams",
        getById:"/department-change/findById",
        findEmpById:"/employee/getById"
    },
    ejs:{
        addDep:"/script/department-change/ejs/addDepChange.ejs",
        depList:"/script/department-change/ejs/depChangeList.ejs"

    },
    fn:{
        execQuery:function(){
            common.fn.pagination(1,'depChange.fn.findByParams','depChangeListPagination');
        },
        findByParams:function(currentPage,callback){
            var params = {};
            params.currentPage = currentPage;
            params.pageSize = common.globalVariable.pageSize;
            if($("#queryEmpId").val()){
                params.empId =  $("#queryEmpId").val();
            }
            if($("#queryEmpName").val()){
                params.empName =  $("#queryEmpName").val();
            }
            common.fn.ajax(depChange.url.findByParams,params,callBack);
            function callBack(data){
                if(data.success){
                    var dataHtml = new EJS({url:depChange.ejs.depList}).render({data:data.data});
                    $("#depChangeListBody").html('');
                    $("#depChangeListBody").html(dataHtml);
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
                department.fn.findAll("afterDepId") ;
                department.fn.findAll("beforeDepId") ;
                $("#empId").change(function(){
                    function callback(data){
                        if(data.success){
                            $("#beforeDepId").val(data.data.emp.department.id);
                            $("#beforeDepName").val(data.data.emp.department.name);
                            $("#beforePosition").val(data.data.emp.position);
                        }
                    }
                    var empId = $("#empId").val();
                    if(empId){
                        common.fn.ajaxSync(depChange.url.findEmpById,{'id':empId},callback);
                    }
                });

            }
            common.fn.showDialog(depChange.ejs.addDep,{"title":"添加调用记录",'isAdd':true},null,readyFun,null);
        },

        save:function(dialogId){
            function callback(data){
                if(data.success){
                    $("#"+dialogId).modal("hide");
                    common.fn.showInfoMessages("调动记录","保存成功！");
                    depChange.fn.execQuery();
                }
//                else{
//                    common.fn.showInfoMessages("","该员工已经存在调动记录,请删除新建或者直接更新！");
//                }
            }
            var depForm = $("#depChangeForm").validate({});
            if(depForm.form()) {
                var params = common.fn.getFromJsonData("depChangeForm");
                common.fn.ajax(depChange.url.save,params,callback);
            }
        },
        delete:function(id){
            function callback(data){
                if(data.success){
                    common.fn.showInfoMessages("删除","删除成功！");
                    depChange.fn.execQuery();
                }
            }
            function del(){
                var params = {};
                params.id = id;
                common.fn.ajax(depChange.url.delete,params,callback);
            }
            common.fn.showConfirmMessage("删除","确认删除该记录吗？",del,null);
        },
        getById:function(id){
            function callback(data){
                function readyFun(){
                    employee.fn.findAllSelect("empIdS");
                    $("#empId").val(data.data.employee.id);
                    $("#empIdS").val(data.data.employee.id);
                    department.fn.findAll("afterDepId") ;
                    $("#afterDepId").val(data.data.afterDepartment.id);
                    department.fn.findAll("beforeDepId") ;
                    $("#beforeDepId").val(data.data.beforeDepartment.id);
                    $("#empId").change(function(){
                        function callback(data){
                            if(data.success){
                                $("#beforeDepId").val(data.data.emp.department.id);
                                $("#beforeDepName").val(data.data.emp.department.name);
                                $("#beforePosition").val(data.data.emp.position);
                            }
                        }
                        var empId = $("#empId").val();
                        if(empId){
                            common.fn.ajaxSync(depChange.url.findEmpById,{'id':empId},callback);
                        }
                    });
                }
                if(data.success){
                    data.data.title = "更新调动记录";
                    data.data.isAdd = false;
                    common.fn.showDialog(depChange.ejs.addDep,data.data,null,readyFun,null);
                }
            }
            var params = {};
            params.id = id;
            common.fn.ajax(depChange.url.getById,params,callback);
        }
    }
}