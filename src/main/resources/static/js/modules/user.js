/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

$(function () {
    /** 初始化用户列表 */
    $("#jqGrid").jqGrid({
        url: baseURL + 'users?userId=' + userId,
        datatype: "json",
        colModel: [
            {label: '用户ID', name: 'id', align: 'center', hidden: true, index: "id", width: 20, key: true ,sortable: false},
            {label: '账号', name: 'userName', align: 'center', width: 70 ,sortable: false},
            {label: '手机号码', name: 'mobileNumber', align: 'center', width: 100 ,sortable: false},
            {label: '邮箱', name: 'email', align: 'center', width: 100 ,sortable: false},
            // {label: '是否属于garnet', name: 'belongToGarnet', align: 'center', width: 120 ,sortable: false},
            {label: '创建时间', name: 'createdTime', align: 'center', formatter:timeFormat, width: 150 ,sortable: false},
            {label: '更新时间', name: 'modifiedTime', align: 'center', formatter:timeFormat, width: 150 ,sortable: false},
            {label: '更改人', name: 'updatedByUserName', align: 'center', width: 80 ,sortable: false}
            // {
            //     label: '状态', align: 'center', name: 'status', width: 50, formatter: function (value, options, row) {
            //     return value === 0 ?
            //         '<span class="label label-danger">禁用</span>' :
            //         '<span class="label label-success">正常</span>';
            // }
            // }
            // {label: '创建时间', align: 'center', name: 'createTime', width: 90}
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
        // 在URL中传递参数
        postData: {
            token: accessToken
        },
        gridComplete: function () {
            // 隐藏grid底部滚动条
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

/** 应用树 */
var applicationTree;
/** ztree 配置*/
var applicationTreeSetting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id"
        },
        key: {
            url: "nourl"
        }
    },
    check: {
        enable: true,
        nocheckInherit: true,
        chkboxType: {"Y": "", "N": ""}
    }
};

/** 部门树 */
var deptTree;
/** ztree 配置*/
var deptTreeSetting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "departmentId",
            pIdKey: "parentDepartmentId",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    },
    check: {
        enable: true,
        nocheckInherit: true,
        chkboxType: {"Y": "", "N": ""}
    }
};


/** 租户树 */
var tenantTree;
/** ztree 配置*/
var tenantTreeSetting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id"
        },
        key: {
            url: "nourl",
            name: "name"
        }
    },
    check: {
        enable: true,
        nocheckInherit: true,
        chkboxType: {"Y": "", "N": ""}
    }
};

/** 组树 */
var groupTree;
/** ztree 配置*/
var groupTreeSetting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id"
        },
        key: {
            url: "nourl",
            name: "name"
        }
    },
    check: {
        enable: true,
        nocheckInherit: true,
        chkboxType: {"Y": "", "N": ""}
    }
};

