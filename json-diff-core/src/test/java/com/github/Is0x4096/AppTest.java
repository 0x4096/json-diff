package com.github.Is0x4096;

import com.github.Is0x4096.jsondiff.commom.JsonDiffAnalyzeType;
import com.github.Is0x4096.jsondiff.commom.JsonDiffOption;
import com.github.Is0x4096.jsondiff.commom.JsonDiffResult;
import com.github.Is0x4096.jsondiff.core.JsonDiff;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Unit test for simple App.
 */
public class AppTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        assertTrue(true);
    }


    public void testApp2() {
        String oldJson = "{\"p1\":{\"name\":\"zhangsan\",\"age\":18,\"boolean\":true}}";
        String newJson = "{\"p2\":{\"name\":\"zhangsan\",\"age\":19,\"boolean\":true}}";


        JsonDiffOption option = new JsonDiffOption();
        option.setJsonDiffAnalyzeType(JsonDiffAnalyzeType.FastJson2);

        Set<String> ignorePath = new HashSet<>();
        ignorePath.add("opensource");
        option.setIgnorePath(ignorePath);

        Map<String, String> mapping = new HashMap<>();
        mapping.put("p1.name", "p2.name");
        option.setFieldMapping(mapping);

        JsonDiffResult jsonDiffResult = JsonDiff.diff(oldJson, newJson, option);

        System.err.println(jsonDiffResult);
    }

}
