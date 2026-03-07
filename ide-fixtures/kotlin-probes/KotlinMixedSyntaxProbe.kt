import io.github.nchaugen.tabletest.junit.TableTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.tabletest.junit.TypeConverter

class KotlinMixedSyntaxProbe {

    @TableTest(
        """
        Celsius | Fahrenheit | Equal?
        0       | 32         | true
        10      | 50         | true
        -40     | -40        | true
        """
    )
    fun shouldConvertTemperatureRows(celsius: Int, fahrenheit: Int, expected: Boolean) {
    }

    @TableTest(
        """
        Left operand | Right operand | Sum?
        0            | 0             | 0
        2            | 2             | 4
        """
    )
    fun shouldAddTwoNumbers(leftOperand: Int, rightOperand: Int, expectedSum: Int) {
        assertEquals(expectedSum, leftOperand + rightOperand)
    }

    @TableTest(
        """
        Column A | Column B | Column C
        a        | a        | a
        asfas    | asf      | asf
        1        | a        | as
                 | ''       | ""
        """
    )
    fun shouldExerciseStringAndNullabilityRows(columnA: String?, columnB: String?) {
    }

    @TableTest(
        """
        Column A     | Column B | Column C
        asdf         | asdf     | asdfasfasf
        asdf         | adf      | as
        aa;lsdkldsjf | asdfa    | asdf
        // asdfasfd
        af           | asf      | asdf
        a            | b        | c

        aasdfdsf     | af       | sdaf
        asd          | asf      | as
        a            | a        | a
        """
    )
    fun shouldExerciseNumericCoercionAndComments(columnA: Int, columnB: Int, columnC: Int) {
    }

    @TableTest(
        """
        Collection literal | Map literal                       | Set literal?
        []                 | [:]                               | {}
        [ [] ]             | [a:[]]                            | { [ ] }
        [ [:] ]            | [a:[:]]                           | { [:] }
        [ {} ]             | [a:{}]                            | { { } }
        [1,2,3]            | [one:1,two:2,three:3]             | {1, 2, 3}
        ['1',"2",3]       | [one:'1',two:"2",three:3]       | {'1', "2", 3}
        [[], [], []]       | [a: [], b: [], c: []]             | {[], [], []}
        abc                | a,b,c                             | > [ a , b , c ] <
        1                  | 2                                 | 3
        '1'                | "2"                               | 3
        ''                 | ""                                |

        //
        // comment

        xx                 | yy                                | zz<caret>
        æææ                | øøø                               | ååå
        [ 1,2,3 ]          | [ one:1,two:2,three:3]            | {1, 2, 3 }
        !@#$%^&*()_+-=     | "|,[]{}:"                         | 12:34:56.789
        naïve résumé       | α β γ δ ε                         | ∑ ∏ ∫ √
        ┌─┐│ │└─┘          | $€£¥₹                             | «»""''—–
        //   the end
        """
    )
    fun shouldExerciseCollectionAndUnicodeSyntax(columnA: Int, columnB: Int, columnC: Int) {
    }

    companion object {
        @JvmStatic
        @TypeConverter
        fun parseIntegerFromString(input: String): Int = Integer.parseInt(input)
    }
}
