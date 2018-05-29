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
            {label: '角色ID', name: 'role.id', align: 'center', hidden: true, index: "id", width: 20, key: true ,sortable: false},
            {label: '角色名称', name: 'role.name', align: 'center', width: 70 ,sortable: false},
            // {label: '所属租户', name: 'tenantName', align: 'center', width: 70 ,sortable: false},
            // {label: '所属应用', name: 'applicationName', align: 'center', width: 70 ,sortable: false},
            {label: '类型', name: 'type', align: 'center', width: 70 ,sortable: false ,sortable: false},
            {label: '组列表', name: 'groupNames', align: 'center', width: 140 ,sortable: false},
            {label: '权限列表', name: 'permissionNames', align: 'center', width: 140 ,sortable: false},
            {label: '创建时间', name: 'role.createdTime', align: 'center', formatter:timeFormat, width: 120 ,sortable: false},
            {label: '更新时间', name: 'role.modifiedTime', align: 'center', formatter:timeFormat, width: 120 ,sortable: false},
            {label: '更改人', name: 'role.updatedByUserName', align: 'center', width: 100 ,sortable: false}
            // {label: '创建时间', name: 'createTime', align: 'center', width: 90}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: false,
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
            userId: userId,
            token: accessToken
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

//时间戳 转 Y-M-D
function timeFormat(cellvalue, options, row) {
    var date = new Date(cellvalue);
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'; // 0-11月，0代表1月
    var D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate());
    var h = (date.getHours() < 10 ? '0' + (date.getHours()) + ':' : date.getHours() + ':');
    var m = (date.getMinutes() < 10 ? '0' + (date.getMinutes()) + ':' : date.getMinutes() + ':');
    var s = (date.getSeconds() < 10 ? '0' + (date.getSeconds()) : date.getSeconds());
    return Y + M + D + "  " + h + m + s;
}

function getType(cellvalue, options, row) {
    if (cellvalue == null || cellvalue == 0) {
        return "应用";
    } else {
        return "租户";
    }
}

