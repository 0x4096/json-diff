package com.github.Is0x4096.jsondiff.core.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.github.Is0x4096.jsondiff.commom.JsonDiffConst;
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
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        Map<String, Object> jsonPathResult = new HashMap<>();
        backTrack(null, jsonObject, jsonPathResult);
        return jsonPathResult;
    }

    private void backTrack(String prefix, JSONObject jsonObject, Map<String, Object> jsonPathResult) {
        if (Objects.isNull(jsonObject)) {
            return;
        }

        for (String s : jsonObject.keySet()) {
            String key;
            if (Objects.isNull(prefix)) {
                key = s;
            } else {
                key = prefix + JsonDiffConst.DOT + s;
            }

            Object object = jsonObject.get(s);
            if (object instanceof JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    String tempKey = JsonDiffConst.LEFT_BRACKET + i + JsonDiffConst.RIGHT_BRACKET;
                    if (jsonArray.get(i) instanceof JSONObject) {
                        backTrack(key + tempKey, jsonArray.getJSONObject(i), jsonPathResult);
                    } else {
                        jsonPathResult.put(key + tempKey, jsonArray.get(i));
                    }
                }
            } else if (object instanceof JSONObject) {
                backTrack(key, (JSONObject) object, jsonPathResult);
            } else {
                jsonPathResult.put(key, object);
            }
        }
    }

}
