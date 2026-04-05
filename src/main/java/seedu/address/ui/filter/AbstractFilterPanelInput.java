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
 * Base class for reusable filter inputs used in the {@code FilterPanel}.
 */
public abstract class AbstractFilterPanelInput extends UiPart<Region> {
    private final List<String> currentKeywords;
    private final KeywordsChangedHandler onKeywordsChanged;

    @FXML
    private Label titleLabel;
    @FXML
    private FlowPane keywordsFlowPane;

    /**
     * Creates a reusable filter input section.
     *
     * @param fxmlFilePath Relative FXML path for this input.
     * @param title Title shown above this input.
     * @param onKeywordsChanged Callback invoked when keywords are edited from the UI.
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
     * Replaces the current list of keywords and redraws this input's FlowPane tags.
     *
     * @param updatedKeywords New list of keywords to set for this input.
     */
    public void setKeywords(List<String> updatedKeywords) {
        requireNonNull(updatedKeywords);
        applyValidatedKeywords(updatedKeywords);
    }

    /**
     * Attempts to append a keyword and push the updated value through validation callback.
     */
    protected final void tryAddKeyword(String keywordCandidate) {
        if (keywordCandidate == null) {
            clearInputControl();
            return;
        }

        String trimmedKeyword = keywordCandidate.trim();
        if (trimmedKeyword.isEmpty() || currentKeywords.contains(trimmedKeyword)) {
            clearInputControl();
            return;
        }

        List<String> proposedKeywords = new ArrayList<>(currentKeywords);
        proposedKeywords.add(trimmedKeyword);

        List<String> validatedKeywords = onKeywordsChanged.handle(List.copyOf(proposedKeywords));
        applyValidatedKeywords(validatedKeywords);
        clearInputControl();
    }

    /**
     * Clears the concrete input control after handling user input.
     */
    protected abstract void clearInputControl();

    private void applyValidatedKeywords(List<String> validatedKeywords) {
        currentKeywords.clear();

        validatedKeywords.stream()
                .map(String::trim)
                .filter(keyword -> !keyword.isEmpty())
                .filter(keyword -> !currentKeywords.contains(keyword))
                .forEach(currentKeywords::add);

        keywordsFlowPane.getChildren().clear();
        currentKeywords.forEach(keyword -> keywordsFlowPane.getChildren()
                .add(new FilterPanelTag(keyword, this::handleDeleteTag).getRoot()));
    }

    private void handleDeleteTag(String tagToDelete) {
        List<String> proposedKeywords = new ArrayList<>(currentKeywords);
        if (!proposedKeywords.remove(tagToDelete)) {
            return;
        }

        List<String> validatedKeywords = onKeywordsChanged.handle(List.copyOf(proposedKeywords));
        applyValidatedKeywords(validatedKeywords);
    }

    /**
     * Handler for when the keywords in this input are edited.
     */
    @FunctionalInterface
    public interface KeywordsChangedHandler {
        List<String> handle(List<String> keywords);
    }
}

