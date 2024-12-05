/* (C)2024 */
package org.kainsin.lsbdatabasegui.items;

import java.util.Optional;
import java.util.function.Supplier;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.kainsin.lsbdatabasegui.controls.Modifiable;
import org.kainsin.lsbdatabasegui.entities.items.ItemEntityReduced;

/**
 * This class contains the simple item information for the selection list.
 */
public class ItemInfo implements Modifiable {
    /**
     * The database ID of the item.
     */
    private final ReadOnlyIntegerWrapper id;

    /**
     * The name of the item.
     */
    private final StringProperty name;

    /**
     * True if this item has been modified, false if all the properties match the underlying entity.
     */
    private final ReadOnlyBooleanWrapper modified;

    /**
     * The item being edited when this is selected.
     */
    private Item item;

    /**
     * Create a new ItemInfo with information from the given database entity.
     *
     * @param itemEntity the database entity to pull the information from
     */
    public ItemInfo(final ItemEntityReduced itemEntity) {
        id = new ReadOnlyIntegerWrapper(this, "id", itemEntity.getId());
        name = new SimpleStringProperty(this, "name", itemEntity.getName());

        modified = new ReadOnlyBooleanWrapper(this, "modified", false);
    }

    public int getId() {
        return id.get();
    }

    public ReadOnlyIntegerProperty idProperty() {
        return id.getReadOnlyProperty();
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(final String value) {
        name.set(value);
    }

    @Override
    public boolean isModified() {
        return modified.get();
    }

    @Override
    public ReadOnlyBooleanProperty modifiedProperty() {
        return modified.getReadOnlyProperty();
    }

    public Optional<Item> getItem() {
        return Optional.ofNullable(item);
    }

    public Item getItem(final Supplier<Item> itemSupplier) {
        if (item == null) {
            setItem(itemSupplier.get());
            if (item == null) {
                throw new IllegalStateException("Item is still null after fetching it from the provided supplier");
            }
        }
        return item;
    }

    public void setItem(final Item value) {
        if (item != null) {
            modified.unbind();
        }

        item = value;

        if (value != null) {
            modified.bind(item.modifiedProperty());
        } else {
            modified.set(false);
        }
    }
}
