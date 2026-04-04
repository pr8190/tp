package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the hall ledger.
 *
 * <p>Each tag has a {@link TagType} that determines what content is valid for it.
 * Supported tag types include gender pronouns ({@link TagType#GENDER}),
 * academic major ({@link TagType#MAJOR}), and year of study ({@link TagType#YEAR}).
 *
 * <p>Gender tags are case-insensitive and are normalised to lowercase upon construction.
 *
 * <p>Guarantees: immutable; tag content is validated against its {@link TagType} upon construction.
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric. "
            + "\nGender tags can be: she/her, he/him or they/them"
            + "\nYear tags should be a positive integer between 1 and 6 inclusive.";

    public final String tagName;
    public final TagType tagType;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagType The type of the tag.
     * @param tagContent valid tag content.
     */
    public Tag(TagType tagType, String tagContent) {
        requireNonNull(tagType);
        requireNonNull(tagContent);

        checkArgument(isValidTagContent(tagContent, tagType), MESSAGE_CONSTRAINTS);

        this.tagName = getNormalisedTagContent(tagContent, tagType);
        this.tagType = tagType;
    }

    /**
     * Checks if the given string is valid content for the specified {@link TagType}.
     *
     * <p>The content is normalised (e.g., lowercased for gender tags) before validation
     *
     * @param test the tag content string to validate;
     * @param type the {@link TagType} whose rules the content is validated against;
     * @return {@code true} if {@code test} is valid for {@code type}, {@code false} otherwise
     */
    public static boolean isValidTagContent(String test, TagType type) {
        requireNonNull(type);
        return type.isValidTagContent(getNormalisedTagContent(test, type));
    }

    /**
     * Returns the normalised form of the given {@link String} for the specified {@link TagType}.
     */
    public static String getNormalisedTagContent(String test, TagType type) {
        // check for gender as it is the only case-insensitive tag type
        return type == TagType.GENDER ? test.toLowerCase() : test;
    }

    public String getTagName() {
        return tagName;
    }

    public TagType getTagType() {
        return tagType;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Tag otherTag)) {
            return false;
        }
        return tagType == otherTag.tagType && tagName.equals(otherTag.tagName);
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    @Override
    public String toString() {
        return '[' + tagName + ']';
    }

}
