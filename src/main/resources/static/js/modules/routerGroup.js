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
            {label: 'ID', name: 'routerGroup.id', align: 'center', hidden: true, width: 20, key: true, sortable: false},
            {label: '名称', name: 'routerGroup.groupName', align: 'center', width: 80, sortable: false},
            {label: '备注', name: 'routerGroup.remark', align: 'center', width: 160 ,sortable: false},
            {label: '应用列表', name: 'applicationNames', align: 'center', width: 160},
            {label: '创建时间', name: 'routerGroup.createdTime', formatter:timeFormat, align: 'center', width: 120 ,sortable: false},
            {label: '修改时间', name: 'routerGroup.modifiedTime', formatter:timeFormat, align: 'center', width: 120 ,sortable: false},
            {label: '更改人', name: 'routerGroup.updatedByUserName', align: 'center', width: 80 ,sortable: false}
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
            appCode: null,
            remark: null,
            applicationIdList: [],
            updatedByUserName: null
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
            $('#checkRouterName').hide();

            vm.showList = false;
            vm.title = "新增";
            vm.appCodeList = [];
            vm.routerGroup = {
                id:null,
                groupName: null,
                appCode: null,
                remark:null,
                updatedByUserName: null
            };
            vm.applicationNames = null;

            // 加载应用树

            // $.get(baseURL + "applications?userId=" + userId + "&page=1&limit=1000", function (response) {
            $.get(baseURL + "applications/withoutroutergroup?userId=" + userId , function (response) {
                applicationTree = $.fn.zTree.init($("#applicationTree"), applicationTreeSetting, response);
                applicationTree.expandAll(true);
            });
        },
        /**  更新按钮点击事件 */
        update: function () {
            $('#checkRouterName').hide();

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
            // $.get(baseURL + "applications?userId=" + userId + "&page=1&limit=1000", function (response) {
            $.get(baseURL + "applications/withoutroutergroup?userId=" + userId + "&routerGroupId=" + routerGroupId , function (response) {
                applicationTree = $.fn.zTree.init($("#applicationTree"), applicationTreeSetting, response);
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
            window.parent.swal({
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
            var obj = new Object();
            vm.routerGroup.updatedByUserName = localStorage.getItem("userName");
            obj.routerGroup = vm.routerGroup;
            obj.applicationIds = vm.applicationIds;
            obj.appCodeList = vm.appCodeList;
            // alert(JSON.stringify(obj));
            if(vm.routerGroup.groupName === null || $.trim(vm.routerGroup.groupName) == ""){
                window.parent.swal("", "名称不能为空", "warning");
                return;
            }

            if (vm.routerGroup.groupName.length > 30) {
                window.parent.swal("", "名称长度不能大于30", "warning");
                return;
            }

            if(vm.routerGroup.remark === null || $.trim(vm.routerGroup.remark) == ""){
                window.parent.swal("", "备注不能为空", "warning");
                return;
            }

            if (vm.appCodeList == null || vm.appCodeList.length == 0) {
                window.parent.swal("", "请添加应用", "warning")
                return;
            }

            var id = vm.routerGroup.id;
            var b = false;
            var groupName = $('#groupName').val();
            if (id == null) {
                id = 0;
            }
            $.get(baseURL + "routergroups/checkname?groupName=" + groupName + "&id=" + id , function (response) {
                console.log("sure: " + JSON.stringify(response.data));
                if (response.data == Boolean(false)) {
                    b = false;
                } else {
                    b = true;
                }
            });

            if (!b) {
                window.parent.swal("", "此应用组已被使用", "warning");
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
                    window.parent.swal("操作成功!", "", "success");
                },
                error: function (response) {
                    window.parent.swal("", getExceptionMessage(response), "error");
                }
            });
        },
        /**  根据ID获取应用信息 */
        getrouterGroup: function (routerGroupId) {
            $.get(baseURL + "routergroups/" + routerGroupId, function (response) {

                response = response.data;

                if (response) {
                    vm.routerGroup.id = response.routerGroup.id;
                    vm.routerGroup.groupName = response.routerGroup.groupName;
                    vm.routerGroup.appCode = response.routerGroup.appCode;
                    vm.routerGroup.remark = response.routerGroup.remark;
                    vm.routerGroup.applicationIdList = response.applicationIdList;
                    vm.appCodeList = response.appCodeList;
                    vm.applicationNames = response.applicationNames;


                    // console.log(JSON.stringify(vm.routerGroup.applicationList));
                    // console.log(JSON.stringify(response.applicationIdList))

                    // // 勾选已有应用
                    // $.each(response.applicationIdList, function (index, item) {
                    //     var node = applicationTree.getNodeByParam("id", item);
                    //     applicationTree.checkNode(node, true, false);
                    // });

                    // $.each(response.applicationNames, function (index, item) {
                    //     vm.applicationNames.push(item);
                    // })
                }
            });
        },
        /**  应用树点击事件 */
        applicationTree: function (routerGroup) {

            $('#laySearchName').val('');
            vm.getAppList('');

            // 勾选已有应用
            $.each(routerGroup.applicationIdList, function (index, item) {
                var node = applicationTree.getNodeByParam("id", item);
                applicationTree.checkNode(node, true, false);
            });

            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择租户",
                area: ['300px', '450px'],
                shade: 0.3,
                shadeClose: false,
                content: jQuery("#applicationLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    vm.applicationNames = [];
                    vm.appCodeList = [];
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
                    // console.log("routerGroup Id == " + JSON.stringify(vm.applicationIds));
                    // console.log("routerGroup appCode == " + JSON.stringify(vm.appCodeList));
                    layer.close(index);
                }
            });

            $('#laySearch').on("click", function () {

                var searchName = $('#laySearchName').val();
                vm.getAppList(searchName);

            });
        },

        checkRouterName: function () {
            var groupName = $('#groupName').val();
            var id = vm.routerGroup.id;

            if (id == null) {
                id = 0;
            }

            $.get(baseURL + "routergroups/checkname?groupName=" + groupName + "&id=" + id , function (response) {
                if (response.data == Boolean(false)) {
                    $('#checkRouterName').show();
                } else {
                    $('#checkRouterName').hide();
                }

            });

        },

        getAppList: function (searchName) {
            $.get(baseURL + "applications/withoutroutergroup?userId=" + userId + "&searchName=" + searchName + "&routerGroupId=" + vm.routerGroup.id, function (response) {
                applicationTree = $.fn.zTree.init($("#applicationTree"), applicationTreeSetting, response);
                applicationTree.expandAll(true);
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