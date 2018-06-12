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
                label: '权限ID',
                name: 'permissionId',
                align: 'center',
                hidden: true,
                index: "permission_id",
                width: 20,
                key: true,
                sortable: false
            },
            {label: '权限id', name: 'permission.id', align: 'center', hidden: true, index: "id", width: 20, key: true, sortable: false},
            {label: '权限名称', name: 'permission.name', align: 'center', width: 100, sortable: false},
            {label: '类型', name: 'type', align: 'center', width: 70 ,sortable: false ,sortable: false},
            {label: '应用名称', name: 'applicationName', align: 'center', width: 80},
            {label: '租户名称', name: 'tenantName', align: 'center', width: 80},
            {label: '资源通配符', name: 'permission.resourcePathWildcard', align: 'center', width: 80, sortable: false},
            {label: '详细说明', name: 'permission.description', align: 'center', width: 70,sortable: false},
            {label: '创建时间', name: 'permission.createdTime', align: 'center', formatter:timeFormat, width: 120,sortable: false},
            {label: '更新时间', name: 'permission.modifiedTime', align: 'center', formatter:timeFormat, width: 120 ,sortable: false},
            {label: '更改人', name: 'permission.updatedByUserName', align: 'center', width: 70 ,sortable: false},
            // {
            //     label: '状态', align: 'center', name: 'status', width: 20, formatter: function (value, options, row) {
            //     return value === 0 ?
            //         '<span class="label label-danger">禁用</span>' :
            //         '<span class="label label-success">正常</span>';
            // }
            // },
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

