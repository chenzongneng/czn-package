/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

$(function () {
    /** 初始化动态资源列表 */
    $("#jqGrid").jqGrid({
        url: baseURL + 'resourcedynamicpropertys',
        datatype: "json",
        colModel: [
            {label: '动态资源配置ID', name: 'id', align: 'center', hidden: true, index: "id", width: 20, key: true ,sortable: false},
            {label: '类型名称', name: 'type', align: 'center', width: 40, sortable: false}
            // {label: '应用名称', name: 'applicationName', align: 'center', width: 40},
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

//应用列表
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
        searchName: null,
        resourceDynamicPropertyList : [],
        resourceDynamicProperty: {
            id: null,
            type: null,
            actions: null,
            fieldName: null,
            description:null,
            applicationId:null,
            orderNum: 0
        },
        option: {
            applicationId: 1,
            appSearchId: ""
        }
    },
    mounted:function () {

        for(var i=0; i<10;i++){
            var p = new Object();
            p.description = '';
            p.filedName= 'varchar0'+i;
            this.resourceDynamicPropertyList.push(p);

        }

        for(var i=10; i<20;i++){
            var p = new Object();
            p.description = '';
            p.filedName= 'varchar'+i;
            this.resourceDynamicPropertyList.push(p);

        }

        for(var i=1; i<6; i ++) {
            var property = new Object();
            property.description = '';
            property.filedName= 'int0'+i;
            this.resourceDynamicPropertyList.push(property);
        }

        for(var i=1; i<5; i ++) {
            var property = new Object();
            property.description = '';
            property.filedName= 'boolean0'+i;
            this.resourceDynamicPropertyList.push(property);
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
            applicationList.appList.selectedApp = null;
            //resourceDynamicPropertyList: [];
            // vm.resourceDynamicProperty = {
            //     id: null,
            //     applicationId: 1,
            //     name: null,
            //     code: null,
            //     parentCode: null,
            //     parentName: null,
            //     orderNum: 0
            // };
            // if(vm.option.appSearchId !== undefined && vm.option.appSearchId !== null && vm.option.appSearchId !== ""){
            //     vm.resource.applicationId = vm.option.appSearchId;
            // }
            //vm.getAppList();
            // vm.getTenantList();
            // vm.initTreesToAdd();
            // vm.loadResourceTree();
        },
        /**  更新按钮点击事件 */
        update: function () {
            var resourceDynamicPropertyId = getSelectedRow();
            if (!resourceDynamicPropertyId) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.initTreesToUpdate(resourceDynamicPropertyId);
            // vm.loadResourceTree();
        },
        /**  删除按钮点击事件 */
        del: function () {
            var resourceIds = getSelectedRows();
            var title;
            if (!resourceIds) {
                return;
            }

            $.get(baseURL + "resources/relate?ids=" + resourceIds + "&token=" + accessToken, function (response) {

                console.log("response == " + response);

                if (response == Boolean(true)) {
                    title = "部分资源配置已被资源关联，是否确认删除？";
                } else {
                    title = "确定要删除选中的记录";
                }
            });

            swal({
                    title: title,
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
                        url: baseURL + "resourcedynamicpropertys?ids=" + resourceIds.toString(),
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
                        error: function (result) {

                            console.log(JSON.stringify(result));
                            swal("删除失败!", getExceptionMessage(result), "error");
                        }
                    });
                });
        },
        /**  新增或更新确认 */
        saveOrUpdate: function () {

            // 获取访问权限树选择的访问权限
            var obj = new Object();
            obj.resourceDynamicProperty = vm.resourceDynamicProperty;
            obj.resourceDynamicPropertyList = vm.resourceDynamicPropertyList;

            if(vm.resourceDynamicProperty.type == null || $.trim(vm.resourceDynamicProperty.type) == "") {
                swal("", "资源类型配置名称不能为空", "warning");
                return;
            }

            if (vm.resourceDynamicProperty.type.length > 30) {
                swal("", "资源类型配置名称长度不能大于30", "warning");
                return;
            }

            if (vm.resourceDynamicProperty.actions == null || $.trim(vm.resourceDynamicProperty.actions) == "") {
                swal("", "资源类型配置行为组不能为空", "warning");
                return;
            }

            $.ajax({
                type: vm.resourceDynamicProperty.id === null ? "POST" : "PUT",
                url: baseURL + "resourcedynamicpropertys",
                contentType: "application/json",
                data: JSON.stringify(obj),
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
        /** 添加按钮初始化数据 */
        initTreesToAdd: function () {
            //加载访问权限树
            // $.get(baseURL + "/apis/applicationId/" + vm.resource.applicationId, function (response) {
            //     apiTree = $.fn.zTree.init($("#apiTree"), apiTreeSetting, response);
            //     apiTree.expandAll(false);
            // })
        },
        /** 更新按钮初始化数据 */
        initTreesToUpdate: function (resourceDynamicPropertyId) {
            vm.getResourceDynamicPropertyById(resourceDynamicPropertyId);
        },
        /** 通过id 得到一个resourcedynamicpropertys对象 */
        getResourceDynamicPropertyById: function (resourceDynamicPropertyId) {
            $.get(baseURL + "resourcedynamicpropertys/" + resourceDynamicPropertyId, function (response) {
                response = response.data;

                vm.resourceDynamicProperty.id = response.id;
                vm.resourceDynamicProperty.applicationId = response.applicationId;
                vm.resourceDynamicProperty.type = response.resourceDynamicProperty.type;
                vm.resourceDynamicProperty.actions = response.resourceDynamicProperty.actions;
                vm.resourceDynamicPropertyList = response.resourceDynamicPropertyList;
                applicationList.appList.selectedApp = response.applicationId;


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
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    searchName: vm.searchName,
                    type: vm.type,
                    applicationId: vm.option.appSearchId
                },
                page: page
            }).trigger("reloadGrid");
        },
        /** 应用列表onchange 事件*/
        selectApp: function () {
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
        }
    },
    /**  初始化页面时执行该方法 */
    created: function () {
        // this.getCurrentUser();
        this.getAppList();
    }
});
