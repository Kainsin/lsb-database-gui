/* (C)2024 */
package org.kainsin.lsbdatabasegui.preferences;

import java.util.Objects;
import java.util.prefs.Preferences;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * This class keeps track of the size and position of a window and stores it in the given preference node.
 */
@Slf4j
public class WindowPreferences {
    private static final String WINDOW_POSITION_X = "Position_X";
    private static final String WINDOW_POSITION_Y = "Position_Y";
    private static final String WINDOW_WIDTH = "Width";
    private static final String WINDOW_HEIGHT = "Height";

    /**
     * The window to set and save the preferences for.
     */
    private final Window window;

    /**
     * The preferences node to load and save the window preferences.
     */
    private final Preferences preferences;

    /**
     * Create a new WindowPreferences and load the preferences for the given window. The window will be tracked and when
     * closed the size and position will be saved.
     *
     * @param window      the window to manage the preferences for
     * @param preferences the preferences node for this window
     */
    public WindowPreferences(final Window window, final Preferences preferences) {
        this.window = Objects.requireNonNull(window);
        this.preferences = Objects.requireNonNull(preferences);

        // Get the preferences before setting them. Adjusting one setting before getting the next might alter what
        // it would have been
        log.debug("Loading preferences for window");
        final double x = preferences.getDouble(WINDOW_POSITION_X, window.getX());
        final double y = preferences.getDouble(WINDOW_POSITION_Y, window.getY());
        final double width = preferences.getDouble(WINDOW_WIDTH, window.getWidth());
        final double height = preferences.getDouble(WINDOW_HEIGHT, window.getHeight());
        window.setX(x);
        window.setY(y);
        window.setWidth(width);
        window.setHeight(height);

        // Save the preferences before the window closes.
        window.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::onWindowClosing);
        window.addEventFilter(WindowEvent.WINDOW_HIDING, this::onWindowClosing);
    }

    /**
     * This method is called when the window will be closed.
     *
     * @param event information about the event
     */
    private void onWindowClosing(final WindowEvent event) {
        log.debug("Saving preferences for window");
        preferences.putDouble(WINDOW_POSITION_X, window.getX());
        preferences.putDouble(WINDOW_POSITION_Y, window.getY());
        preferences.putDouble(WINDOW_WIDTH, window.getWidth());
        preferences.putDouble(WINDOW_HEIGHT, window.getHeight());
    }
}
