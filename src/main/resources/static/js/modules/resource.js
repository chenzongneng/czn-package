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
            {label: '资源ID', name: 'id', align: 'center', hidden: true, index: "resource_id", width: 20, key: true},
            // {label: '应用名称', name: 'applicationName', align: 'center', width: 40},
            {label: '资源名称', name: 'name', align: 'center', width: 40},
            // {label: '说明', name: 'varchar00', align: 'center', width: 70},
            // {label: '父资源标识', name: 'varchar02', align: 'center', width: 70},
            // {label: '资源标识', name: 'varchar01', align: 'center', width: 70},
            {label: '路径标识', name: 'path', align: 'center', width: 70},
            {label: 'action', name: 'actions', align: 'center', width: 40},
            // {
            //     label: '状态', align: 'center', name: 'status', width: 20, formatter: function (value, options, row) {
            //     return value === 0 ?
            //         '<span class="label label-danger">禁用</span>' :
            //         '<span class="label label-success">正常</span>';
            // }
            // }
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
            code: null,
            actions: null,
            parentCode: null,
            parentName: null,
            applicationId: null,
            tenantId: null,
            type:null,
            varchar00: null,
            varchar01: null,
            varchar02: null,
            varchar03: null,
            varchar04: null,
            varchar05: null,
            varchar06: null,
            varchar07: null,
            varchar08: null,
            varchar09: null,
            varchar10: null,
            varchar11: null,
            varchar12: null,
            varchar13: null,
            varchar14: null,
            varchar15: null,
            varchar16: null,
            varchar17: null,
            varchar18: null,
            varchar19: null,
            int01: null,
            int02: null,
            int03: null,
            int04: null,
            int05: null,
            boolean01: null,
            boolean02: null,
            boolean03: null,
            boolean04: null,


            orderNum: 0
        },
        // 租户表数据
        tenantList: {
            selectedTenant: "",
            options: []
        },
        // 类型数据
        typeList: {
            selectedType: "",
            options: [{
                selectedType: "",
                "id" : "1",
                "name" : "ui-UI组件"
            },{
                selectedType: "",
                "id" : "2",
                "name" : "openApi"
            },{
                selectedType: "",
                "id" : "3",
                "name" : "function"
            }]
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
                id: null,
                applicationId: 1,
                name: null,
                code: null,
                parentCode: null,
                //说明
                // varchar00: null,
                // //资源标识
                // varchar01: null,
                // //父资源标识
                // varchar02: null,
                parentName: null,
                apiIds: null,
                orderNum: 0,
                status: 1
            };
            if(vm.option.appSearchId !== undefined && vm.option.appSearchId !== null && vm.option.appSearchId !== ""){
                vm.resource.applicationId = vm.option.appSearchId;
            }
            //vm.getAppList();
            vm.getTenantList();
            vm.initTreesToAdd();
            vm.loadResourceTree();
        },
        /**  更新按钮点击事件 */
        update: function () {
            var resourceId = getSelectedRow();

            if (!resourceId) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.tenantList.options = [];
            // vm.resource.apiIdList = [];
            // vm.showParentCode = true;
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
                        url: baseURL + "resources?ids=" + resourceIds.toString(),
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
            var obj = new Object();
            obj.resource = vm.resource;

            // if(vm.resource.name === null ||  vm.resource.name === ""){
            //     alert("资源名称不能为空");
            //     return;
            // }
            // if(vm.resource.varchar01 === null ||  vm.resource.varchar01 === ""){
            //     alert("资源标识不能为空");
            //     return;
            // }
            //
            // if(vm.resource.varchar02 === vm.resource.varchar01) {
            //     alert("父子标志不能相同");
            //     return;
            // }
            // var nodes = apiTree.getCheckedNodes(true);
            // var apiIdList = [];
            // for (var i = 0; i < nodes.length; i++) {
            //     if (!nodes[i].isParent) {
            //         apiIdList.push(nodes[i].apiId);
            //     }
            // }
            // vm.resource.apiIds = apiIdList.join(",");

            $.ajax({
                type: vm.resource.id === null ? "POST" : "PUT",
                url: baseURL + "resources",
                contentType: "application/json",
                data: JSON.stringify(obj),
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
                // vm.getTenantList();
                // vm.getResourceById(resourceId);
            })

            vm.getResourceById(resourceId);
            vm.getTenantList();
        },
        /** 通过id 得到一个resource对象 */
        getResourceById: function (resourceId) {
            $.get(baseURL + "resources/" + resourceId, function (response) {
                response = response.data;

                console.log("reposnse == " + JSON.stringify(response));

                vm.resource.id = response.id;
                vm.resource.applicationId = response.applicationId;
                vm.resource.name = response.name;
                vm.resource.path = response.path;
                vm.resource.status = response.status;
                applicationList.appList.selectedApp = response.applicationId;
                vm.tenantList.selectedTenant = response.tenantId;
                vm.typeList.selectedType = response.type;
                vm.resource.varchar00 = response.varchar00;
                vm.resource.varchar01 = response.varchar01;
                vm.resource.varchar02 = response.varchar02;
                vm.resource.varchar03 = response.varchar03;
                vm.resource.varchar04 = response.varchar04;
                vm.resource.varchar05 = response.varchar05;
                vm.resource.varchar06 = response.varchar06;
                vm.resource.varchar07 = response.varchar07;
                vm.resource.varchar08 = response.varchar08;
                vm.resource.varchar09 = response.varchar09;
                vm.resource.varchar10 = response.varchar10;
                vm.resource.varchar11 = response.varchar11;
                vm.resource.varchar12 = response.varchar12;
                vm.resource.varchar13 = response.varchar13;
                vm.resource.varchar14 = response.varchar14;
                vm.resource.varchar15 = response.varchar15;
                vm.resource.varchar16 = response.varchar16;
                vm.resource.varchar17 = response.varchar17;
                vm.resource.varchar18 = response.varchar18;
                vm.resource.varchar19 = response.varchar19;
                vm.resource.int01 = response.int01;
                vm.resource.int02 = response.int02;
                vm.resource.int03 = response.int03;
                vm.resource.int04 = response.int04;
                vm.resource.int05 = response.int05;
                vm.resource.boolean01 = response.boolean01;
                vm.resource.boolean02 = response.boolean02;
                vm.resource.boolean03 = response.boolean03;
                vm.resource.boolean04 = response.boolean04;

                // vm.initTreesToAdd();
                // $.each(response.apiIdList, function (index, item) {
                //     var node = apiTree.getNodeByParam("apiId", item);
                //     apiTree.checkNode(node, true, false);
                // });
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
        selectTenant: function () {
            vm.initTreesToAdd()
        },
        selectType: function () {
            vm.initTreesToAdd()
        },
        /**  获取应用列表 */
        getAppList: function () {
            $.get(baseURL + "applications?page=1&limit=1000", function (response) {
                $.each(response.list, function (index, item) {
                    applicationList.appList.options.push(item);
                    applicationList.appSearchList.options.push(item);
                })
            });

            // console.log("aaaa == " + JSON.stringify(applicationList.appList.options));

        },
        /**  获取租户列表 */
        getTenantList: function () {
            $.get(baseURL + "tenants?page=1&limit=1000", function (response) {
                $.each(response.list, function (index, item) {
                    vm.tenantList.options.push(item);
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
