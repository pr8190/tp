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

    private final List<String> keywords;
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
	 */
    public FilterPanelField(String title, String promptText, KeywordsChangedHandler onKeywordsChanged) {
		super(FXML);
		requireNonNull(title);
		requireNonNull(promptText);
        this.onKeywordsChanged = requireNonNull(onKeywordsChanged);
        this.keywords = List.of();
		titleLabel.setText(title);
		keywordInputField.setPromptText(promptText);
	}

	/**
     * Replaces the current list of keywords and redraws this field's FlowPane tags.
	 */
    public void setKeywords(List<String> updatedKeywords) {
        requireNonNull(updatedKeywords);
        keywords.clear();
        updatedKeywords.stream()
                .map(keyword -> keyword.trim())
                .forEach(this::addKeywordIfAbsent);
        renderKeywords();
	}

	@FXML
	private void handleFieldEntered() {
		String keyword = keywordInputField.getText();
        String trimmedKeyword = keyword.trim();

        if (trimmedKeyword == null || trimmedKeyword.isEmpty()) {
			return;
		}

        addKeywordIfAbsent(trimmedKeyword);
        renderKeywords();
        onKeywordsChanged.handle(List.copyOf(keywords));
		keywordInputField.clear();
	}

    private void addKeywordIfAbsent(String keyword) {
        if (!keywords.contains(keyword)) {
            keywords.add(keyword);
        }
    }

    private void renderKeywords() {
		keywordsFlowPane.getChildren().clear();
        keywords.forEach(keyword -> keywordsFlowPane.getChildren()
                .add(new FilterPanelTag(keyword, this::handleDeleteTag).getRoot()));
    }

    private void handleDeleteTag(String tagToDelete) {
        if (!keywords.remove(tagToDelete)) {
            return;
        }

        renderKeywords();
        onKeywordsChanged.handle(List.copyOf(keywords));
	}

    @FunctionalInterface
    public interface KeywordsChangedHandler {
        void handle(List<String> keywords);
    }
}
