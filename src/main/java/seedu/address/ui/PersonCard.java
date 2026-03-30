package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.ui.util.TagFormatter;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label studentId;
    @FXML
    private Label email;
    @FXML
    private Label roomNumber;
    @FXML
    private Label emergencyContact;
    @FXML
    private Label demeritPoints;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        studentId.setText(person.getStudentId().value);
        email.setText(person.getEmail().value);
        roomNumber.setText(person.getRoomNumber().value);
        emergencyContact.setText(person.getEmergencyContact().value);
        demeritPoints.setText(String.valueOf(person.getTotalDemeritPoints()));

        person.getTags().values().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(
                        new Label(TagFormatter.formatTagValue(tag))
                ));
    }
}
