package seedu.address.ui.tab;

import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/** Student profile component of the Student Details Tab. */
public class Profile extends UiPart<Region> {

    private static final String FXML = "Profile.fxml";

    public Profile() {
        super(FXML);
    }
}
