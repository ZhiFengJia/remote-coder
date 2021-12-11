nohup java -server -Xms1G -Xmx1G -Xmn256m -Dfile.encoding=UTF-8 -cp "../config/:../lib/*" com.jzf.remote.web.RemoteWebApplication 1>/dev/null 2>&1 &
echo "PID [ $! ]"