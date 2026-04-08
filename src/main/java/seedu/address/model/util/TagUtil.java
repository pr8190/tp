package seedu.address.model.util;

import java.util.HashMap;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagType;

/**
 * Utility class to help with building tag sets.
 */
public class TagUtil {
    /**
     * Builds a HashMap of TagType to Tag from an array of pairs, where each pair consists of a tag type (as a string)
     * and tag content (as a string).
     * @param tags an array of pairs
     * @return a HashMap mapping each TagType to its corresponding Tag
     */
    public static HashMap<TagType, Tag> getTagSet(Object[]... tags) {
        HashMap<TagType, Tag> tagMap = new HashMap<>();
        for (Object[] pair : tags) {
            TagType type = TagType.valueOf(pair[0].toString());
            String tagName = pair[1].toString();
            tagMap.put(type, new Tag(type, tagName));
        }
        return tagMap;
    }
}
