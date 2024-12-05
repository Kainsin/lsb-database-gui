/* (C)2024 */
package org.kainsin.lsbdatabasegui.items;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.Objects;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.kainsin.lsbdatabasegui.entities.items.AuctionHouseCategory;
import org.kainsin.lsbdatabasegui.entities.items.ItemEntity;
import org.kainsin.lsbdatabasegui.entities.items.ItemEntityReduced;

@Slf4j
public class ItemManagementPaneController {
    /**
     * The preferences for the application.
     */
    private final Preferences applicationPreferences;

    /**
     * The preferences for this pane.
     */
    private final Preferences contentPreferences;

    /**
     * The factory to use to create entity managers.
     */
    private final EntityManagerFactory entityManagerFactory;

    /// Left side of the split pane
    @FXML
    private TextField itemNameFilter;

    @FXML
    private TableView<ItemInfo> itemSelectionTable;

    /// Right side of the split pane
    @FXML
    private Node rightPane;

    @FXML
    private TextField nameTextField;

    @FXML
    private ChoiceBox<AuctionHouseCategory> auctionHouseCategoryChoiceBox;

    @FXML
    private CheckBox sellableCheckBox;

    @FXML
    private Spinner<Integer> baseSalePriceSpinner;

    /**
     * Create a new ItemManagementPaneController.
     *
     * @param applicationPreferences the preferences for the application
     * @param panePreferences the preferences specifically for this pane
     * @param entityManagerFactory the factory to use to create new entity managers for accessing the database
     */
    public ItemManagementPaneController(
            final Preferences applicationPreferences,
            final Preferences panePreferences,
            final EntityManagerFactory entityManagerFactory) {
        this.applicationPreferences = Objects.requireNonNull(applicationPreferences);
        this.contentPreferences = Objects.requireNonNull(panePreferences).node("Content");
        this.entityManagerFactory = Objects.requireNonNull(entityManagerFactory);
    }

    public void initialize() {
        itemSelectionTable.setRowFactory();
        itemSelectionTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        itemSelectionTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
//        itemSelectionTable.getColumns().get(0).setCellFactory(new ModifiableValueCellFactory<>());
        final FilteredList<ItemInfo> filteredItems = new FilteredList<>(itemSelectionTable.getItems());
        filteredItems
                .predicateProperty()
                .bind(Bindings.createObjectBinding(() -> this::isItemShown, itemNameFilter.textProperty()));
        itemSelectionTable.setItems(filteredItems);
        itemSelectionTable.getSelectionModel().selectedItemProperty().addListener(this::selectedItemChanged);

        rightPane
                .disableProperty()
                .bind(itemSelectionTable
                        .getSelectionModel()
                        .selectedItemProperty()
                        .isNull());

        auctionHouseCategoryChoiceBox.getItems().setAll(AuctionHouseCategory.values());

        // Load the item selection table from the database.
        final Thread loadingThread = new Thread(new ItemSelectionTableLoadingTask(), "Item-Table-Loading-Thread");
        loadingThread.setDaemon(true);
        loadingThread.start();
    }

    /**
     * This is triggered when the selected item changes. Stop editing the old item and start editing the new one.
     *
     * @param observable the observable that changed
     * @param oldValue   the old value
     * @param newValue   the new value
     */
    private void selectedItemChanged(
            final ObservableValue<? extends ItemInfo> observable,
            final ItemInfo oldValue,
            final ItemInfo newValue) {
        if (oldValue != null) {
            oldValue.getItem().ifPresent(item -> {
                nameTextField.textProperty().unbindBidirectional(item.nameProperty());
                auctionHouseCategoryChoiceBox.valueProperty().unbindBidirectional(item.auctionHouseCategoryProperty());
            });

            // If the item isn't modified then stop holding onto it so that we're not wasting memory.
            if (!oldValue.isModified()) {
                oldValue.setItem(null);
            }
        }

        if (newValue != null) {
            // Load the item from the database if needed.
            final Item item = newValue.getItem(() -> loadItem(newValue.getId()));
            nameTextField.textProperty().bindBidirectional(item.nameProperty());
            auctionHouseCategoryChoiceBox.valueProperty().bindBidirectional(item.auctionHouseCategoryProperty());
        }
    }

    /**
     * Load the item with the given ID.
     *
     * @param id the ID of the item to load
     * @return the item with the given ID
     */
    private Item loadItem(final int id) {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final ItemEntity itemEntity = entityManager.find(ItemEntity.class, id);
        return new Item(itemEntity);
    }

    /**
     * Check to see if the given item should be shown in the item selected list.
     *
     * @param item the item to check
     * @return true if the item is to be shown, false if not
     */
    private boolean isItemShown(final ItemInfo item) {
        return Objects.equals(itemSelectionTable.getSelectionModel().getSelectedItem(), item)
                || StringUtils.isBlank(itemNameFilter.getText())
                || StringUtils.containsIgnoreCase(item.getName(), itemNameFilter.getText());
    }

    /**
     * This task will load the item selection table from the database. It will use simplified item objects to only
     * obtain the data that needs to be fetched.
     */
    protected class ItemSelectionTableLoadingTask extends Task<Void> {
        @Override
        protected Void call() {
            final EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager
                    .createNamedQuery("ItemEntityReduced.findAll", ItemEntityReduced.class)
                    .getResultStream()
                    .forEach(item -> {
                        final FilteredList<ItemInfo> filteredList =
                                (FilteredList<ItemInfo>) itemSelectionTable.getItems();
                        final ObservableList<ItemInfo> sourceList =
                                (ObservableList<ItemInfo>) filteredList.getSource();
                        Platform.runLater(() -> sourceList.add(new ItemInfo(item)));
                    });
            return null;
        }
    }
}
