package com.github.Is0x4096.jsondiff.core;

import java.util.Collection;
import java.util.Map;

/**
 * 根据不同的 json 库解析器可自行实现
 *
 * @author 0x4096.peng@gmail.com
 * @date 2025/1/5
 */
public interface IJsonDiffValidation {

    /**
     * 将 JSON 字符串转为 jsonpath 样式
     *
     * @return jsonpath map
     */
    Map<String, Object> jsonStr2Path();

    /**
     * 过滤 jsonpathMap 中的数据
     *
     * @param jsonpathMap jsonStr2Path() 处理后数据
     * @see com.github.Is0x4096.jsondiff.core.IJsonDiffValidation#jsonStr2Path()
     */
    void filter(Map<String, Object> jsonpathMap);

    /**
     * @param jsonpathMap jsonStr2Path() -> filter(jsonpathMap)  处理后数据
     * @param removePath  需要过滤的 key, 不支持正则, 需要全匹配
     * @return jsonpathMap
     */
    Map<String, Object> removeAndGet(Map<String, Object> jsonpathMap, Collection<String> removePath);

}
