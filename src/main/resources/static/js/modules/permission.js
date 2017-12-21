/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

$(function () {
    /** 初始化角色列表 */
    $("#jqGrid").jqGrid({
        url: baseURL + 'permissions',
        datatype: "json",
        colModel: [
            {
                label: '访问权限ID',
                name: 'permissionId',
                align: 'center',
                hidden: true,
                index: "permission_id",
                width: 20,
                key: true
            },
            {label: '具体名称', name: 'name', align: 'left', width: 70},
            {label: '访问权限标识符', name: 'permission', align: 'center', width: 70},
            {label: '说明', name: 'description', align: 'center', width: 70},
            {label: '对应的链接', name: 'url', align: 'center', width: 70},
            {label: '方法', name: 'method', align: 'center', width: 70},
            {
                label: '状态', align: 'center', name: 'status', width: 50, formatter: function (value, options, row) {
                return value === 0 ?
                    '<span class="label label-danger">禁用</span>' :
                    '<span class="label label-success">正常</span>';
            }
            }
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
        postData: {
            token: garnetToken
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
        title: null,
        permission: {
            permissionId: null
        },
        option: {
            appId: 1,
            appSearchId: ""
        },
        // 当前用户信息
        currentUser: {}
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
            vm.tenantList.selectedTenant = "";
            vm.tenantList.options = [];
            applicationList.appList.selectedApp = "";
            vm.permission = {
                permissionId: null,
                name: null,
                permission: null,
                description: null,
                url: null,
                method: "GET",
                status: 1
            };
        },
        /**  更新按钮点击事件 */
        update: function () {
            var permissionId = getSelectedRow();
            if (!permissionId) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.getPermissionById(permissionId);
        },
        /**  删除按钮点击事件 */
        del: function () {
            var permissionIds = getSelectedRows();
            if (!permissionIds) {
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
                        url: baseURL + "permission?permissionIds=" + permissionIds.toString(),
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
                type: vm.permission.permissionId === null ? "POST" : "PUT",
                url: baseURL + "permission",
                contentType: "application/json",
                data: JSON.stringify(vm.permission),
                dataType: '',
                success: function () {
                    vm.reload();
                    swal("操作成功!", "", "success");
                },
                error: function (response) {
                    swal(response.responseJSON.errorMessage, "", "error");
                }
            });
        },
        /** 更新按钮初始化数据 */
        initTreesToUpdate: function (permissionId) {
            vm.getPermissionById(permissionId);
        },
        /** 通过id 得到一个permission对象 */
        getPermissionById: function (permissionId) {
            $.get(baseURL + "permission/" + permissionId, function (response) {
                vm.permission.permissionId = response.permissionId;
                vm.permission.applicationId = response.applicationId;
                vm.permission.name = response.name;
                vm.permission.permission = response.permission;
                vm.permission.description = response.description;
                vm.permission.url = response.url;
                vm.permission.method = response.method;
                vm.permission.status = response.status;
                applicationList.appList.selectedApp = response.appId;
            });
        },
        /** 查询当前用户信息 */
        getCurrentUser: function () {
            $.getJSON(baseURL + "token/userInfo?token=" + garnetToken, function (response) {
                vm.currentUser = response;
            });
        },
        /** 重新加载 */
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {name: vm.name, applicationId: vm.option.appSearchId},
                page: page
            }).trigger("reloadGrid");
        },
        /** 租户列表onchange 事件*/
        selectTenant: function () {
            vm.permission.tenantId = vm.tenantList.selectedTenant;
        },
        /** 应用列表onchange 事件*/
        selectApp: function () {
            vm.permission.applicationId = applicationList.appList.selectedApp;
        },
        /**  获取应用列表 */
        getAppList: function () {
            $.get(baseURL + "applications?page=1&limit=1000", function (response) {
                $.each(response.list, function (index, item) {
                    applicationList.appList.options.push(item);
                    applicationList.appSearchList.options.push(item);
                })
            });
        }
    },
    /**  初始化页面时执行该方法 */
    created: function () {
        this.getCurrentUser();
        this.getAppList();
    }
});
