var webSSH = null;
var term = null;
var isConnected = false;
//判断当前浏览器是否支持WebSocket, 主要此处要更换为自己的地址
if (window.WebSocket) {
    if (window.location.protocol == 'https:') {
        var protocol = 'wss://';
    } else {
        var protocol = 'ws://';
    }
    webSSH = new WebSocket(protocol + "localhost/web/terminal");
} else {
    alert('Not support websocket');
}

//连接成功建立的回调方法
webSSH.onopen = function (event) {
    console.log("Web Terminal connection successful");
    term = new Terminal({
        cols: 180,
        rows: 1,
        cursorBlink: true, // 光标闪烁
        cursorStyle: "block", // 光标样式  null | 'block' | 'underline' | 'bar'
        scrollback: 800, //回滚
        tabStopWidth: 8, //制表宽度
        screenKeys: true
    });
    term.on('data', function (data) {
        //键盘输入时的回调函数
        sendClientData(data);
    });
    term.open(document.getElementById('terminal'));
    //在页面上显示连接中...
    term.write('Connecting...\r\n');
}

//连接关闭的回调方法
webSSH.onclose = function () {
    console.log("Web Terminal close");
    term.write("\r\nconnection closed");
}

//接收到消息的回调方法
webSSH.onmessage = function (event) {
    //将消息显示在网页上
    term.write(event.data);
}

//连接发生错误的回调方法
webSSH.onerror = function (error) {
    console.log("Web Terminal error");
    term.write('\r\nError: ' + error);
};

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
    webSSH.close();
}

function sendClientData(data) {
    //发送指令
    webSSH.send(JSON.stringify({ "operate": "command", "command": data }));
}

function connectSSH() {
    //连接ssh,暂时没用到,目前直接在后端配置文件中配置连接信息.
    var host = $("#sshInfo input").get(0).value;
    var port = $("#sshInfo input").get(1).value;
    var username = $("#sshInfo input").get(2).value;
    var password = $("#sshInfo input").get(3).value;
    webSSH.send(JSON.stringify({ "operate": 'connect', "host": host, "port": port, "username": username, "password": password }));
    isConnected = true;
}

function isSSHConnected() {
    return isConnected;
}

function terminalResize(){
    var totalWidth = $(window).width();
    var totalHeight = $(window).height();
    var height = $(".selectorRight").height();

    term.resize(180, (totalHeight - 40 - height - 40) / 24);
    term.fit();
    console.log("Web Terminal resize:",term.cols, term.rows);
}