/** 部门结构树 */
var groupTree;
var groupTreeSetting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "groupId",
            pIdKey: "parentgroupId",
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
        showType: true, // 显示类型
        showByType: true, //根据选择类型选择显示租户级、应用级
        showTenant: false, //显示租户
        showApplication: false, //显示应用
        searchName: null,
        showList: true,
        title: null,
        role: {
            id: null,
            tenantId: null,
            applicationId: null,
            name: null,
            remark: null,
            groupIds: null,
            permissionIds: null,
            updatedByUserName: null
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
        // 类型列表数据
        typeList: {
            selectedType: "",
            options: [
                {
                    id : "1",
                    name : "租户"
                },
                {
                    id : "2",
                    name : "应用"
                },
                {
                    id : "3",
                    name : "租户+应用"
                }]
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
            vm.showType = true;
            vm.showTenant = false;
            vm.showApplication = false;
            vm.title = "新增";
            vm.tenantList.selectedTenant = "";
            vm.tenantList.options = [];
            vm.role.groupIds = [];
            vm.role.permissionIds = [];
            vm.groupIds = [];
            vm.permissionIds = [];
            vm.appList.selectedApp = "";
            vm.appList.options = [];
            vm.typeList.selectedType = "";
            vm.role = {
                id: null,
                tenantId: null,
                applicationId: null,
                name: null,
                remark: null,
                groupIds: null,
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
            vm.showList = false;
            vm.showType = false;
            vm.hiddenByType = false;
            vm.title = "修改";
            // vm.appList.selectedApp = "";
            vm.tenantList.options = [];
            vm.appList.options = [];
            vm.role.groupIdList = [];
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
            window.parent.swal({
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
                        url: baseURL + "roles?ids=" + roleIds.toString(),
                        contentType: "application/json",
                        dataType: "",
                        success: function () {
                            window.parent.swal("删除成功!", "", "success");
                            vm.reload(false);
                        },
                        error: function (result) {
                            window.parent.swal("删除失败!", getExceptionMessage(result), "error");
                        }
                    });
                });
        },
        /**  新增或更新确认 */
        saveOrUpdate: function () {

            var obj = new Object();
            vm.role.updatedByUserName = localStorage.getItem("userName");
            obj.role = vm.role;

            // 获取部门树选择的部门
            var groups = groupTree.getCheckedNodes(true);

            var groupIdList = [];
            for (var i = 0; i < groups.length; i++) {
                groupIdList.push(groups[i].id);
            }
            vm.role.groupIds = groupIdList.join(",");
            // 获取权限树选择的权限
            var permissions = permissionTree.getCheckedNodes(true);
            var permissionIdList = [];
            for (var i = 0; i < permissions.length; i++) {
                permissionIdList.push(permissions[i].id);
            }
            vm.role.permissionIds = permissionIdList.join(",");

            obj.groupIds = groupIdList;
            obj.permissionIds = permissionIdList;

            if(vm.role.name == null || $.trim(vm.role.name) == "") {
                window.parent.swal("", "角色名称不能为空", "warning");
                return;
            }

            if ((vm.role.tenantId == null || $.trim(vm.role.tenantId) =="") && (vm.role.applicationId == null || $.trim(vm.role.applicationId) == "")) {
                window.parent.swal("", "请在选择类型后，选择租户或应用", "warning");
                    return;
            }

            if (vm.typeList.selectedType != null && vm.typeList.selectedType != "") {
                var selectType = vm.typeList.selectedType;
                if (selectType == 1) {
                    //租户级
                    if (vm.role.tenantId == null || $.trim(vm.role.tenantId) == "") {
                        window.parent.swal("", "租户不能为空", "warning");
                        return;
                    }
                } else if (selectType == 2) {
                    //应用级
                    if (vm.role.applicationId == null || $.trim(vm.role.applicationId) == "") {
                        window.parent.swal("", "应用不能为空", "warning");
                        return;
                    }
                } else if (selectType == 3) {
                    if ((vm.role.tenantId == null || $.trim(vm.role.tenantId) =="") || (vm.role.applicationId == null || $.trim(vm.role.applicationId) == "")) {
                        window.parent.swal("", "租户和应用都不能为空", "");
                        return;
                    }
                }
            }

            if(permissionIdList == null || permissionIdList.length == 0) {
                window.parent.swal("", "请选择权限", "warning");
                return;
            }

            if (vm.role.name.length > 30) {
                window.parent.swal("", "角色名称长度不能大于30", "");
                return;
            }

            $.ajax({
                type: vm.role.id === null ? "POST" : "PUT",
                url: baseURL + "roles",
                contentType: "application/json",
                data: JSON.stringify(obj),
                dataType: '',
                success: function () {
                    vm.reload(false);
                    window.parent.swal("操作成功!", "", "success");
                },
                error: function (response) {
                    window.parent.swal("", getExceptionMessage(response), "error");
                }
            });
        },
        /** 添加按钮初始化数据 */
        initTreesToAdd: function () {
            //加载部门树
            // $.get(baseURL + "groups/" + currentUser.userId, function (response) {
            $.get(baseURL + "groups?page=1&limit=1000", function (response) {
                groupTree = $.fn.zTree.init($("#groupTree"), groupTreeSetting, []);
                groupTree.expandAll(true);
            });
            //加载权限树
            // $.get(baseURL + "permissions?page=1&limit=1000", function (response) {
            $.get(baseURL + "permissions?page=1&limit=1000", function (response) {
                permissionTree = $.fn.zTree.init($("#permissionTree"), permissionTreeSetting, []
                );
                permissionTree.expandAll(true);
            })
            // $.get(baseURL + "permissions/applicationId/" + vm.appList.selectedApp, function (response) {
            //     permissionTree = $.fn.zTree.init($("#permissionTree"), permissionTreeSetting, response);
            //     permissionTree.expandAll(true);
            // })
        },
        /** 更新按钮初始化数据 */
        initTreesToUpdate: function (roleId) {
            vm.getRoleById(roleId);
            vm.initTree();
        },
        /** 通过id 得到一个role对象 */
        getRoleById: function (roleId) {
            $.get(baseURL + "roles/" + roleId, function (response) {

                response = response.data;
                vm.role.id = response.role.id;
                vm.role.applicationId = response.role.applicationId;
                vm.role.tenantId = response.role.tenantId;
                vm.role.name = response.role.name;
                vm.role.remark = response.role.remark;
                vm.role.groupIds = response.groupIds;
                vm.role.permissionIds = response.permissionIds;
                // vm.tenantList.selectedTenant = response.role.tenantId;
                // vm.appList.selectedApp = response.role.applicationId;

                var selectedApp = response.role.applicationId;
                var selectedTenant = response.role.tenantId;
                var permissionUrl;
                var groupUrl;

                if (selectedTenant == null || selectedTenant == 0) {
                    //应用级
                    vm.appList.selectedApp = selectedApp;
                    vm.tenantList.selectedTenant = "";
                    vm.showTenant = false;
                    vm.showApplication = true;
                    // permissionUrl = baseURL + "permissions/applicationId/" + selectedApp;
                    // groupUrl = baseURL + "groups/applicationId/" + selectedApp;
                } else if (selectedApp == null || selectedApp == 0) {
                    //租户级
                    vm.tenantList.selectedTenant = selectedTenant;
                    vm.appList.selectedApp = "";
                    vm.showApplication = false;
                    vm.showTenant = true;
                    // permissionUrl = baseURL + "permissions/tenantId/" + selectedTenant;
                    // groupUrl = baseURL + "groups/tenantId/" + selectedTenant;
                } else {
                    //租户+应用
                    vm.appList.selectedApp = selectedApp;
                    vm.tenantList.selectedTenant = selectedTenant;
                    vm.showApplication = true;
                    vm.showTenant = true;
                    // permissionUrl = baseURL + "permissions/tenantId/" + selectedTenant;
                    // groupUrl = baseURL + "groups/tenantId/" + selectedTenant;
                }

                //加载组树
                $.get(baseURL + "groups/byparams?tenantId=" + vm.tenantList.selectedTenant + "&applicationId=" + vm.appList.selectedApp, function (response) {
                // $.get(groupUrl, function (response) {
                    groupTree = $.fn.zTree.init($("#groupTree"), groupTreeSetting, response);
                    groupTree.expandAll(true);

                    $.each(vm.role.groupIds, function (index, item) {
                        var node = groupTree.getNodeByParam("id", item);
                        groupTree.checkNode(node, true, false);
                    });
                });
                //加载权限树
                $.get(baseURL + "permissions/byparams?tenantId=" + vm.tenantList.selectedTenant + "&applicationId=" + vm.appList.selectedApp, function (response) {
                // $.get(permissionUrl, function (response) {
                    permissionTree = $.fn.zTree.init($("#permissionTree"), permissionTreeSetting, response);
                    permissionTree.expandAll(true);
                    $.each(vm.role.permissionIds, function (index, item) {
                        var node = permissionTree.getNodeByParam("id", item);
                        permissionTree.checkNode(node, true, false);
                    });
                });
            });
        },
        initTree:function () {
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
            vm.role.applicationId = vm.appList.selectedApp;
            //重新加载权限
            vm.roadPermissionTree();
        },
        /** 应用列表onchange 事件*/
        selectApp: function () {
            vm.role.tenantId = vm.tenantList.selectedTenant;
            vm.role.applicationId = vm.appList.selectedApp;
            vm.roadPermissionTree();
            // if (vm.tenantList.selectedTenant == null && $.trim(vm.tenantList.selectedTenant) == "") {
            //     vm.roadPermissionTreeByApp();
            // }
        },
        /** 类型列表onchange 事件*/
        selectType: function () {
            vm.tenantList.selectedTenant = "";
            vm.appList.selectedApp = "";
            groupTree = $.fn.zTree.init($("#groupTree"), groupTreeSetting, []);
            permissionTree = $.fn.zTree.init($("#permissionTree"), permissionTreeSetting, []);

            var selectedType = vm.typeList.selectedType;
            if (selectedType == 1) {
                //租户级
                vm.role.applicationId = null;
                vm.role.tenantId = null;
                vm.showTenant = true;
                vm.showApplication = false;
            } else if (selectedType == 2){
                //应用级
                vm.role.tenantId = null;
                vm.role.applicationId = null;
                vm.showTenant = false;
                vm.showApplication = true;
            } else {
                //租户+应用
                vm.role.tenantId = null;
                vm.role.applicationId = null;
                vm.showTenant = true;
                vm.showApplication = true;
            }
        },
        /**  获取租户列表 */
        getTenantList: function () {
            $.get(baseURL + "tenants?page=1&limit=1000&userId=" + userId, function (response) {
                $.each(response.list, function (index, item) {
                    vm.tenantList.options.push(item);
                })
            });
        },
        /**  获取应用列表 */
        getAppList: function () {
            $.get(baseURL + "applications?page=1&limit=1000&userId=" + userId, function (response) {
                $.each(response.list, function (index, item) {
                    vm.appList.options.push(item);
                })
            });
        },
        /** 加载组 */
        roadGroupTree:function () {
            // $.get(baseURL + "groups/tenantId/" + vm.tenantList.selectedTenant, function (response) {
            $.get(baseURL + "groups/byparams?tenantId=" + vm.tenantList.selectedTenant + "&applicationId=" + vm.appList.selectedApp, function (response) {
                groupTree = $.fn.zTree.init($("#groupTree"), groupTreeSetting, response);
                groupTree.expandAll(true);
            })

        },
        /** 根据应用重新加载组 */
        roadGroupTreeByApp:function () {
            $.get(baseURL + "groups/applicationId/" + vm.appList.selectedApp, function (response) {
                groupTree = $.fn.zTree.init($("#groupTree"), groupTreeSetting, response);
                groupTree.expandAll(true);
            })

        },
        /** 加载权限树 */
        roadPermissionTree:function () {
            //加载权限树
            // $.get(baseURL + "permissions/tenantId/" + vm.tenantList.selectedTenant, function (response) {
            $.get(baseURL + "permissions/byparams?tenantId=" + vm.tenantList.selectedTenant + "&applicationId=" + vm.appList.selectedApp, function (response) {
                permissionTree = $.fn.zTree.init($("#permissionTree"), permissionTreeSetting, response);
                permissionTree.expandAll(true);

                //加载组
                vm.roadGroupTree();
            })
        },
        /** 根据应用重新加载权限树 */
        roadPermissionTreeByApp:function () {
            //加载权限树
            $.get(baseURL + "permissions/applicationId/" + vm.appList.selectedApp, function (response) {
                permissionTree = $.fn.zTree.init($("#permissionTree"), permissionTreeSetting, response);
                permissionTree.expandAll(true);

                //根据应用加载组
                vm.roadGroupTreeByApp();
            })
        }
    },
    /**  初始化页面时执行该方法 */
    created: function () {
        // this.getCurrentUser();
    }
});
