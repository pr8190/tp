package seedu.address.ui.filter;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * Reusable filter field in the {@code FilterPanel}.
 */
public class FilterPanelField extends UiPart<Region> {
    private static final String FXML = "FilterPanelField.fxml";

    private final List<String> currentKeywords;
    private final KeywordsChangedHandler onKeywordsChanged;

    @FXML
    private Label titleLabel;
    @FXML
    private TextField keywordInputField;
    @FXML
    private Label keywordsLabel;
    @FXML
    private FlowPane keywordsFlowPane;

    /**
     * Creates a reusable filter field section.
     *
     * @param title The title of this filter field, e.g. "Search by Name".
     * @param promptText The prompt text to show in the keyword input field, e.g. "E.g: Alex".
     * @param onKeywordsChanged The method that runs when the keywords are updated from the UI are. The
     *                          implementation of this method is defined in {@code FilterPanel}.
     */
    public FilterPanelField(String title, String promptText, KeywordsChangedHandler onKeywordsChanged) {
        super(FXML);
        requireNonNull(title);
        requireNonNull(promptText);
        requireNonNull(onKeywordsChanged);

        this.onKeywordsChanged = onKeywordsChanged;
        this.currentKeywords = new ArrayList<>();

        titleLabel.setText(title);
        keywordInputField.setPromptText(promptText);
    }

    /**
     * Replaces the current list of keywords and redraws this field's FlowPane tags.
     *
     * @param updatedKeywords The new list of keywords to set for this field.
     */
    public void setKeywords(List<String> updatedKeywords) {
        requireNonNull(updatedKeywords);
        applyValidatedKeywords(updatedKeywords);
    }

    /**
     * Handler for when the user presses the Enter key in the keyword input field.
     *
     * It adds the keyword, re-render the FlowPane tags, trigger the
     * {@code onKeywordsChanged} event handler, and finally clears the keyword input field.
     */
    @FXML
    private void handleFieldEntered() {
        String keyword = keywordInputField.getText();
        requireNonNull(keyword);

        String trimmedKeyword = keyword.trim();

        if (trimmedKeyword.isEmpty() || currentKeywords.contains(trimmedKeyword)) {
            keywordInputField.clear();
            return;
        }

        // add user input to proposed keywords
        List<String> proposedKeywords = new ArrayList<>(currentKeywords);
        proposedKeywords.add(trimmedKeyword);

        // validate user input with #onKeywordsChanged
        List<String> validatedKeywords = onKeywordsChanged.handle(proposedKeywords);
        applyValidatedKeywords(validatedKeywords);

        keywordInputField.clear();
    }

    // Replaces the current keywords with the validated keywords, then redraws the FlowPane tags
    private void applyValidatedKeywords(List<String> validatedKeywords) {
        currentKeywords.clear();

        validatedKeywords.stream()
                .map(String::trim)
                .filter(keyword -> !keyword.isEmpty()) // remove empty keywords
                .filter(keyword -> !currentKeywords.contains(keyword)) // remove duplicated keywords
                .forEach(keyword -> currentKeywords.add(keyword)); // add to currentKeywords

        // update ui
        keywordsFlowPane.getChildren().clear();
        currentKeywords.forEach(keyword -> keywordsFlowPane.getChildren()
                .add(new FilterPanelTag(keyword, this::handleDeleteTag).getRoot()));
    }

    // Remove tagToDelete from the keyword list
    private void handleDeleteTag(String tagToDelete) {
        List<String> proposedKeywords = new ArrayList<>(currentKeywords);
        if (!proposedKeywords.remove(tagToDelete)) {
            return;
        }
        List<String> validatedKeywords = onKeywordsChanged.handle(List.copyOf(proposedKeywords));
        applyValidatedKeywords(validatedKeywords);
    }

    /**
     * Handler for when the keywords in this field are edited.
     */
    @FunctionalInterface
    public interface KeywordsChangedHandler {
        List<String> handle(List<String> keywords);
    }
}
