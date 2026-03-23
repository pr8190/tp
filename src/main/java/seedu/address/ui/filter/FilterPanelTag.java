package seedu.address.ui.filter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * A UI component that displays a tag in the filter panel.
 */
public class FilterPanelTag extends UiPart<Region> {
    private static final String FXML = "FilterPanelTag.fxml";

    private final String tagLabel;

    @FXML
    private Label label;

    /**
     * Creates a {@code FilterPanelTag} with the given tag name.
     *
     * @param tagLabel The name of the tag to be displayed on the filter panel.
     */
    public FilterPanelTag(String tagLabel) {
        super(FXML);
        this.tagLabel = tagLabel;
        setTagLabel(tagLabel);
    }

    private void setTagLabel(String tagLabel) {
        label.setText(tagLabel);
    }

    @FXML
    private void handleDeleteTag(MouseEvent e) {
        // TODO: wire up delete behaviour when available
    }
}
