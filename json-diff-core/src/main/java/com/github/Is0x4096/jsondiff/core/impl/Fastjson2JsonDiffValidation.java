package com.github.Is0x4096.jsondiff.core.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.github.Is0x4096.jsondiff.commom.JsonDiffConst;
import com.github.Is0x4096.jsondiff.commom.JsonDiffObjectType;
import com.github.Is0x4096.jsondiff.commom.JsonDiffOption;
import com.github.Is0x4096.jsondiff.core.AbstractJsonDiffValidation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author 0x4096.peng@gmail.com
 * @date 2025/1/5
 */
public class Fastjson2JsonDiffValidation extends AbstractJsonDiffValidation {

    public Fastjson2JsonDiffValidation(String jsonStr, JsonDiffOption jsonDiffOption) {
        super(jsonStr, jsonDiffOption);
    }

    @Override
    public Map<String, Object> jsonStr2Path() {
        Object object = null;
        if (objectType == JsonDiffObjectType.OBJECT) {
            object = JSONObject.parseObject(jsonStr);
        } else if (objectType == JsonDiffObjectType.ARRAY) {
            object = JSONArray.parse(jsonStr);
        }

        Map<String, Object> jsonPathResult = new HashMap<>();
        backTrack(null, object, jsonPathResult);
        return jsonPathResult;
    }

    private void backTrack(String prefix, Object object, Map<String, Object> jsonPathResult) {
        if (Objects.isNull(object)) {
            return;
        }

        if (object instanceof JSONObject jsonObject) {
            for (String s : jsonObject.keySet()) {
                String key;
                if (Objects.isNull(prefix)) {
                    key = s;
                } else {
                    key = prefix + JsonDiffConst.DOT + s;
                }
                Object tempObject = jsonObject.get(s);
                backTrack(key, tempObject, jsonPathResult);
            }
        } else if (object instanceof JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.size(); i++) {
                String tempKey = JsonDiffConst.LEFT_BRACKET + i + JsonDiffConst.RIGHT_BRACKET;
                String tempPrefix = Objects.isNull(prefix) ? JsonDiffConst.Blank : prefix;
                if (jsonArray.get(i) instanceof JSONObject || jsonArray.get(i) instanceof JSONArray) {
                    backTrack(tempPrefix + tempKey, jsonArray.getJSONObject(i), jsonPathResult);
                } else {
                    jsonPathResult.put(tempPrefix + tempKey, jsonArray.get(i));
                }
            }
        } else {
            jsonPathResult.put(prefix, object);
        }
    }

}
