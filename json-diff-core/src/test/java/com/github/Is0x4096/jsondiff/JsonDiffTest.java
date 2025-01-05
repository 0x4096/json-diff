package com.github.Is0x4096.jsondiff;

import com.github.Is0x4096.jsondiff.commom.JsonDiffAnalyzeType;
import com.github.Is0x4096.jsondiff.commom.JsonDiffOption;
import com.github.Is0x4096.jsondiff.commom.JsonDiffResult;
import com.github.Is0x4096.jsondiff.core.JsonDiff;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 0x4096.peng@gmail.com
 * @date 2025/1/5
 */
public class JsonDiffTest {

    public static void main(String[] args) {

        String oldJson = "{\"name\":\"Json on\",\"description\":\"一个简洁的在线 JSON 查看器\",\"opensource\":{\"是否开源\":true,\"GitHub\":\"https://github.com/bimohxh/jsonon\"},\"哈哈\":11}";
        String newJson = "{\"name\":\"Json on\",\"description\":\"一个简洁的在线 JSON 查看器\",\"opensource\":{\"是否开源\":false,\"GitHub\":\"https://github.com2/bimohxh/jsonon\"}}";

        JsonDiffOption option = new JsonDiffOption();
        option.setJsonDiffAnalyzeType(JsonDiffAnalyzeType.FastJson2);

        Set<String> ignorePath = new HashSet<>();
        ignorePath.add("opensource");

        option.setIgnorePath(ignorePath);

        JsonDiffResult jsonDiffResult = JsonDiff.diff(oldJson, newJson, option);

        System.err.println(jsonDiffResult);
    }

}
