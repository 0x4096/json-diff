package com.github.Is0x4096.jsondiff.core;

import com.github.Is0x4096.jsondiff.commom.JsonDiffAnalyzeType;
import com.github.Is0x4096.jsondiff.commom.JsonDiffOption;
import com.github.Is0x4096.jsondiff.core.impl.Fastjson2JsonDiffValidation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 0x4096.peng@gmail.com
 * @date 2025/1/5
 */
public class JsonDiffValidation {

    public static IJsonDiffValidation validation(@NotBlank String jsonStr, @NotNull JsonDiffOption option) {
        IJsonDiffValidation validation;
        if (option.getJsonDiffAnalyzeType() == JsonDiffAnalyzeType.FastJson2) {
            validation = new Fastjson2JsonDiffValidation(jsonStr, option);
        } else {
            throw new UnsupportedOperationException("没有支持的 json 解析器");
        }
        return validation;
    }

}
