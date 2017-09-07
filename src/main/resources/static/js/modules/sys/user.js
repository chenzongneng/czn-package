/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */
var addOrUpdate = 0; // 保存或者更新按钮点击事件 0 为新增 , 1 为 更新

$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'v1.0/users',
        datatype: "json",
        colModel: [
            {label: '用户ID', name: 'userId', index: "user_id", width: 45, key: true},
            {label: '用户名', name: 'username', width: 75},
            //{label: '所属部门', name: 'deptName', width: 75},
            {label: '邮箱', name: 'email', width: 90},
            {label: '手机号', name: 'mobile', width: 80},
            {
                label: '是否管理员', name: 'admin', width: 80, formatter: function (value, options, row) {
                return value === 1 ? "是" : "否";
            }
            },
            {
                label: '状态', name: 'status', width: 80, formatter: function (value, options, row) {
                return value === 0 ?
                    '<span class="label label-danger">禁用</span>' :
                    '<span class="label label-success">正常</span>';
            }
            },
            {label: '创建时间', name: 'createTime', index: "create_time", width: 90}
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
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});

var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    }
};
var ztree;

var vm = new Vue({
    el: '#garnetApp',
    data: {
        searchName: null,
        showList: true,
        title: null,
        roleList: {},
        user: {
            username: null,
            password: null,
            email: null,
            mobile: null,
            status: null,
            admin: null
        },
        currentUser: {},
        isAdmin: {
            selectedValue: "0",
            options: [{text: "是", value: "1"}, {text: "否", value: "0"}]
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            addOrUpdate = 0;
            vm.showList = false;
            vm.title = "新增";
            //vm.roleList = {};
            vm.user = {
                userId: null,
                username: null,
                password: null,
                email: null,
                mobile: null,
                status: 1,
                admin: null
            };
            vm.isAdmin.selectedValue = "0";
            //获取角色信息
            //this.getRoleList();

            //vm.getDept();
        },
        getDept: function () {
            //加载部门树
            $.get(baseURL + "sys/dept/list", function (r) {
                ztree = $.fn.zTree.init($("#deptTree"), setting, r);
                var node = ztree.getNodeByParam("deptId", vm.user.deptId);
                if (node != null) {
                    ztree.selectNode(node);

                    vm.user.deptName = node.name;
                }
            })
        },
        update: function () {
            addOrUpdate = 1;
            var userId = getSelectedRow();
            if (userId == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getUser(userId);
            //获取角色信息
            //this.getRoleList();
        },
        del: function () {
            var userIds = getSelectedRows();
            if (userIds == null) {
                return;
            }
            console.log('userIds type  = ' + typeof userIds);
            if (userIds.toString().indexOf(vm.currentUser.userId) >= 0) {
                swal("您不能删除自己!", "", "error");
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
                        url: baseURL + "v1.0/user?userIds=" + userIds.toString(),
                        contentType: "application/json",
                        dataType: "",
                        success: function () {
                            swal("删除成功!", "", "success");
                            vm.reload();
                        },
                        error: function () {
                            swal("删除失败!", "系统错误，请联系系统管理员！", "success");
                        }

                    });
                });
        },
        saveOrUpdate: function () {
            if (!vm.checkValue()) {
                return;
            }
            if (addOrUpdate === 0) {
                vm.doUpInsert();
            } else {
                if ((vm.user.userId == vm.currentUser.userId && vm.currentUser.admin == 1 && vm.user.admin == 0) ||
                    (vm.user.userId == vm.currentUser.userId && vm.currentUser.status == 1 && vm.user.status == 0)) {
                    swal({
                            title: "确定要更改吗？",
                            text: "您已经更改了自己的权限，将会退出该系统",
                            type: "warning",
                            showCancelButton: true,
                            closeOnConfirm: false,
                            confirmButtonText: "确认",
                            cancelButtonText: "取消",
                            confirmButtonColor: "#DD6B55"
                        },
                        function () {
                            var url = "v1.0/user";
                            $.ajax({
                                type: addOrUpdate === 0 ? "POST" : "PUT",
                                url: baseURL + url,
                                contentType: "application/json",
                                data: JSON.stringify(vm.user),
                                dataType: "",
                                success: function () {
                                    localStorage.removeItem("garnetToken");
                                    location.href = baseURL + 'login.html';
                                },
                                error: function (response) {
                                    swal(response.responseJSON.errorMessage, "", "error");
                                }
                            });
                        });
                }else{
                    vm.doUpInsert();
                }
            }

        },
        doUpInsert: function () {
            var url = "v1.0/user";
            $.ajax({
                type: addOrUpdate === 0 ? "POST" : "PUT",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.user),
                dataType: "",
                success: function () {
                    vm.reload();
                    swal("操作成功!", "", "success");
                },
                error: function (response) {
                    swal(response.responseJSON.errorMessage, "", "error");
                }
            });
        },
        getUser: function (userId) {
            $.get(baseURL + "v1.0/user/" + userId, function (response) {
                if (response) {
                    vm.user.userId = response.userId;
                    vm.user.username = response.username;
                    vm.user.password = null;
                    vm.user.email = response.email;
                    vm.user.mobile = response.mobile;
                    vm.isAdmin.selectedValue = response.admin;
                    vm.user.status = response.status;
                }
                //vm.getDept();
            });
        },
        getRoleList: function () {
            $.get(baseURL + "sys/role/select", function (r) {
                vm.roleList = r.list;
            });
        },
        /** 查询用户信息 */
        getCurrentUser: function () {
            $.getJSON(baseURL + "sys/user/info", function (response) {
                vm.currentUser = response.user;
                console.log(vm.currentUser);
            });
        },
        deptTree: function () {
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择部门",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.user.deptId = node[0].deptId;
                    vm.user.deptName = node[0].name;

                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'searchName': vm.searchName},
                page: page
            }).trigger("reloadGrid");
        },
        // 是否管理员的下拉列表
        selectAdmin: function () {
            vm.user.admin = vm.isAdmin.selectedValue;
        },
        checkValue: function () {
            var emailReg = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
            var telReg = /^1[34578]\d{9}$/;
            if (!vm.checkInput(vm.user.username, '用户名', false)) {
                return false;
            }
            if (!vm.checkInput(vm.user.password, '密码', true)) {
                return false;
            }
            if (vm.user.email && !emailReg.test(vm.user.email)) {
                swal("邮箱格式不正确!", "", "warning");
                return false;
            }
            if (vm.user.mobile && !telReg.test(vm.user.mobile)) {
                swal("电话号码格式不正确!", "", "warning");
                return false;
            }
            return true;
        },
        checkInput: function (value, name, isPassword) {
            var chineseReg = /^[\u4e00-\u9fa5]{0,}$/; // 中文正则
            var specialReg = /^(?!_)(?!.*?_$)[-a-zA-Z0-9_\u4e00-\u9fa5]+$/;//非特殊符号的正则表达式

            if (!(isPassword && addOrUpdate === 1)) {
                if (!value) {
                    swal({
                        title: name + '不能为空！',
                        type: 'warning',
                        confirmButtonText: '确定',
                        allowOutsideClick: false
                    });
                    return false;
                }
                if (chineseReg.test(value)) {
                    swal({
                        title: name + '不能为中文！',
                        type: 'warning',
                        confirmButtonText: '确定',
                        allowOutsideClick: false
                    });
                    return false;
                }
                if (!specialReg.test(value)) {
                    swal({
                        title: name + '只能使用英文、数字、下划线或者连字符！',
                        type: 'warning',
                        confirmButtonText: '确定',
                        allowOutsideClick: false
                    });
                    return false;
                }
                if (value.length < 4 || value.length > 20) {
                    swal({
                        title: name + '的长度只能在4-20！',
                        type: 'warning',
                        confirmButtonText: '确定',
                        allowOutsideClick: false
                    });
                    return false;
                }
            }
            return true;
        }
    },
    created: function () {
        this.getCurrentUser();
    }
});