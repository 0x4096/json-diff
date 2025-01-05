package com.github.Is0x4096.jsondiff.core;

import com.github.Is0x4096.jsondiff.commom.JsonDiffAnalyzeType;
import com.github.Is0x4096.jsondiff.commom.JsonDiffOption;
import com.github.Is0x4096.jsondiff.core.impl.Fastjson2JsonPath;

import java.util.Map;

/**
 * @author 0x4096.peng@gmail.com
 * @date 2025/1/5
 */
public class JsonPath {

    public static Map<String, Object> jsonpath(String jsonStr, JsonDiffOption option) {
        IJsonPath iJsonPath;
        if (option.getJsonDiffAnalyzeType() == JsonDiffAnalyzeType.FastJson2) {
            iJsonPath = new Fastjson2JsonPath();
        } else {
            throw new UnsupportedOperationException("没有支持的 json 解析器");
        }
        return iJsonPath.jsonStr2Path(jsonStr, option);
    }

}
