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
    private TextField tagFilterField;
    @FXML
    private ComboBox<String> blockFilterComboBox;
    @FXML
    private ComboBox<String> yearFilterComboBox;
    @FXML
    private ComboBox<String> genderFilterComboBox;
    @FXML
    private ComboBox<String> sortComboBox;
    @FXML
    private FontIcon filterIcon;
    @FXML
    private FontIcon sortIcon;

    public FilterPanel() {
        super(FXML);

        // Initialize dummy values for ComboBoxes for UI demonstration
        blockFilterComboBox.getItems().addAll("Any", "A", "B", "C", "D", "E");
        blockFilterComboBox.getSelectionModel().selectFirst();

        yearFilterComboBox.getItems().addAll("Any", "1", "2", "3", "4", "5+");
        yearFilterComboBox.getSelectionModel().selectFirst();

        genderFilterComboBox.getItems().addAll("Any", "Male", "Female");
        genderFilterComboBox.getSelectionModel().selectFirst();

        sortComboBox.getItems().addAll("Name", "CCA Points (High to Low)", "Year (Seniority)");
        sortComboBox.getSelectionModel().selectFirst();
    }
}
