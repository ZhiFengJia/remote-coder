var classFullName = "HelloWorld";
var editor;
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

    // 编辑器
    editor = CodeMirror.fromTextArea(document.getElementById("code"), {
        mode: "text/x-java",
        lineNumbers: true,
        matchBrackets: true,
        indentUnit: 4,
        indentWithTabs: true,
    });
    editor.setOption("theme", 'darcula');

    $(".selector").resizable({
        // minWidth: 360,
        // maxWidth: 900,
        // minHeight:600,
        // maxHeight: 700,
        resize: function (event, ui) {
            var ele = ui.element;
            var width = $(this).parent().width() - ui.element.outerWidth();
            var height = ui.element.outerHeight();

            ele.siblings().eq(0).css('height', height + 'px');
            ele.siblings().eq(0).css('width',width+'px');
            editor.setSize(width, height - 41);


        }
    });

    //初始化大小
    var width = 300;
    var height = 640;
    $(".selector").css('width',width+'px');
    $(".selector").css('height', height + 'px');
    $(".selector").siblings().eq(0).css('height', height + 'px');
    $(".selector").siblings().eq(0).css('width',$(".selector").parent().width() - width+'px');
    editor.setSize($(".selector").parent().width() - width, height - 41);
    $(".form-control[readonly]").css('height', 13.4 + 'rem');
})


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
        $("#console").val(response);
        $('#execute').attr("disabled", false);
        $('#loading').hide();
    });
});
