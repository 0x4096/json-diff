package com.github.Is0x4096.jsondiff.commom;

import lombok.Data;

/**
 * @author 0x4096.peng@gmail.com
 * @date 2025/1/2
 */
@Data
public class JsonDiffReq {

    private String projectName;

    private String uri;

    private Long timestamp = System.currentTimeMillis();

    private String traceId;

    private Message oldMessage;

    private Message newMessage;

    @Data
    public static class Message {
        private String reqJsonStr;
        private String respJsonStr;
    }

}
