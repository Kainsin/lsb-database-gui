/* (C)2024 */
package org.kainsin.lsbdatabasegui.entities.items;

import jakarta.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import lombok.Getter;

/**
 * This represents where you would find the item in the auction house.
 */
public enum AuctionHouseCategory {
    NONE((short) 0, "None"),
    H2H((short) 1, "Weapons", "H2H"),
    DAGGER((short) 2, "Weapons", "Dagger"),
    SWORD((short) 3, "Weapons", "Sword"),
    GREATSWORD((short) 4, "Weapons", "Greatsword"),
    AXE((short) 5, "Weapons", "Axe"),
    GREATAXE((short) 6, "Weapons", "Greataxe"),
    SCYTHE((short) 7, "Weapons", "Scythe"),
    POLEARM((short) 8, "Weapons", "Polearm"),
    KATANA((short) 9, "Weapons", "Katana"),
    GREATKATANA((short) 10, "Weapons", "Greatkatana"),
    CLUB((short) 11, "Weapons", "Club"),
    STAFF((short) 12, "Weapons", "Staff"),
    BOW((short) 13, "Weapons", "Bow"),
    INSTRUMENTS((short) 14, "Weapons", "Instruments"),
    AMMUNITION((short) 15, "Weapons", "Ammo&Misc", "Ammunition"),
    SHIELD((short) 16, "Armor", "Shield"),
    HEAD((short) 17, "Armor", "Head"),
    BODY((short) 18, "Armor", "Body"),
    HANDS((short) 19, "Armor", "Hands"),
    LEGS((short) 20, "Armor", "Legs"),
    FEET((short) 21, "Armor", "Feet"),
    NECK((short) 22, "Armor", "Neck"),
    WAIST((short) 23, "Armor", "Waist"),
    EARRINGS((short) 24, "Armor", "Earrings"),
    RINGS((short) 25, "Armor", "Rings"),
    BACK((short) 26, "Armor", "Back"),
    UNUSED((short) 27, "Unused"),
    WHITE_MAGIC((short) 28, "Scrolls", "White Magic"),
    BLACK_MAGIC((short) 29, "Scrolls", "Black Magic"),
    SUMMONING((short) 30, "Scrolls", "Summoning"),
    NINJUTSU((short) 31, "Scrolls", "Ninjutsu"),
    SONGS((short) 32, "Scrolls", "Songs"),
    MEDICINES((short) 33, "Medicines"),
    FURNISHINGS((short) 34, "Furnishing"),
    CRYSTALS((short) 35, "Crystals"),
    CARDS((short) 36, "Others", "Cards"),
    CURSED_ITEMS((short) 37, "Others", "Cursed Items"),
    SMITHING((short) 38, "Materials", "Smithing"),
    GOLDSMITHING((short) 39, "Materials", "Goldsmithing"),
    CLOTHCRAFT((short) 40, "Materials", "Clothcraft"),
    LEATHERCRAFT((short) 41, "Materials", "Leathercraft"),
    BONECRAFT((short) 42, "Materials", "Bonecraft"),
    WOODWORKING((short) 43, "Materials", "Woodworking"),
    ALCHEMY((short) 44, "Materials", "Alchemy"),
    GEOMANCER((short) 45, "Scrolls", "Geomancy"),
    MISC((short) 46, "Others", "Misc."),
    FISHING_GEAR((short) 47, "Weapons", "Ammo&Misc", "Fishing Gear"),
    PET_ITEMS((short) 48, "Weapons", "Ammo&Misc", "Pet Items"),
    NINJA_TOOLS((short) 49, "Others", "Ninja Tools"),
    BEAST_MADE((short) 50, "Others", "Beast-made"),
    FISH((short) 51, "Food", "Fish"),
    MEAT_EGGS((short) 52, "Food", "Meals", "Meat&Eggs"),
    SEAFOOD((short) 53, "Food", "Meals", "Seafood"),
    VEGETABLES((short) 54, "Food", "Meals", "Vegetables"),
    SOUPS((short) 55, "Food", "Meals", "Soups"),
    BREADS_RICE((short) 56, "Food", "Meals", "Breads&Rice"),
    SWEETS((short) 57, "Food", "Meals", "Sweets"),
    DRINKS((short) 58, "Food", "Meals", "Drinks"),
    INGREDIENTS((short) 59, "Food", "Ingredients"),
    DICE((short) 60, "Scrolls", "Dice"),
    AUTOMATON((short) 61, "Others", "Automaton"),
    GRIPS((short) 62, "Weapons", "Ammo&Misc", "Grips"),
    ALCHEMY_2((short) 63, "Materials", "Alchemy 2"),
    MISC_2((short) 64, "Others", "Misc.2"),
    MISC_3((short) 65, "Others", "Misc.3"),
    INVALID((short) 255, "Item needs retail verification");

    /**
     * The auction house category ID in the database.
     */
    @Getter
    private final short id;

    /**
     * The main auction house category.
     */
    @Getter
    private final String firstCategory;

    /**
     * The auction house sub-category.
     */
    @Nullable private final String secondCategory;

    /**
     * The auction house sub-sub-category.
     */
    private final String thirdCategory;

    AuctionHouseCategory(final short id, final String firstCategory) {
        this(id, firstCategory, null, null);
    }

    AuctionHouseCategory(final short id, final String firstCategory, @Nullable final String secondCategory) {
        this(id, firstCategory, secondCategory, null);
    }

    AuctionHouseCategory(
            final short id,
            final String firstCategory,
            @Nullable final String secondCategory,
            @Nullable final String thirdCategory) {
        this.id = id;
        this.firstCategory = Objects.requireNonNull(firstCategory);
        this.secondCategory = secondCategory;
        this.thirdCategory = thirdCategory;
    }

    public Optional<String> getSecondCategory() {
        return Optional.ofNullable(secondCategory);
    }

    public Optional<String> getThirdCategory() {
        return Optional.ofNullable(thirdCategory);
    }

    public String toString() {
        if (secondCategory != null) {
            if (thirdCategory != null) {
                return firstCategory + "->" + secondCategory + "->" + thirdCategory;
            }
            return firstCategory + "->" + secondCategory;
        }
        return firstCategory;
    }
}
