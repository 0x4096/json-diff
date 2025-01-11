package com.github.Is0x4096.jsondiff.commom;

import lombok.Data;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 0x4096.peng@gmail.com
 * @date 2025/1/2
 */
@Data
public class JsonDiffResult {

    /**
     * 是否匹配
     */
    private Boolean isMatch;

    /**
     * 差异内容 k: JsonDiffType 差异类型 v: 具体差异值
     */
    private Map<JsonDiffType, List<DiffContent>> diffContent;

    /**
     * JSON 对比原始值
     */
    private Map<String, Object> oldJsonPath;

    /**
     * JSON 对比值
     */
    private Map<String, Object> newJsonPath;

    /**
     * 忽略对比的 jsonpath 规则
     *
     * @see com.github.Is0x4096.jsondiff.commom.JsonDiffOption#ignorePath
     */
    private Set<String> ignorePath;

    /**
     * 映射字段对比规则
     *
     * @see com.github.Is0x4096.jsondiff.commom.JsonDiffOption#fieldMapping
     */
    private Map<String, String> mappingPath;

    @Data
    public static class DiffContent {
        /**
         * 原始对比 jsonpath
         */
        private String jsonPath;

        /**
         * mapping 时的对比值 jsonpath
         */
        private String mappingPath;

        /**
         * JSON 对比原始值的值
         */
        private Object oldValue;

        /**
         * JSON 对比值的值
         */
        private Object newValue;
    }

    public void addDiffContent(JsonDiffType jsonDiffType, DiffContent content) {
        if (Objects.isNull(diffContent)) {
            diffContent = new ConcurrentHashMap<>();
            List<DiffContent> diffContentList = new ArrayList<>();
            diffContentList.add(content);
            diffContent.put(jsonDiffType, diffContentList);
            return;
        }
        if (!diffContent.containsKey(jsonDiffType)) {
            List<DiffContent> diffContentList = new ArrayList<>();
            diffContentList.add(content);
            diffContent.put(jsonDiffType, diffContentList);
        } else {
            diffContent.get(jsonDiffType).add(content);
        }
    }

}
