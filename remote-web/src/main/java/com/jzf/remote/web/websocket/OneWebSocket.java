package com.jzf.remote.web.websocket;

import com.alibaba.fastjson.JSON;
import com.jzf.remote.core.util.MavenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 前后端交互的类实现消息的接收推送(自己发送给自己)
 *
 * @author jiazhifeng
 * @ServerEndpoint(value = "/test/one")注解是服务端与客户端交互的关键，其值(/test/one)得与index页面中的请求路径对应。 前端通过此URI和后端交互，建立连接
 * @date 2021/12/15 17:34
 */
@Slf4j
@ServerEndpoint(value = "/web/console")
@Component
public class OneWebSocket {
    /**
     * 记录当前在线连接数
     */
    private static final AtomicInteger onlineCount = new AtomicInteger(0);

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        onlineCount.incrementAndGet(); // 在线数加1
        log.info("有新连接加入：{}，当前在线人数为：{}", session.getId(), onlineCount.get());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        onlineCount.decrementAndGet(); // 在线数减1
        log.info("有一连接关闭：{}，当前在线人数为：{}", session.getId(), onlineCount.get());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("服务端收到客户端[{}]的消息:{}", session.getId(), message);
        this.sendMessage(message, session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session session) {
        try {
            Map map = JSON.parseObject(message, Map.class);

            MavenUtils.goals((String) map.get("projectName"), (List<String>) map.get("goals"),
                    consumer -> {
                        try {
                            do {
                                String line = consumer.readLine();
                                if (line == null) {
                                    break;
                                }
                                log.info("服务端给客户端[{}]发送消息{}", session.getId(), line);
                                session.getBasicRemote().sendText(line);
                            } while (true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败：{}", e.getMessage());
        }
    }
}