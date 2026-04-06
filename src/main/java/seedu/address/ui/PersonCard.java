package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
    private TextField name;
    @FXML
    private Label id;
    @FXML
    private TextField phone;
    @FXML
    private TextField studentId;
    @FXML
    private TextField email;
    @FXML
    private TextField roomNumber;
    @FXML
    private TextField emergencyContact;
    @FXML
    private TextField demeritPoints;
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

        handleClickOnTextField();

        person.getTags().values().stream()
                .sorted(Comparator.comparing(tag -> tag.tagContent))
                .forEach(tag -> tags.getChildren().add(
                        new Label(TagFormatter.formatTagValue(tag))
                ));
    }

    /**
     * Handles the click event on the text fields to make it a click on the card pane instead
     */
    private void handleClickOnTextField() {
        phone.addEventFilter(MouseEvent.MOUSE_PRESSED,
                event -> cardPane.fireEvent(event.copyFor(cardPane, cardPane)));
        studentId.addEventFilter(MouseEvent.MOUSE_PRESSED,
                event -> cardPane.fireEvent(event.copyFor(cardPane, cardPane)));
        email.addEventFilter(MouseEvent.MOUSE_PRESSED,
                event -> cardPane.fireEvent(event.copyFor(cardPane, cardPane)));
        roomNumber.addEventFilter(MouseEvent.MOUSE_PRESSED,
                event -> cardPane.fireEvent(event.copyFor(cardPane, cardPane)));
        emergencyContact.addEventFilter(MouseEvent.MOUSE_PRESSED,
                event -> cardPane.fireEvent(event.copyFor(cardPane, cardPane)));
        demeritPoints.addEventFilter(MouseEvent.MOUSE_PRESSED,
                event -> cardPane.fireEvent(event.copyFor(cardPane, cardPane)));
    }
}
