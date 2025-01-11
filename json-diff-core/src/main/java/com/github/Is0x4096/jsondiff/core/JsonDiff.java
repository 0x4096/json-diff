package com.github.Is0x4096.jsondiff.core;

import com.github.Is0x4096.jsondiff.commom.JsonDiffOption;
import com.github.Is0x4096.jsondiff.commom.JsonDiffResult;
import com.github.Is0x4096.jsondiff.commom.JsonDiffType;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author 0x4096.peng@gmail.com
 * @date 2025/1/2
 */
public class JsonDiff {

    public static JsonDiffResult diff(String oldJson, String newJson, JsonDiffOption option) {
        JsonDiffResult result = new JsonDiffResult();

        IJsonDiffValidation oldJsonValidation = JsonDiffValidation.validation(oldJson, option);
        IJsonDiffValidation newJsonValidation = JsonDiffValidation.validation(newJson, option);

        Map<String, Object> oldJsonMap = oldJsonValidation.jsonStr2Path();
        Map<String, Object> newJsonMap = newJsonValidation.jsonStr2Path();

        Map<String, Object> oldJsonOriginMap = new HashMap<>(oldJsonMap);
        Map<String, Object> newJsonOriginMap = new HashMap<>(newJsonMap);

        result.setOldJsonPath(oldJsonOriginMap);
        result.setNewJsonPath(newJsonOriginMap);
        result.setIgnorePath(option.getIgnorePath());
        result.setMappingPath(option.getFieldMapping());

        // filter
        oldJsonValidation.filter(oldJsonMap);
        newJsonValidation.filter(newJsonMap);

        // mapping diff
        boolean mappingResult = true;
        if (Objects.nonNull(option.getFieldMapping())) {
            Map<String, Object> oldJsonMappingMap = oldJsonValidation.removeAndGet(oldJsonMap, option.getFieldMapping().keySet());
            Map<String, Object> newJsonMappingMap = newJsonValidation.removeAndGet(newJsonMap, option.getFieldMapping().values());
            mappingResult = handleMapping(result, oldJsonMappingMap, newJsonMappingMap, option.getFieldMapping());
        }

        // diff
        boolean diffResult = handleDiff(result, oldJsonMap, newJsonMap);

        result.setIsMatch(mappingResult && diffResult);
        return result;
    }

    private static boolean handleDiff(JsonDiffResult result, Map<String, Object> oldJsonMap, Map<String, Object> newJsonMap) {
        MapDifference<String, Object> difference = Maps.difference(oldJsonMap, newJsonMap);
        if (!difference.areEqual()) {
            Map<String, MapDifference.ValueDifference<Object>> update = difference.entriesDiffering();
            handleUpdate(result, update);

            Map<String, Object> add = difference.entriesOnlyOnLeft();
            Map<String, Object> delete = difference.entriesOnlyOnRight();

            handleAddOrDelete(result, JsonDiffType.ADD, add);
            handleAddOrDelete(result, JsonDiffType.DELETE, delete);
        }
        return difference.areEqual();
    }

    private static void handleAddOrDelete(JsonDiffResult result, JsonDiffType jsonDiffType, Map<String, Object> content) {
        if (content == null || content.isEmpty()) {
            return;
        }

        content.forEach((k, v) -> {
            JsonDiffResult.DiffContent diffContent = new JsonDiffResult.DiffContent();
            diffContent.setJsonPath(k);
            if (jsonDiffType == JsonDiffType.ADD) {
                diffContent.setOldValue(v);
            } else if (jsonDiffType == JsonDiffType.DELETE) {
                diffContent.setNewValue(v);
            }
            result.addDiffContent(jsonDiffType, diffContent);
        });
    }

    private static void handleUpdate(JsonDiffResult result, Map<String, MapDifference.ValueDifference<Object>> content) {
        if (content == null || content.isEmpty()) {
            return;
        }

        content.forEach((k, v) -> {
            JsonDiffResult.DiffContent diffContent = new JsonDiffResult.DiffContent();
            diffContent.setJsonPath(k);
            diffContent.setOldValue(v.leftValue());
            diffContent.setNewValue(v.leftValue());
            result.addDiffContent(JsonDiffType.UPDATE, diffContent);
        });
    }

    private static boolean handleMapping(JsonDiffResult result, Map<String, Object> oldJsonMappingMap, Map<String, Object> newJsonMappingMap, Map<String, String> fieldMapping) {
        if (Objects.isNull(oldJsonMappingMap) || oldJsonMappingMap.isEmpty()) {
            return true;
        }
        if (Objects.isNull(newJsonMappingMap) || newJsonMappingMap.isEmpty()) {
            return true;
        }

        AtomicBoolean isMatch = new AtomicBoolean(true);
        fieldMapping.forEach((k, v) -> {
            Object left = oldJsonMappingMap.get(k);
            Object right = newJsonMappingMap.get(v);
            if (!Objects.equals(left, right)) {
                isMatch.set(false);
                JsonDiffResult.DiffContent diffContent = new JsonDiffResult.DiffContent();
                diffContent.setJsonPath(k);
                diffContent.setMappingPath(v);
                diffContent.setOldValue(left);
                diffContent.setNewValue(right);
                result.addDiffContent(JsonDiffType.MAPPING, diffContent);
            }
        });
        return isMatch.get();
    }
}
