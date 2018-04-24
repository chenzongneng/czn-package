/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

// TODO:暂时修改
// var baseURL = "http://localhost:8080/garnet/v1.0/";
// var baseURL = "http://192.168.0.200:12306/garnet/v1.0/";
var baseURL = "http://localhost:12306/garnet/api/v1.0/";
// var baseURL = "http://192.168.111.100:12306/garnet/api/v1.0/";

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

            var userId = localStorage.getItem("userId");


            var data = {
                userName: vm.userName,
                password: vm.password,
                kaptcha: vm.captcha,
                nowTime: nowTime,
                appCode: 'garnet'
            };
            $.ajax({
                type: "POST",
                url: baseURL + "users/garnetlogin",
                data: JSON.stringify(data),
                contentType: "application/json",
                dataType: "",
                success: function (result) {
                    if (result.loginStatus == "success") {
                        localStorage.setItem("refreshToken", result.refreshToken);
                        localStorage.setItem("accessToken", result.accessToken);
                        localStorage.setItem("userId", result.user.id);
                        localStorage.setItem("userName", result.user.userName);
                        localStorage.setItem("belongToGarnet", result.user.belongToGarnet)

                        console.log("login cookie: " + getCookie(vm.userName));

                        localStorage.setItem("cookie", getCookie(vm.userName));
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


function getCookie(name) {

    console.log("cookie: == " + JSON.stringify(document.cookie));
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");

    if(arr=document.cookie.match(reg))

        return unescape(arr[2]);
    else
        return "hehe";

    // console.log("length: " + document.cookie.length);
    //
    // if(document.cookie.length > 0) {
    //     var c_start = document.cookie.indexOf(c_name + "=");//获取字符串的起点
    //     if(c_start != -1) {
    //         c_start = c_start + c_name.length + 1;//获取值的起点
    //         var c_end = document.cookie.indexOf(";", c_start);//获取结尾处
    //         if(c_end == -1) c_end = document.cookie.length;//如果是最后一个，结尾就是cookie字符串的结尾
    //         return decodeURI(document.cookie.substring(c_start, c_end));//截取字符串返回
    //     }
    // }
    // return "haha";
}
