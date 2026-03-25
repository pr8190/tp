package seedu.address.model.tag;

/**
 * Represents the type of a Tag in the address book.
 * Guarantees: immutable; name is valid
 */
public enum TagType {
    YEAR(1, "[\\p{Alnum} ]*[\\p{Alnum}]+[\\p{Alnum} ]*"),
    MAJOR(1, "[\\p{Alnum} ]*[\\p{Alnum}]+[\\p{Alnum} ]*"),
    GENDER(1, "[\\p{Alnum}/][\\p{Alnum}/ ]*");

    private final int maxTagsPerType;
    private final String validationRegex;

    TagType(int maxTagsPerType, String validationRegex) {
        this.maxTagsPerType = maxTagsPerType;
        this.validationRegex = validationRegex;
    }

    public String getValidationRegex() {
        return validationRegex;
    }

    public int getMaxTagsPerType() {
        return maxTagsPerType;
    }

    public boolean isValidTagName(String tagName) {
        return tagName != null && tagName.matches(validationRegex);
    }
}
