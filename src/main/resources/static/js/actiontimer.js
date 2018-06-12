/*监测页面动作*/

$(document).on('keydown mousedown', function(e) {
    // localStorage.setItem("time", time);
});

var intervalId = setInterval(function() {

    // console.log("检查是否长时间没有操作：" + new Date().getTime());
    // console.log("最后一次操作时间：" + localStorage.getItem("requestTime"));

    localStorage.setItem("actionTimerId", intervalId);
    var nowTime = new Date().getTime();
    var requestTime = localStorage.getItem("requestTime");
    // var maxTime = 30 * 60000; //最大时间，毫秒
    var maxTime = 30 * 60000; //最大时间，毫秒

    // console.log(nowTime + " - " + requestTime);

    if (nowTime - requestTime >= maxTime) {
        // console.log("登录超时了...");

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
    }

// }, 1000);
// }, 30000);
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