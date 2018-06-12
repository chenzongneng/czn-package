/*监测页面动作*/
var maxTime = 30; // seconds
var time = maxTime;
// var time;
var patrn = /^#.*html$/;
var test = maxTime;


$(document).on('keydown mousedown', function(e) {
    time = maxTime; // reset
});

var intervalId = setInterval(function() {

    var test_time = localStorage.getItem("time");
    test_time --;
    localStorage.setItem("time", test_time);

    console.log("test_time: " + test_time);
    console.log("time: " + time);

    if (time<0) {
        alert("我小于0啦~~");
    }

    // if (localStorage.getItem("accessToken") == null || accessToken == null) {
    //     clearInterval(intervalId);
    //     return;
    // }

    var hashName = window.location.hash;

    if (patrn.exec(hashName)) {
        time = maxTime;
    } else {
        // time = localStorage.getItem("time");
        time--;
        // localStorage.setItem("time", time);
    }

    // console.log(hashName);
    // console.log(time);
    localStorage.setItem("indexTimerId", intervalId)
    if (time <= 0) {
        //检查登录定时器
        localStorage.removeItem("checkLoginedTimerId");
        clearInterval(localStorage.getItem("checkLoginedTimerId"));
        //刷新token定时器
        localStorage.removeItem("refreshTokenTimerId");
        clearInterval(localStorage.getItem("refreshTokenTimerId"));
        //首页记录操作定时器
        localStorage.removeItem("indexTimerId");
        clearInterval(intervalId);
        //子页记录操作定时器
        localStorage.removeItem("actionTimerId");
        clearInterval(localStorage.getItem("actionTimerId"));
        ShowInvalidLoginMessage();
    }
}, 1000);
// }, 60000);

function ShowInvalidLoginMessage() {
    localStorage.removeItem("userId");
    localStorage.removeItem("userName");
    localStorage.removeItem("belongToGarnet");
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    window.parent.swal({
            title: "",
            text:  "您已长时间没操作了，请重新登录",
            type: "warning"
        },
        function () {
            // console.log("我是indextimer...");
            parent.location.href = 'login.html';
        });
}