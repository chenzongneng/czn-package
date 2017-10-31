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
            {label: '应用ID', name: 'appId', hidden: true, width: 45, key: true},
            {label: '应用名称', name: 'name', width: 75},
            {label: '所属公司', name: 'company', width: 90},
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
        application: {
            name: null,
            company: null,
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
            vm.application = {
                appId: null,
                name: null,
                company: null,
                remark: null
            };
        },
        /**  更新按钮点击事件 */
        update: function () {
            var appId = getSelectedRow();
            if (!appId) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getApplication(appId);
        },
        /**  删除按钮点击事件 */
        del: function () {
            var appIds = getSelectedRows();
            if (!appIds) {
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
                        url: baseURL + "application?appIds=" + appIds.toString(),
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
                type: vm.application.appId === null ? "POST" : "PUT",
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
        getApplication: function (appId) {
            $.get(baseURL + "application/" + appId, function (response) {
                if (response) {
                    vm.application.appId = response.appId;
                    vm.application.name = response.name;
                    vm.application.company = response.company;
                    vm.application.remark = response.remark;
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