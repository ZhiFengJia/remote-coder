var webConsole = null;
//判断当前浏览器是否支持WebSocket, 主要此处要更换为自己的地址
if (window.WebSocket) {
    if (window.location.protocol == 'https:') {
        var protocol = 'wss://';
    } else {
        var protocol = 'ws://';
    }
    webConsole = new WebSocket(protocol + "localhost/web/console");
} else {
    alert('Not support websocket');
}

//连接成功建立的回调方法
webConsole.onopen = function(event) {
    console.log("Web Console connection successful");
}

//连接关闭的回调方法
webConsole.onclose = function() {
    console.log("Web Console close");
}

//接收到消息的回调方法
webConsole.onmessage = function(event) {
    //将消息显示在网页上
    printConsole(event.data);
}

//连接发生错误的回调方法
webConsole.onerror = function() {
    console.log("Web Console error");
};

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function() {
    webConsole.close();
}

//发送消息
function projectClean() {
    webConsole.send("{'projectName':'algorithm','goals':['clean']}");
}

function projectCompile(){
    webConsole.send("{'projectName':'algorithm','goals':['compile']}");
}

function projectBuild() {
    webConsole.send("{'projectName':'algorithm','goals':['package']}");
}

function projectCleanAndBuild() {
    webConsole.send("{'projectName':'algorithm','goals':['clean','package']}");
}