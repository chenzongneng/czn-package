/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

$(function () {
    /** 初始化动态资源列表 */
    $("#jqGrid").jqGrid({
        url: baseURL + 'resourcedynamicpropertys',
        datatype: "json",
        colModel: [
            {label: '动态资源配置ID', name: 'id', align: 'center', hidden: true, index: "id", width: 20, key: true ,sortable: false},
            {label: '名称', name: 'remark', align: 'center', width: 160 ,sortable: false},
            {label: '代号', name: 'type', align: 'center', width: 80, sortable: false},
            {label: '创建时间', name: 'createdTime', formatter:timeFormat, align: 'center', width: 120 ,sortable: false},
            {label: '修改时间', name: 'modifiedTime', formatter:timeFormat, align: 'center', width: 120 ,sortable: false},
            {label: '更改人', name: 'updatedByUserName', align: 'center', width: 80 ,sortable: false}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: false,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "list",
            page: "currPage",
            total: "totalPage",
            records: "totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit"
        },
        postData: {
            userId: userId,
            token: accessToken
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            // 设置表头居中
            $('.ui-jqgrid .ui-jqgrid-htable .ui-th-div').css('text-align', 'center');
            // checkBox 对齐
            $('.ui-jqgrid td input, .ui-jqgrid td select, .ui-jqgrid td textarea').css('margin-left', '6px');
        }
    });
});

//时间戳 转 Y-M-D
function timeFormat(cellvalue, options, row) {
    var date = new Date(cellvalue);
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'; // 0-11月，0代表1月
    var D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate());
    var h = (date.getHours() < 10 ? '0' + (date.getHours()) + ':' : date.getHours() + ':');
    var m = (date.getMinutes() < 10 ? '0' + (date.getMinutes()) + ':' : date.getMinutes() + ':');
    var s = (date.getSeconds() < 10 ? '0' + (date.getSeconds()) : date.getSeconds());
    return Y + M + D + "  " + h + m + s;
}

//应用列表
var applicationList = {
    // 应用列表数据
    appList: {
        selectedApp: "",
        options: []
    },
    // 搜索框应用列表数据
    appSearchList: {
        selectedApp: "",
        options: []
    }
};

