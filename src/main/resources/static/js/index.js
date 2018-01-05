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
        '<a v-if="item.type === 0" href="javascript:void(0);">',
        '<i v-if="item.icon != null" :class="item.icon"></i>',
        '<span>{{item.name}}</span>',
        '<i class="fa fa-angle-left pull-right"></i>',
        '</a>',
        '<ul v-if="item.type === 0" class="treeview-menu">',
        '<menu-item :item="item" :index="index" v-for="(item, index) in item.menuList"></menu-item>',
        '</ul>',
        '<a v-if="item.type === 1" :href="\'#\'+item.url">',
        '<i v-if="item.icon != null" :class="item.icon"></i>',
        '<i v-else class="fa fa-circle-o"></i>',
        '<span>{{item.name}}</span>',
        '</a>',
        '</li>'
    ].join('')
});
/** 注册菜单组件 */
Vue.component('menuItem', menuItem);
var buttons;
var vm = new Vue({
    el: '#garnetIndexApp',
    data: {
        user: {},
        menuList: {},
        buttonList: {

        },
        main: "main.html",
        password: '',
        newPassword: '',
        navTitle: "欢迎页"
    },
    methods: {
        /** 查询菜单列表 */
        getMenuList: function () {
            $.getJSON(baseURL + "menu/userId/" + userId + "/appId/1/appName/garnet", function (r) {
                vm.menuList = r;
                //路由
                var router = new Router();
                routerList(router, vm.menuList);
                router.start();
            });
        },
        /** 查询按钮列表 */
        getButtonList: function () {
            $.ajaxSettings.async = false;
            $.getJSON(baseURL + "button/userId/" + userId + "/appId/1", function (r) {
                buttons = r;
            });
        },
        /** 查询用户信息 */
        getUser: function () {
            $.getJSON(baseURL + "token/userInfo?token=" + garnetToken, function (r) {
                if (!r) {
                    parent.location.href = 'index.html';
                } else {
                    vm.user = r;
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
            localStorage.removeItem("garnetToken");
            location.href = 'login.html';
        }
    },
    /**  初始化页面时执行该方法 */
    created: function () {
        this.getMenuList();
        this.getUser();
        this.getButtonList();
    }
});


/** 菜单路由 */
function routerList(router, menuList) {
    for (var key in menuList) {
        var menu = menuList[key];
        if (menu.type == 0) {
            routerList(router, menu.menuList);
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

/** iframe自适应 */
$(window).on('resize', function () {
    var $content = $('.content');
    $content.height($(this).height() - 120);
    $content.find('iframe').each(function () {
        $(this).height($content.height());
    });
}).resize();
