var employee = {
    globalVariable:{
        ajaxSuccess:"success"
    },
    url:{
        findAll:"/employee/findAll",
        findByParams:"/employee/findByParams",
        getDepList:"/getDepList",
        findPosByDep:"/findPosByDep",
        findAllEdu:"/findAllEdu",
        find:"/find",
        addEmp:"/employee/save" ,
        findEmpById:"/employee/getById",
        updateStaff:"/updateStaff" ,
        deleteById:"/employee/delete" ,
        download:"/employee/download"
    },
    ejs:{
        empList:"/script/employee/ejs/empList.ejs",
        addEmp:"/script/employee/ejs/addEmp.ejs"


    },
    fn:{
        findAllPage:function(){
            common.fn.pagination(1,'employee.fn.findAllPaging','empListPagination');
        },
       findAllPaging:function(currentPage,callback){
           var params = {};
           params.currentPage = currentPage;
           params.pageSize = common.globalVariable.pageSize;
           var name = $("#queryName").val();
           if(name){
               params.name = name;
           }
           var depId = $("#queryDepId").val();
           if(depId){
               params.depId = depId;
           }
           var sex = $("#querySex").val();
           if(sex){
               params.sex = sex;
           }

           function callBack(data){
               if(data.success){
                  var dataHtml = new EJS({url:employee.ejs.empList}).render({data:data.data});
                  $("#empListBody").html('');
                  $("#empListBody").html(dataHtml);
                   if(data.data.recordTotalCount){
                       callback(data.data.recordTotalCount);
                   }else{
                       callback(0);
                   }
               }
           }
           common.fn.ajax(employee.url.findByParams,params,callBack);
           common.fn.formatDateTime();
       } ,
        showAddEmpInfoDialog:function(){
            function readyFun(){
                department.fn.findAll("depId");
                //var depHtml = $("#depType").html();
                //$("#depType1").append(depHtml);
                //function callback(data){
                //    if(data.success){
                //        for(var i=0 ; i<data.data.length ; i++){
                //            $("#eduName").append('<option value='+data.data[i].eduName+'>'+data.data[i].eduName+'</option>');
                //        }
                //    }
                //}
                //common.fn.ajax(staffInfo.url.findAllEdu,null,callback);
                var national = [
                    "汉族", "壮族", "满族", "回族", "苗族", "维吾尔族", "土家族", "彝族", "蒙古族", "藏族", "布依族", "侗族", "瑶族", "朝鲜族", "白族", "哈尼族",
                    "哈萨克族", "黎族", "傣族", "畲族", "傈僳族", "仡佬族", "东乡族", "高山族", "拉祜族", "水族", "佤族", "纳西族", "羌族", "土族", "仫佬族", "锡伯族",
                    "柯尔克孜族", "达斡尔族", "景颇族", "毛南族", "撒拉族", "布朗族", "塔吉克族", "阿昌族", "普米族", "鄂温克族", "怒族", "京族", "基诺族", "德昂族", "保安族",
                    "俄罗斯族", "裕固族", "乌孜别克族", "门巴族", "鄂伦春族", "独龙族", "塔塔尔族", "赫哲族", "珞巴族"
                ];
                for ( var i = 0; i < national.length; i++)
                {
                    $("#people").append('<option value='+national[i]+'>'+national[i]+'</option>')
                }
            }
            var params = {};
            params.title = "录入员工信息";
            params.emp = "";
            params.empDetail = "";
            common.fn.showDialog(employee.ejs.addEmp,params,null,readyFun);
        } ,
        saveEmp:function(dialogId){
            function callback(data){
                if(data.success){
                    common.fn.showInfoMessages("","新增成功");
                    $("#"+dialogId).modal("hide");
                    employee.fn.findAllPage();
                }else{
                    common.fn.showInfoMessages("","该姓名已经存在！");
                }
            }
            var empForm = $("#empForm").validate({});
            var emp = {};
            if(empForm.form()){
                emp = common.fn.getFromJsonData("empForm");
                common.fn.ajax(employee.url.addEmp,emp,callback);
            }

        },

        showUpdateDialog:function(id,isView){
            var params = {};
            params.id = id;
            function callBack(data){
                if(data.success){
                    function readyFun(){
                        department.fn.findAll("depId");
                        var national = [
                            "汉族", "壮族", "满族", "回族", "苗族", "维吾尔族", "土家族", "彝族", "蒙古族", "藏族", "布依族", "侗族", "瑶族", "朝鲜族", "白族", "哈尼族",
                            "哈萨克族", "黎族", "傣族", "畲族", "傈僳族", "仡佬族", "东乡族", "高山族", "拉祜族", "水族", "佤族", "纳西族", "羌族", "土族", "仫佬族", "锡伯族",
                            "柯尔克孜族", "达斡尔族", "景颇族", "毛南族", "撒拉族", "布朗族", "塔吉克族", "阿昌族", "普米族", "鄂温克族", "怒族", "京族", "基诺族", "德昂族", "保安族",
                            "俄罗斯族", "裕固族", "乌孜别克族", "门巴族", "鄂伦春族", "独龙族", "塔塔尔族", "赫哲族", "珞巴族"
                        ];
                        for ( var i = 0; i < national.length; i++)
                        {
                            $("#people").append('<option value='+national[i]+'>'+national[i]+'</option>')
                        }
                        $("#isMarry").val(data.data.emp.marry+"");
                        $("#sex").val(data.data.emp.sex);
                        $("#depId").val(data.data.emp.department.id);
                        $("#people").val(data.data.emp.people);
                        $("#diploma").val(data.data.empDetail.diploma);
                        $("#birthday").val($("#birthday").val().split(" ")[0]);
                        $("#graduatedDate").val($("#graduatedDate").val().split(" ")[0]);
                        if(isView){
                            $("#empForm .form-control").each(function(){
                                $(this).attr("disabled",true);
                            })
                                $("#saveOrUpdateEmp").css("display","none");
                        }

                    }
                    data.data.title = "修改资料";
                    data.data.id = id;
                    common.fn.showDialog(employee.ejs.addEmp,data.data,null,readyFun);
                }
            }
            common.fn.ajax(employee.url.findEmpById,params,callBack);
        }  ,


        deleteEmpById:function(id){
            var params = {};
            params.id = id;
            function callback(data){
                if(data.success){
                    common.fn.showInfoMessages("提示","删除成功！");
                    employee.fn.findAllPage();
                }
            }
            common.fn.showConfirmMessage("提示","确定删除该员工？删除员工会删除和该员工相关的所有数据，请谨慎操作!",exec,null);
            function exec(){
                common.fn.ajax(employee.url.deleteById,params,callback);
            }
        },
        findAllSelect:function(selectElementId){
            function callback(data){
                if(data.success){
                    $("#"+selectElementId).html("");
                    $("#"+selectElementId).append("<option value=''>请选择</option>")
                    for(var i=0;i<data.data.length;i++){
                        $("#"+selectElementId).append("<option value='"+data.data[i].id+"'>"+data.data[i].name+"</option>");
                    }
                }
            }
            common.fn.ajaxSync(employee.url.findAll,{},callback);
        },
        download:function(){
            var name = $("#queryName").val();
            var depId = $("#queryDepId").val();
            var sex = $("#querySex").val();
            var url = employee.url.download+"?name="+name+"&depId="+depId+"&sex="+sex;
            location.href=url;
        }


    }
}
