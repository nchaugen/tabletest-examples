package io.github.nchaugen.orders;

public record StoreLocation(
    String locationId,
    String locationName,
    LocationType locationType,
    String address
) {
    public StoreLocation(String locationId, LocationType locationType) {
        this(locationId, locationId, locationType, null);
    }
}
