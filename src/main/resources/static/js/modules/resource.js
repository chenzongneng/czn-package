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
            {label: '资源ID', name: 'id', align: 'center', hidden: true, index: "id", width: 20, key: true},
            // {label: '应用名称', name: 'applicationName', align: 'center', width: 40},
            {label: '资源名称', name: 'name', align: 'center', width: 40},
            {label: '路径标识', name: 'path', align: 'center',  width: 70},
            {label: 'actions', name: 'actions', align: 'center',  width: 70}

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
    },
    typeSearchList: {
        searchType: "",
        options: []
    }
};

var typeList = {
    // 类型选择框应用列表数据
    typeSearchList: {
        searchType: "",
        searchTypeOptions: []
    }
};

var vm = new Vue({
    el: '#garnetApp',
    data: {
        test:null,
        showResourceDetail: false,
        resourceDetail: null,
        searchType: null,
        searchApp: null,
        name: null,
        searchName: null, //根据名称搜索
        showList: true,
        showList1: true,
        showParentCode: false,
        title: null,
        resourceDynamicPropertyList : [],
        resource: {
            id: null,
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
            varchar00description: null,
            varchar01description: null,
            varchar02description: null,
            varchar03description: null,
            varchar04description: null,
            varchar05description: null,
            varchar06description: null,
            varchar07description: null,
            varchar08description: null,
            varchar09description: null,
            varchar10description: null,
            varchar11description: null,
            varchar12description: null,
            varchar13description: null,
            varchar14description: null,
            varchar15description: null,
            varchar16description: null,
            varchar17description: null,
            varchar18description: null,
            varchar19description: null,
            int01description: null,
            int02description: null,
            int03description: null,
            int04description: null,
            int05description: null,
            boolean01description: null,
            boolean02description: null,
            boolean03description: null,
            boolean04description: null,
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
            options: []
        },
        option: {
            applicationId: 1,
            appSearchId: ""
        },
        searchTypeOption: {
            searchType: ""
        }
    },
    mounted:function () {

        for(var i=0; i<20;i++){
            var p = new Object();
            p.description = '';
            p.filedName= 'varchar0'+i;
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
        //查看按钮
        view: function () {
            if (vm.searchType == null || vm.searchApp == null) {
                swal("应用和类型都不能为空", "", "error");
            }

            $.get(baseURL + "resources/byappandtype?applicationId=" + vm.searchApp + "&type=" + vm.searchType, function (response) {
                vm.resourceDetail = JSON.stringify(response, null, "\t");
            });
            vm.showList = false;
            vm.showList1 = true;
            vm.showResourceDetail = true;

        },
        //导入Excel
        inputExcel: function () {
          console.log("导入Excel");
          $('#file').click();
        },
        importFile: function (ele) {

            var file = document.getElementById("file").files[0];
            var formData = new FormData();
            formData.append('file',file);    // 将文件转成二进制形式

            console.log("开始发送请求... ");

            $.ajax({
                type: "POST",
                url: baseURL + "/upload/resourceexcel",
                processData : false,
                contentType : undefined,
                data: formData ,
                // dataType: "",
                success: function (result) {
                    vm.reload(false);
                    swal("导入Resource成功", "", "success");
                },
                error: function (result) {
                    console.log("import result == " + JSON.stringify(result));
                    if (result.status == 200 && result.readyState == 4) {
                        swal("导入Resource成功", "", "success");
                    } else {
                        swal("导入Resource失败", "", "error");
                    }
                }
            });

            //重新选择同一个文件的时候，可以重复上传
            $('#file').val('');
        },
        /**  新增按钮点击事件 */
        add: function () {
            vm.showList = false;
            vm.showList1 = false;
            vm.title = "新增";
            vm.tenantList.selectedTenant = "";
            vm.typeList.selectedTenant = "";
            vm.tenantList.options = [];
            vm.typeList.options = [];
            applicationList.appList.selectedApp = "";
            applicationList.appList.options = [];
            vm.resource = {
                id: null,
                applicationId: null,
                tenantId: null,
                type: null,
                name: null,
                orderNum: 0,
                status: 1
            };
            if(vm.option.appSearchId !== undefined && vm.option.appSearchId !== null && vm.option.appSearchId !== ""){
                vm.resource.applicationId = vm.option.appSearchId;
            }
            vm.getAppList();
            vm.getTenantList();
            vm.getTypeList();
            vm.getResourceDynamicPropertyByType();
            // vm.getResourceDynamicPropertyById();
            // vm.initTreesToAdd();
            // vm.loadResourceTree();
        },
        /**  更新按钮点击事件 */
        update: function () {
            var resourceId = getSelectedRow();

            if (!resourceId) {
                return;
            }
            vm.showList = false;
            vm.showList1 = false;
            vm.title = "修改";
            vm.typeList.options = [];
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
           // obj.typeId = vm.typeList.selectedType;
            obj.resource = vm.resource;
            obj.resource.type = vm.typeList.selectedType;
            obj.resource.tenantId = vm.tenantList.selectedTenant;
            obj.resource.applicationId = applicationList.appList.selectedApp;

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
            // $.get(baseURL + "/apis/applicationId/" + vm.resource.applicationId, function (response) {
            //     apiTree = $.fn.zTree.init($("#apiTree"), apiTreeSetting, response);
            //     apiTree.expandAll(false);
            // })
        },
        /** 更新按钮初始化数据 */
        initTreesToUpdate: function (resourceId) {
            vm.getTenantList();
            vm.getTypeList();
            vm.getResourceById(resourceId);
        },
        /** 通过id 得到一个resource对象 */
        getResourceById: function (resourceId) {
            $.get(baseURL + "resources/" + resourceId, function (response) {
                response = response.data;

                vm.resource.id = response.id;
                vm.resource.applicationId = response.applicationId;
                vm.resource.name = response.name;
                vm.resource.path = response.path;
                vm.resource.actions = response.actions;
                vm.resource.status = response.status;
                applicationList.appList.selectedApp = response.applicationId;
                vm.tenantList.selectedTenant = response.tenantId;
                vm.resource.tenantId = response.tenantId;
                vm.resource.type = response.type;
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
                vm.getResourceDynamicPropertyByType();

            });
        },
        /** 查询当前用户信息 */
        getCurrentUser: function () {
            $.getJSON(baseURL + "token/userInfo?token=" + accessToken, function (response) {
                vm.currentUser = response;
            });
        },
        /** 重新加载 */
        reload: function (backFirst) {
            vm.showList = true;
            vm.showList1 = true;
            var page;
            if(backFirst) {
                page = 1;
            }else {
                page = $("#jqGrid").jqGrid('getGridParam', 'page');
            }
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    searchName: vm.searchName,
                    applicationId: vm.searchApp,
                    type: vm.searchType
                },
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
            var type = vm.typeList.selectedType;
            vm.getResourceDynamicPropertyByType(type);
        },
        //通过type和appId返回resource
        getResourceByTypeAndApp: function () {
            vm.searchType = vm.searchTypeOption.searchType;
            vm.searchApp = vm.option.appSearchId;

            console.log("type: " + vm.searchType + " == applicationId: " + vm.searchApp);
            vm.reload(true);
        },
        /**  获取应用列表 */
        getAppList: function () {
            $.get(baseURL + "applications?userId=" + userId +"&page=1&limit=1000", function (response) {
                $.each(response.list, function (index, item) {
                    applicationList.appList.options.push(item);
                    applicationList.appSearchList.options.push(item);
                })
            });

           // console.log("aaaa == " + JSON.stringify(applicationList.appList.options));

        },
        /**  获取租户列表 */
        getTenantList: function () {
            $.get(baseURL + "tenants?userId=" + userId + "&page=1&limit=1000", function (response) {
                $.each(response.list, function (index, item) {
                    vm.tenantList.options.push(item);
                })
            });

           // console.log("bbbb == " + JSON.stringify(vm.tenantList.options));

        },
        /**  获取类型列表 */
        getTypeList: function () {
            $.get(baseURL + "resourcedynamicpropertys?page=1&limit=1000", function (response) {
                $.each(response.list, function (index, item) {
                    vm.typeList.options.push(item);
                    // typeList.typeSearchList.options.push(item);
                })
                // console.log("cccc == " + JSON.stringify(typeList.typeSearchList.options));
            });
        },
        getSearchTypeList: function () {
            $.get(baseURL + "resourcedynamicpropertys?page=1&limit=1000", function (response) {
                $.each(response.list, function (index, item) {
                    // vm.typeList.options.push(item);
                    // typeList.typeSearchList.searchTypeOptions.push(item);
                    applicationList.typeSearchList.options.push(item)
                })
            });
        },
        //获取动态资源列表
        getResourceDynamicPropertyById: function (resourceDynamicPropertyId) {
            $.get(baseURL + "resourcedynamicpropertys/" + resourceDynamicPropertyId, function (response) {
                response = response.data;
                vm.resourceDynamicProperty.id = response.id;
                vm.resourceDynamicProperty.applicationId = response.applicationId;
                vm.resourceDynamicProperty.type = response.resourceDynamicProperty.type;
                vm.resourceDynamicPropertyList = response.resourceDynamicPropertyList;
                applicationList.appList.selectedApp = response.applicationId;
            });
        },
        //通过type获取动态资源列表的description
        getResourceDynamicPropertyByType: function () {
            var type = vm.typeList.selectedType;
            if(type == null || type == "") {
                return;
            }

            $.get(baseURL + "resourcedynamicpropertys/type/" + type, function (response) {
                response = response.data;
                vm.resourceDynamicPropertyList = response.resourceDynamicPropertyList;

                $.each(response.resourceDynamicPropertyList, function (index, item) {
                    if (""+ item.filedName == "varchar00") {vm.resource.varchar00description = item.description;}
                    if (""+ item.filedName == "varchar01") {vm.resource.varchar01description = item.description;}
                    if (""+ item.filedName == "varchar02") {vm.resource.varchar02description = item.description;}
                    if (""+ item.filedName == "varchar03") {vm.resource.varchar03description = item.description;}
                    if (""+ item.filedName == "varchar04") {vm.resource.varchar04description = item.description;}
                    if (""+ item.filedName == "varchar05") {vm.resource.varchar05description = item.description;}
                    if (""+ item.filedName == "varchar06") {vm.resource.varchar06description = item.description;}
                    if (""+ item.filedName == "varchar07") {vm.resource.varchar07description = item.description;}
                    if (""+ item.filedName == "varchar08") {vm.resource.varchar08description = item.description;}
                    if (""+ item.filedName == "varchar09") {vm.resource.varchar09description = item.description;}
                    if (""+ item.filedName == "varchar10") {vm.resource.varchar10description = item.description;}
                    if (""+ item.filedName == "varchar11") {vm.resource.varchar11description = item.description;}
                    if (""+ item.filedName == "varchar12") {vm.resource.varchar12description = item.description;}
                    if (""+ item.filedName == "varchar13") {vm.resource.varchar13description = item.description;}
                    if (""+ item.filedName == "varchar14") {vm.resource.varchar14description = item.description;}
                    if (""+ item.filedName == "varchar15") {vm.resource.varchar15description = item.description;}
                    if (""+ item.filedName == "varchar16") {vm.resource.varchar16description = item.description;}
                    if (""+ item.filedName == "varchar17") {vm.resource.varchar17description = item.description;}
                    if (""+ item.filedName == "varchar18") {vm.resource.varchar18description = item.description;}
                    if (""+ item.filedName == "varchar19") {vm.resource.varchar19description = item.description;}
                    if (""+ item.filedName == "int01") {vm.resource.int01description = item.description;}
                    if (""+ item.filedName == "int02") {vm.resource.int02description = item.description;}
                    if (""+ item.filedName == "int03") {vm.resource.int03description = item.description;}
                    if (""+ item.filedName == "int04") {vm.resource.int04description = item.description;}
                    if (""+ item.filedName == "int05") {vm.resource.int05description = item.description;}
                    if (""+ item.filedName == "boolean01") {vm.resource.boolean01description = item.description;}
                    if (""+ item.filedName == "boolean02") {vm.resource.boolean02description = item.description;}
                    if (""+ item.filedName == "boolean03") {vm.resource.boolean03description = item.description;}
                    if (""+ item.filedName == "boolean04") {vm.resource.boolean04description = item.description;}
                });
            });
        },
        //加载资源树
        loadResourceTree: function () {

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
        this.getSearchTypeList();
    }
});
