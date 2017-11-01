/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

$(function () {
    /** 初始化 Log 列表 */
    $("#jqGrid").jqGrid({
        url: baseURL + 'logs',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', align: 'center', hidden: true, width: 20, key: true},
            {label: '用户名', name: 'userName', align: 'center', width: 40},
            {label: '用户操作', name: 'operation', align: 'center', width: 80},
            {label: '请求方法', name: 'method', align: 'center', width: 35},
            {label: '请求URL', name: 'url', align: 'center', width: 90},
            {label: 'IP地址', name: 'ip', align: 'center', width: 50},
            {label: '执行SQL', name: 'sql', align: 'center', width: 90},
            {label: '请求时间', name: 'createTime', align: 'center', width: 70}
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
            rows: "limit"
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
            createTime: null
        }
    },
    methods: {
        /**  查询按钮点击事件 */
        query: function () {
            vm.reload();
        },
        /**  详情按钮点击事件 */
        detail: function () {
            var logId = getSelectedRow();
            if (logId == null) {
                return;
            }
            vm.showList = false;
            vm.title = "详情";

            vm.getLogDetail(logId);
        },
        /**  根据 id 查询 log 详细数据*/
        getLogDetail: function (id) {
            vm.sql = [];
            $.get(baseURL + "log/" + id, function (response) {
                vm.log.id = response.id;
                vm.log.userName = response.userName;
                vm.log.operation = response.operation;
                vm.log.method = response.method;
                vm.log.url = response.url;
                vm.log.ip = response.ip;
                vm.formatSql(response.sql);
                vm.log.createTime = response.createTime;
            });
        },
        /**  格式化 sql */
        formatSql: function (sql) {
            var sqlArray = sql.split(";");
            for (var i = 0; i < sqlArray.length; i++) {
                if (sqlArray[i]) {
                    vm.sql.push(sqlArray[i]);
                }
            }
        },
        reload: function (event) {
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {searchName: vm.searchName},
                page: page
            }).trigger("reloadGrid");
        },
        /** 返回按钮点击事件 */
        back: function () {
            vm.showList = true;
        }
    }
});