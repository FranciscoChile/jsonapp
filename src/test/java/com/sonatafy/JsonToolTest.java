package com.sonatafy;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.sonatafy.json.JsonTool;
import com.sonatafy.model.ChangeType;


public class JsonToolTest {
    
    public static List<String> dataSetJson() {
        List<String> list = new ArrayList<>();

        list.add("{\"property\": \"firstName\", \"previous\": \"James\", \"current\": \"James\"}");
        list.add("{\"property\": \"firstName\", \"previous\": \"James\", \"current\": \"Jim\"}");
        list.add("{\"property\": \"subscription.status\", \"previous\": \"ACTIVE\", \"current\": \"EXPIRED\"}");
        list.add("{\"property\": \"vehicles[id].displayName\", \"previous\": \"My Car\", \"current\": \"23 Ferrari 296 GTS\"}");
        list.add("{\"property\": \"services\", \"added\": [\"Oil Change\", \"Fuel\"], \"removed\": [\"Interior/Exterior Wash\"]}");
        list.add("{\"property\": \"subscription.status\", \"previous\": \"ACTIVE\", \"current\": \"ACTIVE\"}");
        list.add("{\"property\": \"vehicles[id].displayName\", \"previous\": \"My Car\", \"current\": \"My Car\"}");
        list.add("{\"property\": \"firstName\", \"previous\": \"James\", \"current\": null}");
        list.add("{\"property\": \"firstName\", \"previous\": null, \"current\": \"Jim\"}");
        list.add("{\"property\": \"firstName\", \"previous\": null, \"current\": null}");

        return list;
    }

    private static Stream<Arguments> provideStringsJson() {
        return Stream.of(
            Arguments.of(
                (List<String>)dataSetJson()
            )        
        );
    }

    

    @ParameterizedTest
    @MethodSource("provideStringsJson")
    void givenNotChangedJSON(List<String> param) throws Exception {
        assertTrue(testUtil(param.get(0).toString()).isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideStringsJson")
    void givenChangedJSON(List<String> param) throws Exception {
        assertFalse(testUtil(param.get(1)).isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideStringsJson")
    void givenChangedNested(List<String> param) throws Exception {
        assertFalse(testUtil(param.get(2)).isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideStringsJson")
    void givenChangedIdBracket(List<String> param) throws Exception {
        assertFalse(testUtil(param.get(3)).isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideStringsJson")
    void givenTrackedListJSON(List<String> param) throws Exception {
        assertFalse(testUtil(param.get(4)).isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideStringsJson")
    void givenNotChangedNested(List<String> param) throws Exception {
        assertTrue(testUtil(param.get(5)).isEmpty());
    }

    @Test
    void givenChangedBracketsWrongId() throws Exception {
        JsonTool parserJsonTool = new JsonTool();
        Exception exception = assertThrows(
            Exception.class, 
            () -> parserJsonTool.parserJson("{\"property\": \"vehicles[v_1].displayName\", \"previous\": \"My Car\", \"current\": \"23 Ferrari 296 GTS\"}"));
        assertEquals(
            "Audit system lacks the information it needs to determine what has changed.", 
            exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideStringsJson")
    void givenNotChangedIdBracket(List<String> param) throws Exception {
        assertTrue(testUtil(param.get(6)).isEmpty());
    }


    @ParameterizedTest
    @MethodSource("provideStringsJson")
    void givenNullCurrent(List<String> param) throws Exception {
        assertFalse(testUtil(param.get(7)).isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideStringsJson")
    void givenNullPrevious(List<String> param) throws Exception {
        assertFalse(testUtil(param.get(8)).isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideStringsJson")
    void givenNullBoth(List<String> param) throws Exception {
        assertTrue(testUtil(param.get(9)).isEmpty());
    }

    private List<ChangeType> testUtil(String json) throws Exception {
        JsonTool parserJsonTool = new JsonTool();
        List<ChangeType> list = parserJsonTool.parserJson(json);
        return list;
    }


}
