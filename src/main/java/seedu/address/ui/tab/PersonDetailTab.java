package seedu.address.ui.tab;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.ui.UiPart;

/**
 * A ui for the main tab that is displayed on the main window.
 */
public class PersonDetailTab extends UiPart<Region> {

    private static final String FXML = "PersonDetailTab.fxml";
    /**
     * The person whose details would be shown in the directory tab
     **/
    // TODO: Implement selectedPerson logic
    //  private final ObjectProperty<Optional<Person>> selectedPerson;
    @FXML
    private TabPane detailsTabPane;

    @FXML
    private StackPane profilePlaceholder;

    @FXML
    private StackPane ccaRecordsPlaceholder;

    @FXML
    private StackPane demeritRecordsPlaceholder;

    public PersonDetailTab() {
        super(FXML);
        fillInnerParts();
    }

    private void fillInnerParts() {
    }
}
