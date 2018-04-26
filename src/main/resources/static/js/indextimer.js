/*监测页面动作*/
var maxTime = 30; // seconds
var time = maxTime;
var i = 0;
var pathName = window.document.location.pathname;
var patrn = /.*index.html$/;


$(document).on('keydown mousedown', function(e) {
    time = maxTime; // reset
});

var intervalId = setInterval(function() {

    time--;

    if (patrn.exec(pathName)) {
        i = i + 1;
    } else {
        i = 0;
    }

    if (time <= 0) {
        ShowInvalidLoginMessage();
        clearInterval(intervalId);
    }

    if (i >= 30) {
        i = 0;
        ShowInvalidLoginMessage();
        clearInterval(localStorage.getItem("checkLoginedTimerId"));
        localStorage.removeItem("checkLoginedTimerId");
        clearInterval(intervalId);
    }
}, 60000);

function ShowInvalidLoginMessage() {
    localStorage.removeItem("userId");
    localStorage.removeItem("userName");
    localStorage.removeItem("belongToGarnet");
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    swal({
            title: "",
            text:  "您已长时间没操作了，请重新登录",
            type: "warning"
        },
        function () {
            if (patrn.exec(pathName)) {
                parent.location.href = 'login.html';
            } else {
                parent.location.href = '../login.html';
            }
        });
}