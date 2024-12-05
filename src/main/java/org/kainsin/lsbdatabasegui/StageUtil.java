/* (C)2024 */
package org.kainsin.lsbdatabasegui;

import javafx.scene.layout.Region;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;

/**
 * Utility methods for stages.
 */
@Slf4j
public final class StageUtil {
    /**
     * Keep the constructor private to prevent instantiation.
     */
    private StageUtil() {}

    /**
     * Initialize the given stage for the application.
     *
     * @param stage the stage to initialize
     */
    public static void initialize(final Stage stage) {
        final URL mainStyleSheet = StageUtil.class.getResource("lsbdatabasegui.css");
        if (mainStyleSheet == null) {
            log.warn("Unable to load main stylesheet for application.");
        } else {
            stage.getScene().getStylesheets().add(mainStyleSheet.toExternalForm());
        }
    }

    /**
     * Set the minimum and maximum dimensions of the given stage.
     *
     * @param stage the stage to set the minimum and maximum dimensions for
     */
    public static void setMinAndMaxDimensions(final Stage stage) {
        if (stage.getScene().getRoot() instanceof final Region root) {
            final double widthDifference = stage.getWidth() - root.getWidth();
            if (Double.isFinite(root.getMinWidth()) && root.getMinWidth() >= 0) {
                stage.setMinWidth(root.getMinWidth() + widthDifference);
            }
            if (Double.isFinite(root.getMaxWidth()) && root.getMaxWidth() < Double.MAX_VALUE - widthDifference) {
                stage.setMaxWidth(root.getMaxWidth() + widthDifference);
            }

            final double heightDifference = stage.getHeight() - root.getHeight();
            if (Double.isFinite(root.getMinHeight()) && root.getMinWidth() >= 0) {
                stage.setMinHeight(root.getMinHeight() + heightDifference);
            }
            if (Double.isFinite(root.getMaxHeight()) && root.getMaxHeight() < Double.MAX_VALUE - heightDifference) {
                stage.setMaxHeight(root.getMaxHeight() + heightDifference);
            }
        }
    }
}
