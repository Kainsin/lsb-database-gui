/* (C)2024 */
package org.kainsin.lsbdatabasegui.entities.items;

import lombok.Getter;

@Getter
public enum ItemProperty {
    WALLHANGING(0x0001),
    FLAG_01(0x0002),
    MYSTERY_BOX(0x0004), // Can be gained from Gobbie Mystery Box
    MOG_GARDEN(0x0008), // Can use in Mog Garden
    MAIL2ACCOUNT(0x0010), // CanSendPOL Polutils Value
    INSCRIBABLE(0x0020),
    NOAUCTION(0x0040),
    SCROLL(0x0080),
    LINKSHELL(0x0100), // Linkshell Polutils Value
    CANUSE(0x0200),
    CANTRADENPC(0x0400),
    CANEQUIP(0x0800),
    NOSALE(0x1000),
    NODELIVERY(0x2000),
    EX(0x4000), // NoTradePC Polutils Value
    RARE(0x8000);

    /**
     * The bit flag for this property.
     */
    private final int flag;

    /**
     * Create a new ItemProperty.
     *
     * @param flag the flag for this property
     */
    ItemProperty(final int flag) {
        this.flag = flag;
    }
}
