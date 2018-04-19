/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

$(function () {
    /**  初始化租户列表  */
    $("#jqGrid").jqGrid({
        // url: baseURL + 'tenants?userId='+localStorage.getItem("userId"),
        url: baseURL + 'tenants?userId=' + userId ,
        datatype: "json",
        postData: {
            "mode": localStorage.getItem("mode"),
            "token": accessToken
        },
        colModel: [
            {label: '租户ID', name: 'id', align: 'center', hidden: true, width: 20, key: true ,sortable: false},
            {label: '租户名称', name: 'name', align: 'center', width: 80 ,sortable: false},
            {label: '创建时间', name: 'createdTime', formatter:timeFormat, align: 'center', width: 150 ,sortable: false},
            {label: '修改时间', name: 'modifiedTime', formatter:timeFormat, align: 'center', width: 150 ,sortable: false},
            {label: '更改人', name: 'updatedByUserName', align: 'center', width: 80 ,sortable: false},
            {label: '备注', name: 'description', align: 'center', width: 80 ,sortable: false}
            // {label: '创建时间', name: 'createTime', align: 'center', width: 80}
        ],
        viewrecords: false,
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

function forMatTime(time) {
    return new Date(time).Format('yyyy-MM-dd')
}

var vm = new Vue({
    el: '#garnetApp',
    data: {
        searchName: null,
        showList: true,
        title: null,
        userName:null,
        userNames:null,
        hidden:false,
        tips:null,
        // placeholder:false, //选择应用框，根据选择模式的不同给出相对应的提示
        modeId: 1,// SAAS为0，PAAS为1
        mode: null,
        disabled: null,
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
            selectedMode: localStorage.getItem("mode"),
            options: [
                {
                    id : "all",
                    name : "平台模式-all"
                },
                {
                id : "saas",
                name : "平台模式-saas"
            },{
                id : "paas",
                name : "平台模式-paas"
            }]
        },
        // 新增和更新 选择模式列表
        modeList2: {
            selectedMode: localStorage.getItem("mode"),
            options: [
                {
                    id : "saas",
                    name : "saas"
                },{
                    id : "paas",
                    name : "paas"
                }]
        }
    },
    mounted:function () {

        if (localStorage.getItem("mode") == "saas") {
            $("#selectModeId option[value='paas']").remove();
            $("#selectModeId option[value='all']").remove();
        } else if (localStorage.getItem("mode") == "paas") {
            $("#selectModeId option[value='all']").remove();
            $("#selectModeId option[value='saas']").remove();
        }

    },
    methods: {
        /**  查询按钮点击事件 */
        query: function () {
            vm.reload(true);
        },
        /**  新增按钮点击事件 */
        add: function () {
            var mode;
            if ("all" == localStorage.getItem("mode")) {
                mode = "paas";
                vm.tips = "可选多个应用";
                vm.modeList2.selectedMode = mode;
            } else if ("paas" == localStorage.getItem("mode")) {
                mode = "paas";
                vm.tips = "可选多个应用";
                vm.modeList2.selectedMode = mode;
            } else {
                vm.tips = "只能选择一个应用";
                mode = localStorage.getItem("mode");
            }

            vm.showList = false;
            vm.hidden = false;
            vm.title = "新增";
            vm.userNames = null;
            vm.tenant = {
                id: null,
                name: null,
                description: null,
                updatedByUserName: null,
                appIds: null,
                appNames: [],
                appIdList: []
            };

            // 加载应用树
            $.get(baseURL + "applications?page=1&limit=1000&mode=" + mode + "&userId=" + userId, function (response) {
                appTree = $.fn.zTree.init($("#appTree"), appTreeSetting, response.list);
                appTree.expandAll(true);

            });

        },
        /**  更新按钮点击事件 */
        update: function () {
            vm.hidden = true;
            var mode;

            if ("all" == localStorage.getItem("mode")) {
                mode = "paas";
                vm.tips = "可选多个应用";
                vm.modeList2.selectedMode = mode;
            } else if ("paas" == localStorage.getItem("mode")) {
                mode = "paas";
                vm.tips = "可选多个应用";
                vm.modeList2.selectedMode = mode;
            } else {
                vm.tips = "只能选择一个应用";
                mode = localStorage.getItem("mode");
            }

            var tenantId = getSelectedRow();
            if (!tenantId) {return;}
            vm.showList = false;
            vm.title = "修改";
            vm.tenant.description = null,
            vm.tenant.appIds = null;
            vm.tenant.appNames = [];
            vm.userName = null;
            vm.userNames = null;

            vm.getTenant(tenantId);
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
                        url: baseURL + "tenants?ids=" + tenantIds.toString() + "&token=" + accessToken,
                        contentType: "application/json",
                        dataType: "",
                        success: function () {
                            swal("删除成功!", "", "success");
                            vm.reload(false);
                        },
                        error: function (response) {
                            swal("删除失败!", getExceptionMessage(response), "error");
                        }

                    });
                });
        },
        /**  新增或更新确认 */
        saveOrUpdate: function () {
            // alert(JSON.stringify(vm.tenant));
            var obj = new Object();
            vm.tenant.updatedByUserName = localStorage.getItem("userName");

            if (vm.tenant.id == null) {
                //如果是新增租户，默认绑定登录用户
                vm.userName = localStorage.getItem("userName");
            }

            var userName = vm.userName;
            obj.tenant = vm.tenant;
            obj.appIds =vm.tenant.appIds;
            obj.userTenants = [];
            obj.userName = userName;
            obj.userNames = vm.userNames;
            var appIdList = vm.tenant.appIdList;

            // alert(JSON.stringify(obj));
            if(vm.tenant.name == null || $.trim(vm.tenant.name) == ""){
                swal("", "租户名称不能为空", "error");
                return;
            }

            if (vm.tenant.name.length > 30) {
                swal("", "租户名称长度不能大于30", "error");
                return;
            }

            var mode = vm.modeList2.selectedMode;

            if (mode == null) {
                vm.mode = vm.modeList.selectedMode;
                localStorage.setItem("mode", vm.mode)
            }

            if(mode == "saas") {  //saas
                vm.tenant.serviceMode = "saas";
                if (appIdList != null && appIdList.length > 1) {
                    swal("", "当前模式不能添加多个应用", "error");
                    return;
                }
                // console.log("my modeId is : " + vm.modeId);
            } else if(mode == "paas") {
                vm.tenant.serviceMode = "paas";
            } else {
                // swal({
                //     title: "当前默认为PAAS模式，是否确认添加",
                //         type: "warning",
                //     showCancelButton: true,
                //     closeOnConfirm: false,
                //     confirmButtonText: "确认",
                //     cancelButtonText: "取消",
                //     confirmButtonColor: "#DD6B55"
                // }, function () {
                //     $.ajax({
                //         type: obj.tenant.id === null ? "POST" : "PUT",
                //         url: baseURL + "tenants?token=" + accessToken ,
                //         contentType: "application/json",
                //         data: JSON.stringify(obj),
                //         dataType: "",
                //         success: function () {
                //             vm.reload(false);
                //             swal("操作成功!", "", "success");
                //         },
                //         error: function (response) {
                //             swal(response.responseJSON.data.errorResponseMessage, "",  "error");
                //         }
                //     });
                // });
                swal("", "请选择正确模式", "error");
                return;
            }

            $.ajax({
                type: obj.tenant.id === null ? "POST" : "PUT",
                url: baseURL + "tenants?token=" +　accessToken,
                contentType: "application/json",
                data: JSON.stringify(obj),
                dataType: "",
                success: function () {
                    vm.reload(false);
                    swal("操作成功!", "", "success");
                },
                error: function (response) {
                    swal("", getExceptionMessage(response),  "error");
                }
            });

        },
        /**  根据ID获取租户信息 */
        getTenant: function (tenantId) {
            $.get(baseURL + "tenants/" + tenantId + "?token=" +　accessToken, function (response) {
                // alert(JSON.stringify(response.data.tenant));
                response=response.data;
                var mode;
                if (response) {
                    vm.tenant.id = response.tenant.id;
                    vm.tenant.name = response.tenant.name;
                    vm.tenant.description = response.tenant.description;
                    vm.tenant.createdTime = response.tenant.createdTime;
                    vm.tenant.modifiedTime = response.tenant.modifiedTime;
                    vm.tenant.appIdList = response.appIdList;
                    vm.modeList2.selectedMode = response.tenant.serviceMode;

                    // vm.userName = response.userName;
                    // $.each(response.appIdList, function (index, item) {
                    //     var node = appTree.getNodeByParam("id", item);
                    //     appTree.checkNode(node, true, false);
                    // });
                    $.each(response.appNameList, function (index, item) {
                        vm.tenant.appNames.push(item);
                    })

                    mode = response.tenant.serviceMode;
                    if (mode == "saas") {
                        vm.modeId = 0;
                    } else if (mode == "paas") {
                        vm.modeId = 1;
                    } else {
                        vm.modeId = -1;
                    }
                }

                // 加载应用树
                $.get(baseURL + "applications?page=1&limit=1000&mode=" + mode + "&userId=" + userId, function (response) {
                    appTree = $.fn.zTree.init($("#appTree"), appTreeSetting, response.list);
                    appTree.expandAll(true);
                });
            });
        },
        /**  应用树点击事件 */
        appTree: function (tenant) {
            $.each(tenant.appIdList, function (index, item) {
                var node = appTree.getNodeByParam("id", item);
                if (node == null) {
                    console.log("应用树点击事件 null");
                    return;
                }
                appTree.checkNode(node, true, false);
            });

            var title;
            console.log("vm.mode : " + JSON.stringify(vm.modeList2.selectedMode));
            if (vm.modeList2.selectedMode == "paas") {
                title = "选择应用（可选多个）";
                console.log(title);
            } else if (vm.modeList2.selectedMode == "saas") {
                title = "选择应用（只能选择一个）";
            }

            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: title,
                area: ['300px', '450px'],
                shade: 0.3,
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
            if ("all" == vm.modeList.selectedMode) {
                vm.modeList2.selectedMode == "paas";
            } else {
                vm.modeList2.selectedMode = vm.modeList.selectedMode;
            }
            vm.mode = vm.modeList.selectedMode
            localStorage.setItem("mode", vm.mode);
            vm.reload(true);
            console.log("vm.mode == " + vm.mode);
        },
        //模式选择事件
        selectMode2: function () {
            vm.mode = vm.modeList2.selectedMode;
            localStorage.setItem("mode", vm.mode);
            vm.modeList.selectedMode = vm.mode;

            vm.tenant.appNames = null;
            vm.tenant.appIds = null;
            vm.tenant.appIdList = null;

            if (vm.mode == "paas") {
                vm.tips = "可选多个应用";
            } else if (vm.mode == "saas") {
                vm.tips = "只能选择一个应用";
            }
            // 加载应用树
            $.get(baseURL + "applications?page=1&limit=1000&mode=" + vm.mode + "&userId=" + userId, function (response) {
                appTree = $.fn.zTree.init($("#appTree"), appTreeSetting, response.list);
                appTree.expandAll(true);

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
                    'searchName': vm.searchName,
                    'mode' : localStorage.getItem("mode")
                },
                page: page,

            }).trigger("reloadGrid");
        }
    }
});