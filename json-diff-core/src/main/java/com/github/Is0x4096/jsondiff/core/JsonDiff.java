package com.github.Is0x4096.jsondiff.core;

import com.github.Is0x4096.jsondiff.commom.JsonDiffOption;
import com.github.Is0x4096.jsondiff.commom.JsonDiffResult;
import com.github.Is0x4096.jsondiff.commom.JsonDiffType;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 0x4096.peng@gmail.com
 * @date 2025/1/2
 */
public class JsonDiff {

    public static JsonDiffResult diff(String oldJson, String newJson, JsonDiffOption option) {
        JsonDiffResult result = new JsonDiffResult();

        Map<String, Object> oldJsonMap = JsonPath.jsonpath(oldJson, option);
        Map<String, Object> newJsonMap = JsonPath.jsonpath(newJson, option);


        MapDifference<String, Object> difference = Maps.difference(oldJsonMap, newJsonMap);
        if (!difference.areEqual()) {
            Map<String, MapDifference.ValueDifference<Object>> update = difference.entriesDiffering();
            handleUpdate(result, JsonDiffType.UPDATE, update);

            Map<String, Object> add = difference.entriesOnlyOnLeft();
            Map<String, Object> delete = difference.entriesOnlyOnRight();

            handleAddOrDelete(result, JsonDiffType.ADD, add);
            handleAddOrDelete(result, JsonDiffType.DELETE, delete);
        }

        result.setMatch(difference.areEqual());
        result.setOldJsonPath(oldJsonMap);
        result.setNewJsonPath(newJsonMap);
        return result;
    }

    private static void handleAddOrDelete(JsonDiffResult result, JsonDiffType jsonDiffType, Map<String, Object> content) {
        if (content == null || content.isEmpty()) {
            return;
        }

        Map<JsonDiffType, JsonDiffResult.DiffContent> diffContentMap = new HashMap<>();
        result.setDiffContent(diffContentMap);
        content.forEach((k, v) -> {
            JsonDiffResult.DiffContent diffContent = new JsonDiffResult.DiffContent();
            diffContent.setJsonPath(k);
            if (jsonDiffType == JsonDiffType.ADD) {
                diffContent.setOldValue(v);
            } else if (jsonDiffType == JsonDiffType.DELETE) {
                diffContent.setNewValue(v);
            }
            diffContentMap.put(jsonDiffType, diffContent);
        });
    }

    private static void handleUpdate(JsonDiffResult result, JsonDiffType jsonDiffType, Map<String, MapDifference.ValueDifference<Object>> content) {
        if (content == null || content.isEmpty()) {
            return;
        }

        Map<JsonDiffType, JsonDiffResult.DiffContent> diffContentMap = new HashMap<>();
        result.setDiffContent(diffContentMap);
        content.forEach((k, v) -> {
            JsonDiffResult.DiffContent diffContent = new JsonDiffResult.DiffContent();
            diffContent.setJsonPath(k);
            diffContent.setOldValue(v.leftValue());
            diffContent.setNewValue(v.leftValue());
            diffContentMap.put(jsonDiffType, diffContent);
        });
    }
}
