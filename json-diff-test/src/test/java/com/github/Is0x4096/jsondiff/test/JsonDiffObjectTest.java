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
 * @date 2025/1/11
 */
public class JsonDiffObjectTest {

    @Test
    public void test() {
        String oldJson = "{\"name\":\"Json on\",\"description\":\"一个简洁的在线 JSON 查看器\",\"opensource\":{\"是否开源\":true,\"GitHub\":\"https://github.com/bimohxh/jsonon\"},\"哈哈\":11}";
        String newJson = "{\"name\":\"Json on\",\"description\":\"一个简洁的在线 JSON 查看器\",\"opensource\":{\"是否开源\":false,\"GitHub\":\"https://github.com2/bimohxh/jsonon\"}}";

        oldJson = "{\"p1\":{\"name\":\"zhangsan\",\"age\":18,\"boolean\":true}}";
        newJson = "{\"p2\":{\"name\":\"zhangsan1\",\"age\":188,\"boolean\":true}}";


        JsonDiffOption option = new JsonDiffOption();
        option.setJsonDiffAnalyzeType(JsonDiffAnalyzeType.FastJson2);

        Set<String> ignorePath = new HashSet<>();
        option.setIgnorePath(ignorePath);
        ignorePath.add("opensource");

        Map<String, String> mappingMap = new HashMap<>();
        option.setFieldMapping(mappingMap);
        mappingMap.put("p1.name", "p2.name");
        mappingMap.put("p1.age", "p2.age");
        mappingMap.put("p1.boolean", "p2.boolean");


        JsonDiffResult jsonDiffResult = JsonDiff.diff(oldJson, newJson, option);

        System.err.println(JSONObject.toJSONString(jsonDiffResult));

    }

}
