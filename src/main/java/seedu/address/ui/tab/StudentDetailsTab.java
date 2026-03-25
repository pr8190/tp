package seedu.address.ui.tab;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.person.Person;
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
    private final ObservableValue<Person> selectedPerson;
    @FXML
    private TabPane studentDetailsTabPane;

    @FXML
    private StackPane profilePlaceholder;

    @FXML
    private StackPane demeritRecordsPlaceholder;

    /**
     * Creates a {@code StudentDetailsTab} with the given {@code Logic}.
     */
    public StudentDetailsTab(ObservableValue<Person> selectedPerson) {
        super(FXML);
        this.selectedPerson = selectedPerson;
        fillInnerParts();
    }

    private void fillInnerParts() {
        Profile profile = new Profile(selectedPerson);
        profilePlaceholder.getChildren().add(profile.getRoot());

        DemeritRecords demeritRecords = new DemeritRecords();
        demeritRecordsPlaceholder.getChildren().add(demeritRecords.getRoot());
    }
}
