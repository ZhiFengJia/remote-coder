@echo off
@chcp.com 65001 > NUL 
java -server -Xms1G -Xmx1G -Xmn256m -Dfile.encoding=UTF-8 -cp "../config/;../lib/*" com.jzf.remote.web.RemoteWebApplication
pause