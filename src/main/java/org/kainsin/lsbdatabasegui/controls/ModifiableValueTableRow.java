package org.kainsin.lsbdatabasegui.controls;


import javafx.css.PseudoClass;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ModifiableValueTableRow<T extends Modifiable> extends TableRow<T> {
    private static final PseudoClass
            MODIFIED_PSEUDO_CLASS = PseudoClass.getPseudoClass("modified");

    @Override
    public void updateItem(final T item, final boolean empty) {
        super.updateItem(item, empty);
    }
}
