package com.github.Is0x4096.jsondiff.test;

import com.alibaba.fastjson2.JSONObject;
import com.github.Is0x4096.jsondiff.commom.JsonDiffAnalyzeType;
import com.github.Is0x4096.jsondiff.commom.JsonDiffOption;
import com.github.Is0x4096.jsondiff.commom.JsonDiffResult;
import com.github.Is0x4096.jsondiff.core.JsonDiff;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author 0x4096.peng@gmail.com
 * @date 2025/1/11
 */
public class JsonDiffObjectTest {

    @lombok.SneakyThrows
    @Test
    public void test() {
        String resource = "object/old.json";
        File oldJsonFile = new File(ClassLoader.getSystemResource(resource).getFile());
        String oldJson = Files.readString(oldJsonFile.toPath());

        resource = "object/new.json";
        File newJsonFile = new File(ClassLoader.getSystemResource(resource).getFile());
        String newJson = Files.readString(newJsonFile.toPath());

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
