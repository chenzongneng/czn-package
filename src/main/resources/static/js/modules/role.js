/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

$(function () {
    /** 初始化角色列表 */
    $("#jqGrid").jqGrid({
        url: baseURL + 'roles',
        datatype: "json",
        colModel: [
            {label: '角色ID', name: 'roleId', align: 'center', hidden: true, index: "role_id", width: 20, key: true},
            {label: '角色名称', name: 'name', align: 'center', width: 70},
            {label: '所属租户', name: 'tenantName', align: 'center', width: 70},
            {label: '所属应用', name: 'appName', align: 'center', width: 70},
            {label: '部门列表', name: 'deptNameList', align: 'center', width: 100},
            {label: '权限列表', name: 'permissionNameList', align: 'center', width: 100}
            // {label: '创建时间', name: 'createTime', align: 'center', width: 90}
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

/** 部门结构树 */
var deptTree;
var deptTreeSetting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "departmentId",
            pIdKey: "parentDepartmentId",
            rootPId: -1
        },
        key: {
            url: "nourl",
            name: "name"
        }
    },
    check: {
        enable: true,
        nocheckInherit: true,
        chkboxType: {"Y": "", "N": ""}
    }
};

/** 部门结构树 */
var permissionTree;
var permissionTreeSetting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "permissionId"
        },
        key: {
            url: "nourl",
            name: "name"
        }
    },
    check: {
        enable: true,
        nocheckInherit: true,
        chkboxType: {"Y": "", "N": ""}
    }
};

var currentUser;

