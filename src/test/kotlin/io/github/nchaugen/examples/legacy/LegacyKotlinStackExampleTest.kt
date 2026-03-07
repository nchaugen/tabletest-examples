package io.github.nchaugen.examples.legacy

import io.github.nchaugen.tabletest.junit.TableTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import java.util.Stack
import java.util.function.Supplier

class LegacyKotlinStackExampleTest {

    @TableTest(
        """
        Scenario                              | Initial stack | Operation | Operation result?            | Resulting stack?
        is empty                              | []            | [empty]   | [success: true]              | []
        has no size                           | []            | [size]    | [success: 0]                 | []
        throws when popped                    | []            | [pop]     | [error: EmptyStackException] | []
        throws when peeked                    | []            | [peek]    | [error: EmptyStackException] | []
        push acquires elements                | []            | [push, a] | [success: a]                 | [a]

        is not empty                          | [a, b, c]     | [empty]   | [success: false]             | [a, b, c]
        has size                              | [a, b, c]     | [size]    | [success: 3]                 | [a, b, c]
        peek reveals top element              | [a, b, c]     | [peek]    | [success: c]                 | [a, b, c]
        pop reveals elements in reverse order | [a, b, c]     | [pop]     | [success: c]                 | [a, b]
        push acquires elements                | [a, b, c]     | [push, d] | [success: d]                 | [a, b, c, d]
        """
    )
    fun shouldProcessStackOperations(
        initialState: List<String>,
        operation: List<String>,
        expectedResult: Map<String, *>,
        expectedState: List<String>
    ) {
        val stack = Stack<String>()
        stack.addAll(initialState)

        when (operation.first()) {
            "empty" -> assertOperation(expectedResult) { stack.isEmpty() }
            "size" -> assertOperation(expectedResult) { stack.size }
            "peek" -> assertOperation(expectedResult) { stack.peek() }
            "pop" -> assertOperation(expectedResult) { stack.pop() }
            "push" -> assertOperation(expectedResult) { stack.push(operation.last()) }
            else -> fail("Unknown operation: ${operation.first()}")
        }

        assertStackState(expectedState, stack)
    }

    companion object {
        private fun assertOperation(expectedResult: Map<String, *>, operation: Supplier<*>) {
            if (expectedResult.containsKey("error")) {
                try {
                    operation.get()
                    fail("Expected exception not thrown")
                } catch (e: Exception) {
                    assertEquals(expectedResult["error"], e.javaClass.simpleName)
                }
                return
            }

            assertEquals(expectedResult["success"].toString(), operation.get().toString())
        }

        private fun assertStackState(expectedState: List<String>, stack: Stack<String>) {
            val expectedStack = Stack<String>()
            expectedStack.addAll(expectedState)
            assertEquals(expectedStack, stack)
        }
    }
}
