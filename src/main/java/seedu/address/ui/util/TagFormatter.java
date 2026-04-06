package seedu.address.ui.util;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagType;

/**
 * Utility class for formatting tag values for display in the UI.
 */
public class TagFormatter {
    private static final String YEAR_PREFIX = "Y";

    /**
     * Formats the tag value for display. For year tags, it adds a "Y" prefix.
     *
     * @param tag The value of the tag.
     * @return The formatted tag value for display.
     */
    public static String formatTagValue(Tag tag) {
        if (tag.getTagType() == TagType.YEAR) {
            return YEAR_PREFIX + tag.getTagContent();
        }
        return tag.getTagContent();
    }
}
