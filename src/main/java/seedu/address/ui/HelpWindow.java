package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for the HallLedger help window.
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2526s2-cs2103t-t15-1.github.io/tp/";

    private static final String HELP_CONTENT =
            "• add - add n=NAME i=STUDENT_ID r=ROOM_NUMBER p=PHONE_NUMBER "
                    + "e=EMAIL ec=EMERGENCY_CONTACT\n\n"
                    + "• delete - delete i=STUDENT_ID\n\n"
                    + "• edit - edit i=STUDENT_ID [n=NEW_NAME] [i=NEW_ID] [p=NEW_PHONE] "
                    + "[e=NEW_EMAIL] [r=NEW_ROOM_NUMBER] [ec=NEW_E_CONTACT]\n\n"
                    + "• find - find [n=NAME] [i=STUDENT_ID] [r=ROOM_NUMBER] [p=PHONE] "
                    + "[e=EMAIL] [t=TAG]\n\n"
                    + "• tag - tag i=STUDENT_ID [y=YEAR] [m=MAJOR] [g=GENDER]\n\n"
                    + "• list - list\n\n"
                    + "• clear - clear\n\n"
                    + "• help - help\n\n"
                    + "• exit - exit";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Label helpContent;

    @FXML
    private Label guideText;

    @FXML
    private TextField link;

    /**
     * Creates a new HelpWindow.
     *
     * @param owner Stage to use as the owner of the HelpWindow.
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage owner, Stage root) {
        super(FXML, root);
        root.initOwner(owner);
        root.initModality(Modality.NONE);
        root.setResizable(false);

        helpContent.setText(HELP_CONTENT);
        guideText.setText("Refer to the HallLedger User Guide for more details:\n");

        link.setText(USERGUIDE_URL);
        link.setEditable(false);
        link.setFocusTraversable(false);
    }

    /**
     * Creates a new HelpWindow.
     *
     * @param owner Stage to use as the owner of the HelpWindow.
     */
    public HelpWindow(Stage owner) {
        this(owner, new Stage());
    }

    /**
     * Shows the help window.
     */
    public void show() {
        logger.fine("Showing HallLedger help window.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Opens the guide link in a browser.
     *
     * @param event The click event.
     */
    @FXML
    public void handleLinkClick(Event event) {
        try {
            logger.fine("Opened link in browser");
            Desktop.getDesktop().browse(new URI(link.getText()));
        } catch (URISyntaxException | IOException e) {
            logger.info("The URL is not correct");
        }
    }
}
