/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

$(function () {
    /** 初始化角色列表 */
    $("#jqGrid").jqGrid({
        url: baseURL + 'apis',
        datatype: "json",
        colModel: [
            {
                label: '访问权限ID',
                name: 'apiId',
                align: 'center',
                hidden: true,
                index: "api_id",
                width: 20,
                key: true
            },
            {label: '具体名称', name: 'name', align: 'left', width: 70},
            {label: 'shiro标识符', name: 'permission', align: 'center', width: 70},
            {label: '说明', name: 'description', align: 'center', width: 70},
            {label: '对应的链接', name: 'url', align: 'center', width: 70},
            {label: '方法', name: 'method', align: 'center', width: 70},
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

var applicationList = {
    // 应用列表数据
    appList: {
        selectedApp: "1",
        options: []
    },
    // 搜索框应用列表数据
    appSearchList: {
        selectedApp: "",
        options: []
    },
    appImportList:{
        selectedApp: "",
        options: []
    }
};

/** 访问权限结构树 */
var parentTree;
var parentTreeSetting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "apiId",
            pIdKey: "parentId",
            rootPId: 0
        },
        key: {
            url: "nourl",
            name: "name"
        }
    }
};

var vm = new Vue({
    el: '#garnetApp',
    data: {
        name: null,
        showList: true,
        title: null,
        api: {
            apiId: null
        },
        option: {
            applicationId: 1,
            appSearchId: "",
            appCode:""
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
            vm.title = "新增";
            // applicationList.appList.selectedApp = "1";
            vm.api = {
                apiId: null,
                applicationId: "1",
                name: null,
                parentName: null,
                parentId: 0,
                permission: null,
                description: null,
                url: null,
                method: "GET",
                status: 1
            };
            if(vm.option.appSearchId !== undefined && vm.option.appSearchId !== null && vm.option.appSearchId !== ""){
                applicationList.appList.selectedApp = vm.option.appSearchId;
                vm.api.applicationId = vm.option.appSearchId;
            }
        },
        /**  更新按钮点击事件 */
        update: function () {
            var apiId = getSelectedRow();
            if (!apiId) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.getPermissionById(apiId);
        },
        /**  删除按钮点击事件 */
        del: function () {
            var apiIds = getSelectedRows();
            if (!apiIds) {
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
                        url: baseURL + "api?apiIds=" + apiIds.toString(),
                        contentType: "application/json",
                        dataType: "",
                        success: function (result) {
                            if (!result.message) {
                                swal("删除成功!", "", "success");
                                vm.reload(false);
                            } else {
                                swal("无法删除!", result.message, "error");
                            }
                            vm.reload(false);
                        },
                        error: function () {
                            swal("删除失败!", "系统错误，请联系系统管理员！", "error");
                        }
                    });
                });
        },
        /** 导入按钮点击事件 */
        importApi: function () {
            if(vm.option.appCode === null || vm.option.appCode === "") {
                swal("请选择应用", "", "error");
                return;
            }
            layer.open({
                type: 1,
                title: false,
                area: ['1000px', '600px'],
                closeBtn: 0,
                shadeClose: true,
                anim: 1,
                content:
                // '<div class="form-group col-sm-2">' +
                // '    <select class="form-control" v-model_bk="option.appCode">' +
                // '        <option disabled value=""> 选择应用 </option>' +
                // '        <option v-for="option in applicationList.appImportList.options" v-bind:value="option.code">' +
                // '            {{ option.name }}' +
                // '        </option>' +
                // '    </select>' +
                '<div style="padding:50px;">' +
                '   <textarea id="apiImportTextarea" style="width: 890px; height: 445px"></textarea>' +
                '</div>',
                btn: ['确定', '取消'],
                btn1: function (index) {
                    layer.confirm('批量导入将会覆盖原有数据，是否继续？', {icon: 3, title:'提示'}, function(index){
                        var apiImportJson = $("#apiImportTextarea").val();
                        if(apiImportJson === null || apiImportJson === ""){
                            layer.alert('内容不能为空');
                            return;
                        }
                        $.ajax({
                            type: "POST",
                            url: baseURL + "importApis/" + vm.option.appCode,
                            contentType: "application/json",
                            data: apiImportJson,
                            dataType: '',
                            success: function () {
                                swal("导入成功!", "", "success");
                                vm.reload(false);
                            },
                            error: function () {
                                swal("导入失败！", "", "error");
                            }
                        });
                        layer.closeAll();
                    });
                }
            })
        },
        /**  新增或更新确认 */
        saveOrUpdate: function () {
            $.ajax({
                type: vm.api.apiId === null ? "POST" : "PUT",
                url: baseURL + "api",
                contentType: "application/json",
                data: JSON.stringify(vm.api),
                dataType: '',
                success: function () {
                    vm.reload(false);
                    swal("操作成功!", "", "success");
                },
                error: function (response) {
                    swal("", getExceptionMessage(response), "error");
                }
            });
        },
        /** 更新按钮初始化数据 */
        initTreesToUpdate: function (apiId) {
            vm.getPermissionById(apiId);
        },
        /** 通过id 得到一个api对象 */
        getPermissionById: function (apiId) {
            $.get(baseURL + "api/" + apiId, function (response) {
                vm.api.apiId = response.apiId;
                vm.api.applicationId = response.applicationId;
                vm.api.name = response.name;
                vm.api.permission = response.permission;
                vm.api.description = response.description;
                vm.api.url = response.url;
                vm.api.method = response.method;
                vm.api.status = response.status;
                applicationList.appList.selectedApp = response.appId;
            });
        },
        /** 查询当前用户信息 */
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
                postData: {name: vm.name, applicationId: vm.option.appSearchId},
                page: page
            }).trigger("reloadGrid");
        },
        /** 应用列表onchange 事件*/
        selectApp: function () {

        },
        /** 选择应用导入API onchange 事件*/
        selectImportApp: function () {

        },
        /**  获取应用列表 */
        getAppList: function () {
            $.get(baseURL + "applications?page=1&limit=1000", function (response) {
                $.each(response.list, function (index, item) {
                    applicationList.appList.options.push(item);
                    applicationList.appSearchList.options.push(item);
                    applicationList.appImportList.options.push(item);
                })
            });
        },
        //加载父权限
        loadParentTree: function () {
            $.get(baseURL + "apis?page=1&limit=1000&parentId=0&applicationId=" + vm.api.applicationId, function (response) {
                parentTree = $.fn.zTree.init($("#parentTree"), parentTreeSetting, response.list);
                parentTree.expandAll(true);
            })
        },
        /**  父树点击事件 */
        parentTree: function () {
            vm.loadParentTree();
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择访问权限",
                area: ['300px', '700px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#parentLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = parentTree.getSelectedNodes();
                    //选择上级部门
                    console.log(JSON.stringify(node));
                    vm.api.parentName = node[0].name;
                    vm.api.parentId = node[0].apiId;
                    vm.api.permission = node[0].permission;
                    vm.api.description = node[0].description;
                    vm.api.url = node[0].url;
                    vm.api.method = node[0].method;
                    vm.api.status = node[0].status;
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
