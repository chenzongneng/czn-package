/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

var Group = {
    id: "groupTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
// Group.initColumn = function () {
//     return [
//         {field: 'selectItem', radio: true},
//         {title: '部门ID', field: 'groupId', CanHide: 0, Visible: 0, align: 'center', valign: 'middle', width: '70px'},
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
        url: baseURL + 'groups?userId=' + userId,
        datatype: "json",
        colModel: [
            {label: '组id', name: 'group.id', align: 'center', hidden: true, index: "id", width: 20, key: true ,sortable: false ,sortable: false},
            {label: '组名称', name: 'group.name', align: 'center', width: 100 ,sortable: false ,sortable: false},
            {label: '类型', name: 'type', align: 'center', width: 80 ,sortable: false ,sortable: false},
            {label: '创建时间', name: 'group.createdTime', align: 'center', formatter:timeFormat, width: 130 ,sortable: false ,sortable: false},
            {label: '更新时间', name: 'group.modifiedTime', align: 'center',formatter:timeFormat, width: 130 ,sortable: false ,sortable: false},
            {label: '更改人', name: 'group.updatedByUserName', align: 'center', width: 80 ,sortable: false}
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
        // postData: {
        //     token: garnetToken
        // },
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

/** 部门树 */
var groupTree;
var groupTreeSetting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id"
            // pIdKey: "parentGroupId",
            // rootPId: -1
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
            idKey: "id"
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
            idKey: "id"
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

var tenantSearchList = {
    searchTenant: "",
    options: [
        {
            id : "",
            name : "全部"
        }
    ]
};

var appSearchList = {
    searchApp: "",
    options: [
        {
            id : "",
            name : "全部"
        }
    ]
};

var vm = new Vue({
    el: '#rrapp',
    data: {
        // isGroupNameEditable: null,
        // isGroupSelectAppEditable: null,
        // isGroupSelectTenantEditable: null,
        // isGroupSelectUserEditable: null,
        // isGroupSelectRoleEditable: null,
        groupUpdateButton: false,

        typesEditAble: false, //类型选项，是否可以编辑，默认不可编辑
        showType: true, // 显示类型
        showByType: true, //根据选择类型选择显示租户级、应用级
        hiddenByType: true,
        showTenant: false, //显示租户下拉框
        showApplication: false, //显示应用下拉框
        showList: true,
        title: null,
        searchName: null,
        // 当前用户信息
        currentUser: {},
        group: {
            groupId: null,
            parentGroupId: null,
            tenantId: null,
            applicationId: null,
            name: null,
            parentName: null,
            orderNum: 0,
            userIds: null,
            roleIds: null,
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
        }
    },
    methods: {
        /**  查询按钮点击事件 */
        query: function () {
            vm.reload(true);
        },
        /**  新增按钮点击事件 */
        add: function () {

            // vm.isGroupNameEditable = true;
            // vm.isGroupSelectAppEditable = true;
            // vm.isGroupSelectTenantEditable = true;
            // vm.isGroupSelectUserEditable = true;
            // vm.isGroupSelectRoleEditable = true;
            vm.groupUpdateButton = true;

            vm.typesEditAble = true;
            vm.showList = false;
            vm.showTenant = false;
            vm.showApplication = false;
            vm.showType = true;
            vm.title = "新增";
            vm.tenantList.selectedTenant = "";
            vm.tenantList.options = [];
            vm.appList.selectedApp = "";
            vm.appList.options = [];
            vm.typeList.selectedType = "";
            vm.group = {
                groupId: null,
                parentGroupId: null,
                tenantId: null,
                applicationId: null,
                name: null,
                parentName: null,
                orderNum: 0,
                userIds: null,
                roleIds: null
            };

            vm.initTreesToAdd();

            vm.dealTypeList();
        },
        /**  更新按钮点击事件 */
        update: function () {
            var groupId = getSelectedRow();
            if (!groupId) {
                return;
            }

            // vm.isGroupNameEditable = resources.isGroupNameEditable;
            // vm.isGroupSelectAppEditable = resources.isGroupSelectAppEditable;
            // vm.isGroupSelectTenantEditable = resources.isGroupSelectTenantEditable;
            // vm.isGroupSelectUserEditable = resources.isGroupSelectUserEditable;
            // vm.isGroupSelectRoleEditable = resources.isGroupSelectRoleEditable;
            vm.groupUpdateButton = resources.groupUpdateButton;

            vm.showList = false;
            vm.showType = true; //类型选项是否显示
            vm.showTenant = true;
            vm.showApplication = true;
            // vm.hiddenByType = false;
            vm.title = "修改";
            vm.tenantList.options = [];
            vm.appList.options = [];
            vm.group.userIdList = [];
            vm.group.roleIdList = [];
            vm.group.id = groupId;

            vm.initTreesToUpdate(groupId);
            vm.getTenantList();
            vm.getAppList();
            vm.getPermissionAction();

        },
        /**  删除按钮点击事件 */
        del: function () {
            var groupIds = getSelectedRows();

            if (!groupIds) {
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
                        url: baseURL + "groups?ids=" + groupIds.toString(),
                        contentType: "application/json",
                        dataType: "",
                        success: function (result) {
                            if (!result.message) {
                                window.parent.swal("删除成功!", "", "success");
                                vm.reload(false);
                            } else {
                                window.parent.swal("删除失败!", result.message, "error");
                            }
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
            vm.group.updatedByUserName = localStorage.getItem("userName");
            obj.group = vm.group;
            //change by ming
            var LoginUserId = localStorage.getItem("userId");

            if(vm.group.name == null || $.trim(vm.group.name) == ""){
                window.parent.swal("", "组名称不能为空", "warning");
                return;
            }

            if (vm.group.name.length > 30) {
                window.parent.swal("", "组名称不能为空", "warning");
            }

            if((vm.group.tenantId == null || $.trim(vm.group.tenantId) == "") && (vm.group.applicationId == null || $.trim(vm.group.applicationId) == "")){
                window.parent.swal("", "请在选择类型后，选择租户或应用", "warning");
                return;
            }

            if (vm.typeList.selectedType != null && vm.typeList.selectedType != "") {
                var selectType = vm.typeList.selectedType;
                if (selectType == 1) {
                    if (vm.group.tenantId == null || $.trim(vm.group.tenantId) == "") {
                        window.parent.swal("", "租户不能为空", "warning");
                        return;
                    }
                } else if (selectType == 2) {
                    if (vm.group.applicationId == null || $.trim(vm.group.applicationId) == "") {
                        window.parent.swal("", "应用不能为空", "warning");
                        return;
                    }
                } else if (selectType == 3) {
                    if ((vm.group.tenantId == null || $.trim(vm.group.tenantId) == "") || (vm.group.applicationId == null || $.trim(vm.group.applicationId) == "")) {
                        window.parent.swal("", "租户和应用都不能为空", "warning");
                        return;
                    }
                }
            }
            // 获取用户树选择的用户
            var userNodes = userTree.getCheckedNodes(true);
            var userIdList = [];
            for (var i = 0; i < userNodes.length; i++) {
                userIdList.push(userNodes[i].id);

            }
            vm.group.userIds = userIdList.join(",");

            // 获取角色树选择的角色
            var roleNodes = roleTree.getCheckedNodes(true);
            var roleIdList = [];
            for (var k = 0; k < roleNodes.length; k++) {
                roleIdList.push(roleNodes[k].id);
            }
            vm.group.roleIds = roleIdList.join(",");


            if(roleIdList == null || roleIdList.length == 0){
                window.parent.swal("", "角色不能为空", "warning");
                return;
            }

            if(userIdList == null || userIdList.length == 0){
                window.parent.swal("", "用户不能为空", "warning");
                return;
            }


            obj.userIds = userIdList;
            obj.roleIds = roleIdList;

            $.ajax({
                type: vm.group.groupId === null ? "POST" : "PUT",
                url: baseURL + "groups?userId=" + userId,
                contentType: "application/json",
                data: JSON.stringify(obj),
                dataType: "",
                success: function () {
                    vm.reload(false);
                    window.parent.swal("操作成功!", "", "success");
                },
                error: function (result) {
                    window.parent.swal("操作失败!", getExceptionMessage(result), "error");
                }
            });
        },
        /**  部门树点击事件 */
        groupTree: function () {
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择部门",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#groupLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = groupTree.getSelectedNodes();
                    //选择上级部门
                    vm.group.parentGroupId = node[0].groupId;
                    vm.group.parentName = node[0].name;
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
                postData: {
                    searchName: vm.searchName,
                    applicationId: appSearchList.searchApp,
                    tenantId: tenantSearchList.searchTenant
                },
                page: page
            }).trigger("reloadGrid");
        },
        /** 添加按钮初始化数据 */
        initTreesToAdd: function () {
            //加载用户树
            $.get(baseURL + "usertree?token=" + accessToken + "&page=1&limit=1000", function (response) {
                userTree = $.fn.zTree.init($("#userTree"), userTreeSetting, []);
                userTree.expandAll(true);
            });

            // 加载角色树
            $.get(baseURL + "/roletree?token=" + accessToken, function (response) {
                roleTree = $.fn.zTree.init($("#roleTree"), roleTreeSetting, []);
                roleTree.expandAll(true);
            });
        },
        /** 更新按钮初始化数据 */
        initTreesToUpdate: function (groupId) {
            // 获取部门信息
            vm.getGroupInfo(groupId);
        },
        /** 根据ID获取部门信息 */
        getGroupInfo: function (groupId) {
            $.get(baseURL + "groups/" + groupId, function (response) {

                vm.group.groupId = response.data.group.groupId;
                vm.group.applicationId = response.data.group.applicationId;
                vm.group.tenantId = response.data.group.tenantId;
                vm.group.name = response.data.group.name;
                // vm.tenantList.selectedTenant = response.data.group.tenantId;
                // vm.appList.selectedApp = response.data.group.applicationId;
                vm.group.parentName = response.data.group.parentName;
                vm.group.parentGroupId = response.data.group.parentGroupId;
                vm.group.orderNum = response.data.group.orderNum;

                var userUrl;
                var roleUrl;
                var selectedTenant = response.data.group.tenantId;
                var selectedApp = response.data.group.applicationId;
                if (selectedTenant == null || selectedTenant == 0) {
                    //应用级
                    vm.typeList.selectedType = "2";
                    vm.appList.selectedApp = selectedApp;
                    vm.tenantList.selectedTenant = "";
                    vm.showApplication = true;
                    vm.showTenant = false;
                    // userUrl = baseURL + "users/applicationId/" + vm.appList.selectedApp;
                    // roleUrl = baseURL + "roles/applicationId/" + vm.appList.selectedApp;
                } else if (selectedApp == null || selectedApp == 0){
                    //租户级
                    vm.typeList.selectedType = "1";
                    vm.tenantList.selectedTenant = selectedTenant;
                    vm.appList.selectedApp = "";
                    vm.showTenant = true;
                    vm.showApplication = false;
                    // userUrl = baseURL + "users/tenantId/" + vm.tenantList.selectedTenant;
                    // roleUrl = baseURL + "roles/tenantId/" + vm.tenantList.selectedTenant;
                } else {
                    vm.typeList.selectedType = "3";
                    vm.appList.selectedApp = selectedApp;
                    vm.tenantList.selectedTenant = selectedTenant;
                    vm.showApplication = true;
                    vm.showTenant = true;
                    // userUrl = baseURL + "users/tenantId/" + vm.tenantList.selectedTenant;
                    // roleUrl = baseURL + "roles/tenantId/" + vm.tenantList.selectedTenant;
                }

                // console.log(JSON.stringify(response.data.group));

                // 加载用户树
                $.get(baseURL + "users/byparams?tenantId=" + selectedTenant + "&applicationId=" + selectedApp, function (response) {
                // $.get(userUrl + "?token=" + accessToken, function (response) {
                    userTree = $.fn.zTree.init($("#userTree"), userTreeSetting, response);
                    userTree.expandAll(true);

                    // 加载角色树
                    $.get(baseURL + "roles/byparams?tenantId=" + selectedTenant + "&applicationId=" + selectedApp, function (response) {
                    // $.get(roleUrl + "?token=" + accessToken, function (response) {
                        roleTree = $.fn.zTree.init($("#roleTree"), roleTreeSetting, response);
                        roleTree.expandAll(true);
                    });
                });

                // 勾选已有用户
                $.each(response.data.userIds, function (index, item) {
                    var node = userTree.getNodeByParam("id", item);
                    userTree.checkNode(node, true, false);
                });
                // 勾选已有角色
                $.each(response.data.roleIds, function (index, item) {
                    var node = roleTree.getNodeByParam("id", item);
                    roleTree.checkNode(node, true, false);
                });
            });
        },
        /** 初始化部门信息 */
        initGroupInfo: function () {
            // 获取当前用户信息
            $.getJSON(baseURL + "users/" +  userId + "?token=" + accessToken, function (response) {
                currentUser = response;
            });
        },
        /** 租户列表onchange 事件*/
        selectTenant: function () {
            vm.group.tenantId = vm.tenantList.selectedTenant;
            vm.group.applicationId = vm.appList.selectedApp;
            vm.reloadUserTree();
            vm.reloadRoleTree();

            vm.appList.options = [];
            vm.appList.selectedApp = "";
            vm.getAppList();
        },
        /** 应用列表onchange 事件*/
        selectApp: function () {
            vm.group.tenantId = vm.tenantList.selectedTenant;
            vm.group.applicationId = vm.appList.selectedApp;
            vm.reloadUserTree();
            vm.reloadRoleTree();
            // vm.reloadRoleTreeByApp();
            // vm.reloadUserTreeByApp();
        },
        /** 类型列表onchange 事件*/
        selectType: function () {
            userTree = $.fn.zTree.init($("#userTree"), userTreeSetting, []);
            roleTree = $.fn.zTree.init($("#roleTree"), roleTreeSetting, []);
            vm.tenantList.selectedTenant = "";
            vm.appList.selectedApp = "";

            var selectedType = vm.typeList.selectedType;
            if (selectedType == 1) {
                //租户级
                vm.group.applicationId = null;
                vm.group.tenantId = null;
                vm.showTenant = true;
                vm.showApplication = false;

                vm.tenantList.options = [];
                vm.appList.options = [];
                vm.getTenantList();
            } else if (selectedType == 2) {
                //应用级
                vm.group.tenantId = null;
                vm.group.applicationId = null;
                vm.showTenant = false;
                vm.showApplication = true;

                vm.tenantList.options = [];
                vm.appList.options = [];
                vm.getAppList();
            } else {
                vm.group.tenantId = null;
                vm.group.applicationId = null;
                vm.showApplication = true;
                vm.showTenant = true;

                vm.tenantList.options = [];
                vm.appList.options = [];
                vm.getTenantList();
            }
        },
        dealTypeList: function () {
            var path = "/garnet/option/groupManage/types";
            $.get(baseURL + "/resources/gettype?userId=" + userId + "&path=" + path, function (response) {
                var type = response.data;
                if (type == "001") {
                    //应用级
                    $("#typeList option[value='1']").remove();
                    $("#typeList option[value='3']").remove();
                } else if (type == "010"){
                    //租户级
                    $("#typeList option[value='2']").remove();
                    $("#typeList option[value='3']").remove();
                } else if (type == "100") {
                    //租户+应用
                    $("#typeList option[value='1']").remove();
                    $("#typeList option[value='2']").remove();
                } else if (type == "011") {
                    //应用级、租户级
                    $("#typeList option[value='3']").remove();
                } else if (type == "101") {
                    //应用级、租户+应用
                    $("#typeList option[value='1']").remove();
                } else if (type == "110") {
                    //租户级、租户+应用
                    $("#typeList option[value='2']").remove();
                } else if (type == "111") {
                    //应用级、租户级、租户+应用
                } else {
                    $("#typeList option[value='1']").remove();
                    $("#typeList option[value='2']").remove();
                    $("#typeList option[value='3']").remove();
                }
            });

        },
        //根据租户加载用户树
        reloadUserTree : function() {
            // $.get(baseURL + "users/tenantId/" + vm.tenantList.selectedTenant, function (response) {
            $.get(baseURL + "users/byparams?tenantId=" + vm.tenantList.selectedTenant + "&applicationId=" + vm.appList.selectedApp, function (response) {
                userTree = $.fn.zTree.init($("#userTree"), userTreeSetting, response);
                userTree.expandAll(true);
                // for (var i = 0; i<response.length; i++) {
                //     var userId1 = JSON.stringify(response[i].id);
                //     if (userId1 == userId) {
                //     //勾选登录用户
                //     var node = userTree.getNodeByParam("id", userId);
                //     userTree.checkNode(node, true, false);
                //     }
                // }
            })
        },
        //根据应用加载用户树
        reloadUserTreeByApp : function() {
            $.get(baseURL + "users/applicationId/" + vm.appList.selectedApp, function (response) {
                userTree = $.fn.zTree.init($("#userTree"), userTreeSetting, response);
                userTree.expandAll(true);
                // for (var i = 0; i<response.length; i++) {
                //     var userId1 = JSON.stringify(response[i].id);
                //     if (userId1 == userId) {
                //         //勾选登录用户
                //         var node = userTree.getNodeByParam("id", userId);
                //         userTree.checkNode(node, true, false);
                //     }
                // }
            })
        },
        //根据租户加载角色树
        reloadRoleTree : function() {
            // $.get(baseURL + "roles/tenantId/" + vm.tenantList.selectedTenant, function (response) {
            $.get(baseURL + "roles/byparams?tenantId=" + vm.tenantList.selectedTenant + "&applicationId=" +vm.appList.selectedApp, function (response) {
                roleTree = $.fn.zTree.init($("#roleTree"), roleTreeSetting, response);
                roleTree.expandAll(true);
            })
        },
        //根据应用加载角色树
        reloadRoleTreeByApp : function() {
            $.get(baseURL + "roles/applicationId/" + vm.appList.selectedApp, function (response) {
                roleTree = $.fn.zTree.init($("#roleTree"), roleTreeSetting, response);
                roleTree.expandAll(true);
            })
        },
        /**  获取租户列表 */
        getTenantList: function () {
            // $.get(baseURL + "tenants?page=1&limit=1000"  + "&userId=" + userId, function (response) {
            //     $.each(response.list, function (index, item) {
            //         vm.tenantList.options.push(item);
            //     })
            // });
            var path = "/garnet/data/groupManage/tenantList";
            $.get(baseURL + "tenants/byuseridandpath?userId=" + userId + "&path=" + path, function (response) {
                $.each(response.data, function (index, item) {
                    vm.tenantList.options.push(item);
                })
            });
        },
        /**  获取应用列表 */
        getAppList: function () {
            // $.get(baseURL + "applications?page=1&limit=1000"  + "&userId=" + userId, function (response) {
            //     $.each(response.list, function (index, item) {
            //         vm.appList.options.push(item);
            //     })
            // });
            var tenantId = vm.tenantList.selectedTenant;
            var path = "/garnet/data/groupManage/tenantList";
            $.get(baseURL + "applications/byuseridandtenantid?userId=" + userId + "&tenantId=" + tenantId + "&path=" + path, function (response) {
                // console.log(JSON.stringify(response));
                $.each(response, function (index, item) {
                    vm.appList.options.push(item);
                })
            });
        },
        getSearchTenantList: function () {
            var path = "/garnet/data/groupManage/tenantList";
            $.get(baseURL + "tenants/byuseridandpath?userId=" + userId + "&path=" + path, function (response) {
                $.each(response.data, function (index, item) {
                    // vm.tenantList.options.push(item);
                    tenantSearchList.options.push(item);
                })
            });
        },
        getSearchAppList: function () {
            // $.get(baseURL + "applications?page=1&limit=1000"  + "&userId=" + userId, function (response) {
            //     $.each(response.list, function (index, item) {
            //         // vm.appList.options.push(item);
            //         appSearchList.options.push(item);
            //     })
            // });
            var path = "/garnet/data/groupManage/tenantList";
            $.get(baseURL + "applications/byuseridandtenantid?userId=" + userId + "&path=" + path, function (response) {
                $.each(response, function (index, item) {
                    appSearchList.options.push(item);
                })
            });
        },
        getGroupsByTeantIdAndAppId: function () {
            vm.searchApp = appSearchList.searchApp;
            vm.searchTenant = tenantSearchList.searchTenant;
            vm.reload(true);
        },
        getPermissionAction: function () {
            var type;
            var path = "/garnet/option/groupManage/types";
            $.get(baseURL + "/resources/gettype?userId=" + userId + "&path=" + path, function (response) {
                type = response.data;
            });
            var path1 = path + "/" + type;
            // console.log("path1：" + path1);
            $.get(baseURL + "/resources/getuseraction?userId=" + userId + "&path=" + path1, function (response) {
                var action = response.data;
                console.log("action: " + action);
                if (action == "read") {
                    vm.typesEditAble = false;
                } else {
                    vm.typesEditAble = true;
                }
            });
        },
    },
    /**  初始化页面时执行该方法 */
    created: function () {
        this.initGroupInfo();
        this.getSearchAppList();
        this.getSearchTenantList();
    }
});