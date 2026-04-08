package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
            "• ADD \t\t\t add n=NAME i=STUDENT_ID r=ROOM_NUMBER p=PHONE_NUMBER "
                    + "e=EMAIL ec=EMERGENCY_CONTACT\n\n"
                    + "• DELETE \t\t\t delete i=STUDENT_ID\n\n"
                    + "• EDIT \t\t\t edit i=STUDENT_ID [n=NEW_NAME] [i=NEW_ID] [p=NEW_PHONE] "
                    + "[e=NEW_EMAIL] [r=NEW_ROOM_NUMBER] [ec=NEW_E_CONTACT]\n\n"
                    + "• FIND \t\t\t find [n=NAME] [i=STUDENT_ID] [r=ROOM_NUMBER] [p=PHONE] "
                    + "[e=EMAIL] [t=TAG]\n\n"
                    + "• TAG \t\t\t tag i=STUDENT_ID [y=YEAR] [m=MAJOR] [g=GENDER]\n\n"
                    + "• REMARK \t\t\t remark i=STUDENT_ID rm=REMARK\n\n"
                    + "• DEMERIT \t\t\t demerit i=STUDENT_ID di=RULE_INDEX [rm=REMARK]\n\n"
                    + "• DEMERIT LIST\t\t demeritlist \n\n"
                    + "• LIST \t\t\t\t list\n\n"
                    + "• CLEAR \t\t\t clear\n\n"
                    + "• HELP \t\t\t help\n\n"
                    + "• EXIT \t\t\t exit";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private TextArea helpContent;

    @FXML
    private Label guideText;

    @FXML
    private TextField link;

    /**
     * Creates a help window with the given owner and backing stage.
     *
     * @param owner owner of the help window
     * @param root stage backing the help window
     */
    public HelpWindow(Stage owner, Stage root) {
        super(FXML, root);
        root.initOwner(owner);
        root.initModality(Modality.NONE);
        root.setResizable(false);

        helpContent.setText(HELP_CONTENT);
        helpContent.setEditable(false);
        guideText.setText("Refer to the HallLedger User Guide for more details:\n");

        link.setText(USERGUIDE_URL);
        link.setEditable(false);
        link.setFocusTraversable(false);
    }

    /**
     * Creates a help window owned by the given stage.
     *
     * @param owner owner of the help window
     */
    public HelpWindow(Stage owner) {
        this(owner, new Stage());
    }

    /**
     * Shows the help window and centres it on screen.
     */
    public void show() {
        logger.fine("Showing HallLedger help window.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns whether the help window is currently visible.
     *
     * @return true if the help window is showing
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
     * Requests focus for the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Opens the user guide link in the default browser.
     *
     * @param event click event from the link field
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
