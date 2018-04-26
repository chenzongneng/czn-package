/*监测页面动作*/
var maxTime = 30; // seconds
var time = maxTime;

$(document).on('keydown mousedown', function(e) {
    time = maxTime; // reset
});

var intervalId = setInterval(function() {
    time--;

    if (time <= 0) {
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
            parent.location.href = '../login.html';
        });
}