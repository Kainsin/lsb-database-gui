/* (C)2024 */
package org.kainsin.lsbdatabasegui;

import java.net.URL;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.kainsin.lsbdatabasegui.preferences.WindowPreferences;

/**
 * The main application for the LSB FFXI database GUI.
 */
public class DatabaseGuiApplication extends Application {
    /**
     * The application entry point.
     *
     * @param arguments the command line arguments
     */
    public static void main(final String[] arguments) {
        launch(arguments);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        // Get the preferences for the connection dialog.
        final Preferences applicationPreferences = Preferences.userNodeForPackage(DatabaseGuiApplication.class)
                .node(DatabaseGuiApplication.class.getSimpleName());
        final Preferences connectionDialogPreferences = applicationPreferences.node("ConnectionDialog");

        // Load the connection dialog.
        final URL fxmlUrl = getClass().getResource("ConnectionPane.fxml");
        final FXMLLoader loader = new FXMLLoader(fxmlUrl);
        loader.setControllerFactory(
                c -> {
                    final ConnectionPaneController controller = new ConnectionPaneController(applicationPreferences, connectionDialogPreferences);
                    stage.setOnShown(controller::paneShown);
                    return controller;
                });
        final GridPane root = loader.load();
        final Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Connect to LandSandBoat FFXI Database...");
        stage.sizeToScene();
        StageUtil.initialize(stage);
        stage.show();
        StageUtil.setMinAndMaxDimensions(stage);

        // Load the preferences for the connection dialog.
        stage.getProperties()
                .put(
                        WindowPreferences.class.getSimpleName(),
                        new WindowPreferences(stage, connectionDialogPreferences));
    }
}
