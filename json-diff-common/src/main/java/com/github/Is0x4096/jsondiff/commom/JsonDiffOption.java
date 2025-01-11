package com.github.Is0x4096.jsondiff.commom;

import lombok.Data;

import java.util.Map;
import java.util.Set;

/**
 * @author 0x4096.peng@gmail.com
 * @date 2025/1/2
 */
@Data
public class JsonDiffOption {

    private JsonDiffAnalyzeType jsonDiffAnalyzeType;

    private boolean isArraySort;

    /**
     * jsonpath 过滤，正则表达式
     */
    private Set<String> ignorePath;

    /**
     * 字段映射,具体路径,完全匹配不支持正则表达式,exp: person.name, json={"person": {"name": "zhangsan"}}
     */
    private Map<String, String> fieldMapping;

}
