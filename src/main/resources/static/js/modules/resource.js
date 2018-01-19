/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

$(function () {
    /** 初始化角色列表 */
    $("#jqGrid").jqGrid({
        url: baseURL + 'resources',
        datatype: "json",
        colModel: [
            {label: '资源ID', name: 'resourceId', align: 'center', hidden: true, index: "resource_id", width: 20, key: true},
            {label: '应用名称', name: 'applicationName', align: 'center', width: 40},
            {label: '资源名称', name: 'name', align: 'center', width: 40},
            {label: '说明', name: 'description', align: 'center', width: 70},
            {label: '父资源标识', name: 'parentCode', align: 'center', width: 70},
            {label: '资源标识', name: 'code', align: 'center', width: 70},
            {label: '路径标识', name: 'path', align: 'center', width: 70},
            {
                label: '状态', align: 'center', name: 'status', width: 20, formatter: function (value, options, row) {
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

/** 资源结构树 */
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
            name: "name"
        }
    }
};

/** 访问权限结构树 */
var apiTree;
var apiTreeSetting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "apiId",
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
        name: null,
        showList: true,
        showParentCode: false,
        title: null,
        resource: {
            resourceId: null,
            name: null,
            parentName: null,
            applicationId: 1,
            apiIds: null,
            orderNum: 0
        },
        option: {
            applicationId: 1,
            appSearchId: ""
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
            applicationList.appList.selectedApp = 1;
            vm.resource = {
                resourceId: null,
                applicationId: 1,
                name: null,
                parentName: null,
                apiIds: null,
                orderNum: 0,
                status: 1
            };
            vm.initTreesToAdd();
            // vm.loadResourceTree();
        },
        /**  更新按钮点击事件 */
        update: function () {
            var resourceId = getSelectedRow();
            if (!resourceId) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.resource.apiIdList = [];
            vm.showParentCode = true;
            vm.initTreesToUpdate(resourceId);
            // vm.loadResourceTree();
        },
        /**  删除按钮点击事件 */
        del: function () {
            var resourceIds = getSelectedRows();
            if (!resourceIds) {
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
                        url: baseURL + "resource?resourceIds=" + resourceIds.toString(),
                        contentType: "application/json",
                        dataType: "",
                        success: function (result) {
                            if (!result.message) {
                                swal("删除成功!", "", "success");
                                vm.reload(false);
                            } else {
                                swal("删除失败!", result.message, "error");
                            }
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
            // 获取访问权限树选择的访问权限
            if(vm.resource.parentCode == vm.resource.code) {
                alert("父子标志不能相同");
                return;
            }
            var nodes = apiTree.getCheckedNodes(true);
            var apiIdList = [];
            for (var i = 0; i < nodes.length; i++) {
                if (!nodes[i].isParent) {
                    apiIdList.push(nodes[i].apiId);
                }
            }
            vm.resource.apiIds = apiIdList.join(",");
            console.log(JSON.stringify(vm.resource));
            $.ajax({
                type: vm.resource.resourceId === null ? "POST" : "PUT",
                url: baseURL + "resource",
                contentType: "application/json",
                data: JSON.stringify(vm.resource),
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
            //加载访问权限树
            $.get(baseURL + "/apis/applicationId/" + vm.resource.applicationId, function (response) {
                apiTree = $.fn.zTree.init($("#apiTree"), apiTreeSetting, response);
                apiTree.expandAll(false);
            })
        },
        /** 更新按钮初始化数据 */
        initTreesToUpdate: function (resourceId) {
            //加载访问权限树
            $.get(baseURL + "apis/applicationId/" + vm.resource.applicationId, function (response) {
                apiTree = $.fn.zTree.init($("#apiTree"), apiTreeSetting, response);
                apiTree.expandAll(false);
                vm.getResourceById(resourceId);
            })
        },
        /** 通过id 得到一个resource对象 */
        getResourceById: function (resourceId) {
            $.get(baseURL + "resource/" + resourceId, function (response) {
                vm.resource.resourceId = response.resourceId;
                vm.resource.applicationId = response.applicationId;
                vm.resource.name = response.name;
                vm.resource.description = response.description;
                vm.resource.code = response.code;
                vm.resource.parentCode = response.parentCode;
                vm.resource.parentName = response.parentName;
                vm.resource.path = response.path;
                vm.resource.status = response.status;
                applicationList.appList.selectedApp = response.applicationId;
                vm.initTreesToAdd();
                $.each(response.apiIdList, function (index, item) {
                    var node = apiTree.getNodeByParam("apiId", item);
                    apiTree.checkNode(node, true, false);
                });
                // vm.typeChange();
            });
        },
        /** 查询当前用户信息 */
        getCurrentUser: function () {
            $.getJSON(baseURL + "token/userInfo?token=" + garnetToken, function (response) {
                vm.currentUser = response;
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
            console.log(JSON.stringify(page));
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {name: vm.name, applicationId: vm.option.appSearchId},
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
        //加载资源树
        loadResourceTree: function () {
            $.get(baseURL + "/resources/applicationId/" + vm.resource.applicationId, function (response) {
                resourceTree = $.fn.zTree.init($("#resourceTree"), resourceTreeSetting, response);
                resourceTree.expandAll(true);
            })
        },
        /**  资源树点击事件 */
        resourceTree: function () {
            vm.loadResourceTree();
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择资源",
                area: ['300px', '700px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#resourceLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = resourceTree.getSelectedNodes();
                    //选择上级部门
                    // console.log(JSON.stringify(node));
                    vm.resource.parentCode = node[0].code;
                    vm.resource.parentName = node[0].name;
                    vm.resource.code = node[0].code;
                    vm.resource.path = node[0].path + "/";
                    vm.showParentCode = true;
                    layer.close(index);
                }
            });
        }
    },
    /**  初始化页面时执行该方法 */
    created: function () {
        this.getCurrentUser();
        this.getAppList();
    }
});
