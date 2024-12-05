package org.kainsin.lsbdatabasegui.controls;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class ModifiableValueTableRowFactory<S extends Modifiable> implements Callback<TableView<S>, TableRow<S>> {
    @Override
    public TableRow<S> call(final TableView<S> param) {
        return new ModifiableValueTableRow<>();
    }
}
