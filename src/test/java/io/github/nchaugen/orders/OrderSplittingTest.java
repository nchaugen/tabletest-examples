package io.github.nchaugen.orders;

import io.github.nchaugen.tabletest.junit.TableTest;
import org.tabletest.junit.TypeConverter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderSplittingTest {

    @TableTest("""
        Scenario                  | Cart                                                                                                                                                               | Inventory                                                                                                                             | Orders?
        Mixing BOPIS and Delivery | [items: [[productId: ShirtA, quantity: 1, fulfillmentType: BOPIS, pickupLocationId: Store1], [productId: ShirtB, quantity: 1, fulfillmentType: DELIVERY, deliveryAddress: 'Main St 1']]] | [[locationId: Store1, inventory: [[productId: ShirtA, quantityAvailable: 5]]], [locationId: Megastore, inventory: [[productId: ShirtB, quantityAvailable: 10]]]] | [[orderId: Order1, lines: [[productId: ShirtA, quantity: 1, sourceLocationId: Store1]], sourceLocationId: Store1, fulfillmentType: BOPIS, shipTiming: PICKUP], [orderId: Order2, lines: [[productId: ShirtB, quantity: 1, sourceLocationId: Megastore]], sourceLocationId: Megastore, fulfillmentType: DELIVERY, destinationAddress: 'Main St 1', shipTiming: SHIP_NOW]]
        """)
    void shouldSplitOrdersForMixedFulfilment(Cart cartItems, Inventory inventory, List<Order> expectedOrders) {
        List<Order> actualOrders = splitOrders(cartItems, inventory);
        assertEquals(expectedOrders, actualOrders);
    }

    private static List<Order> splitOrders(Cart cart, Inventory inventory) {
        return IntStream.range(0, cart.items().size())
            .mapToObj(index -> toOrderForItem(index, cart.items().get(index), inventory))
            .toList();
    }

    private static Order toOrderForItem(int index, CartItem item, Inventory inventory) {
        String sourceLocationId = resolveSourceLocation(item, inventory);
        ShipTiming shipTiming = item.fulfillmentType() == FulfillmentType.BOPIS
            ? ShipTiming.PICKUP
            : item.isPreOrder() ? ShipTiming.SHIP_LATER : ShipTiming.SHIP_NOW;

        OrderLine line = new OrderLine(item.productId(), item.quantity(), sourceLocationId);
        return new Order(
            "Order" + (index + 1),
            List.of(line),
            sourceLocationId,
            item.fulfillmentType(),
            item.deliveryAddress(),
            shipTiming
        );
    }

    private static String resolveSourceLocation(CartItem item, Inventory inventory) {
        if (item.fulfillmentType() == FulfillmentType.BOPIS) {
            if (item.pickupLocationId() == null || item.pickupLocationId().isBlank()) {
                throw new IllegalArgumentException("BOPIS items must provide pickupLocationId");
            }
            return item.pickupLocationId();
        }

        return inventory.storeInventory().entrySet().stream()
            .filter(entry -> hasAvailableProduct(entry.getValue(), item.productId(), item.quantity()))
            .map(entry -> entry.getKey().locationId())
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No inventory for product " + item.productId()));
    }

    private static boolean hasAvailableProduct(List<ItemStock> stocks, String productId, int requiredQuantity) {
        return stocks.stream()
            .filter(ItemStock::isAvailableNow)
            .anyMatch(stock -> stock.productId().equals(productId) && stock.quantityAvailable() >= requiredQuantity);
    }

    @TypeConverter
    public static Cart toCart(Map<String, ?> input) {
        List<CartItem> items = asMapList(input.get("items")).stream()
            .map(OrderSplittingTest::toCartItem)
            .toList();
        return new Cart(items);
    }

    @TypeConverter
    public static CartItem toCartItem(Map<String, ?> input) {
        return new CartItem(
            String.valueOf(input.get("productId")),
            toInt(input.get("quantity")),
            FulfillmentType.valueOf(String.valueOf(input.get("fulfillmentType"))),
            asNullableString(input.get("deliveryAddress")),
            asNullableString(input.get("pickupLocationId")),
            toBoolean(input.containsKey("isPreOrder") ? input.get("isPreOrder") : Boolean.FALSE),
            asNullableString(input.get("dependsOnProductId"))
        );
    }

    @TypeConverter
    public static Inventory toInventory(List<Map<String, ?>> stores) {
        Map<StoreLocation, List<ItemStock>> storeInventory = new LinkedHashMap<>();

        for (Map<String, ?> store : stores) {
            String locationId = String.valueOf(store.get("locationId"));
            StoreLocation location = new StoreLocation(locationId, LocationType.STORE);

            List<ItemStock> stocks = asMapList(store.get("inventory")).stream()
                .map(stock -> toItemStock(locationId, stock))
                .toList();

            storeInventory.put(location, stocks);
        }

        return new Inventory(storeInventory);
    }

    @TypeConverter
    public static Order toOrder(Map<String, ?> input) {
        List<OrderLine> lines = asMapList(input.get("lines")).stream()
            .map(OrderSplittingTest::toOrderLine)
            .toList();

        return new Order(
            String.valueOf(input.get("orderId")),
            lines,
            String.valueOf(input.get("sourceLocationId")),
            FulfillmentType.valueOf(String.valueOf(input.get("fulfillmentType"))),
            asNullableString(input.get("destinationAddress")),
            ShipTiming.valueOf(String.valueOf(input.get("shipTiming")))
        );
    }

    @TypeConverter
    public static OrderLine toOrderLine(Map<String, ?> input) {
        return new OrderLine(
            String.valueOf(input.get("productId")),
            toInt(input.get("quantity")),
            String.valueOf(input.get("sourceLocationId"))
        );
    }

    private static ItemStock toItemStock(String locationId, Map<String, ?> input) {
        return new ItemStock(
            String.valueOf(input.get("productId")),
            locationId,
            toInt(input.get("quantityAvailable"))
        );
    }

    @SuppressWarnings("unchecked")
    private static List<Map<String, ?>> asMapList(Object input) {
        return (List<Map<String, ?>>) input;
    }

    private static int toInt(Object input) {
        if (input instanceof Number number) {
            return number.intValue();
        }
        return Integer.parseInt(String.valueOf(input));
    }

    private static boolean toBoolean(Object input) {
        if (input instanceof Boolean booleanValue) {
            return booleanValue;
        }
        return Boolean.parseBoolean(String.valueOf(input));
    }

    private static String asNullableString(Object value) {
        if (value == null) {
            return null;
        }
        String stringValue = String.valueOf(value);
        return stringValue.isBlank() ? null : stringValue;
    }
}
