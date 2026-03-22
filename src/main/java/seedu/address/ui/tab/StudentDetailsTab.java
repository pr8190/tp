package seedu.address.ui.tab;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.ui.UiPart;

/**
 * A ui for the main tab that is displayed on the main window.
 */
public class StudentDetailsTab extends UiPart<Region> {

    private static final String FXML = "StudentDetailsTab.fxml";
    /**
     * The person whose details would be shown in the directory tab
     **/
    // TODO: Implement selectedPerson logic
    //  private final ObjectProperty<Optional<Person>> selectedPerson;
    @FXML
    private TabPane studentDetailsTabPane;

    @FXML
    private StackPane profilePlaceholder;

    @FXML
    private StackPane ccaRecordsPlaceholder;

    @FXML
    private StackPane demeritRecordsPlaceholder;

    /**
     * Creates a {@code StudentDetailsTab} with the given {@code Logic}.
     */
    public StudentDetailsTab() {
        super(FXML);
        fillInnerParts();
    }

    private void fillInnerParts() {
        Profile profile = new Profile();
        profilePlaceholder.getChildren().add(profile.getRoot());

        DemeritRecords demeritRecords = new DemeritRecords();
        demeritRecordsPlaceholder.getChildren().add(demeritRecords.getRoot());
    }
}
