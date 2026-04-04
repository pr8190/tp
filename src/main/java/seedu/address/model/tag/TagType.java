package seedu.address.model.tag;

/**
 * Represents the category of a {@link Tag} in the hall ledger.
 *
 * <p>Each {@code TagType} defines its own validation regex that determines what
 * content is considered valid for tags of that type.
 *
 * <p>Guarantees: immutable; each constant's validation regex is fixed at compile time.
 */
public enum TagType {

    /**
     * Tag representing the resident's year of study.
     * Valid content is a single digit from {@code 1} to {@code 6} inclusive.
     */
    YEAR("^[1-6]$"),

    /**
     * Tag representing the resident's academic major.
     * Valid content is an alphanumeric string; internal spaces between words are permitted,
     * but leading and trailing spaces are not.
     */
    MAJOR("[\\p{Alnum} ]*[\\p{Alnum}]+[\\p{Alnum} ]*"),

    /**
     * Tag representing the resident's gender pronouns.
     * Valid content is one of {@code she/her}, {@code he/him}, or {@code they/them}.
     * Input is normalised to lowercase before validation — see {@link Tag#getNormalisedTagContent}.
     */
    GENDER("^(she/her|he/him|they/them)$");

    private final String validationRegex;

    TagType(String validationRegex) {
        this.validationRegex = validationRegex;
    }

    /**
     * Checks if the given tag content matches the specified tag type's validation regex.
     *
     * @param tagContent the tag content to validate, or null if no tag is present.
     * @return true if {@code tagContent} is null or matches the validation regex, false otherwise.
     */
    public boolean isValidTagContent(String tagContent) {
        if (tagContent == null) {
            return true;
        }
        return tagContent.matches(validationRegex);
    }
}
