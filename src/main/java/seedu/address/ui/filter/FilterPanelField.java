package seedu.address.ui.filter;

import static java.util.Objects.requireNonNull;

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
        this.keywords = new ArrayList<>();
		titleLabel.setText(title);
		keywordInputField.setPromptText(promptText);
	}

	/**
	 * Replaces currently displayed tags with the provided keywords.
	 */
	public void setKeywords(List<String> keywords) {
		requireNonNull(keywords);
		renderKeywords(keywords);
	}

	@FXML
	private void handleFieldEntered() {
		String keyword = keywordInputField.getText();
        String trimmedKeyword = keyword.trim();

        if (trimmedKeyword == null || trimmedKeyword.isEmpty()) {
			return;
		}

        List<String> listOfKeywords = onKeywordSubmitted.handle(trimmedKeyword);
        renderKeywords(listOfKeywords);
		keywordInputField.clear();
	}

	private void renderKeywords(List<String> keywords) {
		keywordsFlowPane.getChildren().clear();
		keywords.forEach(keyword -> keywordsFlowPane.getChildren().add(new FilterPanelTag(keyword).getRoot()));
	}

    @FunctionalInterface
    public interface KeywordSubmittedHandler {
        List<String> handle(String keyword);
    }
}
