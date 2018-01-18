/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

$(function () {
    /**  初始化租户列表  */
    $("#jqGrid").jqGrid({
        url: baseURL + 'tenants',
        datatype: "json",
        colModel: [
            {label: '租户ID', name: 'tenantId', align: 'center', hidden: true, width: 20, key: true},
            {label: '租户名称', name: 'name', align: 'center', width: 80},
            {label: '应用列表', name: 'appNameList', align: 'center', width: 100},
            {label: '备注', name: 'remark', align: 'center', width: 80}
            // {label: '创建时间', name: 'createTime', align: 'center', width: 80}
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
var appTree;
var appTreeSetting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "appId"
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
        tenant: {
            name: null,
            remark: null,
            appIds: null,
            appNames: [],
            appIdList: []
        }
    },
    methods: {
        /**  查询按钮点击事件 */
        query: function () {
            vm.reload();
        },
        /**  新增按钮点击事件 */
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.tenant = {
                tenantId: null,
                name: null,
                remark: null,
                appIds: null,
                appNames: [],
                appIdList: []
            };

            // 加载应用树
            $.get(baseURL + "applications?page=1&limit=1000", function (response) {
                appTree = $.fn.zTree.init($("#appTree"), appTreeSetting, response.list);
                appTree.expandAll(true);
            });
        },
        /**  更新按钮点击事件 */
        update: function () {
            var tenantId = getSelectedRow();
            if (!tenantId) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.tenant.appIds = null;
            vm.tenant.appNames = [];
            // 加载应用树
            $.get(baseURL + "applications?page=1&limit=1000", function (response) {
                appTree = $.fn.zTree.init($("#appTree"), appTreeSetting, response.list);
                appTree.expandAll(true);
                vm.getTenant(tenantId);
            });
        },
        /**  删除按钮点击事件 */
        del: function () {
            var tenantIds = getSelectedRows();
            if (!tenantIds) {
                return;
            }
            swal({
                    title: "确定要删除选中的记录？",
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
                        url: baseURL + "tenant?tenantIds=" + tenantIds.toString(),
                        contentType: "application/json",
                        dataType: "",
                        success: function () {
                            swal("删除成功!", "", "success");
                            vm.reload();
                        },
                        error: function () {
                            swal("删除失败!", "系统错误，请联系系统管理员！", "success");
                        }

                    });
                });
        },
        /**  新增或更新确认 */
        saveOrUpdate: function () {
            $.ajax({
                type: vm.tenant.tenantId === null ? "POST" : "PUT",
                url: baseURL + "tenant",
                contentType: "application/json",
                data: JSON.stringify(vm.tenant),
                dataType: "",
                success: function () {
                    vm.reload();
                    swal("操作成功!", "", "success");
                },
                error: function (response) {
                    swal(response.responseJSON.errorMessage, "", "error");
                }
            });
        },
        /**  根据ID获取租户信息 */
        getTenant: function (tenantId) {
            $.get(baseURL + "tenant/" + tenantId, function (response) {
                if (response) {
                    vm.tenant.tenantId = response.tenantId;
                    vm.tenant.name = response.name;
                    vm.tenant.remark = response.remark;
                    vm.tenant.appIdList = response.appIdList;
                    $.each(response.appNameList, function (index, item) {
                        vm.tenant.appNames.push(item);
                    })
                }
            });
        },
        /**  应用树点击事件 */
        appTree: function (tenant) {
            console.log(JSON.stringify(tenant));
            $.each(tenant.appIdList, function (index, item) {
                var node = appTree.getNodeByParam("applicationId", item);
                appTree.checkNode(node, true, false);
            });
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择应用",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#appLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    // 勾选已有应用
                    // 获取应用树选择的应用
                    var appNodes = appTree.getCheckedNodes(true);
                    var appIdList = [];
                    vm.tenant.appNames = [];
                    vm.tenant.appIds = null;
                    for (var i = 0; i < appNodes.length; i++) {
                        appIdList.push(appNodes[i].applicationId);
                        vm.tenant.appNames.push(appNodes[i].name);
                    }
                    vm.tenant.appIds = appIdList.join(",");
                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'searchName': vm.searchName},
                page: page
            }).trigger("reloadGrid");
        }
    }
});