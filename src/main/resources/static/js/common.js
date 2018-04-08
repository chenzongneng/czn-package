/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

/** 请求url前缀 */
// TODO:暂时修改
// var baseURL = "http://localhost:8080/garnet/v1.0/";
// var baseURL = "http://192.168.0.200:12306/garnet/v1.0/";
var baseURL = "http://localhost:12306/garnet/api/v1.0/";

/** token */
// var garnetToken = localStorage.getItem("garnetToken");
var accessToken = localStorage.getItem("accessToken");
if (!accessToken) {
    // parent.location.href = 'login.html';
}
var userId = localStorage.getItem("userId");

/** 权限判断 */
/*function hasPermission(permission) {
    return window.parent.permissions.indexOf(permission) > -1;
}*/

/** jquery全局配置 */
$.ajaxSetup({
    dataType: "json",
    cache: false,
    headers: {
        // "token": token,
        // "gempileToken": localStorage.getItem("gempileToken")
        // "garnetToken": localStorage.getItem("garnetToken"),
        // "userToken": localStorage.getItem("userToken")
        "accessToken": localStorage.getItem("accessToken"),
        "refreshToken": localStorage.getItem("refreshToken")
    },

    complete: function (xhr) {
        // console.log("xhr == " + JSON.stringify(xhr));
        var response = JSON.parse(xhr.responseText);

        // token过期，则跳转到登录页面
        if (response.code == 401) {
            swal({
                    title: response.message,
                    type: "error"
                },
                function () {
                    parent.location.href = '../login.html';
                });
            // parent.location.href = '../login.html';
        } else if (response.code == 403) {
            swal({
                    // title: "没有权限",
                    title: response.message,
                    type: "error"
                },
                function () {
                    parent.location.href = '../index.html';
                });
        }
    }
});

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

/** 选择jqGrid列表中的一条记录 */
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

/** 选择jqGrid列表中的多条记录 */
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

/** 重写window中的alert属性 */
window.alert = function (msg, callback) {
    parent.layer.alert(msg, function (index) {
        parent.layer.close(index);
        if (typeof(callback) === "function") {
            callback("ok");
        }
    });
};
/** 重写window中的confirm属性 */
window.confirm = function (msg, callback) {
    parent.layer.confirm(msg, {btn: ['确定', '取消']}, function () {
        if (typeof(callback) === "function") {
            callback("ok");
        }
    });
};