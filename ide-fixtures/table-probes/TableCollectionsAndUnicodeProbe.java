import org.tabletest.junit.TableTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class TableCollectionsAndUnicodeProbe {

    @TableTest("""
            Offset | Numbers      | Shifted values?
            123    | [10, 20, 30] | [10: 133, 20: 143, 30: 153]
            """)
    void shouldComputeMappedValuesForIntegerList(int offset, List<Integer> numbers, Map<String, Object> expected) {
        Map<String, Integer> actual = numbers.stream()
                .map(value -> Map.entry(value.toString(), value + offset))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        assertIterableEquals(expected.keySet(), actual.keySet());
    }

    @TableTest("""
            Collection literal | Map literal                    | Set literal?
                []                 | [:]                            | {}
                [[]]               | [a: []]                        | {[]}
                [[:]]              | [a: [:]]                       | {[:]}
                [{}]               | [a: {}]                        | {{}}
                [1, 2, 3]          | [one: 1, two: 2, three: 3]     | {1, 2, 3}
                ['1', "2", 3]      | [one: '1', two: "2", three: 3] | {'1', "2", 3}
                [[], [], []]       | [a: [], b: [], c: []]          | {[], [], []}
                abc                | a,b,c                          | > [ a , b , c ] <
                                   |                                |
                1                  | 2                              | 3
                '1'                | "2"                            | 3
                ''                 | ""                             |
            
                //
                // comment
            
                xx                 | yy                             | zz
                æææ                | øøø                            | ååå
                [1, 2, 3]          | [one: 1, two: 2, three: 3]     | {1, 2, 3}
                !@#$%^&*()_+-=     | "|,[]{}:"                      | 12:34:56.789
                你好世界           | こんにちは世界                 | 안녕하세요
                مرحبا بالعالم      | Привет мир                     | Γεια σου κόσμε
                שלום עולם          | สวัสดีโลก                      | नमस्ते संसार
                😀                  | Hello 👋 World                  | Café ☕ tastes good 😋
                🌍🌎🌏                | 👨‍💻                            | 🇺🇸
                naïve résumé       | α β γ δ ε                      | ∑ ∏ ∫ √
                ┌─┐│ │└─┘          | $€£¥₹                          | «»""''—–
                //   the end
            """)
    void shouldExerciseCollectionLiteralsAndUnicodeTokens(String collectionLiteral, String mapLiteral) {
    }
}
