/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

var Dept = {
    id: "deptTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Dept.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '部门ID', field: 'deptId', CanHide: 0, Visible: 0, align: 'center', valign: 'middle', width: '80px'},
        {title: '部门名称', field: 'name', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '上级部门', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '用户列表', field: 'userNameList', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '角色列表', field: 'roleNameList', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '100px'}];
};

var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentDeptId",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    }
};
var ztree;

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        // 当前用户信息
        currentUser: {},
        dept: {
            parentName: null,
            parentDeptId: 0,
            orderNum: 0
        }
    },
    methods: {
        getDept: function () {
            //加载部门树
            $.get(baseURL + "depts/add", function (r) {
                ztree = $.fn.zTree.init($("#deptTree"), setting, r);
                var node = ztree.getNodeByParam("deptId", vm.dept.parentDeptId);
                ztree.selectNode(node);
                vm.dept.parentName = node.name;
            })
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.dept = {parentName: null, parentDeptId: 0, orderNum: 0};
            vm.getDept();
        },
        update: function () {
            var deptId = getDeptId();
            if (deptId == null) {
                return;
            }

            $.get(baseURL + "dept/" + deptId, function (r) {
                vm.showList = false;
                vm.title = "修改";
                vm.dept = r;
                vm.getDept();
            });
        },
        del: function () {
            var deptId = getDeptId();
            if (deptId == null) {
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
                        url: baseURL + "dept/" + deptId,
                        contentType: "application/json",
                        dataType: "",
                        success: function (result) {
                            if (!result.message) {
                                swal("删除成功!", "", "success");
                                vm.reload();
                            } else {
                                swal("删除失败!", result.message, "error");
                            }
                        }
                    });
                });
        },
        saveOrUpdate: function () {
            $.ajax({
                type: vm.dept.deptId == null ? "POST" : "PUT",
                url: baseURL + "dept",
                contentType: "application/json",
                data: JSON.stringify(vm.dept),
                dataType: "",
                success: function () {
                    vm.reload();
                    swal("操作成功!", "", "success");
                }
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
                    vm.dept.parentDeptId = node[0].deptId;
                    vm.dept.parentName = node[0].name;
                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            Dept.table.refresh();
        },
        /** 初始化部门信息 */
        initDeptInfo: function () {
            // 获取当前用户信息
            $.getJSON(baseURL + "token/userInfo?token=" + garnetToken, function (response) {
                vm.currentUser = response;

                // 初始化表格数据
                var columns = Dept.initColumn();
                var table = new TreeTable(Dept.id, baseURL + "depts/" + response.userId, columns);
                //table.setRootCodeValue(r.deptId);
                table.setExpandColumn(2);
                table.setIdField("deptId");
                table.setCodeField("deptId");
                table.setParentCodeField("parentDeptId");
                table.setExpandAll(false);
                table.init();
                Dept.table = table;
            });
        }
    },
    created: function () {
        this.initDeptInfo();
    }
});

function getDeptId() {
    var selected = $('#deptTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return false;
    } else {
        return selected[0].id;
    }
}