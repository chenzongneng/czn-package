/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */
var addOrUpdate = 0; // 保存或者更新按钮点击事件 0 为新增 , 1 为 更新

$(function () {
    /** 初始化用户列表 */
    $("#jqGrid").jqGrid({
        url: baseURL + 'users',
        datatype: "json",
        colModel: [
            {label: '用户ID', name: 'userId', hidden: true, index: "user_id", width: 45, key: true},
            {label: '用户名', name: 'userName', width: 75},
            {label: '所属租户', name: 'tenantName', width: 75},
            {label: '所属应用', name: 'appName', width: 75},
            {label: '所属部门', name: 'deptNameList', width: 75},
            {label: '邮箱', name: 'email', width: 90},
            {label: '手机号', name: 'mobile', width: 80},
            {
                label: '状态', name: 'status', width: 80, formatter: function (value, options, row) {
                return value === 0 ?
                    '<span class="label label-danger">禁用</span>' :
                    '<span class="label label-success">正常</span>';
            }
            },
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
/** 部门树 */
var deptTree;

/** ztree 配置*/
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentDeptId",
            rootPId: -1
        },
        key: {
            url: "nourl"
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
        // 用户信息
        user: {
            userId: null,
            appId: null,
            tenantId: null,
            deptIds: null,
            userName: null,
            password: null,
            email: null,
            mobile: null,
            status: null
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
        },
        // 当前用户信息
        currentUser: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            addOrUpdate = 0;
            vm.showList = false;
            vm.title = "新增";
            vm.tenantList.selectedTenant = "";
            vm.tenantList.options = [];
            vm.appList.selectedApp = "";
            vm.appList.options = [];
            vm.user = {
                userId: null,
                userName: null,
                tenantId: null,
                appId: null,
                password: null,
                email: null,
                mobile: null,
                status: 1,
                deptIds: null
            };
            vm.initDeptTreeToAdd();
            vm.getTenantList();
            vm.getAppList();
        },
        update: function () {
            var userId = getSelectedRow();
            if (userId == null) {
                return;
            }
            addOrUpdate = 1;
            vm.showList = false;
            vm.title = "修改";
            vm.tenantList.options = [];
            vm.appList.options = [];
            vm.user.deptIdList = [];
            vm.initDeptTreeToUpdate(userId);
            vm.getTenantList();
            vm.getAppList();
        },
        del: function () {
            var userIds = getSelectedRows();
            if (userIds == null) {
                return;
            }
            if(userIds.includes(vm.currentUser.userId.toString())){
                swal("您不能删除自己!", "", "error");
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
                        url: baseURL + "user?userIds=" + userIds.toString(),
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
        saveOrUpdate: function () {
            var nodes = deptTree.getCheckedNodes(true);
            var deptIdList = new Array();
            for (var i = 0; i < nodes.length; i++) {
                deptIdList.push(nodes[i].deptId);
            }
            vm.user.deptIds = deptIdList.join(",");
            if (!vm.checkValue()) {
                return;
            }
            $.ajax({
                type: addOrUpdate === 0 ? "POST" : "PUT",
                url: baseURL + "user",
                contentType: "application/json",
                data: JSON.stringify(vm.user),
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
        initDeptTreeToAdd: function () {
            //加载部门树
            $.get(baseURL + "depts/add/" + vm.currentUser.userId, function (r) {
                deptTree = $.fn.zTree.init($("#deptTree"), setting, r);
                deptTree.expandAll(true);
            })
        },
        initDeptTreeToUpdate: function (userId) {
            //加载部门树
            $.get(baseURL + "depts/add/" + vm.currentUser.userId, function (r) {
                deptTree = $.fn.zTree.init($("#deptTree"), setting, r);
                deptTree.expandAll(true);
                vm.getUser(userId);
            })
        },
        getUser: function (userId) {
            $.get(baseURL + "user/" + userId, function (response) {
                vm.user.userId = response.userId;
                vm.user.appId = response.appId;
                vm.user.tenantId = response.tenantId;
                vm.user.userName = response.userName;
                vm.user.password = null;
                vm.user.email = response.email;
                vm.user.mobile = response.mobile;
                vm.user.status = response.status;
                vm.tenantList.selectedTenant = response.tenantId;
                vm.appList.selectedApp = response.appId;
                $.each(response.deptIdList, function (index, item) {
                    var node = deptTree.getNodeByParam("deptId", item);
                    deptTree.checkNode(node, true, false);
                });
            });
        },
        /** 查询当前用户信息 */
        getCurrentUser: function () {
            $.getJSON(baseURL + "token/userInfo?token=" + garnetToken, function (response) {
                vm.currentUser = response;
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'searchName': vm.searchName},
                page: page
            }).trigger("reloadGrid");
        },
        checkValue: function () {
            var emailReg = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
            var telReg = /^1[34578]\d{9}$/;
            if (!vm.checkInput(vm.user.userName, '用户名', false)) {
                return false;
            }
            if (!vm.checkInput(vm.user.password, '密码', true)) {
                return false;
            }
            if (vm.user.email && !emailReg.test(vm.user.email)) {
                swal("邮箱格式不正确!", "", "warning");
                return false;
            }
            if (vm.user.mobile && !telReg.test(vm.user.mobile)) {
                swal("电话号码格式不正确!", "", "warning");
                return false;
            }
            return true;
        },
        checkInput: function (value, name, isPassword) {
            var chineseReg = /^[\u4e00-\u9fa5]{0,}$/; // 中文正则
            var specialReg = /^(?!_)(?!.*?_$)[-a-zA-Z0-9_\u4e00-\u9fa5]+$/;//非特殊符号的正则表达式

            if (!(isPassword && addOrUpdate === 1)) {
                if (!value) {
                    swal({
                        title: name + '不能为空！',
                        type: 'warning',
                        confirmButtonText: '确定',
                        allowOutsideClick: false
                    });
                    return false;
                }
                if (chineseReg.test(value)) {
                    swal({
                        title: name + '不能为中文！',
                        type: 'warning',
                        confirmButtonText: '确定',
                        allowOutsideClick: false
                    });
                    return false;
                }
                if (!specialReg.test(value)) {
                    swal({
                        title: name + '只能使用英文、数字、下划线或者连字符！',
                        type: 'warning',
                        confirmButtonText: '确定',
                        allowOutsideClick: false
                    });
                    return false;
                }
                if (value.length < 4 || value.length > 20) {
                    swal({
                        title: name + '的长度只能在4-20！',
                        type: 'warning',
                        confirmButtonText: '确定',
                        allowOutsideClick: false
                    });
                    return false;
                }
            }
            return true;
        },
        /** 租户列表onchange 事件*/
        selectTenant: function () {
            vm.user.tenantId = vm.tenantList.selectedTenant;
        },
        /** 应用列表onchange 事件*/
        selectApp: function () {
            vm.user.appId = vm.appList.selectedApp;
        },
        getTenantList: function () {
            $.get(baseURL + "tenants?page=1&limit=1000", function (response) {
                $.each(response.list, function (index, item) {
                    vm.tenantList.options.push(item);
                })
            });
        },
        getAppList: function () {
            $.get(baseURL + "applications?page=1&limit=1000", function (response) {
                $.each(response.list, function (index, item) {
                    vm.appList.options.push(item);
                })
            });
        }
    },
    created: function () {
        this.getCurrentUser();
    }
});