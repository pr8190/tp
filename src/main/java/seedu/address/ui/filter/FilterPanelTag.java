package seedu.address.ui.filter;

import static java.util.Objects.requireNonNull;

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
    private final TagDeleteHandler onTagDelete;

    @FXML
    private Label label;

    /**
     * Creates a {@code FilterPanelTag} with the given tag name and delete callback.
     *
     * @param tagLabel    The label to display on this tag.
     * @param onTagDelete The callback to execute when the delete button on this tag is clicked. The callback will be
     *                    passed the tag label of this tag.
     */
    public FilterPanelTag(String tagLabel, TagDeleteHandler onTagDelete) {
        super(FXML);
        this.tagLabel = requireNonNull(tagLabel);
        this.onTagDelete = requireNonNull(onTagDelete);
        setTagLabel(tagLabel);
    }

    private void setTagLabel(String tagLabel) {
        label.setText(tagLabel);
    }

    @FXML
    private void handleDeleteTag(MouseEvent e) {
        onTagDelete.handle(tagLabel);
    }

    /**
     * Functional interface for handling the deletion of a tag.
     *
     * The interface's implementation is done in the {@link FilterPanelField}, and the callback is passed to this
     * {@code FilterPanelTag} to be executed when the 'x' button is clicked.
     */
    @FunctionalInterface
    public interface TagDeleteHandler {
        void handle(String tagToDelete);
    }
}
