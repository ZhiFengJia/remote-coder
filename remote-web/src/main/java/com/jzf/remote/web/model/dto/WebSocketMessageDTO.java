package com.jzf.remote.web.model.dto;

import lombok.Data;

/**
 * @author jiazhifeng
 * @date 2021/12/15 18:10
 */
@Data
public class WebSocketMessageDTO {
    private String sessionId;
    private String message;
}
