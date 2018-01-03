/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

$(function () {
    /** 初始化角色列表 */
    $("#jqGrid").jqGrid({
        url: baseURL + 'authorities',
        datatype: "json",
        colModel: [
            {
                label: '权限ID',
                name: 'authorityId',
                align: 'center',
                hidden: true,
                index: "authority_id",
                width: 20,
                key: true
            },
            {label: '应用名称', name: 'applicationName', align: 'center', width: 70},
            {label: '权限名称', name: 'name', align: 'center', width: 70},
            {label: '通配符', name: 'wildcard', align: 'center', width: 70},
            {label: '详细说明', name: 'description', align: 'center', width: 70},
            {
                label: '状态', align: 'center', name: 'status', width: 50, formatter: function (value, options, row) {
                return value === 0 ?
                    '<span class="label label-danger">禁用</span>' :
                    '<span class="label label-success">正常</span>';
            }
            }
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
            name: "name",
            title:"path"
        }
    },
    check: {
        enable: false,
        nocheckInherit: true,
        chkboxType: {"Y": "s", "N": "s"}
    },
    callback:{
        beforeClick:function (treeId, treeNode, clickFlag) {
            vm.authority.wildcard = treeNode.path + "%";
        }
    }
};

var vm = new Vue({
    el: '#garnetApp',
    data: {
        searchName: null,
        showList: true,
        title: null,
        authority: {
            authorityId: null,
            applicationId: 1,
            name: null,
            wildcard: null,
            description: null,
            menuIds: null,
            status: 1
        },
        // 租户列表数据
        menuList: {
            selectedMenu: "",
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
        /**  查询按钮点击事件 */
        query: function () {
            vm.reload();
        },
        /**  新增按钮点击事件 */
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.authority = {
                authorityId: null,
                applicationId: 1,
                name: null,
                description: null,
                menuIds: null,
                status: 1
            };
            vm.getAppList();
            vm.initTreesToAdd();
        },
        /**  更新按钮点击事件 */
        update: function () {
            var authorityId = getSelectedRow();
            if (!authorityId) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.initTreesToUpdate(authorityId);
            vm.getAppList();
        },
        /**  删除按钮点击事件 */
        del: function () {
            var authorityIds = getSelectedRows();
            if (!authorityIds) {
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
                        url: baseURL + "authority?authorityIds=" + authorityIds.toString(),
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
            // 获取菜单树选择的菜单
            var nodes = menuTree.getCheckedNodes(true);
            var permissionIdList = [];
            for (var i = 0; i < nodes.length; i++) {
                permissionIdList.push(nodes[i].menuId);
            }
            vm.authority.menuIds = permissionIdList.join(",");
            console.log(JSON.stringify(vm.authority));
            $.ajax({
                type: vm.authority.authorityId === null ? "POST" : "PUT",
                url: baseURL + "authority",
                contentType: "application/json",
                data: JSON.stringify(vm.authority),
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
            //加载菜单树
            $.get(baseURL + "/menus/applicationId/" + vm.authority.applicationId, function (response) {
                menuTree = $.fn.zTree.init($("#menuTree"), menuTreeSetting, response);
                menuTree.expandAll(true);
            })
        },
        /** 更新按钮初始化数据 */
        initTreesToUpdate: function (authorityId) {
            //加载访问权限树
            $.get(baseURL + "menus/applicationId/" + vm.authority.applicationId, function (response) {
                menuTree = $.fn.zTree.init($("#menuTree"), menuTreeSetting, response);
                menuTree.expandAll(true);
                vm.getAuthorityById(authorityId);
            })
        },
        /** 通过id 得到一个authority对象 */
        getAuthorityById: function (authorityId) {
            $.get(baseURL + "authority/" + authorityId, function (response) {
                vm.authority.authorityId = response.authorityId;
                vm.authority.name = response.name;
                vm.authority.wildcard = response.wildcard;
                vm.authority.description = response.description;
                vm.authority.status = response.status;
                $.each(response.menuIdList, function (index, item) {
                    var node = menuTree.getNodeByParam("menuId", item);
                    console.log(JSON.stringify(node));
                    menuTree.checkNode(node, true, false);
                });
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
                postData: {searchName: vm.searchName},
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
                    vm.appList.options.push(item);
                })
            });
        }
    },
    /**  初始化页面时执行该方法 */
    created: function () {
        this.getCurrentUser();
    }
});
