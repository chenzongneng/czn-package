/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

var ztree;
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    }
};

var Dept = {
    id: "departmentTable",
    table: null,
    layerIndex: -1
};

/** 初始化表格的列 */
Dept.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '部门ID', field: 'id', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '部门名称', field: 'name', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '上级部门', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '100px'}];
};

var vm = new Vue({
    el: '#garnetApp',
    data: {
        showList: true,
        title: null,
        dept: {
            parentName: null,
            parentId: 0,
            orderNum: 0
        }
    },
    methods: {
        /** 加载部门树 */
        getDepartmentTree: function () {
            // TODO : set user id
            var param = '?containThis=true&userId=1';
            $.get(baseURL + "/v1.0/departments" + param, function (result) {
                ztree = $.fn.zTree.init($("#departmentTree"), setting, result);
                var node = ztree.getNodeByParam("id", vm.dept.parentId);
                ztree.selectNode(node);
                vm.dept.parentName = node.name;
            })
        },
        /** 打开新增部门页面 */
        toAddDepartment: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.dept = {
                parentName: null,
                parentId: 0,
                orderNum: 0
            };
            vm.getDepartmentTree();
        },
        /** 打开修改部门页面 */
        toUpdateDepartment: function () {
            var thisDepartmentId = getDepartmentId();
            if (thisDepartmentId == null) {
                return;
            }
            var url = baseURL + "/v1.0/department/" + thisDepartmentId;
            $.get(url, function (result) {
                vm.showList = false;
                vm.title = "修改";
                vm.dept = result;
                vm.getDepartmentTree();
            });
        },
        /** 删除选中的部门 */
        deleteDepartment: function () {
            var thisDepartmentId = getDepartmentId();
            if (thisDepartmentId != null) {
                confirm('确定要删除选中的部门？', function () {
                    $.ajax({
                        type: "DELETE",
                        url: baseURL + "/v1.0/department/" + thisDepartmentId,
                        dataType: '',
                        success: function () {
                            alert('操作成功', function () {
                                vm.reloadDepartmentTree();
                            });
                        },
                        error: function () {
                            alert('删除失败，请重试！', function () {
                                vm.reloadDepartmentTree();
                            });
                        }
                    });
                });
            }
        },
        /** 保存部门信息 */
        addOrUpdateDepartment: function () {
            var url, requestType;
            if (vm.dept.id == null) {
                url = baseURL + "/v1.0/department";
                requestType = "POST";
            } else {
                url = baseURL + "/v1.0/department/" + vm.dept.id;
                requestType = "PUT";
            }
            var department = {
                id: vm.dept.id,
                name: vm.dept.name,
                orderNum: vm.dept.orderNum,
                parentId: vm.dept.parentId
            };
            $.ajax({
                url: url,
                type: requestType,
                dataType: '',
                contentType: "application/json",
                data: JSON.stringify(department),
                success: function () {
                    alert('操作成功', function () {
                        vm.reloadDepartmentTree();
                    });
                },
                error: function () {
                    alert('操作失败，请重试！', function () {
                        vm.reloadDepartmentTree();
                    });
                }
            });
        },
        /** 打开部门树页面 */
        openDepartmentTree: function () {
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
                    vm.dept.parentId = node[0].id;
                    vm.dept.parentName = node[0].name;
                    layer.close(index);
                }
            });
        },
        /** 重新加载部门列表 */
        reloadDepartmentTree: function () {
            vm.showList = true;
            Dept.table.refresh();
        }
    }
});

/** 获取选中行的id */
function getDepartmentId() {
    var selected = $('#departmentTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return null;
    } else {
        return selected[0].id;
    }
}

/** 初始化加载列表 */
$(function () {
    var columns = Dept.initColumn();
    //TODO: set userId
    var param = '?userId=1&containThis=false';
    var table = new TreeTable(Dept.id, baseURL + "/v1.0/departments" + param, columns);
    table.setExpandColumn(2);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("parentId");
    table.setExpandAll(false);
    table.init();
    Dept.table = table;
});