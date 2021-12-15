var websocket = null;
//判断当前浏览器是否支持WebSocket, 主要此处要更换为自己的地址
if ('WebSocket' in window) {
    websocket = new WebSocket("ws://localhost/test/one");
} else {
    alert('Not support websocket');
}

//连接成功建立的回调方法
websocket.onopen = function(event) {
    console.log("WebSocket connection successful");
}

//连接关闭的回调方法
websocket.onclose = function() {
    console.log("WebSocket close");
}

//接收到消息的回调方法
websocket.onmessage = function(event) {
    //将消息显示在网页上
    $("#console").val(event.data);
}

//连接发生错误的回调方法
websocket.onerror = function() {
    console.log("WebSocket error");
};

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function() {
    websocket.close();
}

//发送消息
function sendMessage() {
    websocket.send("{'projectName':'algorithm','goals':['clean','package']}");
}