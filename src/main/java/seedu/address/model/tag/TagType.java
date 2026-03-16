package seedu.address.model.tag;

/**
 * Represents the type of a Tag in the address book.
 * Guarantees: immutable; name is valid
 */
public enum TagType {
    YEAR(1),
    MAJOR(2),
    GENDER(1);

    private final int maxTagsPerType;

    TagType(int maxTagsPerType) {
        this.maxTagsPerType = maxTagsPerType;
    }

    public int getMaxTagsPerType() {
        return maxTagsPerType;
    }
}
