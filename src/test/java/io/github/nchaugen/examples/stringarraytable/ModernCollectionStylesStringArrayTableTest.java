package io.github.nchaugen.examples.stringarraytable;

import org.junit.jupiter.api.Tag;
import org.tabletest.junit.TableTest;

@Tag("snapshot")
public class ModernCollectionStylesStringArrayTableTest {

    @TableTest({
        "Scenario               | Collection literal                                              ",
        "Flat list              | [1, 2, 3]                                                       ",
        "Nested list            | [[1, 2], [3, 4]]                                                ",
        "Flat set               | {alpha, beta, gamma}                                            ",
        "Set of sets            | {{'alpha', \"beta\"}, {gamma}}                                  ",
        "Flat map               | [a: 1, b: 2]                                                    ",
        "Map with list values   | [left: [1, 2], right: [3, 4]]                                   ",
        "Map with set values    | [north: {oslo, bergen}, south: {madrid}]                        ",
        "List of maps           | [[id: 1, qty: 10], [id: 2, qty: 20]]                            ",
        "Map of maps            | [storeA: [price: 10, qty: 2], storeB: [price: 5, qty: 4]]       ",
        "Mixed nested structure | [items: [[sku: A, qty: 1], [sku: B, qty: 2]], tags: {new, sale}]"
    })
    void shouldRenderCollectionLiteralsFromInlineStringArray(Object collectionLiteral) {
    }

    @TableTest({
        "Scenario                     | Left literal           | Right literal      | Marker?",
        "List and set columns         | [1, 2, 3]              | {x, y, z}          | render",
        "Map and list-of-maps columns | [a: 1, b: 2]           | [[id: 1], [id: 2]] | render",
        "Nested list and map columns  | [[1], [2, 3]]          | [group: [1, 2, 3]] | render",
        "Map-of-maps and set columns  | [a: [x: 1], b: [y: 2]] | {left, right}      | render"
    })
    void shouldRenderMultipleCollectionColumnsFromInlineStringArray(
            Object leftLiteral,
            Object rightLiteral,
            String marker) {
    }
}
