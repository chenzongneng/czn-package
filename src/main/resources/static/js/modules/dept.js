/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

var Dept = {
    id: "deptTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Dept.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '部门ID', field: 'deptId', CanHide: 0, Visible: 0, align: 'center', valign: 'middle', width: '70px'},
        {title: '部门名称', field: 'name', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '所属租户', field: 'tenantName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '所属应用', field: 'appName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '上级部门', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '用户列表', field: 'userNameList', align: 'center', valign: 'middle', sortable: true, width: '150px'},
        {title: '角色列表', field: 'roleNameList', align: 'center', valign: 'middle', sortable: true, width: '150px'},
        {title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '60px'}];
};

/** 部门树 */
var deptTree;
var deptTreeSetting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentDeptId",
            rootPId: -1
        },
        key: {
            url: "nourl",
            name: "name"
        }
    }
};


/** 用户树 */
var userTree;
/** ztree 配置*/
var userTreeSetting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "userId"
        },
        key: {
            url: "nourl",
            name: "userName"
        }
    },
    check: {
        enable: true,
        nocheckInherit: true,
        chkboxType: {"Y": "", "N": ""}
    }
};

/** 角色树 */
var roleTree;
/** ztree 配置*/
var roleTreeSetting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "roleId"
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

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        // 当前用户信息
        currentUser: {},
        dept: {
            deptId: null,
            parentDeptId: null,
            tenantId: null,
            appId: null,
            name: null,
            parentName: null,
            orderNum: 0,
            userIds: null,
            roleIds: null
        },
        // 租户列表数据
        tenantList: {
            selectedTenant: "",
            options: []
        },
        // 应用列表数据
        appList: {
            selectedApp: "",
            options: []
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
            vm.tenantList.selectedTenant = "";
            vm.tenantList.options = [];
            vm.appList.selectedApp = "";
            vm.appList.options = [];
            vm.dept = {
                deptId: null,
                parentDeptId: null,
                tenantId: null,
                appId: null,
                name: null,
                parentName: null,
                orderNum: 0,
                userIds: null,
                roleIds: null
            };
            vm.initTreesToAdd();
            vm.getTenantList();
            vm.getAppList();
        },
        /**  更新按钮点击事件 */
        update: function () {
            var deptId = getDeptId();
            if (!deptId) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.tenantList.options = [];
            vm.appList.options = [];
            vm.dept.userIdList = [];
            vm.dept.roleIdList = [];
            vm.getTenantList();
            vm.getAppList();
            vm.initTreesToUpdate(deptId);

        },
        /**  删除按钮点击事件 */
        del: function () {
            var deptId = getDeptId();
            if (!deptId) {
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
                        url: baseURL + "dept/" + deptId,
                        contentType: "application/json",
                        dataType: "",
                        success: function (result) {
                            if (!result.message) {
                                swal("删除成功!", "", "success");
                                vm.reload();
                            } else {
                                swal("删除失败!", result.message, "error");
                            }
                        }
                    });
                });
        },
        /**  新增或更新确认 */
        saveOrUpdate: function () {
            // 获取用户树选择的用户
            var userNodes = userTree.getCheckedNodes(true);
            var userIdList = [];
            for (var i = 0; i < userNodes.length; i++) {
                userIdList.push(userNodes[i].userId);
            }
            vm.dept.userIds = userIdList.join(",");

            // 获取角色树选择的角色
            var roleNodes = roleTree.getCheckedNodes(true);
            var roleIdList = [];
            for (var k = 0; k < roleNodes.length; k++) {
                roleIdList.push(roleNodes[k].roleId);
            }
            vm.dept.roleIds = roleIdList.join(",");

            $.ajax({
                type: vm.dept.deptId === null ? "POST" : "PUT",
                url: baseURL + "dept",
                contentType: "application/json",
                data: JSON.stringify(vm.dept),
                dataType: "",
                success: function () {
                    vm.reload();
                    swal("操作成功!", "", "success");
                }
            });
        },
        /**  部门树点击事件 */
        deptTree: function () {
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择部门",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = deptTree.getSelectedNodes();
                    //选择上级部门
                    vm.dept.parentDeptId = node[0].deptId;
                    vm.dept.parentName = node[0].name;
                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            Dept.table.refresh();
        },
        /** 添加按钮初始化数据 */
        initTreesToAdd: function () {
            // 加载部门树
            $.get(baseURL + "depts/add/" + vm.currentUser.userId, function (response) {
                deptTree = $.fn.zTree.init($("#deptTree"), deptTreeSetting, response);
            });

            // 加载用户树
            $.get(baseURL + "users?token=" + garnetToken + "&page=1&limit=1000", function (response) {
                userTree = $.fn.zTree.init($("#userTree"), userTreeSetting, response.list);
                userTree.expandAll(true);
            });

            // 加载角色树
            $.get(baseURL + "roles?token=" + garnetToken + "&page=1&limit=1000", function (response) {
                roleTree = $.fn.zTree.init($("#roleTree"), roleTreeSetting, response.list);
                roleTree.expandAll(true);
            });
        },
        /** 更新按钮初始化数据 */
        initTreesToUpdate: function (deptId) {
            // 加载部门树  封装ajax 请求，防止数据异步导致页面数据错乱
            $.get(baseURL + "depts/add/" + vm.currentUser.userId, function (response) {
                deptTree = $.fn.zTree.init($("#deptTree"), deptTreeSetting, response);

                // 加载用户树
                $.get(baseURL + "users?token=" + garnetToken + "&page=1&limit=1000", function (response) {
                    userTree = $.fn.zTree.init($("#userTree"), userTreeSetting, response.list);
                    userTree.expandAll(true);

                    // 加载角色树
                    $.get(baseURL + "roles?token=" + garnetToken + "&page=1&limit=1000", function (response) {
                        roleTree = $.fn.zTree.init($("#roleTree"), roleTreeSetting, response.list);
                        roleTree.expandAll(true);

                        // 获取部门信息
                        vm.getDeptInfo(deptId);
                    });
                });
            });
        },
        /** 根据ID获取部门信息 */
        getDeptInfo: function (deptId) {
            $.get(baseURL + "dept/" + deptId, function (response) {
                vm.dept.deptId = response.deptId;
                vm.dept.appId = response.appId;
                vm.dept.tenantId = response.tenantId;
                vm.dept.name = response.name;
                vm.tenantList.selectedTenant = response.tenantId;
                vm.appList.selectedApp = response.appId;
                vm.dept.parentName = response.parentName;
                vm.dept.parentDeptId = response.parentDeptId;
                vm.dept.orderNum = response.orderNum;
                // 勾选已有用户
                $.each(response.userIdLList, function (index, item) {
                    var node = userTree.getNodeByParam("userId", item);
                    userTree.checkNode(node, true, false);
                });
                // 勾选已有角色
                $.each(response.roleIdLList, function (index, item) {
                    var node = roleTree.getNodeByParam("roleId", item);
                    roleTree.checkNode(node, true, false);
                });
            });
        },
        /** 初始化部门信息 */
        initDeptInfo: function () {
            // 获取当前用户信息
            $.getJSON(baseURL + "token/userInfo?token=" + garnetToken, function (response) {
                vm.currentUser = response;
                // 初始化表格数据
                var columns = Dept.initColumn();
                var table = new TreeTable(Dept.id, baseURL + "depts/" + response.userId, columns);
                table.setExpandColumn(2);
                table.setIdField("deptId");
                table.setCodeField("deptId");
                table.setParentCodeField("parentDeptId");
                table.setExpandAll(false);
                table.init();
                Dept.table = table;
            });
        },
        /** 租户列表onchange 事件*/
        selectTenant: function () {
            vm.dept.tenantId = vm.tenantList.selectedTenant;
        },
        /** 应用列表onchange 事件*/
        selectApp: function () {
            vm.dept.appId = vm.appList.selectedApp;
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
        }
    },
    /**  初始化页面时执行该方法 */
    created: function () {
        this.initDeptInfo();
    }
});

/**  获取选择的部门ID */
function getDeptId() {
    var selected = $('#deptTable').bootstrapTreeTable('getSelections');
    if (selected.length === 0) {
        swal("请选择一条记录!", "", "warning");
        return false;
    } else {
        return selected[0].id;
    }
}