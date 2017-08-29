/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

var roleId = localStorage.getItem("roleId");
if(roleId == 'null'){
    roleId ='';
}
var addOrUpdate = 0; // 保存或者更新按钮点击事件 0 为新增 , 1 为 更新
$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'v1.0/userRoles',
        datatype: "json",
        colModel: [
            {label: '用户ID', name: 'userId', index: "user_id", width: 45, key: true},
            {label: '用户名称', name: 'userName', index: "user_name", width: 75},
            {label: '拥有权限', name: 'roles', width: 75},
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
        postData:{tenantId:roleId},
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});

var vm = new Vue({
    el: '#garnetApp',
    data: {
        searchName:null,
        title: null,
        userId:null,
        showList: true,
        selectedRoleIds:[],
        // 用户下拉列表数据
        userList:{
            selectedUser:"",
            options: []
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            addOrUpdate = 0;
            vm.title = "新增";
            vm.userId = null;
            vm.userList.options = [];
            vm.selectedRoleIds = [];
            vm.getAllUsers();
            $("#userListSelect").removeAttr("disabled");  // 用户可选
        },
        update: function () {
            vm.showList = false;
            addOrUpdate = 1;// 更新 的点击事件
            vm.title = "修改";
            vm.userList.options = [];
            vm.selectedRoleIds=[];
            vm.getAllUsers();
            vm.getRoleIds();
            $("#userListSelect").attr("disabled","disabled"); // 控制用户不可选
        },
        del: function () {
            var selectedRoleIds = getSelectedRows();
            if (selectedRoleIds == null) {
                return;
            }
            console.log(selectedRoleIds);
            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "DELETE",
                    url: baseURL + "v1.0/userRole?userIds=" + selectedRoleIds.toString(),
                    contentType: "application/json",
                    dataType:"",
                    success: function () {
                        alert('操作成功', function () {
                            vm.reload();
                        });
                    },
                    error: function () {
                        alert('操作失败', function () {
                            vm.reload();
                        });
                    }
                });
            });
        },
        saveOrUpdate: function () {
            var url = "v1.0/userRole?userId="+vm.userId+"&selectedRoleIds="+vm.selectedRoleIds;
            $.ajax({
                type: addOrUpdate === 0 ? "POST":"PUT",
                url: baseURL + url,
                contentType: "application/json",
                dataType: '',
                success: function () {
                    alert('操作成功', function () {
                        vm.reload();
                    });
                }
            });
        },
        /** 重新加载 */
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                url: baseURL + 'v1.0/userRoles',
                postData: {searchName: vm.searchName},
                page: page
            }).trigger("reloadGrid");
        },
        /** 用户列表onchange 事件*/
        selectUser:function () {
            vm.userId = vm.userList.selectedUser;
        },
        /** 通过id 得到一个role对象 */
        getRoleIds:function(){
            var selectedRoleId = getSelectedRow();
            console.log(selectedRoleId);
            if (selectedRoleId == null) {
                return;
            }
            vm.userId = selectedRoleId;
            $.get(baseURL + "v1.0/roleIds/"+selectedRoleId, function (response) {
                vm.userList.selectedUser = selectedRoleId;
                $.each(response,function (index,item) {
                    vm.selectedRoleIds.push(item);
                })
            });
        },
        getAllUsers:function () {
            $.get(baseURL + "v1.0/userList", function (response) {
                $.each(response,function(index,item){
                    vm.userList.options.push(item);
                })
            });
        }
    }
});