/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

// TODO:暂时修改
// var baseURL = "http://localhost:8080/garnet/v1.0/";
// var baseURL = "http://192.168.0.200:12306/garnet/v1.0/";
var baseURL = "http://localhost:12306/garnet/api/v1.0/";

var nowTime = $.now();
var vm = new Vue({
    el: '#garnetApp',
    data: {
        username: '',
        password: '',
        captcha: '',
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

            alert(JSON.stringify(this.inputtext));

        },
        login: function () {

            // var data = {
            //     userName: vm.username,
            //     password: vm.password,
            //     vcode: vm.captcha,
            // };
            // alert(JSON.stringify(this.inputtext));

            $.ajax({
                type: "POST",
                url: baseURL + "userLogin",
                data: JSON.stringify(this.inputtext),
                contentType: "application/json",
                dataType: "",
                success: function (result) {

                    if (result.loginStatus == "success") {

                        // alert(result.user.id);
                        // localStorage.setItem("garnetToken", result.garnetToken);
                        // localStorage.setItem("userToken", result.userToken);
                        localStorage.setItem("userId", result.user.id);
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