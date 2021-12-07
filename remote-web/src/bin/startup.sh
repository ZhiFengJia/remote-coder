nohup java -server -Xms1G -Xmx1G -Xmn256m -cp "../config/:../lib/*" com.jzf.remote.core.RemoteCoreApplication 1>/dev/null 2>&1 &
echo "PID [ $! ]"