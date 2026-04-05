package seedu.address.ui.filter;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Reusable filter field in the {@code FilterPanel}.
 */
public class FilterPanelField extends AbstractFilterPanelInput {
    private static final String FXML = "FilterPanelField.fxml";

    @FXML
    private TextField keywordInputField;

    /**
     * Creates a text-based filter input section.
     *
     * @param title Section title, e.g. "Search by Name".
     * @param promptText Prompt text shown in the input field.
     * @param onKeywordsChanged Callback invoked when keywords are edited from the UI.
     */
    public FilterPanelField(String title, String promptText, KeywordsChangedHandler onKeywordsChanged) {
        super(FXML, title, onKeywordsChanged);
        requireNonNull(promptText);

        keywordInputField.setPromptText(promptText);
    }

    // Handles user input from the text field, adds the keyword and clears the field for the next input.
    @FXML
    private void handleFieldEntered() {
        tryAddKeyword(keywordInputField.getText());
    }

    // Tries to add the given keyword to the list, if valid. Clears the input field regardless of success.
    @Override
    protected void clearInputControl() {
        keywordInputField.clear();
    }

}
