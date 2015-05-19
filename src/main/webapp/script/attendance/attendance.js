var attendance = {
    globalVariable:{
        ajaxSuccess:"success"
    },
    url:{
        delete:"/attendance/delete",
        save:"/attendance/save",
        findByParams:"/attendance/findByParams",
        getById:"/attendance/findById",
        findAll:"/attendance/findAll"
    },
    ejs:{
        addAttendance:"/script/attendance/ejs/addAttendance.ejs",
        attendanceList:"/script/attendance/ejs/attendanceList.ejs"
    },
    fn:{
        execQuery:function(){
            common.fn.pagination(1,'attendance.fn.findByParams','attendanceListPagination');
        },
        findByParams:function(currentPage,callback){
            var params = {};
            params.currentPage = currentPage;
            params.pageSize = common.globalVariable.pageSize;
            if($("#queryEmpId").val()){
                params.empId =  $("#queryEmpId").val();
            }
            if($("#queryAttendanceDate").val()){
                params.attendanceDate =  $("#queryAttendanceDate").val();
            }
            common.fn.ajax(attendance.url.findByParams,params,callBack);
            function callBack(data){
                if(data.success){
                    var dataHtml = new EJS({url:attendance.ejs.attendanceList}).render({data:data.data});
                    $("#attendanceListBody").html('');
                    $("#attendanceListBody").html(dataHtml);
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
            }
            common.fn.showDialog(attendance.ejs.addAttendance,{"title":"添加考勤信息"},null,readyFun,null);
        },

        save:function(dialogId){
            function callback(data){
                if(data.success){
                    $("#"+dialogId).modal("hide");
                    common.fn.showInfoMessages("考勤信息","保存成功！");
                    attendance.fn.execQuery();
                }else{
                    common.fn.showInfoMessages("考勤信息","该考勤日期已经存在，请勿重复添加!");
                }
            }
            var depForm = $("#attendanceForm").validate({});
            if(depForm.form()) {
                var params = common.fn.getFromJsonData("attendanceForm");
                common.fn.ajax(attendance.url.save,params,callback);
            }
        },
        delete:function(id){
            function callback(data){
                if(data.success){
                    common.fn.showInfoMessages("删除","删除成功！");
                    attendance.fn.execQuery();
                }
            }
            function del(){
                var params = {};
                params.id = id;
                common.fn.ajax(attendance.url.delete,params,callback);
            }
            common.fn.showConfirmMessage("删除","确认删除该考勤信息吗？",del,null);
        },
        getById:function(id){
            function callback(data){
                function readyFun(){
                    employee.fn.findAllSelect("empId");
                    $("#empId").val(data.data.employee.id);
                }
                if(data.success){
                    data.data.title = "更新考勤信息";
                    common.fn.showDialog(attendance.ejs.addAttendance,data.data,null,readyFun,null);
                }
            }
            var params = {};
            params.id = id;
            common.fn.ajax(attendance.url.getById,params,callback);
        },
        findAllSelect:function(selectElementId){
            function callback(data){
                if(data.success){
                    $("#"+selectElementId).html("");
                    $("#"+selectElementId).append("<option value=''>请选择</option>")
                    for(var i=0;i<data.data.length;i++){
                        $("#"+selectElementId).append("<option value='"+data.data[i].id+"'>"+common.fn.formatMonth(data.data[i].attendanceDate)+"</option>");
                    }
                }
            }
            common.fn.ajaxSync(attendance.url.findAll,{},callback);
        }
    }
}