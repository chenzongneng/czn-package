/*监测页面动作*/
var maxTime = 30; // seconds
var time = maxTime;
// var time;

$(document).on('keydown mousedown', function(e) {
    time = maxTime; // reset
    // localStorage.setItem("time", time);
});

var intervalId = setInterval(function() {
    // time = localStorage.getItem("time");
    time--;

    console.log("others time: " + time);

    // localStorage.setItem("time", time);

    // console.log("action timer: " + time);

    localStorage.setItem("actionTimerId", intervalId);
    if (time <= 0) {
        //检查登录定时器
        window.clearInterval(localStorage.getItem("checkLoginedTimerId"));
        localStorage.removeItem("checkLoginedTimerId");
        //刷新token定时器
        window.clearInterval(localStorage.getItem("refreshTokenTimerId"));
        localStorage.removeItem("refreshTokenTimerId");
        //记录操作定时器
        clearInterval(intervalId);
        localStorage.removeItem("actionTimerId");
        ShowInvalidLoginMessage();
        //首页记录操作定时器
        // clearInterval(localStorage.getItem("indexTimerId"));
        // localStorage.removeItem("indexTimerId");
    }
// }, 1000);
}, 60000);

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
            // console.log("我是actiontimer...");
            parent.location.href = 'login.html';
        });
}

hookAjax({
    open:function(arg,xhr){
        var patrn = /^#.*checklogined.*/;
        var url = arg[1];
        console.log("aaa=" + patrn.exec(url));
        if (patrn.exec(url) == null) {
            localStorage.setItem("time", 30);
            console.log("open called: method:%s,url:%s,async:%s",arg[0],arg[1],arg[2]);
        } else {
            console.log("checklogined");
        }

    }
});