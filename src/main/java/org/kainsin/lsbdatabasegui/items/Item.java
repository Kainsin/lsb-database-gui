/* (C)2024 */
package org.kainsin.lsbdatabasegui.items;

import javafx.beans.property.*;
import org.kainsin.lsbdatabasegui.entities.items.AuctionHouseCategory;
import org.kainsin.lsbdatabasegui.entities.items.ItemEntity;

/**
 * This class wraps an ItemEntity and stores unsaved changes.
 */
public class Item {
    /**
     * The database entity that this class wraps.
     */
    private final ItemEntity itemEntity;

    /**
     * The database ID of the item.
     */
    private final ReadOnlyIntegerWrapper id;

    /**
     * The name of the item.
     */
    private final StringProperty name;

    /**
     * The type of this item.
     */
    private final ObjectProperty<AuctionHouseCategory> auctionHouseCategory;

    /**
     * True if this item has been modified, false if all the properties match the underlying entity.
     */
    private final ReadOnlyBooleanWrapper modified;

    /**
     * Create a new Item that wraps the given database entity.
     *
     * @param itemEntity the database entity to wrap
     */
    public Item(final ItemEntity itemEntity) {
        this.itemEntity = itemEntity;
        id = new ReadOnlyIntegerWrapper(this, "id", itemEntity.getId());
        name = new SimpleStringProperty(this, "name", itemEntity.getName());
        auctionHouseCategory =
                new SimpleObjectProperty<>(this, "auctionHouseCategory", itemEntity.getAuctionHouseCategory());

        modified = new ReadOnlyBooleanWrapper(this, "modified", false);
        modified.bind(name.isNotEqualTo(itemEntity.getName())
                .or(auctionHouseCategory.isNotEqualTo(itemEntity.getAuctionHouseCategory())));
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

    public AuctionHouseCategory getAuctionHouseCategory() {
        return auctionHouseCategory.get();
    }

    public ObjectProperty<AuctionHouseCategory> auctionHouseCategoryProperty() {
        return auctionHouseCategory;
    }

    public void setAuctionHouseCategory(final AuctionHouseCategory value) {
        auctionHouseCategory.set(value);
    }

    public boolean isModified() {
        return modified.get();
    }

    public ReadOnlyBooleanProperty modifiedProperty() {
        return modified.getReadOnlyProperty();
    }
}
