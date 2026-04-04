package seedu.address.ui;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FilterDetails;
import seedu.address.model.ReadOnlyFilterDetails;
import seedu.address.ui.executors.FilterExecutor;
import seedu.address.ui.filter.FilterPanelComboBox;
import seedu.address.ui.filter.FilterPanelField;

/**
 * Panel containing the list of filtering and sorting options.
 */
public class FilterPanel extends UiPart<Region> {
    private static final String FXML = "FilterPanel.fxml";
    private final ReadOnlyFilterDetails filterDetails;
    private final FilterExecutor filterExecutor;

    @FXML
    private StackPane nameFilterFieldPlaceholder;
    @FXML
    private StackPane phoneFilterFieldPlaceholder;
    @FXML
    private StackPane emailFilterFieldPlaceholder;
    @FXML
    private StackPane studentIdFilterFieldPlaceholder;
    @FXML
    private StackPane roomNumberFilterFieldPlaceholder;
    @FXML
    private StackPane majorFilterFieldPlaceholder;
    @FXML
    private StackPane emergencyContactFilterFieldPlaceholder;
    @FXML
    private StackPane yearFilterFieldPlaceholder;
    @FXML
    private StackPane genderFilterFieldPlaceholder;

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
     * Fills inner placeholders with reusable field components.
     */
    private void fillInnerParts() {
        bindTextField(nameFilterFieldPlaceholder, "Search by Name", "E.g: Alex",
                filterDetails.getNameKeywords(), FilterDetails::setNameKeywords);

        bindTextField(phoneFilterFieldPlaceholder, "Search by Phone", "E.g: +65 91234567",
                filterDetails.getPhoneNumberKeywords(), FilterDetails::setPhoneNumberKeywords);

        bindTextField(emailFilterFieldPlaceholder, "Search by Email", "E.g: alex@example.com",
                filterDetails.getEmailKeywords(), FilterDetails::setEmailKeywords);

        bindTextField(studentIdFilterFieldPlaceholder, "Search by Student ID", "E.g: A1234567X",
                filterDetails.getStudentIdKeywords(), FilterDetails::setStudentIdKeywords);

        bindTextField(roomNumberFilterFieldPlaceholder, "Search by Room Number", "E.g: 12A or 12",
                filterDetails.getRoomNumberKeywords(), FilterDetails::setRoomNumberKeywords);

        bindTextField(majorFilterFieldPlaceholder, "Search by Major", "E.g: Computer Science",
                filterDetails.getTagMajorKeywords(), FilterDetails::setTagMajorKeywords);

        bindTextField(emergencyContactFilterFieldPlaceholder, "Search by Emergency Contact", "E.g: +65 98765432",
                filterDetails.getEmergencyContactKeywords(), FilterDetails::setEmergencyContactKeywords);

        bindComboBoxField(yearFilterFieldPlaceholder, "Search by Year", "E.g: 1",
                List.of("1", "2", "3", "4", "5", "6"),
                filterDetails.getTagYearKeywords(), FilterDetails::setTagYearKeywords);

        bindComboBoxField(genderFilterFieldPlaceholder, "Search by Gender", "E.g: he/him",
                List.of("he/him", "she/her", "they/them"),
                filterDetails.getTagGenderKeywords(), FilterDetails::setTagGenderKeywords);
    }

    /**
     * Binds a Filter Panel text field to the keywords it is supposed to display
     *
     * <p>When users edit tags in the field, this method sets the {@link ReadOnlyFilterDetails} via
     * {@link #applyKeywordsAndExecuteFilter(KeywordSetter, ObservableSet, Set)}.
     *
     * <p>When {@code sourceKeywords} are changes from logic or model, the field UI is updated through a listener
     *
     * @param placeholder    target UI container
     * @param title          section label
     * @param promptText     placeholder text
     * @param sourceKeywords observable keyword set from {@link ReadOnlyFilterDetails} for this field.
     * @param keywordSetter  setter that writes updated keywords.
     */
    private void bindTextField(StackPane placeholder,
                               String title,
                               String promptText,
                               ObservableSet<String> sourceKeywords,
                               KeywordSetter keywordSetter) {
        FilterPanelField field = new FilterPanelField(
                title,
                promptText,
                // When the field updates, apply the change and execute filtering with the new keywords
                keywords -> applyKeywordsAndExecuteFilter(
                        keywordSetter,
                        sourceKeywords,
                        new LinkedHashSet<>(keywords)));

        field.setKeywords(List.copyOf(sourceKeywords));
        placeholder.getChildren().setAll(field.getRoot());

        // Listen for changes in the source keyword set and update the field UI accordingly
        sourceKeywords.addListener((SetChangeListener<? super String>) ignoredChange ->
                field.setKeywords(List.copyOf(sourceKeywords)));
    }

    /**
     * Binds a combo-box based filter field to the keywords it is supposed to display.
     *
     * <p>
     * When users edit tags in the field, this method sets the {@link ReadOnlyFilterDetails} via
     * {@link #applyKeywordsAndExecuteFilter(KeywordSetter, ObservableSet, Set
     * </p>
     */
    private void bindComboBoxField(StackPane placeholder,
                                   String title,
                                   String promptText,
                                   List<String> options,
                                   ObservableSet<String> sourceKeywords,
                                   KeywordSetter keywordSetter) {
        FilterPanelComboBox field = new FilterPanelComboBox(
                title,
                promptText,
                options,
                keywords -> applyKeywordsAndExecuteFilter(
                        keywordSetter,
                        sourceKeywords,
                        new LinkedHashSet<>(keywords)));

        field.setKeywords(List.copyOf(sourceKeywords));
        placeholder.getChildren().setAll(field.getRoot());

        sourceKeywords.addListener((SetChangeListener<? super String>) ignoredChange ->
                field.setKeywords(List.copyOf(sourceKeywords)));
    }

    /**
     * Applies one field type update to a fresh {@link FilterDetails} copy then executes filtering.
     *
     * @param keywordSetter   method that sets old Filter Details keywords to new ones
     * @param updatedKeywords updated keywords from UI
     *
     */
    private List<String> applyKeywordsAndExecuteFilter(
            KeywordSetter keywordSetter,
            ObservableSet<String> sourceKeywords,
            Set<String> updatedKeywords) {
        FilterDetails newFilterDetails = new FilterDetails(filterDetails);
        keywordSetter.set(newFilterDetails, updatedKeywords);

        try {
            filterExecutor.execute(newFilterDetails);
        } catch (CommandException e) {
            // Rebuild UI from last accepted values.
            return List.copyOf(sourceKeywords);
        }

        return List.copyOf(sourceKeywords);
    }

    /**
     * Functional interface for setting a specific keyword set in a {@link FilterDetails} instance.
     */
    @FunctionalInterface
    private interface KeywordSetter {
        void set(FilterDetails details, Set<String> keywords);
    }
}
