/*验证是否是同一个人登录*/
function checkLogined() {
    $.get(baseURL + "checklogined/?userName=" + localStorage.getItem("userName") + "&token=" + accessToken, function (response) {

        // console.log("检查是否在其他地方登录...");
        localStorage.setItem("checkLoginedTimerId", checkLoginedTimerId)

        if (!response) {
            return;
        }

        if (response.data == Boolean(false)) {
            localStorage.removeItem("userId");
            localStorage.removeItem("userName");
            localStorage.removeItem("belongToGarnet");
            localStorage.removeItem("accessToken");
            localStorage.removeItem("refreshToken");
            swal({
                    title: "",
                    text:  "您的账号已在其他地方登录。",
                    type: "error"
                },
                function () {
                    location.href = 'login.html';
                });
        }

    });
}

//刷新token
function refreshToken () {

    console.log("刷新token: " + $.now());

    var data = {
        userName: localStorage.getItem("userName"),
        token: localStorage.getItem("refreshToken"),
        appCode: "garnet"
    }
    $.ajax({
        type: "POST",
        async: true,
        url: baseURL + "users/garnetrefreshtoken?token=" + accessToken,
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "",
        success: function (result) {
            // console.log(JSON.stringify(result.accessToken) + "\n" + JSON.stringify(result.refreshToken));
            localStorage.setItem("accessToken", result.accessToken);
            localStorage.setItem("refreshToken", result.refreshToken);
        }
    });
}
//每半小时自动刷新token
// window.setInterval("refreshToken();", 60000 * 28);
window.setInterval("refreshToken();", 60000 * 29);
var checkLoginedTimerId = window.setInterval("checkLogined();", 20000);