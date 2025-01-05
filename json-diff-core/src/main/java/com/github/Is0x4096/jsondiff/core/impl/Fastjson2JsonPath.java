package com.github.Is0x4096.jsondiff.core.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.github.Is0x4096.jsondiff.commom.JsonDiffOption;
import com.github.Is0x4096.jsondiff.core.IJsonPath;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author 0x4096.peng@gmail.com
 * @date 2025/1/5
 */
public class Fastjson2JsonPath implements IJsonPath {

    @Override
    public Map<String, Object> jsonStr2Path(String json, JsonDiffOption option) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        Map<String, Object> jsonPathResult = new HashMap<>();

        // 过滤
        Set<Pattern> patternSet = buildIgnorePathPatternSet(option);

        backTrack(null, jsonObject, jsonPathResult, patternSet);
        return jsonPathResult;
    }

    private void backTrack(String prefix, JSONObject jsonObject, Map<String, Object> jsonPathResult, Set<Pattern> patternSet) {
        if (Objects.isNull(jsonObject)) {
            return;
        }

        for (String s : jsonObject.keySet()) {
            String key;
            if (Objects.isNull(prefix)) {
                key = s;
            } else {
                key = prefix + "." + s;
            }

            Object object = jsonObject.get(s);
            if (object instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) object;
                for (int i = 0; i < jsonArray.size(); i++) {
                    String temp = "[" + i + "]";
                    if (jsonArray.get(i) instanceof JSONObject) {
                        backTrack(key + temp, jsonArray.getJSONObject(i), jsonPathResult, patternSet);
                    } else {
                        if (!isIgnorePath(key, patternSet)) {
                            jsonPathResult.put(key + temp, jsonArray.get(i));
                        }
                    }
                }
            } else if (object instanceof JSONObject) {
                backTrack(key, (JSONObject) object, jsonPathResult, patternSet);
            } else {
                if (!isIgnorePath(key, patternSet)) {
                    jsonPathResult.put(key, object);
                }
            }
        }
    }

    private boolean isIgnorePath(String path, Set<Pattern> patternSet) {
        if (Objects.isNull(patternSet) || patternSet.isEmpty()) {
            return false;
        }

        for (Pattern pattern : patternSet) {
            if (pattern.matcher(path).find()) {
                return true;
            }
        }
        return false;
    }

    private Set<Pattern> buildIgnorePathPatternSet(JsonDiffOption option) {
        if (Objects.isNull(option) || Objects.isNull(option.getIgnorePath()) || option.getIgnorePath().isEmpty()) {
            return Collections.emptySet();
        }

        Set<Pattern> patternSet = new HashSet<>();
        option.getIgnorePath().forEach(k -> {
            Pattern pattern = Pattern.compile(k);
            patternSet.add(pattern);
        });

        return patternSet;
    }

}
