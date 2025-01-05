package com.github.Is0x4096.jsondiff.core;

import com.github.Is0x4096.jsondiff.commom.JsonDiffOption;

import java.util.Map;

/**
 * 根据不同的 json 库解析器可自行实现
 *
 * @author 0x4096.peng@gmail.com
 * @date 2025/1/5
 */
public interface IJsonPath {

    Map<String, Object> jsonStr2Path(String json, JsonDiffOption option);

}
