/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

$(function () {

    /**  初始化租户列表  */
    $("#jqGrid").jqGrid({
        // url: baseURL + 'tenants?userId='+localStorage.getItem("userId"),
        url: baseURL + 'tenants',
        datatype: "json",
        colModel: [
            {label: '租户ID', name: 'id', align: 'center', hidden: true, width: 20, key: true},
            {label: '租户名称', name: 'name', align: 'center', width: 80},
            {label: '创建时间', name: 'createdTime', align: 'center', width: 100},
            {label: '修改时间时间', name: 'modifiedTime', align: 'center', width: 100},
            {label: '备注', name: 'description', align: 'center', width: 80}
            // {label: '创建时间', name: 'createTime', align: 'center', width: 80}
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
            // 设置表头居中
            $('.ui-jqgrid .ui-jqgrid-htable .ui-th-div').css('text-align', 'center');
            // checkBox 对齐
            $('.ui-jqgrid td input, .ui-jqgrid td select, .ui-jqgrid td textarea').css('margin-left', '6px');
        }
    });
});

/** 用户树 */
var appTree;
var appTreeSetting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id"
        },
        key: {
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
    el: '#garnetApp',
    data: {
        searchName: null,
        showList: true,
        title: null,
        userName:null,
        modeId: 1,// SAAS为0，PAAS为1
        modeName: null,
        tenant: {
            name: null,
            description: null,
            serviceMode: null,
            appIds: null,
            appNames: [],
            appIdList: []
        },
        // 选择模式列表
        modeList: {
            selectedMode: 1,
            options: [
                // {
                //     id : -1,
                //     name : "ALL"
                // },
                {
                id : 0,
                name : "SASS"
            },{
                id : 1,
                name : "PASS"
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
            vm.showList = false;
            vm.title = "新增";
            vm.tenant = {
                id: null,
                name: null,
                description: null,
                appIds: null,
                appNames: [],
                appIdList: []
            };

            // 加载应用树
            $.get(baseURL + "applications?page=1&limit=1000", function (response) {
                appTree = $.fn.zTree.init($("#appTree"), appTreeSetting, response.list);
                appTree.expandAll(true);

            });

        },
        /**  更新按钮点击事件 */
        update: function () {
            var tenantId = getSelectedRow();
            if (!tenantId) {return;}
            vm.showList = false;
            vm.title = "修改";
            vm.tenant.description = null,
            vm.tenant.appIds = null;
            vm.tenant.appNames = [];

            // 加载应用树
            $.get(baseURL + "applications?page=1&limit=1000", function (response) {
                appTree = $.fn.zTree.init($("#appTree"), appTreeSetting, response.list);
                appTree.expandAll(true);
                vm.getTenant(tenantId);
            });
        },
        /**  删除按钮点击事件 */
        del: function () {
            var tenantIds = getSelectedRows();
            if (!tenantIds) {
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
                        url: baseURL + "tenants?ids=" + tenantIds.toString(),
                        contentType: "application/json",
                        dataType: "",
                        success: function () {
                            swal("删除成功!", "", "success");
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

            // alert(JSON.stringify(vm.tenant));
            var obj = new Object();
            var userName = vm.userName;
            obj.tenant = vm.tenant;
            obj.appIds =vm.tenant.appIds;
            obj.userTenants = [];
            obj.userName = userName;
            var appIdList = vm.tenant.appIdList;

            // alert(JSON.stringify(obj));
            if(vm.tenant.name === null){
                alert("租户名称不能为空");
                return;
            }

            if (vm.modeId == null) {
                vm.modeId = vm.modeList.selectedMode;
            }

            if(vm.modeId == 0) {  //saas
                vm.tenant.serviceMode = "saas";
                if (appIdList.length > 1) {
                    swal("当前模式不能添加多个应用", "", "error");
                    return;
                }
                // console.log("my modeId is : " + vm.modeId);
            } else if(vm.modeId == 1) {
                vm.tenant.serviceMode = "paas"
                // console.log("my modeId is : " + vm.modeId);
            } else {
                swal({
                    title: "当前默认为PAAS模式，是否确认添加",
                    type: "warning",
                    showCancelButton: true,
                    closeOnConfirm: false,
                    confirmButtonText: "确认",
                    cancelButtonText: "取消",
                    confirmButtonColor: "#DD6B55"
                }, function () {
                    $.ajax({
                        type: obj.tenant.id === null ? "POST" : "PUT",
                        url: baseURL + "tenants",
                        contentType: "application/json",
                        data: JSON.stringify(obj),
                        dataType: "",
                        success: function () {
                            vm.reload(false);
                            swal("操作成功!", "", "success");
                        },
                        error: function (response) {
                            swal(response.responseJSON.data.errorResponseMessage, "",  "error");
                        }
                    });
                });
                return;
            }

            $.ajax({
                type: obj.tenant.id === null ? "POST" : "PUT",
                url: baseURL + "tenants",
                contentType: "application/json",
                data: JSON.stringify(obj),
                dataType: "",
                success: function () {
                    vm.reload(false);
                    swal("操作成功!", "", "success");
                },
                error: function (response) {
                    swal(response.responseJSON.data.errorResponseMessage, "",  "error");
                }
            });
        },
        /**  根据ID获取租户信息 */
        getTenant: function (tenantId) {
            $.get(baseURL + "tenants/" + tenantId, function (response) {
                // alert(JSON.stringify(response.data.tenant));
                response=response.data;
                if (response) {
                    vm.tenant.id = response.tenant.id;
                    vm.tenant.name = response.tenant.name;
                    vm.tenant.description = response.tenant.description;
                    vm.tenant.createdTime = response.tenant.createdTime;
                    vm.tenant.modifiedTime = response.tenant.modifiedTime;
                    vm.tenant.appIdList = response.appIdList;
                    vm.userName = response.userName;
                    $.each(response.appNameList, function (index, item) {
                        vm.tenant.appNames.push(item);
                    })
                }
            });
        },
        /**  应用树点击事件 */
        appTree: function (tenant) {
            console.log(JSON.stringify(tenant));
            $.each(tenant.appIdList, function (index, item) {
                var node = appTree.getNodeByParam("id", item);
                appTree.checkNode(node, true, false);
            });
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择应用",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#appLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    // 勾选已有应用
                    // 获取应用树选择的应用
                    var appNodes = appTree.getCheckedNodes(true);
                    var appIdList = [];
                    vm.tenant.appNames = [];
                    vm.tenant.appIds = null;
                    for (var i = 0; i < appNodes.length; i++) {
                        appIdList.push(appNodes[i].id);
                        vm.tenant.appNames.push(appNodes[i].name);
                    }
                    vm.tenant.appIds = appIdList.join(",");
                    vm.tenant.appIdList = appIdList;
                    layer.close(index);
                }
            });
        },
        //模式选择事件
        selectMode: function () {
            vm.modeId = vm.modeList.selectedMode;
            vm.reload(true);
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
                    'searchName': vm.searchName,
                    'modeId' : vm.modeId
                },
                page: page

            }).trigger("reloadGrid");
        }
    }
});