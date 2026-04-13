package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

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

    public static final String MESSAGE_CONSTRAINTS = "Gender tags can be: she/her, he/him or they/them."
            + "\nYear tags should be a positive integer between 1 and 6 inclusive."
            + "\nMajor tags should be less than 100 characters long and may contain"
            + " alphabetic characters, ampersands (&) and commas (,).";

    private final String tagContent;
    private final TagType tagType;

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

        this.tagContent = tagContent;
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
        return type.isValidTagContent(test);
    }

    public String getTagContent() {
        return tagContent;
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
        return tagType == otherTag.tagType && tagContent.equals(otherTag.tagContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagType, tagContent);
    }

    @Override
    public String toString() {
        return '[' + tagContent + ']';
    }

}
