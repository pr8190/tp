package seedu.address.ui.tab;

import java.util.Comparator;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.ui.UiPart;

/** Student profile component of the Student Details Tab. */
public class Profile extends UiPart<Region> {

    private static final String FXML = "Profile.fxml";

    @FXML
    private FlowPane tags;

    @FXML
    private TextField nameField;

    @FXML
    private TextField studentIdField;

    @FXML
    private TextField roomField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField emergencyContactField;

    @FXML
    private TextField remarkField;

    /**
     * UI for the Profile Tab that is displayed on the left hand side of the main
     * @param selectedPerson The selected resident whose details are displayed
     */
    public Profile(ObservableValue<Person> selectedPerson) {
        super(FXML);
        selectedPerson.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            nameField.setText(newValue.getName().toString());
            studentIdField.setText(newValue.getStudentId().toString());
            roomField.setText(newValue.getRoomNumber().toString());
            emailField.setText(newValue.getEmail().toString());
            emergencyContactField.setText(newValue.getEmergencyContact().toString());
            remarkField.setText(newValue.getRemark().toString());
            tags.getChildren().clear();
            newValue.getTags().values().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        });
    }
}
