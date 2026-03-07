package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;
import seedu.address.ui.tab.PersonDetailTab;

/**
 * UI for the TabSection that is displayed on the left hand side of the main
 * window.
 */
public class TabSection extends UiPart<Region> {

    private static final String FXML = "TabSection.fxml";

    private final Logic logic;

    @FXML
    private TabPane tabPaneRoot;

    @FXML
    private Tab personDetailTab;

    @FXML
    private StackPane directoryTabPlaceholder;

    @FXML
    private StackPane dashboardTabPlaceholder;

    @FXML
    private StackPane settingsTabPlaceholder;

    public TabSection(Logic logic) {
        super(FXML);
        this.logic = logic;
        fillInnerParts();
    }

    private void fillInnerParts() {
        PersonDetailTab personDetailTab = new PersonDetailTab();
        directoryTabPlaceholder.getChildren().add(personDetailTab.getRoot());
    }
}
