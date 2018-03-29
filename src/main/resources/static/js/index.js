/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */
/** 生成菜单 */
var menuItem = Vue.extend({
    name: 'menu-item',
    props: {item: {}, index: 0},
    template: [
        '<li :class="{active: (item.type===0 && index === 0)}">',
        '<a v-if="(item.type === 0 && setButtons(item.code))" href="javascript:void(0);">',
        '<i v-if="item.icon != null" :class="item.icon"></i>',
        '<span>{{item.name}}</span>',
        '<i class="fa fa-angle-left pull-right"></i>',
        '</a>',
        '<ul v-if="(item.type === 0 && setButtons(item.code))" class="treeview-menu">',
        '<menu-item :item="item" :index="index" v-for="(item, index) in item.list"></menu-item>',
        '</ul>',
        '<a v-if="(item.type === 1 && setButtons(item.code))" :href="\'#\'+item.url">',
        '<i v-if="item.icon != null" :class="item.icon"></i>',
        '<i v-else class="fa fa-circle-o"></i>',
        '<span>{{item.name}}</span>',
        '</a>',
        '</li>'
    ].join(''),
    methods: {
        setButtons: function (code) {
            return resources[code];
        }
    }
});
/** 注册菜单组件 */
Vue.component('menuItem', menuItem);
var resources;
var vm = new Vue({
    el: '#garnetIndexApp',
    data: {
        user: {},
        menuList: {},
        resourceList: {

        },
        main: "main.html",
        password: '',
        newPassword: '',
        navTitle: "欢迎页"
    },
    methods: {
        /** 查询菜单列表 */
        getMenuList: function () {
            var that = this;
            // $.getJSON(baseURL + "menu/userId/" + userId + "/appId/1/appName/garnet", function (r) {
            $.ajaxSettings.async = false;
            // $.getJSON("http://localhost:12306/garnet/test.json", function (r) {
            $.getJSON(baseURL + "resources/getsysmenu", function (r) {
                that.menuList = r;
                //路由
                var router = new Router();
                routerList(router, that.menuList,that);
                router.start();
            });
        },
        /** 查询按钮列表 */
        getButtonList: function () {
            $.ajaxSettings.async = false;
            // $.getJSON("http://localhost:12306/garnet/test.json", function (r) {
            $.getJSON(baseURL + "resources/getappcode", function (r) {
                resources = r;
                // console.log(JSON.stringify(resources));
            });
            this.getMenuList();
        },
        /** 查询用户信息 */
        getUser: function () {
            // $.getJSON(baseURL + "token/userInfo?token=" + garnetToken, function (r) {
            $.getJSON(baseURL + "/users/"+ userId + "?token=" + accessToken, function (response) {
                if (!response) {
                    parent.location.href = 'index.html';
                } else {
                    vm.user = response.data.user;
                }
            });
        },
        getMode : function () {
            $.get(baseURL + "/systemconfigs/parameter?parameter=mode", function (response) {
                if (!response) {
                    parent.location.href = 'index.html';
                } else {
                    var mode = response.data.value;
                    localStorage.setItem("mode", mode);
                }
            });
        },
        /** 修改密码 */
        updatePassword: function () {
            vm.password = '';
            vm.newPassword = '';
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: "修改密码",
                area: ['550px', '270px'],
                shadeClose: false,
                content: jQuery("#passwordLayer"),
                btn: ['修改', '取消'],
                btn1: function (index) {
                    $.ajax({
                        type: "POST",
                        url: baseURL + "password",
                        data: {
                            userId: vm.user.userId,
                            oldPassword: vm.password,
                            newPassword: vm.newPassword
                        },
                        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                        dataType: "",
                        success: function () {
                            layer.close(index);
                            swal("修改密码成功!", "", "success");
                        },
                        error: function (response) {
                            layer.close(index);
                            swal("修改密码失败！", response.responseJSON.errorMessage, "error");
                        }
                    });
                }
            });

        },
        /** 退出登录 */
        logout: function () {
            localStorage.removeItem("accessToken");
            localStorage.removeItem("refreshToken");
            location.href = 'login.html';
        }
    },
    /**  初始化页面时执行该方法 */
    created: function () {
        // this.getMenuList();
        this.getMode();
        this.getUser();
        this.getButtonList();
        // this.refreshToken();
    }
});

/** 菜单路由 */
function routerList(router, menuList,vm) {
    for (var key in menuList) {
        var menu = menuList[key];
        if (menu.type == 0) {
            routerList(router, menu.list,vm);
        } else if (menu.type == 1) {
            router.add('#' + menu.url, function () {
                var url = window.location.hash;
                //替换iframe的url
                vm.main = url.replace('#', '');
                //导航菜单展开
                $(".treeview-menu li").removeClass("active");
                $(".sidebar-menu li").removeClass("active");
                $("a[href='" + url + "']").parents("li").addClass("active");
                vm.navTitle = $("a[href='" + url + "']").text();
            });
        }
    }
}


//刷新token
function refreshToken () {
    var data = {
        userName: localStorage.getItem("userName"),
        token: localStorage.getItem("refreshToken"),
        appCode: "garnet"
    }
    $.ajax({
        type: "POST",
        url: baseURL + "users/garnetrefreshtoken?token=" + accessToken,
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "",
        success: function (result) {
            console.log(JSON.stringify(result.accessToken) + "\n" + JSON.stringify(result.refreshToken));
            localStorage.setItem("accessToken", result.accessToken);
            localStorage.setItem("refreshToken", result.refreshToken);
        }
        // ,
        // error:function(result){
        //     console.log("result refresh token error = " + JSON.stringify(result));
        // }
    });
}
//每半小时自动刷新token
// window.setInterval("refreshToken();", 5000);
window.setInterval("refreshToken();", 30 * 60 * 1000);
// setTimeout(refreshToken(), 30 * 60 * 1000);

/** iframe自适应 */
$(window).on('resize', function () {
    var $content = $('.content');
    $content.height($(this).height() - 120);
    $content.find('iframe').each(function () {
        $(this).height($content.height());
    });
}).resize();
