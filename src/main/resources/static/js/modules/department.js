/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

var Department = {
    id: "departmentTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
// Department.initColumn = function () {
//     return [
//         {field: 'selectItem', radio: true},
//         {title: '部门ID', field: 'departmentId', CanHide: 0, Visible: 0, align: 'center', valign: 'middle', width: '70px'},
//         {title: '部门名称', field: 'name', align: 'center', valign: 'middle', sortable: true, width: '180px'},
//         {title: '所属租户', field: 'tenantName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
//         {title: '所属应用', field: 'appName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
//         {title: '上级部门', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
//         {title: '用户列表', field: 'userNameList', align: 'center', valign: 'middle', sortable: true, width: '150px'},
//         {title: '角色列表', field: 'roleNameList', align: 'center', valign: 'middle', sortable: true, width: '150px'},
//         {title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '60px'}];
// };
$(function () {
    /** 初始化角色列表 */
    $("#jqGrid").jqGrid({
        url: baseURL + 'departments',
        datatype: "json",
        colModel: [
            {label: '部门ID', name: 'departmentId', align: 'center', hidden: true, index: "menu_id", width: 20, key: true},
            {label: '部门名称', name: 'name', align: 'center', width: 40},
            {label: '所属租户', name: 'tenantName', align: 'center', width: 40},
            {label: '所属应用', name: 'appName', align: 'center', width: 70},
            {label: '上级部门', name: 'parentName', align: 'center', width: 70},
            {label: '用户列表', name: 'userNameList', align: 'center', width: 70},
            {label: '角色列表', name: 'roleNameList', align: 'center', width: 70},
            {label: '排序号', name: 'orderNum', align: 'center', width: 20}
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
/** 部门树 */
var departmentTree;
var departmentTreeSetting = {
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

var currentUser;

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        searchName: null,
        // 当前用户信息
        currentUser: {},
        department: {
            departmentId: null,
            parentDepartmentId: null,
            tenantId: null,
            applicationId: null,
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
            vm.reload(true);
        },
        /**  新增按钮点击事件 */
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.tenantList.selectedTenant = "";
            vm.tenantList.options = [];
            vm.appList.selectedApp = "";
            vm.appList.options = [];
            vm.department = {
                departmentId: null,
                parentDepartmentId: null,
                tenantId: null,
                applicationId: null,
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
            var departmentId = getSelectedRow();
            if (!departmentId) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.tenantList.options = [];
            vm.appList.options = [];
            vm.department.userIdList = [];
            vm.department.roleIdList = [];
            vm.getTenantList();
            vm.getAppList();
            vm.initTreesToUpdate(departmentId);

        },
        /**  删除按钮点击事件 */
        del: function () {
            var departmentIds = getSelectedRows();
            if (!departmentIds) {
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
                        url: baseURL + "department?departmentIds=" + departmentIds.toString(),
                        contentType: "application/json",
                        dataType: "",
                        success: function (result) {
                            if (!result.message) {
                                swal("删除成功!", "", "success");
                                vm.reload(false);
                            } else {
                                swal("删除失败!", result.message, "error");
                            }
                        }
                    });
                });
        },
        /**  新增或更新确认 */
        saveOrUpdate: function () {

            if(vm.department.name === null || vm.department.name === ""){
                alert("部门名称不能为空");
                return;
            }

            // 获取用户树选择的用户
            var userNodes = userTree.getCheckedNodes(true);
            var userIdList = [];
            for (var i = 0; i < userNodes.length; i++) {
                userIdList.push(userNodes[i].userId);
            }
            vm.department.userIds = userIdList.join(",");

            // 获取角色树选择的角色
            var roleNodes = roleTree.getCheckedNodes(true);
            var roleIdList = [];
            for (var k = 0; k < roleNodes.length; k++) {
                roleIdList.push(roleNodes[k].roleId);
            }
            vm.department.roleIds = roleIdList.join(",");

            $.ajax({
                type: vm.department.departmentId === null ? "POST" : "PUT",
                url: baseURL + "department",
                contentType: "application/json",
                data: JSON.stringify(vm.department),
                dataType: "",
                success: function () {
                    vm.reload(false);
                    swal("操作成功!", "", "success");
                }
            });
        },
        /**  部门树点击事件 */
        departmentTree: function () {
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择部门",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#departmentLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = departmentTree.getSelectedNodes();
                    //选择上级部门
                    vm.department.parentDepartmentId = node[0].departmentId;
                    vm.department.parentName = node[0].name;
                    layer.close(index);
                }
            });
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
                postData: {searchName: vm.searchName},
                page: page
            }).trigger("reloadGrid");
        },
        /** 添加按钮初始化数据 */
        initTreesToAdd: function () {
            // 加载部门树
            // $.get(baseURL + "departments/add/" + currentUser.userId, function (response) {
            $.get(baseURL + "departments?page=1&limit=1000", function (response) {
                departmentTree = $.fn.zTree.init($("#departmentTree"), departmentTreeSetting, response.list);
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
        initTreesToUpdate: function (departmentId) {
            // 加载部门树  封装ajax 请求，防止数据异步导致页面数据错乱
            // $.get(baseURL + "departments/add/" + currentUser.userId, function (response) {
            $.get(baseURL + "departments?page=1&limit=1000" + currentUser.userId, function (response) {
                departmentTree = $.fn.zTree.init($("#departmentTree"), departmentTreeSetting, response.list);

                // 加载用户树
                $.get(baseURL + "users?token=" + garnetToken + "&page=1&limit=1000", function (response) {
                    userTree = $.fn.zTree.init($("#userTree"), userTreeSetting, response.list);
                    userTree.expandAll(true);

                    // 加载角色树
                    $.get(baseURL + "roles?token=" + garnetToken + "&page=1&limit=1000", function (response) {
                        roleTree = $.fn.zTree.init($("#roleTree"), roleTreeSetting, response.list);
                        roleTree.expandAll(true);

                        // 获取部门信息
                        vm.getDepartmentInfo(departmentId);
                    });
                });
            });
        },
        /** 根据ID获取部门信息 */
        getDepartmentInfo: function (departmentId) {
            $.get(baseURL + "department/" + departmentId, function (response) {
                vm.department.departmentId = response.departmentId;
                vm.department.applicationId = response.applicationId;
                vm.department.tenantId = response.tenantId;
                vm.department.name = response.name;
                vm.tenantList.selectedTenant = response.tenantId;
                vm.appList.selectedApp = response.applicationId;
                vm.department.parentName = response.parentName;
                vm.department.parentDepartmentId = response.parentDepartmentId;
                vm.department.orderNum = response.orderNum;
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
        initDepartmentInfo: function () {
            // 获取当前用户信息
            $.getJSON(baseURL + "token/userInfo?token=" + garnetToken, function (response) {
                currentUser = response;
            });
        },
        /** 租户列表onchange 事件*/
        selectTenant: function () {
            vm.department.tenantId = vm.tenantList.selectedTenant;
        },
        /** 应用列表onchange 事件*/
        selectApp: function () {
            vm.department.applicationId = vm.appList.selectedApp;
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
        this.initDepartmentInfo();
    }
});