package com.github.Is0x4096.jsondiff.commom;

import lombok.Data;

import java.util.Map;

/**
 * @author 0x4096.peng@gmail.com
 * @date 2025/1/2
 */
@Data
public class JsonDiffResult {

    private boolean isMatch;

    private Map<JsonDiffType, DiffContent> diffContent;

    private Map<String, Object> oldJsonPath;
    private Map<String, Object> newJsonPath;

    @Data
    public static class DiffContent {
        private String jsonPath;
        private Object oldValue;
        private Object newValue;
    }

}
