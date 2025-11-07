package io.github.nchaugen.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class SmartStringSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (isBoolean(value)) gen.writeBoolean(Boolean.parseBoolean(value));
        else if (isDecimal(value)) gen.writeNumber(Double.parseDouble(value));
        else if (isInteger(value)) gen.writeNumber(Long.parseLong(value));
        else if (isForcedString(value)) gen.writeString(value.substring(1, value.length() - 1));
        else gen.writeString(value);
    }

    private boolean isForcedString(String value) {
        return value != null && value.length() > 1 && value.startsWith("\"") && value.endsWith("\"");
    }

    private boolean isBoolean(String value) {
        return "true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value);
    }

    private static final String DECIMAL_PATTERN = "-?(0|[1-9]\\d*)(\\.\\d+)([eE][+-]?\\d+)?";
    private static final String INTEGER_PATTERN = "-?(0|[1-9]\\d*)([eE][+-]?\\d+)?";

    private boolean isDecimal(String value) {
        return value != null && value.matches(DECIMAL_PATTERN);
    }

    private boolean isInteger(String value) {
        return value != null && value.matches(INTEGER_PATTERN);
    }
}

