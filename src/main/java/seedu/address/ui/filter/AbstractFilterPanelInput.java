package seedu.address.ui.filter;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * Abstract base class for filter input sections in the {@code FilterPanel}.
 *
 * <p><b>Typical Usage:</b> Subclasses provide specialized input controls and implements their own
 * KeywordsChangedHandler to sync keywords across UI and Model for a certain prefix.
 */
public abstract class AbstractFilterPanelInput extends UiPart<Region> {
    private final List<String> currentKeywords;
    private final KeywordsChangedHandler onKeywordsChanged;

    @FXML
    private Label titleLabel;
    @FXML
    private FlowPane keywordsFlowPane;

    /**
     * Initializes a reusable filter input section with title, layout, and {@code onKeywordsChanged} callback.
     *
     * @param fxmlFilePath relative FXML file path (e.g., "FilterPanelComboBox.fxml")
     * @param title display title shown above the input (e.g., "Search by Gender")
     * @param onKeywordsChanged callback invoked when keywords list has changed, this callback must return a list of
     *                          validated keywords to display
     * @throws NullPointerException if title or onKeywordsChanged is null
     */
    protected AbstractFilterPanelInput(String fxmlFilePath, String title, KeywordsChangedHandler onKeywordsChanged) {
        super(fxmlFilePath);
        requireNonNull(title);
        requireNonNull(onKeywordsChanged);

        this.currentKeywords = new ArrayList<>();
        this.onKeywordsChanged = onKeywordsChanged;

        titleLabel.setText(title);
    }

    /**
     * Replaces all keywords displayed in this input and redraws the keyword tags.
     *
     * <p><b>Purpose:</b> Used to sync the GUI when keywords are updated externally through CLI commands.
     *
     * @param updatedKeywords new list of keywords to display (can be empty, duplicates/blanks removed internally)
     * @throws NullPointerException if updatedKeywords is null
     */
    public void setKeywords(List<String> updatedKeywords) {
        requireNonNull(updatedKeywords);
        applyValidatedKeywords(updatedKeywords);
    }

    /**
     * Attempts to add a new keyword from GUI. Keyword is added only if it passes validation checks and is approved
     * by the {@code onKeywordsChanged} callback.
     *
     * <p><b>Validation Rules:</b>
     * <ul>
     *   <li>Null values are rejected (user canceled input)</li>
     *   <li>Empty or whitespace-only strings are rejected</li>
     *   <li>Duplicates (already in currentKeywords) are rejected</li>
     * </ul>
     *
     * <p><b>Flow for keyword:</b>
     * <ol>
     *   <li>Trim whitespace from input</li>
     *   <li>Create proposed list with new keyword appended</li>
     *   <li>Send to {@code onKeywordsChanged} callback for validation in Logic and Model</li>
     *   <li>Logic and Model will return a list of validated keywords</li>
     *   <li>Clear input control for next entry</li>
     * </ol>
     *
     * @param keywordCandidate user input string, may be null or contain whitespace
     */
    protected final void tryAddKeyword(String keywordCandidate) {
        // Null, empty or duplicate keywords are not added
        if (keywordCandidate == null) {
            clearInputControl();
            return;
        }

        String trimmedKeyword = keywordCandidate.trim();
        if (trimmedKeyword.isEmpty() || currentKeywords.contains(trimmedKeyword)) {
            clearInputControl();
            return;
        }

        // Add the keyword candidate into proposedKeywords
        List<String> proposedKeywords = new ArrayList<>(currentKeywords);
        proposedKeywords.add(trimmedKeyword);

        // Push proposedKeywords through validation callback and apply the validated result
        List<String> validatedKeywords = onKeywordsChanged.handle(List.copyOf(proposedKeywords));
        applyValidatedKeywords(validatedKeywords);
    }

    /**
     * Resets the input control to a blank, ready state for the next keyword entry.
     *
     * <p><b>Purpose:</b> Called after a keyword is successfully added/removed, allowing
     * users to enter additional keywords immediately.
     *
     */
    protected abstract void clearInputControl();

    /**
     * Redraws the GUI field with the given list of validated keywords.
     */
    private void applyValidatedKeywords(List<String> validatedKeywords) {
        // Clear old keyword list and UI tags
        currentKeywords.clear();

        // Filter out whitespace/empty keywords and duplicates, then populate currentKeywords
        validatedKeywords.stream()
                .map(String::trim)
                .filter(keyword -> !keyword.isEmpty())
                .filter(keyword -> !currentKeywords.contains(keyword))
                .forEach(currentKeywords::add);

        // Clear old tags and create new FilterPanelTag UI elements for each keyword
        keywordsFlowPane.getChildren().clear();
        currentKeywords.forEach(keyword -> keywordsFlowPane.getChildren()
                .add(new FilterPanelTag(keyword, this::handleDeleteTag).getRoot()));

        // If there are no keywords left after validation, clear the input control to reset the UI state.
        // This is necessary to reset the prompt text in FilterPanelComboBox.
        if (currentKeywords.isEmpty()) {
            clearInputControl();
        }
    }

    /**
     * Handles deletion of a keyword tag when user clicks the delete button on a tag.
     *
     * <p><b>Process:</b>
     * <ol>
     *   <li>Create a proposed list with the tag removed</li>
     *   <li>If removal fails (keyword not found), exit silently</li>
     *   <li>Send proposed list to {@code onKeywordsChanged} callback for validation</li>
     *   <li>Apply the validated keywords (which should exclude the deleted tag)</li>
     *   <li>UI refreshes showing remaining tags</li>
     * </ol>
     *
     * @param tagToDelete the keyword text from the tag being deleted
     */
    private void handleDeleteTag(String tagToDelete) {
        List<String> proposedKeywords = new ArrayList<>(currentKeywords);
        if (!proposedKeywords.remove(tagToDelete)) {
            return;
        }

        List<String> validatedKeywords = onKeywordsChanged.handle(List.copyOf(proposedKeywords));
        applyValidatedKeywords(validatedKeywords);
    }
}


