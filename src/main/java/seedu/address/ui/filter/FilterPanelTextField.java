package seedu.address.ui.filter;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Reusable filter input backed by a {@link TextField}.
 */
public class FilterPanelTextField extends AbstractFilterPanelInput {
    private static final String FXML = "FilterPanelTextField.fxml";

    @FXML
    private TextField keywordInputField;

    /**
     * Creates a text-based filter input section.
     *
     * @param title Section title, e.g. "Search by Name".
     * @param promptText Prompt text shown in the input field.
     * @param onKeywordsChanged Callback invoked when keywords are edited from the UI.
     */
    public FilterPanelTextField(String title, String promptText, KeywordsChangedHandler onKeywordsChanged) {
        super(FXML, title, onKeywordsChanged);
        requireNonNull(promptText);

        keywordInputField.setPromptText(promptText);
    }

    @FXML
    private void handleFieldEntered() {
        tryAddKeyword(keywordInputField.getText());
    }

    @Override
    protected void clearInputControl() {
        keywordInputField.clear();
    }
}

