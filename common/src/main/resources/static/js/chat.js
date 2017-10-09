/**
 * Created by tpx on 2017/10/8.
 */
function chat(element, imgSrc, doctextContent) {
    var $user = element;
    var $doctorHead = imgSrc;
    //获取输入的值
    var $textContent = $('.chat-info').html();
    var $doctextContent = doctextContent;
    //获取容器
    var $box = $('.bubbleDiv');
    var $boxHeght = $box.height();
    var $sectionHeght = $(".chat-box").height();
    var $elvHeght = Math.abs($boxHeght - $sectionHeght);
    //医生
    if ($user === "leftBubble") {
        $box.append(createdoct(imgSrc, $doctextContent)).animate({scrollTop: $(document).height()}, 300);
    }
    //患者
    else if ($user === "rightBubble") {
        $box.append(createuser($textContent)).animate({scrollTop: $(document).height()}, 300);
    } else {
        console.log("请传入必须的参数");
    }
}
function createdoct(imgSrc, $doctextContent) {
    var $textContent = $doctextContent;
    var $imgSrc = imgSrc;
    var block;
    if ($textContent == '' || $textContent == 'null') {
        alert('亲！别太着急，先说点什么～');
        return;
    }
    block = '<div class="bubbleItem">' +
        '<div class="doctor-head">' +
        '<img src="' + imgSrc + '" alt="doctor"/>' +
        '</div>' +
        '<span class="bubble leftBubble">' + $textContent + '<span class="topLevel"></span></span>' +
        '</div>';
    return block;
};
function createuser($textContent) {
    var $textContent = $textContent;
    var block;
    if ($textContent == '' || $textContent == 'null') {
        return;
    }
    block = '<div class="bubbleItem clearfix">' +
        '<span class="bubble rightBubble">' + $textContent + '<span class="topLevel"></span></span>' +
        '</div>';
    return block;
};

var url = null;
var stompClient = null;
//this line.
function connect(userid) {
    var socket = new SockJS(url + "webSocket");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/greetings', function (greeting) {
            var msgJson = JSON.parse(greeting.body);
            chat("leftBubble", "images/head_portrait.png", msgJson.data);
        });

        stompClient.subscribe('/user/' + userid + '/message', function (greeting) {
            var msgJson = JSON.parse(greeting.body);
            chat("leftBubble", "images/head_portrait.png", msgJson.data);
            //showGreeting(msgJson.data + "-----" + getNowFormatDate());
        });
    });
}

function sendMsg() {
    var $textContent = $('.chat-info').html();
    if ($textContent) {
        stompClient.send("/app/webSocket", {atytopic: "/topic/greetings", msg: $textContent});
    }else{
        return;
    }
    //右侧 自己发 调用，只需填一个参数"rightBubble"
    chat("rightBubble");
    //清空输入框
    $('.chat-info').html('');
}
function connectAny() {
    var socket = new SockJS(url + "webSocket");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/greetings', function (greeting) {
            var msgJson = JSON.parse(greeting.body);
            chat("leftBubble", "images/head_portrait.png", msgJson.data);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
}
/**************************************时间格式化处理************************************/
function dateFtt(fmt, date) { //author: meizz
    var o = {
        "M+": date.getMonth() + 1,                 //月份
        "d+": date.getDate(),                    //日
        "h+": date.getHours(),                   //小时
        "m+": date.getMinutes(),                 //分
        "s+": date.getSeconds(),                 //秒
        "q+": Math.floor((date.getMonth() + 3) / 3), //季度
        "S": date.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
function getNowFormatDate() {
    var crtTime = new Date();
    return dateFtt("yyyy-MM-dd hh:mm:ss", crtTime);//直接调用公共JS里面的时间类处理的办法
}