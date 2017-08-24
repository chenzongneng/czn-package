/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

var roleId = localStorage.getItem("roleId");
if(roleId == 'null'){
    roleId ='';
}
var addOrUpdate = 0; // 0 为新增 , 1 为 更新
$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'v1.0/roleList',
        datatype: "json",
        colModel: [
            {label: '角色ID', name: 'id', index: "id", width: 45, key: true},
            {label: '角色名称', name: 'name', index: "name", width: 75},
            {label: '所属部门', name: 'deptName', width: 75},
            {label: '备注', name: 'remark', width: 100},
            {label: '创建时间', name: 'createTime', index: "create_time", width: 80}
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

//菜单树
var menu_ztree;
var menu_setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "menuId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    },
    check: {
        enable: true,
        nocheckInherit: true
    }
};

//部门结构树
var dept_ztree;
var dept_setting = {
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

//数据树
var data_ztree;
var data_setting = {
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
        q: {
            roleName: null
        },
        showList: true,
        title: null,
        role: {
            id:null,
            tenantId:roleId,
            name: null,
            remark:null
        },
        isShowDept:false,
        parentDepartments:{
            selectedDepartment:"",
            options: []
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
            vm.role = {
                id:null,
                tenantId:roleId,
                name: null,
                remark:null
            };
            /*vm.getMenuTree(null);

            vm.getDept();

            vm.getDataTree();*/
            vm.checkAdmin();
        },
        update: function () {
            addOrUpdate = 1;
            var roleId = getSelectedRow();
            if (roleId == null) {
                return;
            }

            vm.showList = false;
            vm.title = "修改";
            /*vm.getDataTree();
            vm.getMenuTree(roleId);
            vm.getDept();*/
            vm.checkAdmin();
            vm.getRoleById();
        },
        del: function () {
            var roleIds = getSelectedRows();
            if (roleIds == null) {
                return;
            }
            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "DELETE",
                    url: baseURL + "v1.0/role/" + roleIds,
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
        getRole: function (roleId) {
            $.get(baseURL + "sys/role/info/" + roleId, function (r) {
                vm.role = r.role;

                //勾选角色所拥有的菜单
                var menuIds = vm.role.menuIdList;
                for (var i = 0; i < menuIds.length; i++) {
                    var node = menu_ztree.getNodeByParam("menuId", menuIds[i]);
                    menu_ztree.checkNode(node, true, false);
                }

                //勾选角色所拥有的部门数据权限
                var deptIds = vm.role.deptIdList;
                for (var i = 0; i < deptIds.length; i++) {
                    var node = data_ztree.getNodeByParam("deptId", deptIds[i]);
                    data_ztree.checkNode(node, true, false);
                }

                vm.getDept();
            });
        },
        saveOrUpdate: function () {
           /* //获取选择的菜单
            var nodes = menu_ztree.getCheckedNodes(true);
            var menuIdList = new Array();
            for (var i = 0; i < nodes.length; i++) {
                menuIdList.push(nodes[i].menuId);
            }
            vm.role.menuIdList = menuIdList;

            //获取选择的数据
            var nodes = data_ztree.getCheckedNodes(true);
            var deptIdList = new Array();
            for (var i = 0; i < nodes.length; i++) {
                deptIdList.push(nodes[i].deptId);
            }
            vm.role.deptIdList = deptIdList;*/
            var url = "v1.0/role";
            console.log(vm.role);
            $.ajax({
                type: addOrUpdate === 0 ? "POST":"PUT",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.role),
                dataType: '',
                success: function () {
                    alert('操作成功', function () {
                        vm.reload();
                    });
                }
            });
        },
        getMenuTree: function (roleId) {
            //加载菜单树
            $.get(baseURL + "sys/menu/list", function (r) {
                menu_ztree = $.fn.zTree.init($("#menuTree"), menu_setting, r);
                //展开所有节点
                menu_ztree.expandAll(true);

                if (roleId != null) {
                    vm.getRole(roleId);
                }
            });
        },
        getDataTree: function (roleId) {
            //加载菜单树
            $.get(baseURL + "sys/dept/list", function (r) {
                data_ztree = $.fn.zTree.init($("#dataTree"), data_setting, r);
                //展开所有节点
                data_ztree.expandAll(true);
            });
        },
        getDept: function () {
            //加载部门树
            $.get(baseURL + "sys/dept/list", function (r) {
                dept_ztree = $.fn.zTree.init($("#deptTree"), dept_setting, r);
                var node = dept_ztree.getNodeByParam("deptId", vm.role.deptId);
                if (node != null) {
                    dept_ztree.selectNode(node);

                    vm.role.deptName = node.name;
                }
            })
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
                    var node = dept_ztree.getSelectedNodes();
                    //选择上级部门
                    vm.role.deptId = node[0].deptId;
                    vm.role.deptName = node[0].name;

                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                url: baseURL + 'v1.0/roleList',
                postData: {roleName: vm.q.roleName},
                page: page
            }).trigger("reloadGrid");
        },
        checkAdmin:function(){
            if(roleId === ''){
                vm.isShowDept = true;
            }
            if(vm.isShowDept){
                vm.parentDepartments.options = [];
                $.get(baseURL + "v1.0/parentDepartments", function (response) {
                    $.each(response,function(index,item){
                        vm.parentDepartments.options.push(item);
                    });
                });
            }
        },
        selectTenantValue:function () {
            vm.role.tenantId = vm.parentDepartments.selectedDepartment;
        },
        getRoleById:function(){
            var roleIds = getSelectedRows();
            if (roleIds == null) {
                return;
            }
            vm.role.id = roleIds[0];
            $.get(baseURL + "v1.0/role/"+roleIds, function (response) {
                vm.parentDepartments.selectedDepartment = response.tenantId;
                vm.role.name = response.name;
                vm.role.remark = response.remark;
            });
        }
    }
});
