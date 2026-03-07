package io.github.nchaugen.orders;

import java.util.List;
import java.util.Map;

public record Inventory(Map<StoreLocation, List<ItemStock>> storeInventory) {


}
