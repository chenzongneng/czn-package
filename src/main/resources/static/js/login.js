/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

// TODO:暂时修改
// var baseURL = "http://localhost:8080/garnet/v1.0/";
// var baseURL = "http://192.168.0.200:12306/garnet/v1.0/";
var baseURL = "http://localhost:12306/garnet/v1.0/";
var nowTime = $.now();
var vm = new Vue({
    el: '#garnetApp',
    data: {
        username: '',
        password: '',
        captcha: '',
        error: false,
        errorMsg: '',
        src: baseURL + 'kaptcha?nowTime=' + nowTime
    },
    methods: {
        refreshCode: function () {
            var oldTime = nowTime;
            nowTime = $.now();
            this.src = baseURL + "kaptcha?nowTime=" + nowTime + "&oldTime=" + oldTime;
        },
        login: function () {
            var data = {
                userName: vm.username,
                password: vm.password,
                captcha: vm.captcha,
                nowTime: nowTime
            };
            $.ajax({
                type: "POST",
                url: baseURL + "login/1?loginFrom=garnet",
                data: JSON.stringify(data),
                contentType: "application/json",
                dataType: "",
                success: function (result) {
                    console.log(JSON.stringify(result));
                    if (result.loginStatus == "success") {
                        localStorage.setItem("garnetToken", result.garnetToken);
                        localStorage.setItem("userToken", result.userToken);
                        localStorage.setItem("userId", result.userId);
                        parent.location.href = 'index.html';
                    } else {
                        vm.error = true;
                        vm.errorMsg = result.message;
                    }
                }
            });
        }
    }
});