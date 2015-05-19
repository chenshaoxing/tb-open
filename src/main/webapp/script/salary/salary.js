var salary = {
    globalVariable:{
        ajaxSuccess:"success"
    },
    url:{
        save:"/salary/save",
        findByParams:"/salary/findByParams"
    },
    ejs:{
        addSalary:"/script/salary/ejs/addSalary.ejs",
        salaryList:"/script/salary/ejs/salaryList.ejs"
    },
    fn:{

        execQuery:function(){
            common.fn.pagination(1,'salary.fn.findByParams','salaryListPagination');
        },
        findByParams:function(currentPage,callback){
            var params = common.fn.getFromJsonData("querySalaryForm");
            var p = {};
            if(params.queryEmpId){
                p.empId = params.queryEmpId;
            }
            if(params.querySalaryType){
                p.salaryType =  params.querySalaryType;
            }
            if(params.queryYear){
                p.year =  params.queryYear;
            }
            if(params.queryMonth){
                p.month =  params.queryMonth;
            }
            p.currentPage = currentPage;
            p.pageSize = common.globalVariable.pageSize;
            common.fn.ajax(salary.url.findByParams,p,callBack);
            function callBack(data){
                if(data.success){
                    var dataHtml = new EJS({url:salary.ejs.salaryList}).render({data:data.data});
                    $("#salaryListBody").html('');
                    $("#salaryListBody").html(dataHtml);
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
            common.fn.showDialog(salary.ejs.addSalary,{"title":"添加薪资信息"},null,readyFun,null);
        },

        save:function(dialogId){
            function callback(data){
                if(data.success){
                    $("#"+dialogId).modal("hide");
                    common.fn.showInfoMessages("添加薪资信息","添加成功！");
                    salary.fn.execQuery();
                }else{
                    if(data.msg){
                        common.fn.showInfoMessages("",data.msg);
                    }else{
                        common.fn.showInfoMessages("","员工该月份薪资已经存在，请勿重复添加！");
                    }
                }
            }
            var depForm = $("#salaryForm").validate({});
            if(depForm.form()) {
                var params = common.fn.getFromJsonData("salaryForm");
                var baseSalary = params.basicSalary;
                params.performanceRelatedPay = params.performanceRelatedPay*baseSalary;
                params.tax = params.tax*baseSalary;
                params.pension = params.pension*baseSalary;
                params.unemploymentInsurance = params.unemploymentInsurance*baseSalary;
                params.medicalInsurance = params.medicalInsurance*baseSalary;
                params.publicReserveFund = params.publicReserveFund*baseSalary;
                common.fn.ajax(salary.url.save,params,callback);
            }
        }
    }
}