var currentUser;
var vm = new Vue({
    el: '#garnetApp',
    data: {
        searchName: null,
        showList: true,
        readusername:true,
        title: null,
        belongToGarnet:false,

        multiple: {
            originOptionsTenant: [],
            selectedListTenant: []
        },
        // 用户信息
        // user: {
        //     userId: null,
        //     tenantId: null,
        //     deptIds: null,
        //     userName: null,
        //     password: null,
        //     name: null,
        //     email: null,
        //     mobile: null,
        //     status: null
        // },

        user: {

        },
        tenantIds: [], //租户树选择的id
        groupIds: [], //组树选择的id
        // 租户列表数据
        tenantList: {
            selectedTenant: "",
            options: []
        },
        // 应用列表数据
        appList: {
            selectedApp: "",
            options: []
        },
    },

    mounted: function () {

    },

    methods: {

        queryData: function (method) {

            var mySelf = this;
            //do ajax here
            //获取租户信息
            $.get(baseURL + "tenants?page=1&limit=1000", function (response) {
                mySelf.multiple.originOptionsTenant = response.list;
                // console.log("originOptionsTenant" + JSON.stringify(vm.multiple.originOptionsTenant))
            });
            if (method == "update") {
                // alert(vm.user.id);
                $.get(baseURL + "tenants?page=1&limit=1000&userId="+vm.user.id, function (response) {
                    mySelf.multiple.selectedListTenant= response.list;
                });

            } else {

                mySelf.multiple.selectedListTenant = [];
            }
            // 多选
            // mySelf.multiple.originOptionsTenant = [{"id":"1","name":"lemon"},{"id":"2","name":"mike"},{"id":"3","name":"lara"},{"id":"4","name":"zoe"},{"id":"5","name":"steve"},{"id":"6","name":"nolan"}];
            // mySelf.multiple.selectedListTenant = [{"id":"1","name":"lemon"},{"id":"3","name":"lara"}]


            // this.$nextTick(function () {
            //
            // })
        },
        multipleCallback: function (data) {
            console.log("multipleCallbace");
            this.multiple.selectedListTenant = data;
            console.log('父级元素调用multipleSelected 选中的是' + JSON.stringify(data))
        },
        /**  查询按钮点击事件 */
        query: function () {
            vm.reload(true);
        },
        /**  新增按钮点击事件 */
        add: function () {
            vm.showList = false;
            vm.readusername = false;
            vm.title = "新增";
            vm.tenantList.selectedTenant = "";
            vm.tenantList.options = [];
            vm.appList.selectedApp = "";
            vm.appList.options = [];
            vm.user = {
                id: null,
                belongToGarnet : 'N'
            };
            this.queryData("add");
            vm.initTreeToAdd();
            // if ("Y" == localStorage.getItem("belongToGarnet")) {
            //     vm.belongToGarnet = true;
            // } else {
            //     vm.belongToGarnet = false;
            // }

        },
        /**  更新按钮点击事件 */
        update: function () {
            var userId = getSelectedRow();

            if (!userId) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.readusername = true;
            vm.tenantList.options = [];
            vm.appList.options = [];
            vm.user.applicationIdList = [];
            vm.user.deptIdList = [];
            vm.belongToGarnet = false;
            vm.tenantIds = [];
            vm.groupIds = [];

            vm.getUser(userId);
            this.queryData("update");

            // vm.initTreeToUpdate(userId);
            // vm.getTenantList();
            // vm.getAppList();
        },
        /**  删除按钮点击事件 */
        del: function () {
            var userIds = getSelectedRows();

            if (!userIds) {
                return;
            }
            // if (userIds.includes(currentUser.userId.toString())) {
            //     swal("您不能删除自己!", "", "error");
            //     return;
            // }
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
                        url: baseURL + "users?loginUserId=" + userId +"&ids=" + userIds.toString(),
                        contentType: "application/json",
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
            vm.user.updatedByUserName = localStorage.getItem("userName");
            obj.loginUserId = userId;
            obj.user = vm.user;
            obj.userTenants = [];
            obj.password = vm.user.password;
            if (!vm.checkValue()) {
                return;
            }

            // 获取租户树选择的租户
            var tenantNodes = tenantTree.getCheckedNodes(true);
            var tenantIdList = [];
            for (var i = 0; i < tenantNodes.length; i++) {
                tenantIdList.push(tenantNodes[i].id);
            }
            vm.tenantIds = tenantIdList.join(",");
            obj.tenantIds = tenantIdList;

            // 获取组树选择的租户
            var groupNodes = groupTree.getCheckedNodes(true);
            var groupIdList = [];
            for (var i = 0; i < groupNodes.length; i++) {
                groupIdList.push(groupNodes[i].id);
            }
            vm.groupIds = groupIdList.join(",");
            obj.groupIds = groupIdList;

            //获取选择的租户
            // for (var i = 0; i < vm.multiple.selectedListTenant.length; i++) {
            //     var userTenant = new Object();
            //     userTenant.tenantId = vm.multiple.selectedListTenant[i].id;
            //     obj.userTenants.push(userTenant);
            // }

            // console.log("tenantIdList: " + tenantIdList);
            // console.log("tenantIdList.length: " + tenantIdList.length);
            // console.log("tenantIdList.length type: " + typeof tenantIdList.length);
            if (tenantIdList == null || tenantIdList.length == 0) {
                // console.log("我应该进来的呀");
                // window.parent.swal("", "您没有为用户关联租户，后续可能无权管理该用户", "warning");
                window.parent.swal({
                        title: "",
                        text: "您没有为用户关联租户，后续可能无权管理该用户",
                        type: "warning",
                        showCancelButton: true,
                        closeOnConfirm: false,
                        confirmButtonText: "确认",
                        cancelButtonText: "取消",
                        confirmButtonColor: "#DD6B55"
                    },
                    function () {
                        $.ajax({
                            type: obj.user.id === null ? "POST" : "PUT",
                            url: baseURL + "users",
                            contentType: "application/json",
                            data: JSON.stringify(obj),
                            dataType: "",
                            success: function () {
                                vm.reload(false);
                                window.parent.swal("操作成功!", "", "success");
                            },
                            error: function (response) {
                                window.parent.swal("", getExceptionMessage(response), "error");
                            }
                        });
                    });
            } else {
                $.ajax({
                    type: obj.user.id === null ? "POST" : "PUT",
                    url: baseURL + "users",
                    contentType: "application/json",
                    data: JSON.stringify(obj),
                    dataType: "",
                    success: function () {
                        vm.reload(false);
                        window.parent.swal("操作成功!", "", "success");
                    },
                    error: function (response) {
                        window.parent.swal("", getExceptionMessage(response), "error");
                    }
                });
            }


        },
        /** 添加按钮初始化数据 */
        initTreeToAdd: function () {
            //加载租户树
            $.get(baseURL + "tenants/bylevel?userId=" + userId, function (response) {
                // console.log(JSON.stringify(response.data));
                tenantTree = $.fn.zTree.init($("#tenantTree"), tenantTreeSetting, response.data);
                tenantTree.expandAll(true);
            });

            // 加载组树
            $.get(baseURL + "groups/bylevel?userId=" + userId, function (response) {
                // console.log(JSON.stringify(response));
                groupTree = $.fn.zTree.init($("#groupTree"), groupTreeSetting, response);
                groupTree.expandAll(true);
            });

            // //加载应用树
            // $.get(baseURL + "applications?page=1&limit=1000", function (response) {
            //     applicationTree = $.fn.zTree.init($("#applicationTree"), applicationTreeSetting, response.list);
            //     applicationTree.expandAll(true);
            // });
            // //加载部门树
            // $.get(baseURL + "departments?page=1&limit=1000", function (response) {
            //     deptTree = $.fn.zTree.init($("#deptTree"), deptTreeSetting, response.list);
            //     deptTree.expandAll(true);
            // });
        },
        /** 更新按钮初始化数据 */
        initTreeToUpdate: function (userId) {
            //加载应用树
            $.get(baseURL + "applications?page=1&limit=1000", function (response) {
                applicationTree = $.fn.zTree.init($("#applicationTree"), applicationTreeSetting, response.list);
                applicationTree.expandAll(true);
            });
            //加载部门树
            // $.get(baseURL + "depts/add/" + currentUser.userId, function (r) {
            $.get(baseURL + "departments?page=1&limit=1000" + currentUser.userId, function (r) {
                deptTree = $.fn.zTree.init($("#deptTree"), deptTreeSetting, r.list);
                deptTree.expandAll(true);
                vm.getUser(userId);
            });
        },
        /** 根据用户ID获取用户信息 */
        getUser: function (userId) {
            $.get(baseURL + "users/" + userId + "?loginUserId=" + localStorage.getItem("userId"), function (response) {
                response = response.data;

                vm.user = response.user;
                vm.user.password = response.password;

                //初始化租户树和组树
                vm.initTreeToAdd();

                console.log("response.tenantIds：" + JSON.stringify(response.tenantIds));
                // 勾选已有租户
                $.each(response.tenantIds, function (index, item) {

                    try {
                        var node = tenantTree.getNodeByParam("id", item);
                        tenantTree.checkNode(node, true, false);
                    } catch (e) {
                        console.log("update zhongshan没有权限查看该数据:"+JSON.stringify(item));
                    }

                });
                // 勾选已有组
                $.each(response.groupIds, function (index, item) {

                    try {
                        console.log(index + ": " + JSON.stringify(item));

                        var node = groupTree.getNodeByParam("id", item);
                        groupTree.checkNode(node, true, false);
                    } catch (e) {
                        console.log("update zhongshan没有权限查看该数据:"+JSON.stringify(item));
                    }
                });

                // vm.user.userId = response.userId;
                // vm.user.userName = response.userName;
                // vm.user.password = null;
                // vm.user.name = response.name;
                // vm.user.email = response.email;
                // vm.user.mobile = response.mobile;
                // vm.user.status = response.status;
                // vm.tenantList.selectedTenant = response.tenantId;
                // $.each(response.applicationIdList, function (index, item) {
                //     var node = applicationTree.getNodeByParam("applicationId", item);
                //     applicationTree.checkNode(node, true, false);
                // });
                // $.each(response.deptIdList, function (index, item) {
                //     var node = deptTree.getNodeByParam("departmentId", item);
                //     deptTree.checkNode(node, true, false);
                // });
            });
        },
        /** 查询当前用户信息 */
        getCurrentUser: function () {
            $.getJSON(baseURL + "users/" + userId + "?token=" + accessToken, function (response) {
                currentUser = response;
            });
        },
        reload: function (backFirst) {
            vm.showList = true;
            var page;
            if (backFirst) {
                page = 1;
            } else {
                page = $("#jqGrid").jqGrid('getGridParam', 'page');
            }
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {searchName: vm.searchName},
                page: page
            }).trigger("reloadGrid");
        },
        /** 校验字段 */
        checkValue: function () {
            var emailReg = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
            var telReg = /^1[34578]\d{9}$/;
            if (!vm.checkInput(vm.user.userName, '账号', false)) {
                return false;
            }
            if (!vm.checkInput(vm.user.password, '密码', true)) {
                return false;
            }
            if (vm.user.email && !emailReg.test(vm.user.email)) {
                // alert("邮箱格式不正确!");
                window.parent.swal({
                    title: "",
                    text: '邮箱格式不正确！',
                    type: 'warning',
                    confirmButtonText: '确定',
                    allowOutsideClick: false
                });
                return false;
            }
            if (vm.user.mobile && !telReg.test(vm.user.mobileNumber)) {
                // alert("电话号码格式不正确!");
                window.parent.swal({
                    title: "",
                    text: '电话号码格式不正确！',
                    type: 'warning',
                    confirmButtonText: '确定',
                    allowOutsideClick: false
                });
                return false;
            }
            return true;
        },
        checkInput: function (value, name, isPassword) {
            var chineseReg = /^[\u4e00-\u9fa5]{0,}$/; // 中文正则
            var specialReg = /^(?!_)(?!.*?_$)[-a-zA-Z0-9_\u4e00-\u9fa5]+$/;//非特殊符号的正则表达式

            if (!(isPassword && vm.user.userId)) {
                if (!value) {
                    window.parent.swal({
                        title: "",
                        text: name + '不能为空！',
                        type: 'warning',
                        confirmButtonText: '确定',
                        allowOutsideClick: false
                    });
                    return false;
                }
                if (chineseReg.test(value)) {
                    window.parent.swal({
                        title: "",
                        text: name + '不能为中文！',
                        type: 'warning',
                        confirmButtonText: '确定',
                        allowOutsideClick: false
                    });
                    return false;
                }
                if (!specialReg.test(value)) {
                    window.parent.swal({
                        title: "",
                        text: name + '只能使用英文、数字、下划线或者连字符！',
                        type: 'warning',
                        confirmButtonText: '确定',
                        allowOutsideClick: false
                    });
                    return false;
                }
                if (value.length < 4 || value.length > 30) {
                    window.parent.swal({
                        title: "",
                        text: name + '的长度只能在4-30！',
                        type: 'warning',
                        confirmButtonText: '确定',
                        allowOutsideClick: false
                    });
                    return false;
                }
                if (value.indexOf(",") != -1 && name == "账号") {
                    window.parent.swal({
                        title: "",
                        text: name + '不能包含特殊符号","',
                        type: 'warning',
                        confirmButtonText: '确定',
                        allowOutsideClick: false
                    });
                    return false;
                }
            }
            return true;
        },
        /** 租户列表onchange 事件*/
        selectTenant: function () {
            vm.user.tenantId = vm.tenantList.selectedTenant;
        },
        /**  获取租户列表 */
        getTenantList: function () {
            $.get(baseURL + "tenants?page=1&limit=1000", function (response) {
                $.each(response.list, function (index, item) {
                    vm.tenantList.options.push(item);
                })
            });
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
        // this.getCurrentUser();
    }
});