package seedu.address.ui.filter;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

/**
 * Reusable filter input backed by a non-editable {@link ComboBox}.
 */
public class FilterPanelComboBox extends AbstractFilterPanelInput {
    private static final String FXML = "FilterPanelComboBox.fxml";

    @FXML
    private ComboBox<String> keywordComboBox;

    /**
     * Creates a reusable combo-box filter field section.
     *
     * @param title             The title of this filter field, e.g. "Search by Gender".
     * @param promptText        The prompt text to show in the combo box editor.
     * @param choices           The list of selectable suggestions shown in the combo box.
     * @param onKeywordsChanged The callback that validates and applies edited keywords.
     */
    public FilterPanelComboBox(String title, String promptText, List<String> choices,
                               KeywordsChangedHandler onKeywordsChanged) {
        super(FXML, title, onKeywordsChanged);
        requireNonNull(promptText);
        requireNonNull(choices);

        keywordComboBox.setPromptText(promptText);
        keywordComboBox.getItems().setAll(choices);
    }

    @FXML
    private void handleChoiceSelected() {
        tryAddKeyword(keywordComboBox.getValue());
    }

    @Override
    protected void clearInputControl() {
        keywordComboBox.getSelectionModel().clearSelection();
    }
}

