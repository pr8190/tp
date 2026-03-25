package seedu.address.ui;

import java.util.Set;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FilterDetails;
import seedu.address.model.ReadOnlyFilterDetails;
import seedu.address.ui.executors.FilterExecutor;

/**
 * Panel containing the list of filtering and sorting options.
 */
public class FilterPanel extends UiPart<Region> {
    private static final String FXML = "FilterPanel.fxml";
    private final ReadOnlyFilterDetails filterDetails;
    private final FilterExecutor filterExecutor;

    @FXML
    private TextField nameFilterField;
    @FXML
    private FlowPane nameTags;
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
    private ComboBox<String> sortOrderComboBox;
    @FXML
    private FontIcon filterIcon;
    @FXML
    private FontIcon sortIcon;

    /**
     * Creates a {@code FilterPanel} with the given {@code ReadOnlyFilterDetails}.
     */
    public FilterPanel(ReadOnlyFilterDetails filterDetails, FilterExecutor filterExecutor) {
        super(FXML);
        this.filterDetails = filterDetails;
        this.filterExecutor = filterExecutor;
        fillInnerParts();
    }

    /**
    * Fills the inner parts of the FilterPanel, such as setting up event handlers for the filter fields and
    */
    private void fillInnerParts() {
        // Keep name tags synchronized with the observable keyword set.
        filterDetails.getNameKeywords()
                .addListener((SetChangeListener<? super String>) change -> renderNameKeywordTags(filterDetails));
        renderNameKeywordTags(filterDetails);

        // TODO: Remove UI mockup entirely once we're ready
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

        sortOrderComboBox.getItems().addAll("Ascending", "Descending");
        sortOrderComboBox.getSelectionModel().selectFirst();
    }


    // TODO: Refactor this method to be more generic and reusable for other filter fields in the future.
    // Currently, this method is specific to listening to changes from FilterDetails to render updated keyword tags.
    private void renderNameKeywordTags(ReadOnlyFilterDetails details) {
        nameTags.getChildren().clear();
        if (details == null) {
            return;
        }
        Set<String> nameKeywordTags = details.getNameKeywords();
        nameKeywordTags.forEach(tag -> nameTags.getChildren().add(new Label(tag)));
    }

    /*
    * Handles the event when the user presses 'Enter' in the name filter field.
    * Splits the input into individual keywords and displays them as tags in the UI.
     */
    @FXML
    private void handleNameFieldEntered() {
        // Get the input text and split it into keywords
        String nameFilterText = nameFilterField.getText();
        if (nameFilterText.trim().isEmpty()) {
            return;
        }
        Set<String> nameFilterKeywordsSet = StringUtil.splitSentenceIntoWords(nameFilterText);

        // Copy current filters first so updating name does not reset the other criteria.
        FilterDetails newFilterDetails = new FilterDetails(filterDetails);
        newFilterDetails.setNameKeywords(nameFilterKeywordsSet);

        try {
            filterExecutor.execute(newFilterDetails);
        } catch (CommandException e) {
            // No-op: MainWindow#executeCommand will handle displaying the error message to the user.
        }

        nameFilterField.clear();
    }
}
