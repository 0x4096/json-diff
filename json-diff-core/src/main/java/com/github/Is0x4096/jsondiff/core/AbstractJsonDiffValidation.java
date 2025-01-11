package com.github.Is0x4096.jsondiff.core;

import com.github.Is0x4096.jsondiff.commom.JsonDiffOption;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author 0x4096.peng@gmail.com
 * @date 2025/1/9
 */
public abstract class AbstractJsonDiffValidation implements IJsonDiffValidation {

    /**
     * JSON 字符串
     */
    protected String jsonStr;

    /**
     * JSON diff 规则
     */
    protected JsonDiffOption jsonDiffOption;

    public AbstractJsonDiffValidation(String jsonStr, JsonDiffOption jsonDiffOption) {
        this.jsonStr = jsonStr;
        this.jsonDiffOption = jsonDiffOption;
    }

    @Override
    public void filter(Map<String, Object> jsonpathMap) {
        if (Objects.isNull(jsonpathMap) || jsonpathMap.isEmpty()) {
            return;
        }

        if (Objects.isNull(jsonDiffOption) || Objects.isNull(jsonDiffOption.getIgnorePath()) || jsonDiffOption.getIgnorePath().isEmpty()) {
            return;
        }

        Set<Pattern> patternSet = new HashSet<>();
        jsonDiffOption.getIgnorePath().forEach(k -> {
            Pattern pattern = Pattern.compile(k);
            patternSet.add(pattern);
        });

        Set<String> filterKeySet = new HashSet<>();
        jsonpathMap.forEach((k, v) -> {
            for (Pattern pattern : patternSet) {
                if (pattern.matcher(k).matches()) {
                    filterKeySet.add(k);
                }
            }
        });
        filterKeySet.forEach(jsonpathMap::remove);
    }

    @Override
    public Map<String, Object> removeAndGet(Map<String, Object> jsonpathMap, Collection<String> removePath) {
        Map<String, Object> removeMap = new HashMap<>();

        if (Objects.isNull(jsonpathMap) || jsonpathMap.isEmpty()) {
            return removeMap;
        }

        if (Objects.isNull(removePath) || removePath.isEmpty()) {
            return removeMap;
        }

        removePath.forEach(k -> {
            if (jsonpathMap.containsKey(k)) {
                removeMap.put(k, jsonpathMap.get(k));
            }
        });

        removeMap.forEach(jsonpathMap::remove);
        return removeMap;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public JsonDiffOption getJsonDiffOption() {
        return jsonDiffOption;
    }

}
