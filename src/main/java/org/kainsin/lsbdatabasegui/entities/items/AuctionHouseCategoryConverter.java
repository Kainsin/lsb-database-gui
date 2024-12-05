/* (C)2024 */
package org.kainsin.lsbdatabasegui.entities.items;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;

@Converter(autoApply = true)
public class AuctionHouseCategoryConverter implements AttributeConverter<AuctionHouseCategory, Short> {
    @Override
    public Short convertToDatabaseColumn(final AuctionHouseCategory attribute) {
        return attribute.getId();
    }

    @Override
    public AuctionHouseCategory convertToEntityAttribute(final Short dbData) {
        return Arrays.stream(AuctionHouseCategory.values())
                .filter(t -> t.getId() == dbData)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown item type: " + dbData));
    }
}