/** 菜单结构树 */
var resourceTree;
var resourceTreeSetting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "code",
            pIdKey: "parentCode",
            rootPId: "root"
        },
        key: {
            url: "nourl",
            name: "name",
            title: "path"
        }
    },
    check: {
        enable: false,
        nocheckInherit: true,
        chkboxType: {"Y": "s", "N": "s"}
    },
    callback: {
        beforeClick: function (treeId, treeNode, clickFlag) {
            vm.permission.wildcard = treeNode.path;
        }
    }
};

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
        permission: {
            id: null,
            applicationId: null,
            name: null,
            resourcePathWildcard:null,
            wildcard: null,
            tenantId: null,
            description: null,
            resourceIds: null,
            status: null,
            action: null,
            updatedByUserName: null
        },
        // 租户列表数据
        resourceList: {
            selectedMenu: "",
            options: []
        },
        // 应用列表数据
        appList: {
            selectedApp: "",
            options: []
        },
        // 租户表数据
        tenantList: {
            selectedTenant: "",
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
            vm.permission = {
                id: null,
                applicationId: "",
                tenantId: "",
                wildcard: null,
                action: null,
                status: 1
            };
            vm.typeList.selectedType = "";
            vm.tenantList.options = [];
            vm.appList.options = [];
            vm.getAppList();
            vm.getTenantList();
            // vm.initTreesToAdd();
        },
        /**  更新按钮点击事件 */
        update: function () {
            // var missionId = getSelectedRow();
            var id = getSelectedRow()
            if (!id) {
                return;
            }
            vm.showList = false;
            vm.showType = false;
            vm.hiddenByType = false;
            vm.title = "修改";
            vm.tenantList.options = [];
            vm.appList.options = [];
            // vm.initTreesToUpdate(permissionId);
            vm.getPermissionById(id);
            vm.getAppList();
            vm.getTenantList();
        },
        /**  删除按钮点击事件 */
        del: function () {
            var permissionIds = getSelectedRows();
            if (!permissionIds) {
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
                        url: baseURL + "permissions?ids=" + permissionIds.toString(),
                        contentType: "application/json",
                        dataType: "",
                        success: function () {
                            window.parent.swal("删除成功!", "", "success");
                            vm.reload(false);
                        },
                        error: function (response) {
                            window.parent.swal("删除失败!", getExceptionMessage(response), "error");
                        }
                    });
                });
        },
        /**  新增或更新确认 */
        saveOrUpdate: function () {
            // alert($("#applicaitonIdSelected").find("option:selected").val());
            var obj = new Object();
            vm.permission.updatedByUserName = localStorage.getItem("userName");
            obj.permission = vm.permission;

            if ((vm.permission.applicationId == null || $.trim(vm.permission.applicationId) == "") && (vm.permission.tenantId == null || $.trim(vm.permission.tenantId) == "")) {
                window.parent.swal("", "请在选择类型后，选择租户或应用", "warning");
                    return;
            }

            if (vm.typeList.selectedType != null && vm.typeList.selectedType != "") {
                var selectType = vm.typeList.selectedType;
                if (selectType == 1) {
                    if (vm.permission.tenantId == null || $.trim(vm.permission.tenantId) == "") {
                        window.parent.swal("", "租户不能为空", "warning");
                        return;
                    }
                } else if (selectType == 2) {
                    if(vm.permission.applicationId == null || $.trim(vm.permission.applicationId) == "") {
                        window.parent.swal("", "应用不能为空", "warning");
                        return;
                    }
                } else if (selectType == 3) {
                    if ((vm.permission.applicationId == null || $.trim(vm.permission.applicationId) == "") || (vm.permission.tenantId == null || $.trim(vm.permission.tenantId) == "")) {
                        window.parent.swal("", "租户和应用都不能为空", "warning");
                        return;
                    }
                }
            }

            if(vm.permission.name == null || $.trim(vm.permission.name) == ""){
                window.parent.swal("", "权限名称不能为空", "warning");
                return;
            }


            if (vm.permission.action == null || $.trim(vm.permission.action) == "") {
                window.parent.swal("", "行为不能为空", "warning");
                return;
            }

            if (vm.permission.name.length > 30) {
                window.parent.swal("", "权限名称不能大于30", "warning");
                return;
            }

            if (vm.permission.resourcePathWildcard == null || $.trim(vm.permission.resourcePathWildcard) == "") {
                window.parent.swal("", "通配符不能为空", "warning");
                return;
            }

            // 获取菜单树选择的菜单
            // var nodes = resourceTree.getCheckedNodes(true);
            // var permissionIdList = [];
            // for (var i = 0; i < nodes.length; i++) {
            //     permissionIdList.push(nodes[i].resourceId);
            // }
            // vm.permission.resourceIds = permissionIdList.join(",");
            // console.log("permission == " + JSON.stringify(obj.permission));

            $.ajax({
                type: vm.permission.id === null ? "POST" : "PUT",
                url: baseURL + "permissions",
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
            //加载菜单树
            // $.get(baseURL + "/resources/applicationId/" + vm.permission.applicationId, function (response) {
            //     resourceTree = $.fn.zTree.init($("#resourceTree"), resourceTreeSetting, response);
            //     resourceTree.expandAll(true);
            // })
        },
        /** 通过id 得到一个permission对象 */
        getPermissionById: function (id) {
            $.get(baseURL + "permissions/" + id, function (response) {

                response = response.data.permission;
                vm.permission.id = response.id;
                // vm.permission.applicationId = response.applicationId;
                // vm.permission.tenantId = response.tenantId;
                vm.permission.name = response.name;
                vm.permission.wildcard = response.wildcard;
                vm.permission.resourcePathWildcard = response.resourcePathWildcard;
                vm.permission.description = response.description;
                vm.permission.status = response.status;
                vm.permission.action = response.action;

                var applicationId = response.applicationId;
                var tenantId = response.tenantId;

                if (tenantId == null || tenantId == 0) {
                    //应用级
                    vm.permission.applicationId = applicationId;
                    vm.showTenant = false;
                    vm.showApplication = true;
                } else if (applicationId == null || applicationId == 0) {
                    //租户级
                    vm.permission.tenantId = tenantId;
                    vm.showApplication = false;
                    vm.showTenant = true;
                } else {
                    //租户+应用
                    vm.permission.applicationId = applicationId;
                    vm.permission.tenantId = tenantId;
                    vm.showApplication = true;
                    vm.showTenant = true;
                }

            });
        },
        /** 更新按钮初始化数据 */
        initTreesToUpdate: function () {
            //加载资源树
            // $.get(baseURL + "resources/applicationId/" + vm.permission.applicationId, function (response) {
            //     resourceTree = $.fn.zTree.init($("#resourceTree"), resourceTreeSetting, response);
            //     resourceTree.expandAll(true);
            // })
            vm.getPermissionById();
        },
        // /** 查询当前用户信息 */
        // getCurrentUser: function () {
        //     $.getJSON(baseURL + "token/userInfo?token=" + accessToken, function (response) {
        //         vm.currentUser = response;
        //     });
        // },
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
        /** 应用列表onchange 事件*/
        selectApp: function () {
            vm.initTreesToAdd()
        },
        /** 类型列表onchange 事件*/
        selectType: function () {
            var selectedType = vm.typeList.selectedType;
            if (selectedType == 1) {
                //租户级
                vm.permission.applicationId = "";
                vm.permission.tenantId = "";
                vm.showTenant = true;
                vm.showApplication = false;
            } else if (selectedType == 2) {
                //应用级
                vm.permission.tenantId = "";
                vm.permission.applicationId = "";
                vm.showApplication = true;
                vm.showTenant = false;
            } else {
                //租户+应用
                vm.permission.tenantId = "";
                vm.permission.applicationId = "";
                vm.showTenant = true;
                vm.showApplication = true;
            }
        },
        /**  获取应用列表 */
        getAppList: function () {
            $.get(baseURL + "applications?page=1&limit=1000&userId=" + userId, function (response) {
                $.each(response.list, function (index, item) {
                    vm.appList.options.push(item);
                })
            });
            // if (vm.appList.options.length == 0) {
            //
            // }
        },
        /** 租户列表onchange 事件*/
        selectTenant: function () {
            vm.initTreesToAdd()
        },
        /**  获取租户列表 */
        getTenantList: function () {
            $.get(baseURL + "tenants?page=1&limit=1000&userId=" + userId, function (response) {
                $.each(response.list, function (index, item) {
                    vm.tenantList.options.push(item);
                })
            });
            // if (vm.tenantList.options.length == 0) {
            //
            // }
        }

    },
    /**  初始化页面时执行该方法 */
    created: function () {
        // this.getCurrentUser();
    }
});
