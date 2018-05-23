/*验证是否是同一个人登录*/
function checkLogined() {

    if (localStorage.getItem("userName") == null) {
        clearInterval(checkLoginedTimerId);
        // console.log("清理checkLoginedTimerId");
        return;
    }

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
            window.parent.swal({
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

    // console.log("刷新token: " + $.now());

    if (localStorage.getItem("accessToken") == null || accessToken == null) {
        clearInterval(refreshTokenTimerId);
        // console.log("清理refreshTokenTimerId");
        return;
    } else {
        // console.log(localStorage.getItem("accessToken"));
    }

    localStorage.setItem("refreshTokenTimerId", refreshTokenTimerId);
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
var refreshTokenTimerId = window.setInterval("refreshToken();", 60000 * 25);
var checkLoginedTimerId = window.setInterval("checkLogined();", 20000);

//当页面刷新时，刷新token
window.setTimeout("refreshToken();", 3000);