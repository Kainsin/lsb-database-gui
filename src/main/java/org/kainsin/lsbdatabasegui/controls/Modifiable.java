package org.kainsin.lsbdatabasegui.controls;

import javafx.beans.property.ReadOnlyBooleanProperty;

/**
 * This interface defines an object that can be modified.
 */
public interface Modifiable {
    /**
     * Determine if the object is modified.
     *
     * @return true if the object is modified
     */
    boolean isModified();

    /**
     * Get the property used to determine if the object is modified.
     *
     * @return the modified property
     */
    ReadOnlyBooleanProperty modifiedProperty();
}
