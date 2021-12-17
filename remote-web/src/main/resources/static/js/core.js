var classFullName = "HelloWorld";
var tempFilePath = "";
var editor;
var isTerminalResize = false;
$(function () {
    var settings = {
        "url": "/project/tree",
        "method": "GET",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "dataType": "json",
        "contentType": false,
        "data": null
    };
    $.ajax(settings).done(function (response) {
        console.log(response);
        $('#jstree').jstree({
            "plugins": ["wholerow"],
            "core": {
                'data': response
            }
        });
        // 文件树选择事件
        $('#jstree').on("changed.jstree", function (e, data) {
            console.log(data);

            if (data.selected[0].charAt(data.selected[0].length - 1) != '/') {
                tempFilePath = data.selected[0];
                getFileByPath(data.selected[0]);
                var fileName = data.selected[0].substring(data.selected[0].lastIndexOf("/") + 1);
                $("#fileName").text(fileName);
                if(/^.*\.class$/.test(fileName)){
                    editor.setOption("readOnly",true);
                    editor.setOption("mode","text");
                }else{
                    editor.setOption("readOnly",false);
                    editor.setOption("mode","text/x-java");
                }
            }
        });
    });

    // 编辑器
    editor = CodeMirror.fromTextArea(document.getElementById("code"), {
        mode: "text/x-java",
        lineNumbers: true,
        matchBrackets: true,
        indentUnit: 4,
        indentWithTabs: true,
        theme: "darcula"
    });
    editor.setOption("extraKeys", {
        "Ctrl-S": function (cm) {
            console.log("editor save");
            $('#dot').hide();
        }
    });
    editor.on("change", function (instance, changeObj) {
        console.log("editor content change");
        $('#dot').show();
    });

    $(".selectorLeft").resizable({
        handles: "e,s",
        resize: function (event, ui) {
            var ele = ui.element;
            var width = $(this).parent().width() - ui.element.width();
            var height = ui.element.height();

            ele.siblings().eq(0).css('height', height + 'px');
            ele.siblings().eq(0).css('width', width + 'px');
            editor.setSize(width, height - 41);

            var totalHeight = $(window).height();
            $("#console").css('height', totalHeight - 40 - height - 40 + 'px');
            $("#terminal").css('height', totalHeight - 40 - height - 40 + 'px');

            if(isTerminalResize){
                terminalResize();
            }
        },
        stop: function (event, ui) {
            var ele = ui.element;
            var width = $(this).parent().width() - ui.element.width();
            var height = ui.element.height();

            ele.siblings().eq(0).css('height', height + 'px');
            ele.siblings().eq(0).css('width', width + 'px');
            editor.setSize(width, height - 41);

            var totalHeight = $(window).height();
            $("#console").css('height', totalHeight - 40 - height - 40 + 'px');
            $("#terminal").css('height', totalHeight - 40 - height - 40 + 'px');

            terminalResize();
        }
    });

    $(".selectorRight").resizable({
        handles: "s",
        resize: function (event, ui) {
            var ele = ui.element;
            var width = $(this).parent().width() - $(".selectorLeft").width();
            var height = ui.element.height();

            $(this).css('width', width + 'px');
            ele.siblings().eq(0).css('height', height + 'px');
            ele.siblings().eq(0).css('width', $(".selectorLeft").width() + 'px');
            editor.setSize(width, height - 41);

            var totalHeight = $(window).height();
            $("#console").css('height', totalHeight - 40 - height - 40 + 'px');
            $("#terminal").css('height', totalHeight - 40 - height - 40 + 'px');

            if(isTerminalResize){
                terminalResize();
            }
        },
        stop: function (event, ui) {
            var ele = ui.element;
            var width = $(this).parent().width() - $(".selectorLeft").width();
            var height = ui.element.height();

            $(this).css('width', width + 'px');
            ele.siblings().eq(0).css('height', height + 'px');
            ele.siblings().eq(0).css('width', $(".selectorLeft").width() + 'px');
            editor.setSize(width, height - 41);

            var totalHeight = $(window).height();
            $("#console").css('height', totalHeight - 40 - height - 40 + 'px');
            $("#terminal").css('height', totalHeight - 40 - height - 40 + 'px');

            terminalResize();
        }
    });

    //初始化大小
    initSize();
    window.addEventListener("resize", () => {
        //窗口改变
        initSize();
        terminalResize();
    });

    $("#terminal-tab").on('shown.bs.tab', function (event) {
        if($(event.target).attr("id") == "nav-terminal-tab"){
            terminalResize();
            if(!isSSHConnected()){
                $("#sshInfo").modal('show');
            }
            isTerminalResize = true;
        }else{
            isTerminalResize = false;
        }
    })
})

