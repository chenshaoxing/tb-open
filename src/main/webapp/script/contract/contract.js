var contract = {
    globalVariable:{
        ajaxSuccess:"success"
    },
    url:{
        delete:"/contract/delete",
        save:"/contract/save",
        findByParams:"/contract/findByParams",
        getById:"/contract/findById"
    },
    ejs:{
        addContract:"/script/contract/ejs/addContract.ejs",
        contractList:"/script/contract/ejs/contractList.ejs"
    },
    fn:{
        execQuery:function(){
            common.fn.pagination(1,'contract.fn.findByParams','contractListPagination');
        },
        findByParams:function(currentPage,callback){
            var params = {};
            params.currentPage = currentPage;
            params.pageSize = common.globalVariable.pageSize;
            if($("#queryEmpId").val()){
                params.empId =  $("#queryEmpId").val();
            }
            if($("#queryName").val()){
                params.empName =  $("#queryName").val();
            }
            if($("#queryContractType").val()){
                params.contractType =  $("#queryContractType").val();
            }
            if($("#queryContractStatus").val()){
                params.contractStatus =  $("#queryContractStatus").val();
            }
            common.fn.ajax(contract.url.findByParams,params,callBack);
            function callBack(data){
                if(data.success){
                    var dataHtml = new EJS({url:contract.ejs.contractList}).render({data:data.data});
                    $("#contractListBody").html('');
                    $("#contractListBody").html(dataHtml);
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
            common.fn.showDialog(contract.ejs.addContract,{"title":"添加合同信息"},null,readyFun,null);
        },

        save:function(dialogId){
            function callback(data){
                if(data.success){
                    $("#"+dialogId).modal("hide");
                    common.fn.showInfoMessages("合同信息","保存成功！");
                    contract.fn.execQuery();
                }
            }
            var depForm = $("#contractForm").validate({});
            if(depForm.form()) {
                var params = common.fn.getFromJsonData("contractForm");
                common.fn.ajax(contract.url.save,params,callback);
            }
        },
        delete:function(id){
            function callback(data){
                if(data.success){
                    common.fn.showInfoMessages("删除","删除成功！");
                    contract.fn.execQuery();
                }
            }
            function del(){
                var params = {};
                params.id = id;
                common.fn.ajax(contract.url.delete,params,callback);
            }
            common.fn.showConfirmMessage("删除","确认删除该合同信息吗？",del,null);
        },
        getById:function(id){
            function callback(data){
                function readyFun(){
                    employee.fn.findAllSelect("empId");
                    $("#empId").val(data.data.employee.id);
                    $("#contractStatus").val(data.data.contractStatus);
                    $("#contractType").val(data.data.contractType);
                }
                if(data.success){
                    data.data.title = "更新合同信息";
                    common.fn.showDialog(contract.ejs.addContract,data.data,null,readyFun,null);
                }
            }
            var params = {};
            params.id = id;
            common.fn.ajax(contract.url.getById,params,callback);
        }
    }
}