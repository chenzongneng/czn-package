/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

$(function () {
    /**  初始化应用列表  */
    $("#jqGrid").jqGrid({
        url: baseURL + 'applications',
        datatype: "json",
        colModel: [
            {label: '应用ID', name: 'id', align: 'center', hidden: true, width: 20, key: true},
            {label: '应用名称', name: 'name', align: 'center', width: 80},
            {label: '应用标识', name: 'appCode', align: 'center', width: 80},
            {label: '所属公司', name: 'company', align: 'center', width: 80},
            {label: 'refreshResourcesApi', name: 'refreshResourcesApi', align: 'center', width: 80},
            {label: '主机', name: 'hosts', align: 'center', width: 80},
            {label: '创建时间', name: 'createdTime', align: 'center', width: 80},
            {label: '更新时间', name: 'modifiedTime', align: 'center', width: 80}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
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
        modeId: 1,// SAAS为0，PAAS为1
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
            selectedMode: "",
            options: [
                {
                id : -1,
                name : "all"
            },
            {
                id : 0,
                name : "saas"
            },{
                id : 1,
                name : "paas"
            }]
        }
    },
    methods: {
        /**  查询按钮点击事件 */
        query: function () {
            vm.reload(true);
        },
        /**  新增按钮点击事件 */
        add: function () {
            vm.showList = false;
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
                tenantIds: null
            };

            // 加载租户树
            $.get(baseURL + "tenants?page=1&limit=1000&modeId=" + vm.modeId, function (response) {
                tenantTree = $.fn.zTree.init($("#tenantTree"), tenantTreeSetting, response.list);
                tenantTree.expandAll(true);
            });
        },
        /**  更新按钮点击事件 */
        update: function () {
            var applicationId = getSelectedRow();
            if (!applicationId) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.application.tenantIds = null;
            vm.application.tenantNames = [];

            //初始化应用信息，及应用模式
            vm.getApplication(applicationId);

            // 加载租户树
            $.get(baseURL + "tenants?page=1&limit=1000&modeId=" + vm.modeId, function (response) {
                tenantTree = $.fn.zTree.init($("#tenantTree"), tenantTreeSetting, response.list);
                tenantTree.expandAll(true);
            });
        },
        /**  删除按钮点击事件 */
        del: function () {
            var applicationIds = getSelectedRows();
            var title;
            if (!applicationIds) {
                return;
            }
            $.get(baseURL + "applications/relate?ids=" + applicationIds, function (response) {
                if (response == Boolean(true)) {
                    title = "应用仍被其他租户关联，若确定则将关联租户一并删除，是否确定删除？";
                } else {
                    title = "确定要删除选中的记录";
                }
            });

            swal({
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
                        url: baseURL + "applications?ids=" + applicationIds.toString(),
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
            obj.application = vm.application;
            obj.tenantIds =vm.application.tenantIds;
            // alert(JSON.stringify(obj));
            var tenantIdList = vm.application.tenantIdList;
            if(vm.application.name === null){
                alert("应用名称不能为空");
                return;
            }

            if (vm.modeId == null) {
                vm.modeId = vm.modeList.selectedMode;
            }

            if(vm.modeId == 0) {  //saas
                vm.application.serviceMode = "saas";
                //saas模式下，一个租户被一个应用绑定

            } else if(vm.modeId == 1) {
                vm.application.serviceMode = "paas"
                if (tenantIdList.length > 1) {
                    swal("当前模式不能添加多个租户", "", "error");
                    return;
                }
            } else {
                swal({
                    title: "当前默认为PAAS模式，是否确认添加",
                    type: "warning",
                    showCancelButton: true,
                    closeOnConfirm: false,
                    confirmButtonText: "确认",
                    cancelButtonText: "取消",
                    confirmButtonColor: "#DD6B55"
                }, function () {
                    $.ajax({
                        type: vm.application.id === null ? "POST" : "PUT",
                        url: baseURL + "applications",
                        contentType: "application/json",
                        data:JSON.stringify(obj),
                        dataType: "",
                        success: function () {
                            vm.reload(false);
                            swal("操作成功!", "", "success");
                        },
                        error: function (response) {
                            swal(response.responseJSON.errorMessage, "", "error");
                        }
                    });
                });
                return;
            }

            $.ajax({
                type: vm.application.id === null ? "POST" : "PUT",
                url: baseURL + "applications",
                contentType: "application/json",
                data:JSON.stringify(obj),
                dataType: "",
                success: function () {
                    vm.reload(false);
                    swal("操作成功!", "", "success");
                },
                error: function (response) {
                    swal(response.responseJSON.data.errorResponseMessage, "", "error");
                }
            });
        },
        /**  根据ID获取应用信息 */
        getApplication: function (applicationId) {
            $.get(baseURL + "applications/" + applicationId, function (response) {
                response=response.data;

                if (response) {
                    vm.application.id = response.application.id;
                    vm.application.name = response.application.name;
                    vm.application.appCode = response.application.appCode;
                    vm.application.company = response.application.company;
                    vm.application.refreshResourcesApi = response.application.refreshResourcesApi;
                    vm.application.createdTime = response.application.createdTime;
                    vm.application.modifiedTime = response.application.modifiedTime;
                    // 勾选已有租户
                    $.each(response.tenantIdList, function (index, item) {
                        var node = tenantTree.getNodeByParam("id", item);
                        tenantTree.checkNode(node, true, false);
                    });
                    $.each(response.tenantNameList, function (index, item) {
                        vm.application.tenantNames.push(item);
                    })

                    var mode = response.application.serviceMode;
                    if(mode == "saas") {
                        vm.modeId = 0;
                    } else if (mode == "paas") {
                        vm.modeId = 1;
                    } else {
                        vm.modeId = -1;
                    }

                }
            });
        },
        /**  租户树点击事件 */
        tenantTree: function () {
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择租户",
                area: ['300px', '450px'],
                shade: 0,
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
            vm.modeId = vm.modeList.selectedMode;
            vm.reload(true);
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
                    'modeId' : vm.modeId
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});