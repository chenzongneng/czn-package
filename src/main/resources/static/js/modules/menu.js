/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

$(function () {
    /** 初始化角色列表 */
    $("#jqGrid").jqGrid({
        url: baseURL + 'menus',
        datatype: "json",
        colModel: [
            {label: '菜单ID', name: 'menuId', align: 'center', hidden: true, index: "menu_id", width: 20, key: true},
            {label: '应用名称', name: 'applicationName', align: 'center', width: 70},
            {
                label: '类型', align: 'center', name: 'type', width: 50, formatter: function (value, options, row) {
                switch (value) {
                    case 0:
                        return '<span class="label label-primary">目录</span>';
                    case 1:
                        return '<span class="label label-success">菜单</span>';
                    case 2:
                        return '<span class="label label-info">按钮</span>';
                    default:
                        return "";
                }
            }
            },
            {label: '菜单名称', name: 'name', align: 'center', width: 70},
            {label: '说明', name: 'description', align: 'center', width: 70},
            {label: '菜单标识', name: 'code', align: 'center', width: 70},
            {label: '父菜单标识', name: 'parentCode', align: 'center', width: 70},
            {
                label: '菜单图标', name: 'icon', align: 'center', width: 70, formatter: function (value) {
                return "<i class=\"" + value + "\">";
            }
            },
            {label: '菜单URL', name: 'url', align: 'center', width: 70},
            {label: '排序', name: 'orderNum', align: 'center', width: 100},
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

/** 菜单结构树 */
var menuTree;
var menuTreeSetting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "code",
            pIdKey: "parentCode",
            rootPId: "root"
        },
        key: {
            url: "nourl",
            name: "name"
        }
    }
};

/** 访问权限结构树 */
var permissionTree;
var permissionTreeSetting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "permissionId",
            pIdKey: "parentId",
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
        chkboxType: {"Y": "s", "N": "s"}
    }
};

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
        searchName: null,
        showList: true,
        showParentCode: false,
        isNotButton: true,
        title: null,
        menu: {
            menuId: null,
            name: null,
            parentName: null,
            applicationId: 1,
            permissionIds: null,
            orderNum: 0
        },
        option: {
            appId: 1,
            appSearchId: null
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
            applicationList.appList.selectedApp = 1;
            applicationList.appList.options = [];
            vm.menu = {
                menuId: null,
                applicationId: 1,
                name: null,
                parentName: null,
                permissionIds: null,
                orderNum: 0
            };
            vm.initTreesToAdd();
            vm.loadMenuTree();
        },
        /**  更新按钮点击事件 */
        update: function () {
            var menuId = getSelectedRow();
            if (!menuId) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.menu.permissionIdList = [];
            vm.showParentCode = true;
            vm.initTreesToUpdate(menuId);
            vm.loadMenuTree();
        },
        /**  删除按钮点击事件 */
        del: function () {
            var menuIds = getSelectedRows();
            if (!menuIds) {
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
                        url: baseURL + "menu?menuIds=" + menuIds.toString(),
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
            // 获取访问权限树选择的访问权限
            var nodes = permissionTree.getCheckedNodes(true);
            var permissionIdList = [];
            for (var i = 0; i < nodes.length; i++) {
                if (!nodes[i].isParent) {
                    permissionIdList.push(nodes[i].permissionId);
                }
            }
            vm.menu.permissionIds = permissionIdList.join(",");
            console.log(JSON.stringify(vm.menu));
            $.ajax({
                type: vm.menu.menuId === null ? "POST" : "PUT",
                url: baseURL + "menu",
                contentType: "application/json",
                data: JSON.stringify(vm.menu),
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
        /** 添加按钮初始化数据 */
        initTreesToAdd: function () {
            //加载访问权限树
            $.get(baseURL + "/permissions/applicationId/" + vm.menu.applicationId, function (response) {
                permissionTree = $.fn.zTree.init($("#permissionTree"), permissionTreeSetting, response);
                permissionTree.expandAll(true);
            })
        },
        /** 更新按钮初始化数据 */
        initTreesToUpdate: function (menuId) {
            //加载访问权限树
            $.get(baseURL + "permissions/applicationId/" + vm.menu.applicationId, function (response) {
                permissionTree = $.fn.zTree.init($("#permissionTree"), permissionTreeSetting, response);
                permissionTree.expandAll(true);
                vm.getMenuById(menuId);
            })
        },
        /** 通过id 得到一个menu对象 */
        getMenuById: function (menuId) {
            $.get(baseURL + "menu/" + menuId, function (response) {
                vm.menu.menuId = response.menuId;
                vm.menu.applicationId = response.applicationId;
                vm.menu.type = response.type;
                vm.menu.name = response.name;
                vm.menu.description = response.description;
                vm.menu.code = response.code;
                vm.menu.parentCode = response.parentCode;
                vm.menu.parentName = response.parentName;
                vm.menu.icon = response.icon;
                vm.menu.url = response.url;
                vm.menu.orderNum = response.orderNum;
                vm.menu.status = response.status;
                applicationList.appList.selectedApp = response.applicationId;
                $.each(response.permissionIdList, function (index, item) {
                    var node = permissionTree.getNodeByParam("permissionId", item);
                    permissionTree.checkNode(node, true, false);
                });
                vm.typeChange();
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
                postData: {searchName: vm.searchName, applicationId: vm.option.appSearchId},
                page: page
            }).trigger("reloadGrid");
        },
        /** 应用列表onchange 事件*/
        selectApp: function () {
            vm.initTreesToAdd()
        },
        /**  获取应用列表 */
        getAppList: function () {
            $.get(baseURL + "applications?page=1&limit=1000", function (response) {
                $.each(response.list, function (index, item) {
                    console.log(JSON.stringify(item));
                    applicationList.appList.options.push(item);
                    applicationList.appSearchList.options.push(item);
                })
            });
        },
        //加载菜单树
        loadMenuTree: function () {
            $.get(baseURL + "/menus/applicationId/" + vm.menu.applicationId, function (response) {
                menuTree = $.fn.zTree.init($("#menuTree"), menuTreeSetting, response);
                menuTree.expandAll(true);
            })
        },
        /**  菜单树点击事件 */
        menuTree: function () {
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择菜单",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#menuLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = menuTree.getSelectedNodes();
                    //选择上级部门
                    // console.log(JSON.stringify(node));
                    vm.menu.parentCode = node[0].parentCode;
                    vm.menu.parentName = node[0].name;
                    vm.menu.code = node[0].parentCode;
                    vm.showParentCode = true;
                    layer.close(index);
                }
            });
        },
        typeChange: function () {
            if (vm.menu.type === 2) {
                vm.isNotButton = false;
            } else {
                vm.isNotButton = true;
            }
        }
    },
    /**  初始化页面时执行该方法 */
    created: function () {
        this.getCurrentUser();
        this.getAppList();
    }
});
