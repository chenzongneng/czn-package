/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

var baseURL = "http://localhost:8080/garnet/v1.0/";
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
            nowTime = $.now();
            this.src = baseURL + "kaptcha?nowTime=" + nowTime;
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
                url: baseURL + "login?loginFrom=garnet",
                data: JSON.stringify(data),
                contentType: "application/json",
                dataType: "",
                success: function (result) {
                    if (result.loginStatus == "success") {
                        localStorage.setItem("garnetToken", result.garnetToken);
                        localStorage.setItem("userToken", result.userToken);
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