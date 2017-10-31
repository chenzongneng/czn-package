/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'v1.0/logs',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', width: 20, key: true},
            {label: '用户名', name: 'userName', width: 40},
            {label: '用户操作', name: 'operation', width: 80},
            {label: '请求方法', name: 'method', width: 35},
            {label: '请求URL', name: 'url', width: 90},
            {label: 'IP地址', name: 'ip', width: 50},
            {label: '执行SQL', name: 'sql', width: 90},
            {label: '请求时间', name: 'createdTime', width: 70}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 20,
        rowList: [20, 40, 60],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: false,
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

var vm = new Vue({
    el: '#garnetApp',
    data: {
        showList: true,
        searchName: null,
        title: null,
        sql: [],
        log: {
            id: null,
            userName: null,
            operation: null,
            method: null,
            url: null,
            ip: null,
            sql: null,
            createdTime: null
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        detail: function () {
            var logId = getSelectedRow();
            console.log(">>>>>>>" + logId);
            if (logId == null) {
                return;
            }
            vm.showList = false;
            vm.title = "详情";

            vm.getLogDetail(logId);
        },
        getLogDetail: function (id) {
            vm.sql = [];
            $.get(baseURL + "v1.0/log/" + id, function (response) {
                vm.log.id = response.id;
                vm.log.userName = response.userName;
                vm.log.operation = response.operation;
                vm.log.method = response.method;
                vm.log.url = response.url;
                vm.log.ip = response.ip;
                vm.formatSql(response.sql);
                vm.log.createdTime = response.createdTime;
            });
        },
        formatSql: function (sql) {
            var sqlArray = sql.split(";");
            for (var i = 0; i < sqlArray.length; i++) {
                vm.sql.push(sqlArray[i]);
            }
        },
        reload: function (event) {
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {searchName: vm.searchName},
                page: page
            }).trigger("reloadGrid");
        },
        back: function () {
            vm.showList = true;
        }
    }
});