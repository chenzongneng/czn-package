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
            {label: '租户ID', name: 'tenantId', hidden: true, width: 45, key: true},
            {label: '租户名称', name: 'name', width: 75},
            {label: '备注', name: 'remark', width: 80},
            {label: '创建时间', name: 'createTime', width: 90}
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
        }
    });
});

var vm = new Vue({
    el: '#garnetApp',
    data: {
        searchName: null,
        showList: true,
        title: null,
        tenant: {
            name: null,
            remark: null
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
                remark: null
            };
        },
        /**  更新按钮点击事件 */
        update: function () {
            var tenantId = getSelectedRow();
            if (!tenantId) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getTenant(tenantId);
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