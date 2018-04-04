/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

$(function () {
    /**  初始化应用列表  */
    $("#jqGrid").jqGrid({
        url: baseURL + 'routergroups',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', align: 'center', hidden: true, width: 20, key: true},
            {label: '名称', name: 'groupName', align: 'center', width: 80},
            // TODO routerGroup 应用列表显示
            // {label: '应用列表', name: 'appCodeList', align: 'center', width: 80}
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

/** 用户树 */
var applicationTree;
var applicationTreeSetting = {
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
        routerGroup: {
            id:null,
            groupName: null,
            appCode: null
        },
        applicationNames: [],
        appCodeList: [],
        applicationIds: ''
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
            vm.appCodeList = [];
            vm.routerGroup = {
                id:null,
                groupName: null,
                appCode: null
            };

            // 加载应用树
            $.get(baseURL + "applications?userId=" + userId + "&page=1&limit=1000", function (response) {
                applicationTree = $.fn.zTree.init($("#applicationTree"), applicationTreeSetting, response.list);
                applicationTree.expandAll(true);
            });
        },
        /**  更新按钮点击事件 */
        update: function () {
            var routerGroupId = getSelectedRow();

            if (!routerGroupId) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.applicationIds = null;
            vm.applicationNames = [];
            vm.appCodeList = [];
            // 加载应用树
            $.get(baseURL + "applications?userId=" + userId + "&page=1&limit=1000", function (response) {
                applicationTree = $.fn.zTree.init($("#applicationTree"), applicationTreeSetting, response.list);
                applicationTree.expandAll(true);
                vm.getrouterGroup(routerGroupId);
            });
        },
        /**  删除按钮点击事件 */
        del: function () {
            var routerGroupIds = getSelectedRows();
            if (!routerGroupIds) {
                return;
            }
            swal({
                    title: "确定要删除选中的记录",
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
                        url: baseURL + "routergroups?ids=" + routerGroupIds.toString(),
                        contentType: "routerGroup/json",
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
            var obj = new Object();
            obj.routerGroup = vm.routerGroup;
            obj.applicationIds = vm.applicationIds;
            obj.appCodeList = vm.appCodeList;
            // alert(JSON.stringify(obj));
            if(vm.routerGroup.groupName === null){
                alert("名称不能为空");
                return;
            }

            $.ajax({
                type: vm.routerGroup.id === null ? "POST" : "PUT",
                url: baseURL + "routergroups",
                contentType: "application/json",
                data:JSON.stringify(obj),
                dataType: "",
                async: true,
                success: function () {
                    vm.reload(false);
                    swal("操作成功!", "", "success");
                },
                error: function (response) {
                    swal(response.responseJSON.errorMessage, "", "error");
                }
            });
        },
        /**  根据ID获取应用信息 */
        getrouterGroup: function (routerGroupId) {
            $.get(baseURL + "routergroups/" + routerGroupId, function (response) {

                response=response.data;

                if (response) {
                    vm.routerGroup.id = response.routerGroup.id;
                    vm.routerGroup.groupName = response.routerGroup.groupName;
                    vm.routerGroup.appCode = response.routerGroup.appCode;
                    vm.applicationList = response.applicationIdList;
                    // 勾选已有应用
                    $.each(response.applicationIdList, function (index, item) {
                        var node = applicationTree.getNodeByParam("id", item);
                        applicationTree.checkNode(node, true, false);
                    });
                    $.each(response.applicationNames, function (index, item) {
                        vm.applicationNames.push(item);
                    })
                }
            });
        },
        /**  租户树点击事件 */
        applicationTree: function () {
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择租户",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#applicationLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    vm.applicationNames = [];
                    vm.applicationIds = "";
                    // 获取应用树选择的应用
                    var applicationNodes = applicationTree.getCheckedNodes(true);
                    var applicationIdList = [];
                    for (var i = 0; i < applicationNodes.length; i++) {
                        applicationIdList.push(applicationNodes[i].id);
                        vm.applicationNames.push(applicationNodes[i].name);
                        vm.appCodeList.push(applicationNodes[i].appCode);
                    }

                    vm.applicationIds = applicationIdList.join(",");
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
                    'searchName': vm.searchName
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});