/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */
// var baseURL = "http://localhost:8080/garnet/v1.0/";
// var baseURL = "http://192.168.0.200:12306/garnet/v1.0/";
// var baseURL = "http://localhost:12306/garnet/api/v1.0/";
// var baseURL = "http://192.168.111.100:12306/garnet/api/v1.0/";
var baseURL = "http://192.168.108.100:12306/garnet/api/v1.0/";

var nowTime = $.now();
var vm = new Vue({
    el: '#garnetApp',
    data: {
        userName: '',
        password: '',
        captcha: '',
        nowTime:'',
        error: false,
        errorMsg: '',
        src: baseURL + 'kaptcha?nowTime=' + nowTime,
        inputtext:{}
    },
    methods: {
        refreshCode: function () {
            var oldTime = nowTime;
            nowTime = $.now();
            this.src = baseURL + "kaptcha?nowTime=" + nowTime + "&oldTime=" + oldTime;
        },submit:function(){
            // alert(JSON.stringify(this.inputtext));
        },
        login: function () {
            var loc = location.href;
            var length = loc.length;//地址的总长度
            var indexOfAppCode = loc.indexOf("appCode=");
            var indexOfUrl = loc.indexOf("redirectUrl=");
            //http://localhost:12306/garnet/otherslogin.html?redirectUrl=https://www.bilibili.com/&appCode=garnet
            var redirectUrl = loc.substr(indexOfUrl+12, indexOfAppCode-indexOfUrl-13);//从=号后面的内容
            var appCode = loc.substr(indexOfAppCode + 8, length - indexOfAppCode-8);

            if (vm.userName != null && vm.userName != "") {
                document.cookie = "userName=" + vm.userName + ";";
            }

            $.ajax({
                type: "GET",
                url: baseURL + "validatecaptcha",
                data: {captcha: vm.captcha, nowTime: nowTime, userName: vm.userName},
                contentType: "application/json",
                dataType: "",
                success: function (result) {
                    if ("false" == result.loginStatus) {
                        vm.error = true;
                        vm.errorMsg = result.message;
                        return false;
                    } else {
                        var data = {
                            userName: vm.userName,
                            password: vm.password,
                            // kaptcha: vm.captcha,
                            // nowTime: nowTime,
                            appCode: appCode
                        };
                        $.ajax({
                            type: "POST",
                            url: baseURL + "users/login",
                            data: JSON.stringify(data),
                            contentType: "application/json",
                            dataType: "",
                            success: function (result) {
                                if (result.loginStatus == "success") {
                                    var accessToken = result.accessToken;
                                    var refreshToken = result.refreshToken;
                                    var userId = result.user.id;
                                    var userName = result.user.userName;
                                    var tenantNameAndId = JSON.stringify(result.userTenantNameAndIdMap);

                                    localStorage.setItem("accessToken", accessToken);
                                    localStorage.setItem("refreshToken", refreshToken);
                                    localStorage.setItem("userId", result.user.id);
                                    localStorage.setItem("userName", result.user.userName);
                                    localStorage.setItem("belongToGarnet", result.user.belongToGarnet)
                                    localStorage.setItem("requestTime", new Date().getTime());

                                    if (indexOfUrl > 0 && redirectUrl != null && $.trim(redirectUrl) != "") {
                                        console.log("redirectUrl: " + redirectUrl);
                                        window.location.href = redirectUrl + '?accessToken=' + accessToken + '&refreshToken=' +
                                            refreshToken + "&userId=" + userId + "&userName=" + userName + "&appCode=" + appCode + "&tenantNameAndId=" + tenantNameAndId;;
                                    } else {
                                        vm.redirectToAppList(accessToken, refreshToken);
                                    }

                                } else if ("应用不存在" == result.message) {
                                    //没有携带appCode
                                    // console.log("我没有携带appCode");
                                    vm.redirectToAppListWhenNotAppCode(vm.userName, vm.password);
                                } else {
                                    vm.error = true;
                                    vm.errorMsg = result.message;
                                }
                            }
                        });
                    }
                },
                error: function (result) {
                    vm.error = true;
                    vm.errorMsg = result.message;
                    return false;
                }
            });

        },
        /*跳转到应用选择列表*/
        redirectToAppList: function (accessToken, refreshToken) {
            var content = "";
            var colors = ['#d9d9d8', '#d050ae', '#08bdd0','#739fd0','#bbd05e','#39B1F9','#FFAC6F', '#95D988', '#d9c9d8',
            '#c07cd9','#17d99d', '#d9859b','#FF9090'];

            $.get(baseURL + "applications/getdefaulturl?userName=" + vm.userName + "&token=" + accessToken, function (response) {
                $.each(response, function (index, item) {
                    var num = vm.randomNumBoth(0, colors.length - 1);
                    var color = colors[num];
                    content = content + '<div style="padding-top: 15px;"><div class="child hvr-grow" style="background-color: ' + color + ';" onclick="window.location.href=\''+ item.defaultIndexUrl +'\'"><div class="font"> ' + item.name + '</div></div></div>';
                });

                $('#appGroup').append(content);

                var index = layer.open({
                    type: 1,
                    offset: '50px',
                    skin: 'layui-layer-molv',
                    title: "选择跳转应用",
                    area: ['300px', '700px'],
                    shade: 0,
                    // maxmin: true,
                    closeBtn: 0,
                    shadeClose: false,
                    content: jQuery("#appGroup"),
                    btn: ['返回'],
                    btn1: function (index) {
                        $('#appGroup').html('');
                        layer.close(index);
                    }
                });

                layer.full(index);
            });
        },
        /*没有appCode时跳转到应用选择列表*/
        redirectToAppListWhenNotAppCode: function (userName, password) {

            var flag = "false";
            //验证用户名和密码是否正确

            // axios.get(baseURL + "validateuserinfo", {
            //     params: {userName: userName, password: password}
            // }).then(function (response) {
            //     console.log("axios, " + JSON.stringify(response));
            //     if (response.data == Boolean(false)) {
            //         console.log("response == false");
            //         vm.error = true;
            //         vm.errorMsg = "账号或密码不正确";
            //         return false;
            //     }
            // }).catch(function (error) {
            //     vm.error = true;
            //     vm.errorMsg = "账号或密码不正确";
            //     return false;
            // });


            // $.get(baseURL + "validateuserinfo?userName=" + userName + "&password=" + password, function (response) {
            $.ajax({
                type: "GET",
                async: false,
                url: baseURL + "validateuserinfo",
                data: {userName: userName, password: password},
                contentType: "application/json",
                dataType: "",
                success: function (response) {
                    if (response == Boolean(false)) {
                        // console.log("response == false");
                        vm.error = true;
                        vm.errorMsg = "账号或密码不正确";
                        return false;
                        flag = "false";
                    } else {
                        flag = "success";
                        // console.log(flag);
                    }
                }

            });

            if ("false" == flag) {
                // console.log("我是flag");
                return false;
            }

            var colors = ['#d9d9d8', '#d050ae', '#08bdd0','#739fd0','#bbd05e','#39B1F9','#FFAC6F', '#95D988', '#d9c9d8',
                '#c07cd9','#17d99d', '#d9859b','#FF9090'];

            $.get(baseURL + "applications/getdefaulturl?userName=" + vm.userName, function (response) {
                // console.log("我是有点击事件的");
                $.each(response, function (index, item) {
                    var num = vm.randomNumBoth(0, colors.length - 1);
                    var color = colors[num];
                    var content = '<div style="padding-top: 15px;"><div class="child hvr-grow" style="background-color: ' + color + ';" id="' + item.appCode + '"><div class="font"> ' + item.name + '</div></div></div>';

                    $('#appGroup').append(content);

                    /*绑定点击事件*/
                    $('#' + item.appCode).on("click", function () {
                        // window.open("http://www.baidu.com");
                        vm.redirectAction(item.appCode, userName, password);
                    });
                });

                var index = layer.open({
                    type: 1,
                    offset: '50px',
                    skin: 'layui-layer-molv',
                    title: "选择跳转应用",
                    area: ['300px', '700px'],
                    shade: 0,
                    // maxmin: true,
                    closeBtn: 0,
                    shadeClose: false,
                    content: jQuery("#appGroup"),
                    btn: ['返回'],
                    btn1: function (index) {
                        $('#appGroup').html('');
                        layer.close(index);
                    }
                });

                layer.full(index);
            });
        },
        /*获取指定范围内的一个随机数*/
        randomNumBoth: function (min, max){
            var Range = max - min;
            var Rand = Math.random();
            var num = min + Math.round(Rand * Range); //四舍五入
            return num;
        },
        /*应用跳转绑定事件*/
        redirectAction: function (appCode, userName, password) {

            // $.get(baseURL + "applications/redirect/" + appCode + "?userName=" + userName + "&password=" + password, function (response) {
            $.ajax({
                type: "GET",
                async: false,
                url: baseURL + "applications/redirect/" + appCode,
                data: {userName: userName, password: password},
                contentType: "application/json",
                dataType: "",
                success: function (response) {
                    if (response.loginStatus == "success") {
                        // window.location.href =response.redirectUrl;

                        localStorage.setItem("accessToken", response.accessToken);
                        localStorage.setItem("refreshToken", response.refreshToken);
                        localStorage.setItem("userId", response.user.id);
                        localStorage.setItem("userName", response.user.userName);
                        localStorage.setItem("belongToGarnet", response.user.belongToGarnet)
                        localStorage.setItem("requestTime", new Date().getTime());

                        console.log("userId: " + localStorage.getItem("userId"));

                        // console.log("others not accessToken: " + localStorage.getItem("accessToken"));
                        //
                        // console.log("接下来开始跳转");

                        window.open(response.redirectUrl);
                    } else {
                        vm.error = true;
                        vm.errorMsg = response.message;
                    }
                }
            });

        }

    }
});
