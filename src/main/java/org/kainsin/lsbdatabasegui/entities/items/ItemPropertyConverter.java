/* (C)2024 */
package org.kainsin.lsbdatabasegui.entities.items;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class ItemPropertyConverter implements AttributeConverter<Set<ItemProperty>, Integer> {
    @Override
    public Integer convertToDatabaseColumn(final Set<ItemProperty> attribute) {
        return attribute.stream().mapToInt(ItemProperty::getFlag).sum();
    }

    @Override
    public Set<ItemProperty> convertToEntityAttribute(final Integer dbData) {
        return EnumSet.copyOf(Arrays.stream(ItemProperty.values())
                .filter(p -> (dbData & p.getFlag()) > 0)
                .collect(Collectors.toSet()));
    }
}
