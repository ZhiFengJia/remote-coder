@echo off
@chcp.com 65001 > NUL 
java -server -Xms1G -Xmx1G -Xmn256m -cp "../config/;../lib/*" com.jzf.remote.web.RemoteWebApplication
pause