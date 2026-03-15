package io.github.nchaugen.examples.probe.manualsuite

import org.junit.jupiter.api.Tag
import org.tabletest.junit.TableTest

@Tag("probe")
class KotlinRawStringManualSuiteProbe {

    @TableTest(
        """
        // top comment before header

        Scenario|Scalar value|Collection literal|Map literal
        bare scalar|hello|[]|[:]
        bare comma scalar|World, hello|[a, b]|[plain: other]
        bare colon scalar|key: value|[]|[plain: other]
        double quoted comma|"Hello, world"|[1,2,3]|[plain: other]
        single quoted pipe|'|'|{'|', ",", abc}|['key with spaces': value]
          // middle comment with extra indent
        quoted whitespace|"  padded  "|[[1,2],[3,4]]|["key:colon": [left: [1,2], right: {3,4}]]
        quoted brackets|"[(x)]"|[{[a: 1]}, [[b]], {c}]|["[a]{b}(c),|": "(works)"]
        reserved characters|"|,:[]{}()"|[[:], {}, []]|["with,comma": ['x', "y"]]
        unicode mix|naïve résumé|[α, "beta gamma", 'δ']|[こんにちは: '😀', plain: other]

        empty quoted|""|{}|[plain: ""]
        trailing empty|''|[:]|[unquoted: value]
        // bottom comment after data
        """
    )
    fun modernRawStringValidCorpus() {
    }

    @TableTest(
        // comment before implicit value
        """
        Scenario|Result?
        comment before implicit value|active
        """
    )
    fun modernRawStringCommentBeforeValue() {
    }

    @TableTest(
        """
        Scenario|Map literal|Result?
        second kotlin raw string|[plain: other]|active
        quoted map key|["(s)u[c]c{e}ss": false]|shown
        """
    )
    fun modernSecondRawStringCorpus() {
    }

    @OtherRaw(
        """
        Scenario|Result?
        other annotation should stay inactive|ok
        """
    )
    fun otherAnnotationShouldStayInactive() {
    }

    @TableTestXRaw(
        """
        Scenario|Result?
        similar annotation name should stay inactive|ok
        """
    )
    fun similarAnnotationNameShouldStayInactive() {
    }

    fun annotationLikeTextInCommentsAndStringsShouldStayInactive() {
        // @TableTest("""
        // a|b
        // 1|2
        // """)
        val annotationLikeText = "@TableTest(\"\"\"\\na|b\\n1|2\\n\"\"\")"
        val similarAnnotationText = "@TableTestX(\"\"\"\\na|b\\n1|2\\n\"\"\")"
        if (annotationLikeText == similarAnnotationText) {
            error("This manual probe is not expected to execute.")
        }
    }

    // expect diagnostic on next table: malformed list
    @TableTest(
        """
        Value|Expected?
        [a,, b]|x
        """
    )
    fun invalidMalformedListShouldHighlight() {
    }

    // expect diagnostic on next table: malformed set
    @TableTest(
        """
        Value|Expected?
        {a,, b}|x
        """
    )
    fun invalidMalformedSetShouldHighlight() {
    }

    // expect diagnostic on next table: malformed map
    @TableTest(
        """
        Value|Expected?
        [a b, c: d]|x
        """
    )
    fun invalidMalformedMapShouldHighlight() {
    }

    // expect diagnostic on next table: missing closing bracket
    @TableTest(
        """
        Value|Expected?
        [1, 2|x
        """
    )
    fun invalidMissingClosingBracketShouldHighlight() {
    }

    // expect diagnostic on next table: missing closing brace
    @TableTest(
        """
        Value|Expected?
        {1, 2|x
        """
    )
    fun invalidMissingClosingBraceShouldHighlight() {
    }

    // expect diagnostic on next table: trailing comma
    @TableTest(
        """
        Value|Expected?
        [a, b,]|x
        """
    )
    fun invalidTrailingCommaShouldHighlight() {
    }

    // expect diagnostic on next table: map key without value
    @TableTest(
        """
        Value|Expected?
        [key:]|x
        """
    )
    fun invalidMapKeyWithoutValueShouldHighlight() {
    }

    // expect diagnostic on next table: extra top-level colon in map value
    @TableTest(
        """
        Value|Expected?
        [a: b:c:d]|x
        """
    )
    fun invalidExtraTopLevelColonShouldHighlight() {
    }

    // expect diagnostic on next table: empty map whitespace
    @TableTest(
        """
        Value|Expected?
        [: ]|x
        """
    )
    fun invalidEmptyMapWhitespaceShouldHighlight() {
    }

    // expect diagnostic on next table: unquoted map key containing whitespace
    @TableTest(
        """
        Value|Expected?
        [key with spaces: value]|x
        """
    )
    fun invalidUnquotedMapKeyContainingWhitespaceShouldHighlight() {
    }

    // expect diagnostic on next table: unquoted map key containing reserved characters
    @TableTest(
        """
        Value|Expected?
        [key[bracket]: value]|x
        """
    )
    fun invalidUnquotedMapKeyContainingReservedCharactersShouldHighlight() {
    }
}

private annotation class OtherRaw(val value: String)

private annotation class TableTestXRaw(val value: String)