var vm = new Vue({
    el: '#garnetApp',
    data: {
        name: null,
        showList: true,
        showType: true, //类型选择下拉框
        showTenant: false, //显示租户下拉框
        showApplication: false, //显示应用下拉框
        showParentCode: false,
        count: 0, // 添加新的一行时，计数已经添加了多少行
        title: null,
        searchName: null,
        resourceDynamicPropertyList : [],
        resourceDynamicProperty: {
            id: null,
            type: null,
            remark: null,
            actions: null,
            fieldName: null,
            description:null,
            tenantId: null,
            applicationId:null,
            orderNum: 0
        },
        option: {
            applicationId: 1,
            appSearchId: ""
        },
        // 应用列表数据
        appList: {
            selectedApp: "",
            options: []
        },
        // 租户表数据
        tenantList: {
            selectedTenant: "",
            options: []
        },
        // 类型列表数据
        typeList: {
            selectedType: "",
            options: [
                {
                    id : "1",
                    name : "租户"
                },
                {
                    id : "2",
                    name : "应用"
                },
                {
                    id : "3",
                    name : "租户+应用"
                }]
        },
        // 选择的输入框类型
        typeArray: {
            sArray: [],
            iArray: [],
            bArray: []
        },
    },
    mounted:function () {
        // this.initData();
    },
    methods: {
        initData:function () {
            for(var i=0; i<10;i++){
                var p = new Object();
                p.description = '';
                p.filedName= 'varchar0'+i;
                this.resourceDynamicPropertyList.push(p);
            }

            for(var i=10; i<20;i++){
                var p = new Object();
                p.description = '';
                p.filedName= 'varchar'+i;
                this.resourceDynamicPropertyList.push(p);
            }

            for(var i=1; i<6; i ++) {
                var property = new Object();
                property.description = '';
                property.filedName= 'int0'+i;
                this.resourceDynamicPropertyList.push(property);
            }

            for(var i=1; i<5; i ++) {
                var property = new Object();
                property.description = '';
                property.filedName= 'boolean0'+i;
                this.resourceDynamicPropertyList.push(property);
            }
        },

        /**  查询按钮点击事件 */
        query: function () {
            vm.reload(true);
        },
        /**  新增按钮点击事件 */
        add: function () {

            $('#tips').hide();
            $('#checkResourceDyPropType').hide();
            $('#checkResourceDyPropRemark').hide();

            vm.showList = false;
            vm.showType = true;
            vm.showApplication = false;
            vm.showTenant = false;
            vm.title = "新增";
            vm.count = 0;
            applicationList.appList.options = [];
            applicationList.appList.selectedApp = "";
            vm.tenantList.options = [];
            vm.tenantList.selectedTenant = "";
            vm.typeList.selectedType = "";
            vm.typeArray.sArray = [];
            vm.typeArray.iArray = [];
            vm.typeArray.bArray = [];

            vm.resourceDynamicPropertyList = [];
            vm.resourceDynamicProperty = {
                id: null,
                type: null,
                remark: null,
                actions: null,
                tenantId: "",
                applicationId: ""
            };

            //删除动态添加的输入框
            $("input[name='descriptions']").parent().parent().remove();

            // this.initData();
            vm.getTenantList();
            vm.getAppList();
            // if(vm.option.appSearchId !== undefined && vm.option.appSearchId !== null && vm.option.appSearchId !== ""){
            //     vm.resource.applicationId = vm.option.appSearchId;
            // }
            //vm.getAppList();
            // vm.getTenantList();
            // vm.initTreesToAdd();
            // vm.loadResourceTree();
        },
        /**  更新按钮点击事件 */
        update: function () {
            $('#checkResourceDyPropType').hide();
            $('#checkResourceDyPropRemark').hide();

            var resourceDynamicPropertyId = getSelectedRow();
            if (!resourceDynamicPropertyId) {
                return;
            }

            $('#tips').show();

            //删除动态添加的输入框
            $("input[name='descriptions']").parent().parent().remove();

            vm.showList = false;
            vm.showType = false;
            vm.title = "修改";
            applicationList.appList.options = [];
            applicationList.appList.selectedApp = "";
            vm.tenantList.options = [];
            vm.tenantList.selectedTenant = "";
            vm.typeArray.sArray = [];
            vm.typeArray.iArray = [];
            vm.typeArray.bArray = [];

            vm.getTenantList();
            vm.getAppList();
            vm.initTreesToUpdate(resourceDynamicPropertyId);
            // vm.loadResourceTree();
        },
        /**  删除按钮点击事件 */
        del: function () {
            var resourceIds = getSelectedRows();
            var title;
            if (!resourceIds) {
                return;
            }

            $.get(baseURL + "resources/relate?ids=" + resourceIds + "&token=" + accessToken, function (response) {
                if (response == Boolean(true)) {
                    title = "部分资源配置已被资源关联，删除后关联资源也将一并删除。是否确认删除？";
                } else {
                    title = "确定要删除选中的记录";
                }
            });

            window.parent.swal({
                    title: title,
                    type: "warning",
                    showCancelButton: true,
                    closeOnConfirm: false,
                    confirmButtonText: "确认",
                    cancelButtonText: "取消",
                    confirmButtonColor: "#DD6B55"
                },
                function () {
                    $.ajax({
                        type: "DELETE",
                        url: baseURL + "resourcedynamicpropertys?ids=" + resourceIds.toString(),
                        contentType: "application/json",
                        dataType: "",
                        success: function (result) {
                            if (!result.message) {
                                window.parent.swal("删除成功!", "", "success");
                                vm.reload(false);
                            } else {
                                window.parent.swal("删除失败!", result.message, "error");
                            }
                            vm.reload(false);
                        },
                        error: function (result) {
                            window.parent.swal("删除失败!", getExceptionMessage(result), "error");
                        }
                    });
                });
        },
        /**  新增或更新确认 */
        saveOrUpdate: function () {
            vm.resourceDynamicPropertyList = [];
            $("input[name='descriptions']").each(function (i,item) {
                var description = item.value;
                var ttype = item.attributes["ttype"].nodeValue;
                var object = new Object();
                object.filedName = ttype;
                object.description = description;
                vm.resourceDynamicPropertyList.push(object);
            });

            // 获取访问权限树选择的访问权限
            var obj = new Object();
            vm.resourceDynamicProperty.updatedByUserName = localStorage.getItem("userName");
            vm.resourceDynamicProperty.tenantId = vm.tenantList.selectedTenant;
            vm.resourceDynamicProperty.applicationId = applicationList.appList.selectedApp;
            obj.resourceDynamicProperty = vm.resourceDynamicProperty;
            obj.resourceDynamicPropertyList = vm.resourceDynamicPropertyList;

            if(vm.resourceDynamicProperty.type == null || $.trim(vm.resourceDynamicProperty.type) == "") {
                window.parent.swal("", "资源类型配置名称不能为空", "warning");
                return;
            }

            if ((vm.tenantList.selectedTenant == null || $.trim(vm.tenantList.selectedTenant) == "") && (applicationList.appList.selectedApp == null || applicationList.appList.selectedApp == "")) {
                window.parent.swal("", "请在选择类型后，选择租户或应用", "warning");
                return;
            }

            if (vm.typeList.selectedType != null && $.trim(vm.typeList.selectedType) != "") {
                var selectType = vm.typeList.selectedType;
                if (selectType == 1) {
                    //租户级
                    if (vm.resourceDynamicProperty.tenantId == null || $.trim(vm.resourceDynamicProperty.tenantId) == "") {
                        window.parent.swal("", "请选择租户", "warning");
                        return;
                    }
                } else if (selectType == 2) {
                    //应用级
                    if (vm.resourceDynamicProperty.applicationId == null || $.trim(vm.resourceDynamicProperty.applicationId) == "") {
                        window.parent.swal("", "请选择应用", "warning");
                        return;
                    }
                } else if (selectType == 3) {
                    if ((vm.tenantList.selectedTenant == null || $.trim(vm.tenantList.selectedTenant) == "") || (applicationList.appList.selectedApp == null || applicationList.appList.selectedApp == "")) {
                        window.parent.swal("", "租户和应用都不能为空", "warning");
                        return;
                    }
                }
            }


            if (vm.resourceDynamicProperty.type.length > 30) {
                window.parent.swal("", "资源类型配置名称长度不能大于30", "warning");
                return;
            }

            if(vm.resourceDynamicProperty.remark == null || $.trim(vm.resourceDynamicProperty.remark) == "") {
                window.parent.swal("", "备注不能为空", "warning");
                return;
            }

            if (vm.resourceDynamicProperty.actions == null || $.trim(vm.resourceDynamicProperty.actions) == "") {
                window.parent.swal("", "资源类型配置行为组不能为空", "warning");
                return;
            }

            var pattern = /^[a-zA-Z\,]{1,60}$/;
            if (!pattern.exec(vm.resourceDynamicProperty.actions)) {
                window.parent.swal("", "资源类型配置行为组只能输入英文和英文逗号", "warning");
                return;
            }

            if (vm.typeArray.sArray.length == 0 && vm.typeArray.iArray.length == 0 && vm.typeArray.bArray.length == 0) {
                window.parent.swal("", "请至少添加一行输入框", "warning");
                return;
            }

            /*检查名称和代号的唯一性开始*/
            var type = vm.resourceDynamicProperty.type;
            var id = vm.resourceDynamicProperty.id;
            var b1 = false;

            if (id == null) {
                id = 0;
            }

            $.get(baseURL + "/resourcedynamicpropertys/checktype?type=" + type + "&id=" + id , function (response) {
                if (response.data == Boolean(false)) {
                    b1 = false;
                } else {
                    b1 = true;
                }
            });

            var remark = vm.resourceDynamicProperty.remark;
            var id = vm.resourceDynamicProperty.id;
            var b2 = false;

            if (id == null) {
                id = 0;
            }

            $.get(baseURL + "/resourcedynamicpropertys/checkremark?remark=" + remark + "&id=" + id , function (response) {
                if (response.data == Boolean(false)) {
                    b2 = false;
                } else {
                    b2 = true;
                }
            });

            if (!(b1 && b2)) {
                window.parent.swal("", "资源类型名称或代号已被使用", "warning");
                return;
            }
            /*检查名称和代号的唯一性结束*/

            // if (vm.resourceDynamicProperty.actions != "edit" && vm.resourceDynamicProperty.actions != "read" && vm.resourceDynamicProperty.actions != "edit,read") {
            //     swal("", "行为组只能填写以下三种：eidt、read或edit,read", "warning");
            //     return;
            // }

            $.ajax({
                type: vm.resourceDynamicProperty.id === null ? "POST" : "PUT",
                url: baseURL + "resourcedynamicpropertys",
                contentType: "application/json",
                data: JSON.stringify(obj),
                dataType: '',
                success: function () {
                    vm.reload(false);
                    window.parent.swal("操作成功!", "", "success");
                },
                error: function (response) {
                    window.parent.swal("", getExceptionMessage(response), "error");
                }
            });
        },
        /** 添加按钮初始化数据 */
        initTreesToAdd: function () {
            //加载访问权限树
            // $.get(baseURL + "/apis/applicationId/" + vm.resource.applicationId, function (response) {
            //     apiTree = $.fn.zTree.init($("#apiTree"), apiTreeSetting, response);
            //     apiTree.expandAll(false);
            // })
        },
        /** 更新按钮初始化数据 */
        initTreesToUpdate: function (resourceDynamicPropertyId) {
            vm.getResourceDynamicPropertyById(resourceDynamicPropertyId);
        },
        /** 通过id 得到一个resourcedynamicpropertys对象 */
        getResourceDynamicPropertyById: function (resourceDynamicPropertyId) {
            $.get(baseURL + "resourcedynamicpropertys/" + resourceDynamicPropertyId, function (response) {
                response = response.data;

                vm.resourceDynamicProperty.id = response.resourceDynamicProperty.id;
                vm.resourceDynamicProperty.applicationId = response.resourceDynamicProperty.applicationId;
                vm.resourceDynamicProperty.tenantId = response.resourceDynamicProperty.tenantId;
                vm.resourceDynamicProperty.type = response.resourceDynamicProperty.type;
                vm.resourceDynamicProperty.remark = response.resourceDynamicProperty.remark;
                vm.resourceDynamicProperty.actions = response.resourceDynamicProperty.actions;
                vm.resourceDynamicPropertyList = response.resourceDynamicPropertyList;
                applicationList.appList.selectedApp = response.resourceDynamicProperty.applicationId;
                vm.tenantList.selectedTenant = response.resourceDynamicProperty.tenantId;

                var selectedTenant = response.resourceDynamicProperty.tenantId;
                var selectedApp = response.resourceDynamicProperty.applicationId;

                if (selectedTenant == null || selectedTenant == 0) {
                    //应用级
                    vm.typeList.selectedType = 2;
                    applicationList.appList.selectedApp = selectedApp;
                    vm.showApplication = true;
                    vm.showTenant = false;
                } else if (selectedApp == null || selectedApp == 0){
                    //租户级
                    vm.typeList.selectedType = 1;
                    vm.tenantList.selectedTenant = selectedTenant;
                    vm.showTenant = true;
                    vm.showApplication = false;
                } else {
                    //租户+应用
                    vm.typeList.selectedType = 3;
                    applicationList.appList.selectedApp = selectedApp;
                    vm.tenantList.selectedTenant = selectedTenant;
                    vm.showApplication = true;
                    vm.showTenant = true;
                }

                $.each(vm.resourceDynamicPropertyList,function (index, v) {

                    var inputName;
                    var filedName = v.filedName;
                    var delId = "del_" + filedName;
                    var spattern = /^varchar.*$/;
                    var ipattern = /^int.*$/;
                    var bpattern = /^boolean.*$/;

                    if (spattern.exec(filedName)) {
                        inputName = "字符型";
                        vm.typeArray.sArray.push(filedName);
                    } else if (ipattern.exec(filedName)) {
                        inputName = "整型";
                        vm.typeArray.iArray.push(filedName);
                    } else if (bpattern.exec(filedName)) {
                        inputName = "布尔型";
                        vm.typeArray.bArray.push(filedName);
                    }
                    var row = '<div class="form-group">\n' +
                        '                <div class="col-sm-2 control-label">' + inputName + '</div>\n' +
                        '                <div class="col-sm-10">\n' +
                        '                    <input class="form-control" name="descriptions" ttype="' + filedName + '" placeholder="描述" value="'+ v.description +'"/>\n' +
                        '                </div>' +
                        '                <div>' +
                        '                    <input type="button" class="btn btn-default" id="' + delId + '" value="-"/> ' +
                        '                </div>'
                    '            </div>';
                    $('#resourceDynamicPropForm').append(row);


                    /*绑定点击事件开始*/
                    $('#' + delId).on("click", function () {

                        $.get(baseURL + "resources/relate?ids=" + resourceDynamicPropertyId + "&token=" + accessToken, function (response) {
                            if (response == Boolean(true)) {

                                layer.confirm('该资源类型已经存在已关联资源配置，更新后请修改关联的资源', { btn: ['确定','取消'],
                                    btn1: function(index){

                                        if (inputName == "字符型") {
                                            var arr = [];
                                            for (var i=0; i<vm.typeArray.sArray.length; i++) {
                                                if (vm.typeArray.sArray[i] != filedName) {
                                                    arr.push(vm.typeArray.sArray[i]);
                                                }
                                            }

                                            if (arr.length == 0 && vm.typeArray.iArray.length == 0 && vm.typeArray.bArray.length == 0) {
                                                window.parent.swal("", "请至少添加一行输入框", "warning");
                                                return;
                                            } else {
                                                vm.typeArray.sArray = arr;
                                            }

                                        } else if (inputName == "整型") {
                                            var arr = [];
                                            for (var i=0; i<vm.typeArray.iArray.length; i++) {
                                                if (vm.typeArray.iArray[i] != filedName) {
                                                    arr.push(vm.typeArray.iArray[i]);
                                                }
                                            }

                                            if (arr.length == 0 && vm.typeArray.sArray.length == 0 && vm.typeArray.bArray.length == 0) {
                                                window.parent.swal("", "请至少添加一行输入框", "warning");
                                                return;
                                            } else {
                                                vm.typeArray.iArray = arr;
                                            }
                                        } else {
                                            var arr = [];
                                            for (var i=0; i<vm.typeArray.bArray.length; i++) {
                                                if (vm.typeArray.bArray[i] != filedName) {
                                                    arr.push(vm.typeArray.bArray[i]);
                                                }
                                            }

                                            if (arr.length == 0 && vm.typeArray.iArray.length == 0 && vm.typeArray.sArray.length == 0) {
                                                window.parent.swal("", "请至少添加一行输入框", "warning");
                                                return;
                                            } else {
                                                vm.typeArray.bArray = arr;
                                            }
                                        }

                                        // console.log(JSON.stringify(vm.typeArray.sArray) + " - " + JSON.stringify(vm.typeArray.iArray) + " - " + JSON.stringify(vm.typeArray.bArray));

                                        $('#' + delId).parent().parent().remove();

                                        layer.close(index);
                                    },
                                    btn2: function(){
                                        return;
                                    }
                                })
                            }
                            else {

                                if (inputName == "字符型") {
                                    var arr = [];
                                    for (var i=0; i<vm.typeArray.sArray.length; i++) {
                                        if (vm.typeArray.sArray[i] != filedName) {
                                            arr.push(vm.typeArray.sArray[i]);
                                        }
                                    }

                                    if (arr.length == 0 && vm.typeArray.iArray.length == 0 && vm.typeArray.bArray.length == 0) {
                                        window.parent.swal("", "请至少添加一行输入框", "warning");
                                        return;
                                    } else {
                                        vm.typeArray.sArray = arr;
                                    }

                                } else if (inputName == "整型") {
                                    var arr = [];
                                    for (var i=0; i<vm.typeArray.iArray.length; i++) {
                                        if (vm.typeArray.iArray[i] != filedName) {
                                            arr.push(vm.typeArray.iArray[i]);
                                        }
                                    }

                                    if (arr.length == 0 && vm.typeArray.sArray.length == 0 && vm.typeArray.bArray.length == 0) {
                                        window.parent.swal("", "请至少添加一行输入框", "warning");
                                        return;
                                    } else {
                                        vm.typeArray.iArray = arr;
                                    }
                                } else {
                                    var arr = [];
                                    for (var i=0; i<vm.typeArray.bArray.length; i++) {
                                        if (vm.typeArray.bArray[i] != filedName) {
                                            arr.push(vm.typeArray.bArray[i]);
                                        }
                                    }

                                    if (arr.length == 0 && vm.typeArray.iArray.length == 0 && vm.typeArray.sArray.length == 0) {
                                        window.parent.swal("", "请至少添加一行输入框", "warning");
                                        return;
                                    } else {
                                        vm.typeArray.bArray = arr;
                                    }
                                }

                                // console.log(JSON.stringify(vm.typeArray.sArray) + " - " + JSON.stringify(vm.typeArray.iArray) + " - " + JSON.stringify(vm.typeArray.bArray));

                                $('#' + delId).parent().parent().remove();
                            }
                        });

                    });
                    /*绑定点击事件结束*/

                });

                // console.log("update array: " + JSON.stringify(vm.typeArray));

            });
        },
        /*增加新的一行*/
        addRow: function () {

            var sFieldName;
            var iFieldName;
            var bFieldName;
            var inputName;
            var delId;
            var inputId;
            // var count = vm.count;
            var fieldName;

            var sArray = [
                "varchar00", "varchar01", "varchar02", "varchar03", "varchar04", "varchar05", "varchar06",
                "varchar07", "varchar08", "varchar09", "varchar10", "varchar11", "varchar12", "varchar13",
                "varchar14", "varchar15", "varchar16", "varchar17", "varchar18", "varchar19"
            ];

            var iArray = [
                "int01", "int02", "int03", "int04", "int05"
            ];

            var bArray = [
                "boolean01", "boolean02", "boolean03", "boolean04"
            ];

            var type = $('input:radio[name="type"]:checked').val();
            if(type == null){
                window.parent.swal("", "请选择输入框类型", "warning");
                return;
            } else{
                if (type == 1) {
                    //字符型
                    inputName = "字符型";
                    if (vm.typeArray.sArray.length == 0) {
                        sFieldName = sArray[0];
                        vm.typeArray.sArray.push(sFieldName);
                    } else if (vm.typeArray.sArray.length == sArray.length) {
                        window.parent.swal("", "字符型输入框已达到最大值，不能再添加了", "warning");
                        return;
                    } else {

                        outloop:
                        for (var i=0; i<sArray.length; i++) {
                            for (var j=0; j<vm.typeArray.sArray.length; i++) {
                                var index = vm.typeArray.sArray.indexOf(sArray[i]);

                                if (index == -1) {
                                    //这个元素还没有被使用
                                    sFieldName = sArray[i];
                                    vm.typeArray.sArray.push(sArray[i]);
                                    break outloop;
                                }
                            }
                        }
                    }

                } else if ( type == 2){
                    //整型
                    inputName = "整型";
                    if (vm.typeArray.iArray.length == 0) {
                        iFieldName = iArray[0];
                        vm.typeArray.iArray.push(iFieldName);
                    } else if (vm.typeArray.iArray.length == iArray.length) {
                        window.parent.swal("", "整型输入框已达到最大值，不能再添加了", "warning");
                        return;
                    } else {

                        outloop:
                            for (var i=0; i<iArray.length; i++) {
                                for (var j=0; j<vm.typeArray.iArray.length; i++) {
                                    var index = vm.typeArray.iArray.indexOf(iArray[i]);

                                    if (index == -1) {
                                        //这个元素还没有被使用
                                        iFieldName = iArray[i];
                                        vm.typeArray.iArray.push(iArray[i]);
                                        break outloop;
                                    }
                                }
                            }
                    }

                } else {
                    //布尔型
                    inputName = "布尔型";
                    if (vm.typeArray.bArray.length == 0) {
                        bFieldName = bArray[0];
                        vm.typeArray.bArray.push(bFieldName);
                    } else if (vm.typeArray.bArray.length == bArray.length) {
                        window.parent.swal("", "布尔型输入框已达到最大值，不能再添加了", "warning");
                        return;
                    } else {

                        outloop:
                            for (var i=0; i<bArray.length; i++) {
                                for (var j=0; j<vm.typeArray.bArray.length; i++) {
                                    var index = vm.typeArray.bArray.indexOf(bArray[i]);

                                    if (index == -1) {
                                        //这个元素还没有被使用
                                        bFieldName = bArray[i];
                                        vm.typeArray.bArray.push(bArray[i]);
                                        break outloop;
                                    }
                                }
                            }
                    }
                }
            }

            if (inputName == "字符型") {
                 delId = "del_" + sFieldName;
                 inputId = "input_" + sFieldName;
                 fieldName = sFieldName;
            } else if (inputName == "整型") {
                delId = "del_" + iFieldName;
                inputId = "input_" + iFieldName;
                fieldName = iFieldName;
            } else {
                delId = "del_" + bFieldName;
                inputId = "input_" + iFieldName;
                fieldName = bFieldName;
            }

            var row = '<div class="form-group">\n' +
                '                <div class="col-sm-2 control-label">' + inputName + '</div>\n' +
                '                <div class="col-sm-10">\n' +
                '                    <input class="form-control" name="descriptions" ttype="' + fieldName + '" placeholder="描述"/>\n' +
                '                </div>' +
                '                <div>' +
                '                    <input type="button" class="btn btn-default" id="' + delId + '" value="-"/> ' +
                '                </div>'
                '            </div>';

            $('#resourceDynamicPropForm').append(row);

            $('#' + delId).on("click", function () {

                if (inputName == "字符型") {
                    var arr = [];
                    for (var i=0; i<vm.typeArray.sArray.length; i++) {
                        if (vm.typeArray.sArray[i] != sFieldName) {
                            arr.push(vm.typeArray.sArray[i]);
                        }
                    }
                    if (arr.length == 0 && vm.typeArray.iArray.length == 0 && vm.typeArray.bArray.length == 0) {
                        window.parent.swal("", "请至少添加一行输入框", "warning");
                        return;
                    } else {
                        vm.typeArray.sArray = arr;
                    }

                } else if (inputName == "整型") {
                    var arr = [];
                    for (var i=0; i<vm.typeArray.iArray.length; i++) {
                        if (vm.typeArray.iArray[i] != iFieldName) {
                            arr.push(vm.typeArray.iArray[i]);
                        }
                    }

                    if (arr.length == 0 && vm.typeArray.sArray.length == 0 && vm.typeArray.bArray.length == 0) {
                        window.parent.swal("", "请至少添加一行输入框", "warning");
                        return;
                    } else {
                        vm.typeArray.iArray = arr;
                    }

                } else {
                    var arr = [];
                    for (var i=0; i<vm.typeArray.bArray.length; i++) {
                        if (vm.typeArray.bArray[i] != bFieldName) {
                            arr.push(vm.typeArray.bArray[i]);
                        }
                    }
                    if (arr.length == 0 && vm.typeArray.iArray.length == 0 && vm.typeArray.sArray.length == 0) {
                        window.parent.swal("", "请至少添加一行输入框", "warning");
                        return;
                    } else {
                        vm.typeArray.bArray = arr;
                    }
                }

                $('#' + delId).parent().parent().remove();

            });
            // console.log(JSON.stringify(vm.resourceDynamicPropertyList));
        },
        /*检查type是否已被使用*/
        checkResourceDyPropType: function () {
            var type = $('#resourceDyPropType').val();
            var id = vm.resourceDynamicProperty.id;

            if (id == null) {
                id = 0;
            }

            $.get(baseURL + "/resourcedynamicpropertys/checktype?type=" + type + "&id=" + id , function (response) {
                if (response.data == Boolean(false)) {
                    $('#checkResourceDyPropType').show();
                } else {
                    $('#checkResourceDyPropType').hide();
                }

            });
        },
        /*检查资源配置名称是否已被使用*/
        checkResourceDyPropRemark: function () {
            var remark = $('#resourceDyPropRemark').val();
            var id = vm.resourceDynamicProperty.id;

            if (id == null) {
                id = 0;
            }

            $.get(baseURL + "/resourcedynamicpropertys/checkremark?remark=" + remark + "&id=" + id , function (response) {
                if (response.data == Boolean(false)) {
                    $('#checkResourceDyPropRemark').show();
                } else {
                    $('#checkResourceDyPropRemark').hide();
                }

            });
        },
        /** 重新加载 */
        reload: function (backFirst) {
            vm.showList = true;
            var page;
            if(backFirst) {
                page = 1;
            }else {
                page = $("#jqGrid").jqGrid('getGridParam', 'page');
            }
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    searchName: vm.searchName,
                    type: vm.type,
                    applicationId: vm.option.appSearchId
                },
                page: page
            }).trigger("reloadGrid");
        },
        /** 应用列表onchange 事件*/
        selectApp: function () {
            // vm.initTreesToAdd()
            // vm.getAppList();
        },
        /*租户列表onchange事件*/
        selectTenant: function () {
          // vm.getTenantList();
        },
        /*类型列表onchangegkwr*/
        selectType: function () {
            var selectedType = vm.typeList.selectedType;
            if (selectedType == 1) {
                //租户级
                vm.tenantList.selectedTenant = "";
                applicationList.appList.selectedApp = "";
                vm.resourceDynamicProperty.applicationId = null;
                vm.resourceDynamicProperty.tenantId = null;
                vm.showTenant = true;
                vm.showApplication = false;
            } else if (selectedType == 2) {
                //应用级
                vm.tenantList.selectedTenant = "";
                applicationList.appList.selectedApp = "";
                vm.resourceDynamicProperty.tenantId = null;
                vm.resourceDynamicProperty.applicationId = null;
                vm.showTenant = false;
                vm.showApplication = true;
            } else {
                //租户+应用
                vm.tenantList.selectedTenant = "";
                applicationList.appList.selectedApp = "";
                vm.resourceDynamicProperty.tenantId = null;
                vm.resourceDynamicProperty.applicationId = null;
                vm.showApplication = true;
                vm.showTenant = true;
            }
        },
        /**  获取应用列表 */
        getAppList: function () {
            $.get(baseURL + "applications?userId=" + userId + "&page=1&limit=1000", function (response) {
                $.each(response.list, function (index, item) {
                    // vm.appList.options.push(item);
                    applicationList.appList.options.push(item);
                    applicationList.appSearchList.options.push(item);
                })
            });
        },
        /**  获取租户列表 */
        getTenantList: function () {
            $.get(baseURL + "tenants?userId=" + userId + "&page=1&limit=1000", function (response) {
                $.each(response.list, function (index, item) {
                    vm.tenantList.options.push(item);
                })
            });
        },
        /**  获取应用列表 */
        // getAppList: function () {
        //     $.get(baseURL + "applications?page=1&limit=1000", function (response) {
        //         $.each(response.list, function (index, item) {
        //             applicationList.appList.options.push(item);
        //             applicationList.appSearchList.options.push(item);
        //         })
        //
        //     });
        // }
    },
    /**  初始化页面时执行该方法 */
    created: function () {
        // this.getCurrentUser();
        this.getAppList();
    }
});
