/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

/** 请求前缀,写完整链接或从context开始的链接 */
var baseURL = "http://localhost:8080/garnet/v1.0/";

/** token */
/*var token = localStorage.getItem("garnetToken");
if (token == 'null') {
    parent.location.href = baseURL + 'login.html';
}*/

/** 权限判断 */
/*function hasPermission(permission) {
    return window.parent.permissions.indexOf(permission) > -1;
}*/

/** jquery全局配置 */
/*$.ajaxSetup({
    dataType: "json",
    cache: false,
    headers: {
        "token": token,
        "gempileToken": localStorage.getItem("gempileToken")
    },
    complete: function (xhr) {
        // token过期，则跳转到登录页面
        if (xhr.responseJSON.code == 401) {
            parent.location.href = baseURL + 'login.html';
        }
    }
});*/

/** jqGrid 配置 */
$.jgrid.defaults.width = 1000;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';
/*$.extend($.jgrid.defaults, {
    ajaxGridOptions: {
        headers: {
            "token": token
        }
    }
});*/

// 选择一条记录
function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        //alert("请选择一条记录");
        swal("请选择一条记录!", "", "warning");
        return;
    }
    var selectedIDs = grid.getGridParam("selarrrow");
    if (selectedIDs.length > 1) {
        //alert("只能选择一条记录");
        swal("只能选择一条记录!", "", "warning");
        return;
    }
    return rowKey;
}

// 选择多条记录
function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        //alert("请选择一条记录");
        swal("请选择一条记录!", "", "warning");
        return;
    }
    return grid.getGridParam("selarrrow");
}

/** 重写window属性 */
window.alert = function (msg, callback) {
    parent.layer.alert(msg, function (index) {
        parent.layer.close(index);
        if (typeof(callback) === "function") {
            callback("ok");
        }
    });
};
window.confirm = function (msg, callback) {
    parent.layer.confirm(msg, {btn: ['确定', '取消']}, function () {
        if (typeof(callback) === "function") {
            callback("ok");
        }
    });
};