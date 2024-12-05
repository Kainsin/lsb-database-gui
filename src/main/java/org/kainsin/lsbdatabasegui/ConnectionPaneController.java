/* (C)2024 */
package org.kainsin.lsbdatabasegui;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.slf4j.Slf4j;
import org.kainsin.lsbdatabasegui.items.ItemManagementPaneController;
import org.kainsin.lsbdatabasegui.preferences.WindowPreferences;

@Slf4j
public class ConnectionPaneController {
    private static final String HOST_PREFERENCE = "Host";
    private static final String PORT_PREFERENCE = "Port";
    private static final String DATABASE_PREFERENCE = "Database";
    private static final String USERNAME_PREFERENCE = "Username";

    /**
     * The preferences for the application.
     */
    private final Preferences applicationPreferences;

    /**
     * The preferences for this pane.
     */
    private final Preferences contentPreferences;

    @FXML
    private TextField hostTextField;

    @FXML
    private Spinner<Integer> portSpinner;

    @FXML
    private TextField databaseTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button connectButton;

    /**
     * Create a new ConnectionDialogController.
     *
     * @param applicationPreferences the preferences for the application
     * @param panePreferences the preferences specifically for this pane
     */
    public ConnectionPaneController(final Preferences applicationPreferences, final Preferences panePreferences) {
        this.applicationPreferences = Objects.requireNonNull(applicationPreferences);
        this.contentPreferences = Objects.requireNonNull(panePreferences).node("Content");
    }

    @FXML
    public void onConnect(final ActionEvent event) {
        log.info(
                "Connecting to {}:{}/{} with username {}",
                hostTextField.getText(),
                portSpinner.getValue(),
                databaseTextField.getText(),
                usernameTextField.getText());

        try {
            // Configure the entity manager factory and try to establish a connection.
            final Map<String, String> properties = new HashMap<>();
            properties.put(
                    "jakarta.persistence.jdbc.url",
                    "jdbc:mariadb://" + hostTextField.getText() + ":" + portSpinner.getValue() + "/"
                            + databaseTextField.getText());
            properties.put("jakarta.persistence.jdbc.user", usernameTextField.getText());
            properties.put("jakarta.persistence.jdbc.password", passwordField.getText());

            final EntityManagerFactory entityManagerFactory =
                    Persistence.createEntityManagerFactory("org.kainsin.lsbdatabasegui", properties);
            entityManagerFactory.createEntityManager();

            // Create and show the database management window.
            final Preferences managementWindowPreferences = applicationPreferences.node("DatabaseManagementDialog");
            final Stage stage = new Stage();
            final URL fxmlUrl = getClass().getResource("DatabaseManagementPane.fxml");
            final FXMLLoader loader = new FXMLLoader(fxmlUrl);
            loader.setControllerFactory(c -> {
                if (c == ItemManagementPaneController.class) {
                    return new ItemManagementPaneController(
                            applicationPreferences, managementWindowPreferences, entityManagerFactory);
                }
                return null;
            });
            final TabPane root = loader.load();
            final Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(hostTextField.getText() + ":" + portSpinner.getValue() + "/" + databaseTextField.getText());
            stage.sizeToScene();
            StageUtil.initialize(stage);
            stage.show();
            StageUtil.setMinAndMaxDimensions(stage);

            // Load the preferences for the database management window.
            stage.getProperties()
                    .put(
                            WindowPreferences.class.getSimpleName(),
                            new WindowPreferences(stage, managementWindowPreferences));

            // Save preferences and close the dialog.
            contentPreferences.put(HOST_PREFERENCE, hostTextField.getText());
            contentPreferences.putInt(PORT_PREFERENCE, portSpinner.getValue());
            contentPreferences.put(DATABASE_PREFERENCE, databaseTextField.getText());
            contentPreferences.put(USERNAME_PREFERENCE, usernameTextField.getText());
            ((Stage) connectButton.getScene().getWindow()).close();
        } catch (final Exception e) {
            final Alert alert = new Alert(
                    Alert.AlertType.ERROR,
                    "Unable to connect to the database using the given credentials.",
                    ButtonType.OK);
            alert.initOwner(connectButton.getScene().getWindow());
            alert.setHeaderText("Database Connection Error");
            alert.showAndWait();
        }
    }

    /**
     * This is called auto-magically when FXMLLoader finishes.
     */
    public void initialize() {
        log.info("Initializing connection dialog components");
        hostTextField.setText(contentPreferences.get(HOST_PREFERENCE, "localhost"));
        portSpinner.getValueFactory().setValue(contentPreferences.getInt(PORT_PREFERENCE, 3006));
        databaseTextField.setText(contentPreferences.get(DATABASE_PREFERENCE, "xidb"));
        usernameTextField.setText(contentPreferences.get(USERNAME_PREFERENCE, "root"));

        // In order to connect the host, port and username must be filled out. Password can be blank.
        connectButton
                .disableProperty()
                .bind(hostTextField
                        .textProperty()
                        .isEmpty()
                        .or(portSpinner.valueProperty().isNull())
                        .or(usernameTextField.textProperty().isEmpty()));
    }

    public void paneShown(final WindowEvent event) {
        // Select the password field when first shown.
        passwordField.requestFocus();
    }
}
