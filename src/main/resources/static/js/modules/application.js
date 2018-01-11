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
            {label: '应用ID', name: 'applicationId', align: 'center', hidden: true, width: 20, key: true},
            {label: '应用名称', name: 'name', align: 'center', width: 80},
            {label: '应用标识', name: 'code', align: 'center', width: 80},
            {label: '租户列表', name: 'tenantNameList', align: 'center', width: 100},
            {label: '所属公司', name: 'company', align: 'center', width: 80},
            {label: '备注', name: 'remark', align: 'center', width: 80},
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
var tenantTree;
var tenantTreeSetting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "tenantId"
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
        application: {
            name: null,
            code: null,
            company: null,
            remark: null,
            tenantNames: [],
            tenantIds: null
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
            vm.application = {
                applicationId: null,
                name: null,
                code: null,
                company: null,
                remark: null,
                tenantNames: [],
                tenantIds: null
            };

            // 加载租户树
            $.get(baseURL + "tenants?page=1&limit=1000", function (response) {
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
            // 加载应用树
            $.get(baseURL + "tenants?page=1&limit=1000", function (response) {
                tenantTree = $.fn.zTree.init($("#tenantTree"), tenantTreeSetting, response.list);
                tenantTree.expandAll(true);
                vm.getApplication(applicationId);
            });
        },
        /**  删除按钮点击事件 */
        del: function () {
            var applicationIds = getSelectedRows();
            if (!applicationIds) {
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
                        url: baseURL + "application?applicationIds=" + applicationIds.toString(),
                        contentType: "application/json",
                        dataType: "",
                        success: function () {
                            swal("删除成功!", "", "success");
                            vm.reload();
                        },
                        error: function () {
                            swal("删除失败!", "系统错误，请联系系统管理员！", "error");
                        }
                    });
                });
        },
        /**  新增或更新确认 */
        saveOrUpdate: function () {
            $.ajax({
                type: vm.application.applicationId === null ? "POST" : "PUT",
                url: baseURL + "application",
                contentType: "application/json",
                data: JSON.stringify(vm.application),
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
        /**  根据ID获取应用信息 */
        getApplication: function (applicationId) {
            $.get(baseURL + "application/" + applicationId, function (response) {
                if (response) {
                    vm.application.applicationId = response.applicationId;
                    vm.application.name = response.name;
                    vm.application.code = response.code;
                    vm.application.company = response.company;
                    vm.application.remark = response.remark;

                    // 勾选已有应用
                    $.each(response.tenantIdList, function (index, item) {
                        var node = tenantTree.getNodeByParam("tenantId", item);
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
                        tenantIdList.push(tenantNodes[i].tenantId);
                        vm.application.tenantNames.push(tenantNodes[i].name);
                    }
                    vm.application.tenantIds = tenantIdList.join(",");
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