package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric. "
            + "Gender tags may contain '/' eg: she/her";

    public final String tagName;
    public final TagType tagType;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagType The type of the tag.
     * @param tagName A valid tag name.
     */
    public Tag(TagType tagType, String tagName) {
        requireNonNull(tagName);
        requireNonNull(tagName);

        String trimmedName = tagName.trim();
        checkArgument(isValidTagName(trimmedName, tagType), MESSAGE_CONSTRAINTS);

        this.tagName = trimmedName;
        this.tagType = tagType;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test, TagType type) {
        requireNonNull(type);
        return type.isValidTagName(test);
    }

    /**
     * @return the tag name of this tag.
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * @return the tag type of this tag.
     */
    public TagType getTagType() {
        return tagType;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Tag)) {
            return false;
        }
        Tag otherTag = (Tag) other;
        return tagType == otherTag.tagType && tagName.equals(otherTag.tagName);
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
