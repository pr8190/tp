package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;
import seedu.address.ui.tab.StudentDetailsTab;

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
    private Tab studentDetailsTab;

    @FXML
    private StackPane studentDetailsTabPlaceholder;

    @FXML
    private StackPane dashboardTabPlaceholder;

    @FXML
    private StackPane settingsTabPlaceholder;

    /**
     * Creates a {@code TabSection} with the given {@code Logic}.
     *
     * @param logic
     */
    public TabSection(Logic logic) {
        super(FXML);
        this.logic = logic;
        fillInnerParts();
    }

    private void fillInnerParts() {
        StudentDetailsTab studentDetailsTab = new StudentDetailsTab();
        studentDetailsTabPlaceholder.getChildren().add(studentDetailsTab.getRoot());
    }
}
