package io.github.nchaugen.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.nchaugen.tabletest.junit.TableTest;
import org.junit.jupiter.api.BeforeAll;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapToJsonTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    static void init() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(String.class, new SmartStringSerializer());
        mapper.registerModule(module);
    }

    @TableTest("""
        Map definition          | Expected JSON
        [string: abc]           | '{"string":"abc"}'
        [integer: 123]          | '{"integer":123}'
        [decimal: 1.23]         | '{"decimal":1.23}'
        [boolean: true]         | '{"boolean":true}'
        [forcedString: '"123"'] | '{"forcedString":"123"}'
        [list: [1, 2, 3]]       | '{"list":[1,2,3]}'
        [object: [num: 1]]      | '{"object":{"num":1}}'
        """)
    void shouldConvertMapToJson(String convertedJson, String expectedJson) {
        assertEquals(expectedJson, convertedJson);
    }

    @SuppressWarnings("unused")
    public static String toJson(Map<String, ?> map) {
        try {
            return mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @TableTest("""
        Domain object           | Field        | Expected value
        [string: abc]           | string       | abc
        """)
    void shouldConvertMapToDomainObjectViaJson(DomainObject object, String field, String expectedValue) {
        try {
            assertEquals(expectedValue, DomainObject.class.getMethod(field).invoke(object));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({"unused", "ClassEscapesDefinedScope"})
    public static DomainObject toDomainObject(String json) {
        try {
            return mapper.readValue(json, DomainObject.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

record DomainObject(
    String string,
    int integer,
    double decimal,
    boolean booleanValue,
    String forcedString,
    int[] list,
    Map<String, Integer> object
) {}

