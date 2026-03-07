package io.github.nchaugen.examples.legacy;

import io.github.nchaugen.tabletest.junit.TableTest;

import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class LegacyStackExampleTest {

    @TableTest("""
        Scenario                              | Initial stack | Operation | Operation result?            | Resulting stack?
        // An empty stack
        is empty                              | []            | [empty]   | [success: true]              | []
        has no size                           | []            | [size]    | [success: 0]                 | []
        throws when popped                    | []            | [pop]     | [error: EmptyStackException] | []
        throws when peeked                    | []            | [peek]    | [error: EmptyStackException] | []
        push acquires elements                | []            | [push, a] | [success: a]                 | [a]

        // A non-empty stack
        is not empty                          | [a, b, c]     | [empty]   | [success: false]             | [a, b, c]
        has size                              | [a, b, c]     | [size]    | [success: 3]                 | [a, b, c]
        peek reveals top element              | [a, b, c]     | [peek]    | [success: c]                 | [a, b, c]
        pop reveals elements in reverse order | [a, b, c]     | [pop]     | [success: c]                 | [a, b]
        push acquires elements                | [a, b, c]     | [push, d] | [success: d]                 | [a, b, c, d]
        """)
    void shouldProcessStackOperations(
        List<String> initialState,
        List<String> operation,
        Map<String, ?> expectedResult,
        List<String> expectedState
    ) {
        Stack<String> stack = new Stack<>();
        stack.addAll(initialState);

        switch (operation.getFirst()) {
            case "empty" -> assertOperation(expectedResult, stack::isEmpty);
            case "size" -> assertOperation(expectedResult, stack::size);
            case "peek" -> assertOperation(expectedResult, stack::peek);
            case "pop" -> assertOperation(expectedResult, stack::pop);
            case "push" -> assertOperation(expectedResult, () -> stack.push(operation.getLast()));
            default -> fail("Unknown operation: " + operation.getFirst());
        }

        assertStackState(expectedState, stack);
    }

    private static void assertOperation(Map<String, ?> expectedResult, Supplier<?> operation) {
        if (expectedResult.containsKey("error")) {
            try {
                operation.get();
                fail("Expected exception not thrown");
            } catch (Exception e) {
                assertEquals(expectedResult.get("error"), e.getClass().getSimpleName());
            }
            return;
        }

        assertEquals(String.valueOf(expectedResult.get("success")), String.valueOf(operation.get()));
    }

    private static void assertStackState(List<String> expectedState, Stack<String> stack) {
        Stack<String> expectedStack = new Stack<>();
        expectedStack.addAll(expectedState);
        assertEquals(expectedStack, stack);
    }
}