function initSize() {
    var totalWidth = $(window).width();
    var totalHeight = $(window).height();
    var width = totalWidth * 0.12;
    var height = $(window).height() * 0.68;

    $(".selectorLeft").css('width', width + 'px');
    $(".selectorLeft").css('height', height + 'px');
    $(".selectorRight").css('width', $(".selectorLeft").parent().width() - width + 'px');
    $(".selectorRight").css('height', height + 'px');
    $(".selectorLeft").resizable("option", "minWidth", totalWidth * 0.045);
    $(".selectorLeft").resizable("option", "maxWidth", totalWidth * 0.9);
    $(".selectorLeft").resizable("option", "minHeight", totalHeight * 0.045);
    $(".selectorLeft").resizable("option", "maxHeight", totalHeight * 0.90);
    $(".selectorRight").resizable("option", "minHeight", totalHeight * 0.045);
    $(".selectorRight").resizable("option", "maxHeight", totalHeight * 0.90);

    editor.setSize($(".selectorLeft").parent().width() - width, height - 41);

    $("#console").css('height', totalHeight - 40 - height - 40 + 'px');
    $("#terminal").css('height', totalHeight - 40 - height - 40 + 'px');
}


function getFileByPath(filePath) {
    console.log("当前选择的节点:" + filePath);
    var form = new FormData();
    form.append("filePath", filePath);
    var settings = {
        "url": "/project/getFile",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
    };
    $.ajax(settings).done(function (response) {
        if (response.valueOf() == "") {
            console.log("当前选择的节点是文件夹");
            return;
        }
        editor.setValue(response);
        $('#dot').hide();

        var packageStr = editor.getLine(0);
        var className = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."));
        if (/^package.*$/.test(packageStr)) {
            className = packageStr.substring(8, packageStr.indexOf(";")) + "/" + className;
            className = className.replace(/\./g, "/");
        }
        console.log("className:" + className);
        classFullName = className;
    });
}

function getHexStrByFilePath() {
    console.log("转换十六进制显示:" + tempFilePath);
    var form = new FormData();
    form.append("filePath", tempFilePath);
    var settings = {
        "url": "/project/getHexStrByFilePath",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
    };
    $.ajax(settings).done(function (response) {
        editor.setValue(response);
        $('#dot').hide();
        editor.setOption("readOnly",true);
    });
}

function getBytecode() {
    console.log("反编译class文件:" + classFullName);
    var form = new FormData();
    form.append("classFullName", classFullName);
    var settings = {
        "url": "/project/getBytecode",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
    };
    $.ajax(settings).done(function (response) {
        editor.setValue(response);
        $('#dot').hide();
        editor.setOption("readOnly",true);
    });
}

$('#execute').click(function () {
    $('#execute').attr("disabled", true);
    $('#loading').show();
    var form = new FormData();
    form.append("className", classFullName);
    form.append("sourceCode", editor.getValue());

    var settings = {
        "url": "/remote/executeJavaSourceCode",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
    };
    $.ajax(settings).done(function (response) {
        printConsole(response);
        $('#execute').attr("disabled", false);
        $('#loading').hide();
    });
});

function refreshProject() {
    var settings = {
        "url": "/project/tree",
        "method": "GET",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "dataType": "json",
        "contentType": false,
        "data": null
    };
    $.ajax(settings).done(function (response) {
        console.log(response);
        $('#jstree').jstree(true).destroy();
        $('#jstree').jstree({
            "plugins": ["wholerow"],
            "core": {
                'data': response
            }
        });
        // 文件树选择事件
        $('#jstree').on("changed.jstree", function (e, data) {
            console.log(data);

            if (data.selected[0].charAt(data.selected[0].length - 1) != '/') {
                getFileByPath(data.selected[0]);
                var fileName = data.selected[0].substring(data.selected[0].lastIndexOf("/") + 1);
                $("#fileName").text(fileName);
            }
        });
    });
}

function printConsole(data) {
    var consoleObj = $('#console');
    consoleObj.append(data + "\r\n");
    consoleObj.scrollTop(consoleObj.prop('scrollHeight'));
}
