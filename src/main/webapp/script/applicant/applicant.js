var applicant = {
    globalVariable:{
        ajaxSuccess:"success"
    },
    url:{
        delete:"/applicant/delete",
        save:"/applicant/save",
        findByParams:"/applicant/findByParams",
        getById:"/applicant/findById"
    },
    ejs:{
        addApplicant:"/script/applicant/ejs/addApplicant.ejs",
        applicantList:"/script/applicant/ejs/applicantList.ejs"
    },
    fn:{
        execQuery:function(){
            common.fn.pagination(1,'applicant.fn.findByParams','applicantListPagination');
        },
        findByParams:function(currentPage,callback){
            var params = {};
            params.currentPage = currentPage;
            params.pageSize = common.globalVariable.pageSize;
            if($("#queryDepId").val()){
                params.depId =  $("#queryDepId").val();
            }
            if($("#queryApplicantStatus").val()){
                params.applicantStatus =  $("#queryApplicantStatus").val();
            }
            common.fn.ajax(applicant.url.findByParams,params,callBack);
            function callBack(data){
                if(data.success){
                    var dataHtml = new EJS({url:applicant.ejs.applicantList}).render({data:data.data});
                    $("#applicantListBody").html('');
                    $("#applicantListBody").html(dataHtml);
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
                need.fn.findAll("recruitNeedId");
                //employee.fn.findAllSelect("leaderId");
            }
            common.fn.showDialog(applicant.ejs.addApplicant,{"title":"添加应聘信息"},null,readyFun,null);
        },

        save:function(dialogId){
            function callback(data){
                if(data.success){
                    $("#"+dialogId).modal("hide");
                    common.fn.showInfoMessages("添加应聘聘信息","添加成功！");
                    applicant.fn.execQuery();
                }
            }
            var depForm = $("#applicantForm").validate({});
            if(depForm.form()) {
                var params = common.fn.getFromJsonData("applicantForm");
                common.fn.ajax(applicant.url.save,params,callback);
            }
        },
        delete:function(id){
            function callback(data){
                if(data.success){
                    common.fn.showInfoMessages("删除","删除成功！");
                    applicant.fn.execQuery();
                }
            }
            function del(){
                var params = {};
                params.id = id;
                common.fn.ajax(applicant.url.delete,params,callback);
            }
            common.fn.showConfirmMessage("删除","确认删除该应聘信息吗？",del,null);
        },
        getById:function(id){
            function callback(data){
                if(data.success){
                    function readyFun(){
                        need.fn.findAll("recruitNeedId");
                        //employee.fn.findAllSelect("leaderId");
                        $("#recruitNeedId").val(data.data.recruitNeed.id);
                        $("#sex").val(data.data.sex);
                        $("#diploma").val(data.data.diploma);
                        $("#applyStatus").val(data.data.applyStatus);
                        $("#applyResult").val(data.data.applyResult);

                    }
                    data.data.title = "更新应聘信息";
                    common.fn.showDialog(applicant.ejs.addApplicant,data.data,null,readyFun,null);
                }
            }
            var params = {};
            params.id = id;
            common.fn.ajax(applicant.url.getById,params,callback);
        }
    }
}