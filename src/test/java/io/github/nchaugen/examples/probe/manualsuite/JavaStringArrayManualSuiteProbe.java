package io.github.nchaugen.examples.probe.manualsuite;

import org.junit.jupiter.api.Tag;
import org.tabletest.junit.TableTest;

@Tag("probe")
public class JavaStringArrayManualSuiteProbe {

    @TableTest({
            "// top comment before header",
            "",
            "Scenario|Scalar value|Collection literal|Map literal",
            "bare scalar|hello|[]|[:]",
            "bare comma scalar|World, hello|[a, b]|[plain: other]",
            "bare colon scalar|key: value|[]|[plain: other]",
            "double quoted comma|\"Hello, world\"|[1,2,3]|[plain: other]",
            "single quoted pipe|'|'|{'|', \",\", abc}|['key with spaces': value]",
            "  // middle comment with extra indent",
            "quoted whitespace|\"  padded  \"|[[1,2],[3,4]]|[\"key:colon\": [left: [1,2], right: {3,4}]]",
            "quoted brackets|\"[(x)]\"|[{[a: 1]}, [[b]], {c}]|[\"[a]{b}(c),|\": \"(works)\"]",
            "escaped quotes and slash|\"Say \\\"hi\\\"\"|[path\\\\file, \"A,B\"]|[\"(s)u[c]c{e}ss\": false]",
            "",
            "empty quoted|\"\"|{}|[plain: \"\"]",
            "trailing empty|''|[:]|[unquoted: value]",
            "// bottom comment after data"
    })
    void modernImplicitArrayValidCorpus() {
    }

    @TableTest({
            "Scenario|Map literal|Result?",
            "second array corpus|[plain: other]|active",
            "quoted key with punctuation|['[s]u(c)c{e}s[s': 3]|shown"
    })
    void secondModernArrayCorpusShouldActivate() {
    }

    @org.tabletest.junit.TableTest({
            "Scenario|Map literal|Result?",
            "fully qualified array|[plain: other]|active",
            "quoted key|[\"[a]{b}(c),|\": \"(works)\"]|shown"
    })
    void fullyQualifiedModernArrayShouldActivate() {
    }

    @Other({
            "Scenario|Result?",
            "other annotation should stay inactive|ok"
    })
    void otherAnnotationShouldStayInactive() {
    }

    @TableTestX({
            "Scenario|Result?",
            "similar annotation name should stay inactive|ok"
    })
    void similarAnnotationNameShouldStayInactive() {
    }

    void annotationLikeTextInCommentsAndStringsShouldStayInactive() {
        String annotationLikeText = "@TableTest({\"a|b\",\"1|2\"})";
        String similarAnnotationText = "@TableTestX({\"a|b\",\"1|2\"})";
        if (annotationLikeText.equals(similarAnnotationText)) {
            throw new IllegalStateException("This manual probe is not expected to execute.");
        }
    }

    // expect diagnostic on next table: malformed list
    @TableTest({
            "Value|Expected?",
            "[a,, b]|x"
    })
    void invalidMalformedListShouldHighlight() {
    }

    // expect diagnostic on next table: malformed set
    @TableTest({
            "Value|Expected?",
            "{a,, b}|x"
    })
    void invalidMalformedSetShouldHighlight() {
    }

    // expect diagnostic on next table: malformed map
    @TableTest({
            "Value|Expected?",
            "[a b, c: d]|x"
    })
    void invalidMalformedMapShouldHighlight() {
    }

    // expect diagnostic on next table: missing closing bracket
    @TableTest({
            "Value|Expected?",
            "[1, 2|x"
    })
    void invalidMissingClosingBracketShouldHighlight() {
    }

    // expect diagnostic on next table: missing closing brace
    @TableTest({
            "Value|Expected?",
            "{1, 2|x"
    })
    void invalidMissingClosingBraceShouldHighlight() {
    }

    // expect diagnostic on next table: trailing comma
    @TableTest({
            "Value|Expected?",
            "[a, b,]|x"
    })
    void invalidTrailingCommaShouldHighlight() {
    }

    // expect diagnostic on next table: map key without value
    @TableTest({
            "Value|Expected?",
            "[key:]|x"
    })
    void invalidMapKeyWithoutValueShouldHighlight() {
    }

    // expect diagnostic on next table: extra top-level colon in map value
    @TableTest({
            "Value|Expected?",
            "[a: b:c:d]|x"
    })
    void invalidExtraTopLevelColonShouldHighlight() {
    }

    // expect diagnostic on next table: empty map whitespace
    @TableTest({
            "Value|Expected?",
            "[: ]|x"
    })
    void invalidEmptyMapWhitespaceShouldHighlight() {
    }

    // expect diagnostic on next table: unquoted map key containing whitespace
    @TableTest({
            "Value|Expected?",
            "[key with spaces: value]|x"
    })
    void invalidUnquotedMapKeyContainingWhitespaceShouldHighlight() {
    }

    // expect diagnostic on next table: unquoted map key containing reserved characters
    @TableTest({
            "Value|Expected?",
            "[key[bracket]: value]|x"
    })
    void invalidUnquotedMapKeyContainingReservedCharactersShouldHighlight() {
    }

    private @interface Other {
        String[] value();
    }

    private @interface TableTestX {
        String[] value();
    }
}
