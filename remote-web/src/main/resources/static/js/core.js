var classFullName;
var tempFilePath;
var editor;
var isTerminalResize = false;
$(function () {
    $('#jstree').jstree({
        /*core：表示jstree核心参数，主要设置两个data：也就是初始化时候jstree样子，check_callback：必须为true（否则增删改动作没有反应，这些动作都是需要回调。）*/
        "core": {
            "check_callback": true,
            "multiple": false,
            "data": {
                'url': "/project/tree"
            }
        },
        /*plugins:表示你使用那些插件,contextmenu:右键菜单,dnd:节点可以拖拽,unique:同级节点去重,wholerow:凸显被选择的节点*/
        "plugins": [
            "contextmenu", "dnd", "unique", "wholerow"
        ],
        "dnd": {
            "copy": false
        },
        /*contextmenu:就是右键菜单插件，items就是自定义右键菜单选项，label是右键菜单选项的名称。icon就是选项前面的图标，action点击选项触发事件*/
        "contextmenu": {
            /*指示在调用上下文菜单时是否应选择该节点。默认为true.*/
            "select_node": true,
            /*指示菜单是否应与节点对齐。默认为true，否则使用鼠标坐标。*/
            "show_at_node": false,
            "items": {
                "New": {
                    "label": "New",
                    "submenu": {
                        "NewFile": {
                            "label": "File",
                            "action": function (obj) {
                                var inst = $.jstree.reference(obj.reference);
                                var clickedNode = inst.get_node(obj.reference);
                                if (!/^.*\/$/.test(clickedNode.id)) {
                                    //如果当前选择的是文件的话,就在同级目录下创建文件.
                                    clickedNode = inst.get_node(clickedNode.parent);
                                }
                                var newNode = inst.create_node(clickedNode, { text: "新建文件", icon: "jstree-file" });
                                inst.edit(newNode);
                                inst.open_node(clickedNode);
                            }
                        },
                        "NewDirectory": {
                            "label": "Directory",
                            "action": function (obj) {
                                var inst = $.jstree.reference(obj.reference);
                                var clickedNode = inst.get_node(obj.reference);
                                if (!/^.*\/$/.test(clickedNode.id)) {
                                    //如果当前选择的是文件的话,就在同级目录下创建文件夹.
                                    clickedNode = inst.get_node(clickedNode.parent);
                                }
                                var newNode = inst.create_node(clickedNode, { text: "新建文件夹", icon: "jstree-folder" });
                                inst.edit(newNode);
                                inst.open_node(newNode);
                            }
                        }
                    }
                },
                "Rename": {
                    "label": "Rename",
                    "action": function (obj) {
                        var inst = $.jstree.reference(obj.reference);
                        var clickedNode = inst.get_node(obj.reference);
                        inst.edit(clickedNode);
                    }
                },
                "Delete": {
                    "label": "Delete...",
                    "action": function (obj) {
                        var inst = $.jstree.reference(obj.reference);
                        var clickedNode = inst.get_node(obj.reference);
                        inst.delete_node(clickedNode);
                    }
                },
                "Refresh": {
                    "label": "Refresh...",
                    "action": function (obj) {
                        var inst = $.jstree.reference(obj.reference);
                        inst.refresh();
                    }
                }
            }
        }
    });
    // 文件树选择事件
    $('#jstree').on("changed.jstree", function (e, data) {
        console.log(e, data);
        if (data.action == "select_node") {
            if (data.selected[0].charAt(data.selected[0].length - 1) != '/') {
                tempFilePath = data.selected[0];
                getFileByPath(data.selected[0]);
                var fileName = data.selected[0].substring(data.selected[0].lastIndexOf("/") + 1);
                $("#fileName").text(fileName);
                if (/^.*\.java/.test(fileName)) {
                    editor.setOption("readOnly", false);
                    editor.setOption("mode", "text/x-java");
                } else if (/^.*\.class$/.test(fileName)) {
                    editor.setOption("readOnly", true);
                    editor.setOption("mode", "text/plain");
                } else if (/^.*\.jar/.test(fileName)) {
                    editor.setOption("readOnly", true);
                    editor.setOption("mode", "text/plain");
                } else if (/^.*\.xml/.test(fileName)) {
                    editor.setOption("readOnly", false);
                    editor.setOption("mode", "text/x-java");
                } else if (/^.*\.json/.test(fileName)) {
                    editor.setOption("readOnly", false);
                    editor.setOption("mode", "application/json");
                } else if (/^.*\.yml/.test(fileName)) {
                    editor.setOption("readOnly", false);
                    editor.setOption("mode", "text/yaml");
                } else if (/^.*\.js/.test(fileName)) {
                    editor.setOption("readOnly", false);
                    editor.setOption("mode", "text/javascript");
                } else if (/^.*\.css/.test(fileName)) {
                    editor.setOption("readOnly", false);
                    editor.setOption("mode", "text/css");
                } else if (/^.*\.html/.test(fileName)) {
                    editor.setOption("readOnly", false);
                    editor.setOption("mode", "text/html");
                } else if (/^.*\.jsp/.test(fileName)) {
                    editor.setOption("readOnly", false);
                    editor.setOption("mode", "application/x-jsp");
                } else if (/^.*\.sql/.test(fileName)) {
                    editor.setOption("readOnly", false);
                    editor.setOption("mode", "text/x-mysql");
                } else if (/^.*\.md/.test(fileName)) {
                    editor.setOption("readOnly", false);
                    editor.setOption("mode", "text/x-markdown");
                } else if (/^.*\.sh/.test(fileName)) {
                    editor.setOption("readOnly", false);
                    editor.setOption("mode", "text/x-sh");
                } else {
                    editor.setOption("readOnly", false);
                    editor.setOption("mode", "text/plain");
                }
            }
        }
    });

    $('#jstree').on("create_node.jstree", function (e, data) {
        console.log("创建", e, data);
        var isDirectory = data.node.text == "新建文件夹" ? true : false;
        $.post("/file/create", { path: data.node.parent, fileName: data.node.text, isDirectory: isDirectory },
            function (data) {
                console.log(data);
            });
    });

    $('#jstree').on("rename_node.jstree", function (e, data) {
        console.log("重命名", e, data);
        $.post("/file/rename", { path: data.node.parent, oldFileName: data.old, newFileName: data.text },
            function (data) {
                console.log(data);
                refreshWorkspace();
            });
    });

    $('#jstree').on("delete_node.jstree", function (e, data) {
        console.log("删除", e, data);
        $.post("/file/delete", { path: data.node.parent, fileName: data.node.text },
            function (data) {
                console.log(data);
                refreshWorkspace();
            });
    });

    $('#jstree').on("move_node.jstree", function (e, data) {
        console.log("移动", e, data);
        $.post("/file/move", { oldPath: data.old_parent, newPath: data.node.parent, fileName: data.node.text },
            function (data) {
                console.log(data);
                refreshWorkspace();
            });
    });

    // 编辑器
    editor = CodeMirror.fromTextArea(document.getElementById("code"), {
        mode: "text/plain",
        readOnly: true,
        lineNumbers: true,
        matchBrackets: true,
        indentUnit: 4,
        indentWithTabs: true,
        theme: "darcula"
    });
    editor.setOption("extraKeys", {
        "Ctrl-S": function (cm) {
            console.log("editor save");
            saveFile();
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

            if (isTerminalResize) {
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

            if (isTerminalResize) {
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
        if ($(event.target).attr("id") == "nav-terminal-tab") {
            terminalResize();
            if (!isSSHConnected()) {
                $("#sshInfo").modal('show');
            }
            isTerminalResize = true;
        } else {
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
    $(".selectorRight").css('width', $(".selectorLeft").parent().width() - width - 15 + 'px');
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
        if (response.valueOf() == null) {
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
        editor.setOption("readOnly", true);
        editor.setOption("mode", "text");
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
        editor.setOption("readOnly", true);
    });
}

function saveFile() {
    if (tempFilePath != null) {
        console.log("save file", tempFilePath);
        $.post("/file/save", { filePath: tempFilePath, content: editor.getValue() },
            function (data) {
                console.log(data);
                $('#dot').hide();
            });
    }
}

$('#execute').click(function () {
    if(classFullName != null){
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
    }
});

function refreshWorkspace() {
    $.jstree.reference($('#jstree')).refresh();
}

function printConsole(data) {
    var consoleObj = $('#console');
    consoleObj.append(data + "\r\n");
    consoleObj.scrollTop(consoleObj.prop('scrollHeight'));
}