var vm = new Vue({
    el: '#garnetApp',
    data: {
        searchName: null,
        showList: true,
        title: null,
        role: {
            roleId: null,
            tenantId: null,
            applicationId: 1,
            name: null,
            remark: null,
            deptIds: null
        },
        // 租户列表数据
        tenantList: {
            selectedTenant: "",
            options: []
        },
        // 应用列表数据
        appList: {
            selectedApp: "1",
            options: []
        },
        // 当前用户信息
        currentUser: {}
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
            vm.tenantList.selectedTenant = "";
            vm.tenantList.options = [];
            vm.appList.selectedApp = "1";
            vm.appList.options = [];
            vm.role = {
                roleId: null,
                tenantId: null,
                applicationId: 1,
                name: null,
                remark: null,
                deptIds: null,
                permissionIdList:null
            };
            vm.initTreesToAdd();
            vm.getTenantList();
            vm.getAppList();
        },
        /**  更新按钮点击事件 */
        update: function () {
            var roleId = getSelectedRow();
            if (!roleId) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.appList.selectedApp = "";
            vm.tenantList.options = [];
            vm.appList.options = [];
            vm.role.deptIdList = [];
            vm.initTreesToUpdate(roleId);
            vm.getTenantList();
            vm.getAppList();
        },
        /**  删除按钮点击事件 */
        del: function () {
            var roleIds = getSelectedRows();
            if (!roleIds) {
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
                        url: baseURL + "role?roleIds=" + roleIds.toString(),
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
            // 获取部门树选择的部门
            var depts = deptTree.getCheckedNodes(true);
            var deptIdList = [];
            for (var i = 0; i < depts.length; i++) {
                deptIdList.push(depts[i].departmentId);
            }
            vm.role.deptIds = deptIdList.join(",");
            // 获取权限树选择的权限
            var permissions = permissionTree.getCheckedNodes(true);
            var permissionIdList = [];
            for (var i = 0; i < permissions.length; i++) {
                permissionIdList.push(permissions[i].permissionId);
            }
            vm.role.permissionIds = permissionIdList.join(",");
            if(vm.role.name === null || vm.role.name === "") {
                alert("角色名称不能为空");
                return;
            }
            if(vm.role.tenantId === null) {
                alert("请选择租户");
                return;
            }
            if(vm.role.applicationId === null) {
                alert("请选择应用");
                return;
            }
            $.ajax({
                type: vm.role.roleId === null ? "POST" : "PUT",
                url: baseURL + "role",
                contentType: "application/json",
                data: JSON.stringify(vm.role),
                dataType: '',
                success: function () {
                    vm.reload(false);
                    swal("操作成功!", "", "success");
                },
                error: function (response) {
                    swal(response.responseJSON.errorMessage, "", "error");
                }
            });
        },
        /** 添加按钮初始化数据 */
        initTreesToAdd: function () {
            //加载部门树
            // $.get(baseURL + "depts/" + currentUser.userId, function (response) {
            $.get(baseURL + "departments?page=1&limit=1000", function (response) {
                deptTree = $.fn.zTree.init($("#deptTree"), deptTreeSetting, response.list);
                deptTree.expandAll(false);
            });
            //加载权限树
            $.get(baseURL + "permissions/applicationId/" + vm.appList.selectedApp, function (response) {
                permissionTree = $.fn.zTree.init($("#permissionTree"), permissionTreeSetting, response);
                permissionTree.expandAll(true);
            })
        },
        /** 更新按钮初始化数据 */
        initTreesToUpdate: function (roleId) {
            vm.getRoleById(roleId);
            vm.initTree();
        },
        /** 通过id 得到一个role对象 */
        getRoleById: function (roleId) {
            $.get(baseURL + "role/" + roleId, function (response) {
                vm.role.roleId = response.roleId;
                vm.role.applicationId = response.applicationId;
                vm.role.tenantId = response.tenantId;
                vm.role.name = response.name;
                vm.role.remark = response.remark;
                vm.role.deptIdList = response.deptIdList;
                vm.role.permissionIdList = response.permissionIdList;
                vm.tenantList.selectedTenant = response.tenantId;
                vm.appList.selectedApp = response.applicationId;
                //加载部门树
                // $.get(baseURL + "depts/" + currentUser.userId, function (response) {
                $.get(baseURL + "departments?page=1&limit=1000", function (response) {
                    deptTree = $.fn.zTree.init($("#deptTree"), deptTreeSetting, response.list);
                    deptTree.expandAll(true);
                    console.log(JSON.stringify(vm.role.deptIdList));
                    $.each(vm.role.deptIdList, function (index, item) {
                        var node = deptTree.getNodeByParam("departmentId", item);
                        deptTree.checkNode(node, true, false);
                    });
                });
                //加载权限树
                $.get(baseURL + "permissions/applicationId/" + vm.appList.selectedApp, function (response) {
                    permissionTree = $.fn.zTree.init($("#permissionTree"), permissionTreeSetting, response);
                    permissionTree.expandAll(true);
                    $.each(vm.role.permissionIdList, function (index, item) {
                        var node = permissionTree.getNodeByParam("permissionId", item);
                        permissionTree.checkNode(node, true, false);
                    });
                });
            });
        },
        initTree:function () {
        },
        /** 查询当前用户信息 */
        getCurrentUser: function () {
            $.getJSON(baseURL + "token/userInfo?token=" + garnetToken, function (response) {
                // vm.currentUser = response;
                currentUser = response;
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
                postData: {searchName: vm.searchName},
                page: page
            }).trigger("reloadGrid");
        },
        /** 租户列表onchange 事件*/
        selectTenant: function () {
            vm.role.tenantId = vm.tenantList.selectedTenant;
        },
        /** 应用列表onchange 事件*/
        selectApp: function () {
            vm.role.applicationId = vm.appList.selectedApp;
            vm.roadPermissionTree();
        },
        /**  获取租户列表 */
        getTenantList: function () {
            $.get(baseURL + "tenants?page=1&limit=1000", function (response) {
                $.each(response.list, function (index, item) {
                    vm.tenantList.options.push(item);
                })
            });
        },
        /**  获取应用列表 */
        getAppList: function () {
            $.get(baseURL + "applications?page=1&limit=1000", function (response) {
                $.each(response.list, function (index, item) {
                    vm.appList.options.push(item);
                })
            });
        },
        /** 加载权限树 */
        roadPermissionTree:function () {
            //加载权限树
            $.get(baseURL + "permissions/applicationId/" + vm.appList.selectedApp, function (response) {
                permissionTree = $.fn.zTree.init($("#permissionTree"), permissionTreeSetting, response);
                permissionTree.expandAll(true);
            })
            $.each(vm.role.permissionIdList, function (index, item) {
                var node = permissionTree.getNodeByParam("permissionId", item);
                permissionTree.checkNode(node, true, false);
            });
        }
    },
    /**  初始化页面时执行该方法 */
    created: function () {
        this.getCurrentUser();
    }
});
