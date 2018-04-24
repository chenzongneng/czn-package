/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */
$(function () {
    /**  初始化应用列表  */
    $("#jqGrid").jqGrid({
        url: baseURL + 'applications?userId=' + userId,
        datatype: "json",
        postData:{
            'mode': localStorage.getItem("mode"),
            'token': accessToken
        },
        colModel: [
            {label: '应用ID', name: 'id', align: 'center', hidden: true, width: 20, key: true ,sortable: false},
            {label: '应用名称', name: 'name', align: 'center', width: 80 ,sortable: false},
            {label: '应用标识', name: 'appCode', align: 'center', width: 80 ,sortable: false},
            {label: '所属公司', name: 'company', align: 'center', width: 80 ,sortable: false},
            {label: '刷新资源Api', name: 'refreshResourcesApi', align: 'center', width: 80 ,sortable: false},
            {label: '主机', name: 'hosts', align: 'center', width: 80 ,sortable: false},
            {label: '创建时间', name: 'createdTime', align: 'center', formatter:timeFormat, width: 160 ,sortable: false},
            {label: '更新时间', name: 'modifiedTime', align: 'center', formatter:timeFormat, width: 160 ,sortable: false},
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

/** 用户树 */
var tenantTree;
var tenantTreeSetting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id"
        },
        key: {
            name: "name"
        }
    },
    check: {
        enable: true,
        nocheckInherit: true,
        chkboxType: {"Y": "", "N": ""}
    }
};


var vm = new Vue({
    el: '#garnetApp',
    data: {
        searchName: null,
        showList: true,
        title: null,
        modeId: null,// SAAS为0，PAAS为1
        mode: null,
        tips:null, //根据选择模式的不同给出不同的提示信息
        hidden: true,
        modeName: null,
        application: {
            id:null,
            name: null,
            code: null,
            appCode: null,
            company: null,
            refreshResourcesApi: null,
            createdTime: null,
            hosts: null,
            modifiedTime: null,
            tenantNames: [],
            tenantIds: '',
            serviceMode: null,
            tenantIdList: []
        },
        // 选择模式列表
        modeList: {
            selectedMode: localStorage.getItem("mode"),
            options: [
                {
                id : "all",
                name : "平台模式-all"
            },
            {
                id : "saas",
                name : "平台模式-saas"
            },{
                id : "paas",
                name : "平台模式-paas"
            }]
        },
        // 新增和更新 选择模式列表
        modeList2: {
            selectedMode: localStorage.getItem("mode"),
            options: [
                {
                    id : "saas",
                    name : "saas"
                },{
                    id : "paas",
                    name : "paas"
                }]
        }
    },
    mounted:function () {
        //移除mode option
        if (localStorage.getItem("mode") == "saas") {
            $("#selectModeId option[value='paas']").remove();
            $("#selectModeId option[value='all']").remove();
        } else if (localStorage.getItem("mode") == "paas") {
            $("#selectModeId option[value='all']").remove();
            $("#selectModeId option[value='saas']").remove();
        } else if (localStorage.getItem("mode") == "all") {
        }
    },
    methods: {
        /**  查询按钮点击事件 */
        query: function () {
            vm.reload(true);
        },
        /**  新增按钮点击事件 */
        add: function () {
            var mode;
            if ("all" == localStorage.getItem("mode")) {
                mode = "paas";
                vm.tips = "只能选择一个租户";
                vm.modeList2.selectedMode = mode;
                // console.log("vm.modeList2 " + vm.modeList2.selectedMode);
            } else if ("paas" == localStorage.getItem("mode")) {
                mode = "paas";
                vm.tips = "只能选择一个租户";
                vm.modeList2.selectedMode = mode;
            } else {
                vm.tips = "可选多个租户";
                mode = localStorage.getItem("mode");
                vm.modeList2.selectedMode = mode;
            }

            vm.showList = false;
            vm.hidden = false;
            vm.title = "新增";
            vm.application = {
                id:null,
                name: null,
                code: null,
                appCode: null,
                company: null,
                refreshResourcesApi: null,
                createdTime: null,
                hosts: null,
                modifiedTime: null,
                tenantNames: [],
                tenantIds: null,
                updatedByUserName: null
            };

            // 加载租户树
            $.get(baseURL + "tenants?page=1&limit=1000&mode=" + mode + "&userId=" + userId, function (response) {
                tenantTree = $.fn.zTree.init($("#tenantTree"), tenantTreeSetting, response.list);
                tenantTree.expandAll(true);
            });
        },
        /**  更新按钮点击事件 */
        update: function () {
            var mode;
            if ("all" == localStorage.getItem("mode")) {
                mode = "paas";
                vm.tips = "只能选择一个租户";
                vm.modeList2.selectedMode = mode;
            } else if ("paas" == localStorage.getItem("mode")) {
                mode = "paas";
                vm.tips = "只能选择一个租户";
                vm.modeList2.selectedMode = mode;
            }else {
                vm.tips = "可选多个租户";
                mode = localStorage.getItem("mode");
                vm.modeList2.selectedMode = mode;
            }

            var applicationId = getSelectedRow();
            if (!applicationId) {
                return;
            }
            vm.showList = false;
            vm.hidden = true;
            vm.title = "修改";

            vm.application.tenantIds = null;
            vm.application.tenantNames = [];

            //初始化应用信息，及应用模式
            vm.getApplication(applicationId);
        },
        /**  删除按钮点击事件 */
        del: function () {
            var applicationIds = getSelectedRows();
            var title;
            if (!applicationIds) {
                return;
            }

            $.get(baseURL + "applications/relate?ids=" + applicationIds + "&token=" + accessToken, function (response) {
                if (response == Boolean(true)) {
                    title = "应用已被添加到应用组，是否确认删除？";
                } else {
                    title = "确定要删除选中的记录";
                }
            });

            swal({
                    title:title,
                    // title: "删除应用",
                    // text:title,
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
                        url: baseURL + "applications?ids=" + applicationIds.toString() + "&token=" + accessToken,
                        contentType: "application/json",
                        dataType: "",
                        success: function () {
                            swal("删除成功!", "", "success");
                            vm.reload(false);
                        },
                        error: function () {
                            swal("删除失败!", "系统错误，请联系系统管理员！", "error");
                        }
                    });
                });
        },
        /**  新增或更新确认 */
        saveOrUpdate: function () {

            var obj = new Object();
            vm.application.updatedByUserName = localStorage.getItem("userName");
            obj.application = vm.application;
            obj.tenantIds =vm.application.tenantIds;
            // alert(JSON.stringify(obj));
            var tenantIdList = vm.application.tenantIdList;

            if(vm.application.name == null || $.trim(vm.application.name) == ""){
                // alert("应用名称不能为空");
                swal("", "应用名称不能为空", "warning");
                return;
            }

            if (vm.application.appCode == null || $.trim(vm.application.name) == "") {
                swal("", "应用标识不能为空", "warning");
                return;
            }

            // vm.checkAppCodeForm(vm.application.appCode);

            var specialReg = /^(?!_)(?!.*?_$)[-a-zA-Z0-9_\u4e00-\u9fa5]+$/;//非特殊符号的正则表达式
            if (!specialReg.test(vm.application.appCode)) {
                console.log("nonono");
                swal("", "应用标识只能使用英文、数字、下划线或者连字符！", "warning");
                return;
            }

            if (vm.application.company == null || $.trim(vm.application.name) == "") {
                swal("", "公司名称不能为空", "warning");
                return;
            }

            if (vm.application.name.length > 30) {
                swal("", "应用名称长度不能大于30", "warning");
                return;
            }

            if (vm.application.appCode.length > 30) {
                swal("", "应用标识长度不能大于30", "warning");
                return;
            }

            if (vm.application.company.length > 30) {
                swal("", "公司长度不能大于30", "warning");
                return;
            }

            var mode = vm.modeList2.selectedMode;

            if (mode == null) {
                localStorage.setItem("mode", vm.modeList.selectedMode);
                // vm.mode = vm.modeList.selectedMode;
            }

            if(mode == "saas") {  //saas
                vm.application.serviceMode = "saas";

            } else if(mode == "paas") {
                vm.application.serviceMode = "paas";
                if (tenantIdList!=null && tenantIdList.length > 1) {
                    swal("", "当前模式不能添加多个租户", "warning");
                    return;
                }
            } else {
                swal("", "请选择正确模式", "warning");
                return;
            }

            $.ajax({
                type: vm.application.id === null ? "POST" : "PUT",
                url: baseURL + "applications?token=" + accessToken,
                contentType: "application/json",
                data:JSON.stringify(obj),
                dataType: "",
                success: function () {
                    vm.reload(false);
                    swal("操作成功!", "", "success");
                },
                error: function (response) {
                    swal("", getExceptionMessage(response), "error");
                }
            });
        },
        /**  根据ID获取应用信息 */
        getApplication: function (applicationId) {
            $.get(baseURL + "applications/" + applicationId + "?token=" + accessToken, function (response) {
                response=response.data;
                if (response) {
                    vm.application.id = response.application.id;
                    vm.application.name = response.application.name;
                    vm.application.appCode = response.application.appCode;
                    vm.application.company = response.application.company;
                    vm.application.refreshResourcesApi = response.application.refreshResourcesApi;
                    vm.application.createdTime = response.application.createdTime;
                    vm.application.modifiedTime = response.application.modifiedTime;
                    vm.modeList2.selectedMode = response.application.serviceMode;

                    var mode = vm.modeList2.selectedMode;
                    // console.log("app response == " + JSON.stringify(response));

                    // 加载租户树
                    $.get(baseURL + "tenants?page=1&limit=1000&mode=" + mode + "&userId=" + userId, function (response) {
                        tenantTree = $.fn.zTree.init($("#tenantTree"), tenantTreeSetting, response.list);
                        tenantTree.expandAll(true);
                    });

                    // 勾选已有租户
                    $.each(response.tenantIdList, function (index, item) {
                        var node = tenantTree.getNodeByParam("id", item);
                        if (node == null) {
                            console.log("勾选已有租户 null");
                            return;
                        }
                        tenantTree.checkNode(node, true, false);
                    });
                    $.each(response.tenantNameList, function (index, item) {
                        vm.application.tenantNames.push(item);
                    })
                }
            });
        },
        /**  租户树点击事件 */
        tenantTree: function () {

            var title;

            if ("paas" == vm.modeList2.selectedMode) {
                title = "选择租户（只能选择一个）";
            } else if ("saas" == vm.modeList2.selectedMode) {
                title = "选择租户（可选择多个）";
            }

            layer.open({
                type: 1,
                offset: '50px',
                // maxmin: true,
                skin: 'layui-layer-molv',
                title: title,
                area: ['300px', '450px'],
                shade: 0.3,
                shadeClose: false,
                content: jQuery("#tenantLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    vm.application.tenantNames = [];
                    vm.application.tenantIds = "";
                    // 获取应用树选择的应用
                    var tenantNodes = tenantTree.getCheckedNodes(true);
                    var tenantIdList = [];
                    for (var i = 0; i < tenantNodes.length; i++) {
                        tenantIdList.push(tenantNodes[i].id);
                        vm.application.tenantNames.push(tenantNodes[i].name);
                    }
                    vm.application.tenantIds = tenantIdList.join(",");
                    vm.application.tenantIdList = tenantIdList;
                    layer.close(index);
                }
            });
        },
        //模式选择事件
        selectMode: function () {
            var mode = vm.modeList.selectedMode;
            localStorage.setItem("mode", mode);
            vm.reload(false);
        },
        //新增和更新 模式选择事件
        selectMode2: function () {
            vm.mode = vm.modeList2.selectedMode;
            localStorage.setItem("mode", vm.mode);
            vm.modeList.selectedMode = vm.mode;
            vm.application.tenantNames = null;
            vm.application.tenantIds = null;
            vm.application.tenantIdList = null;

            if ("paas" == vm.mode) {
                vm.tips = "只能选择一个租户";
            } else if ("saas" == vm.mode) {
                vm.tips = "可选择多个租户";
            }

            // 加载租户树
            $.get(baseURL + "tenants?page=1&limit=1000&mode=" + vm.mode + "&userId=" + userId, function (response) {
                tenantTree = $.fn.zTree.init($("#tenantTree"), tenantTreeSetting, response.list);
                tenantTree.expandAll(true);
            });
        },
        /**
         * 验证应用标识
         */
        checkAppCodeForm: function (value) {

        },
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
                    'searchName': vm.searchName,
                    'mode': localStorage.getItem("mode")
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});