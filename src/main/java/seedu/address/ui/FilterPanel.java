package seedu.address.ui;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of filtering and sorting options.
 */
public class FilterPanel extends UiPart<Region> {
    private static final String FXML = "FilterPanel.fxml";

    @FXML
    private TextField nameFilterField;
    @FXML
    private TextField phoneNumberFilterField;
    @FXML
    private TextField emailFilterField;
    @FXML
    private TextField studentIdFilterField;
    @FXML
    private TextField roomNumberFilterField;
    @FXML
    private TextField majorFilterField;
    @FXML
    private TextField emergencyContactFilterField;
    @FXML
    private ComboBox<String> floorFilterComboBox;
    @FXML
    private ComboBox<String> yearFilterComboBox;
    @FXML
    private ComboBox<String> genderFilterComboBox;
    @FXML
    private ComboBox<String> sortByComboBox;
    @FXML
    private FontIcon filterIcon;
    @FXML
    private FontIcon sortIcon;

    /**
     * Creates a {@code FilterPanel} with default filter and sort options.
     */
    public FilterPanel() {
        super(FXML);
        // Initialize dummy values for ComboBoxes for UI demonstration
        floorFilterComboBox.getItems().addAll("Any", "1", "2", "3", "4", "5");
        floorFilterComboBox.getSelectionModel().selectFirst();

        yearFilterComboBox.getItems().addAll("Any", "1", "2", "3", "4", "5+");
        yearFilterComboBox.getSelectionModel().selectFirst();

        genderFilterComboBox.getItems().addAll("Any", "Male", "Female");
        genderFilterComboBox.getSelectionModel().selectFirst();

        sortByComboBox.getItems().addAll("None", "Name", "Phone", "Email", "Student ID", "Room number", "Major",
                "Emergency Contact", "Floor", "Year", "Gender");
        sortByComboBox.getSelectionModel().selectFirst();

        sortByComboBox.getItems().addAll("Ascending", "Descending");
        sortByComboBox.getSelectionModel().selectFirst();

        nameFilterField.setOnKeyPressed();
    }
}
