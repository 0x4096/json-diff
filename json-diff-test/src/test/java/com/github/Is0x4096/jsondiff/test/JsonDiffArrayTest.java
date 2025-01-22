package com.github.Is0x4096.jsondiff.test;

import com.alibaba.fastjson2.JSONObject;
import com.github.Is0x4096.jsondiff.commom.JsonDiffAnalyzeType;
import com.github.Is0x4096.jsondiff.commom.JsonDiffOption;
import com.github.Is0x4096.jsondiff.commom.JsonDiffResult;
import com.github.Is0x4096.jsondiff.core.JsonDiff;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author 0x4096.peng@gmail.com
 * @date 2025/1/13
 */
public class JsonDiffArrayTest {

    @Test
    public void test() {
        String oldJson = "[1,2,3,4,5]";
        String newJson = "[1,2,3,4,6]";

        JsonDiffOption option = new JsonDiffOption();
        option.setJsonDiffAnalyzeType(JsonDiffAnalyzeType.FastJson2);

        Set<String> ignorePath = new HashSet<>();
        option.setIgnorePath(ignorePath);

        Map<String, String> mappingMap = new HashMap<>();
        option.setFieldMapping(mappingMap);

        JsonDiffResult jsonDiffResult = JsonDiff.diff(oldJson, newJson, option);

        System.err.println(JSONObject.toJSONString(jsonDiffResult));

    }
}
