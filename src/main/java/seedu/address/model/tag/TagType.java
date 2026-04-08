package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

/**
 * Represents the category of a {@link Tag} in the hall ledger.
 *
 * <p>Each {@code TagType} defines how its associated tag content is validated.
 * Validation is performed using either:
 * <ul>
 *   <li>a fixed set of allowed values (closed set), or</li>
 *   <li>a regular expression (open set).</li>
 * </ul>
 *
 * <p>Exactly one validation strategy is defined per {@code TagType}.
 *
 * <p>Guarantees:
 * <ul>
 *   <li>Immutable: each enum constant is stateless and thread-safe.</li>
 *   <li>Validation rules are fixed at compile time.</li>
 * </ul>
 */
public enum TagType {

    /**
     * Tag representing the resident's year of study.
     * Valid content is a single digit from {@code 1} to {@code 6} inclusive.
     */
    YEAR(List.of("1", "2", "3", "4", "5", "6"), null),

    /**
     * Tag representing the resident's academic major.
     * Valid content must satisfy the following rules:
     * <ul>
     *   <li>1 to 100 characters long</li>
     *   <li>Alphabetic characters and {@code &} only</li>
     * </ul>
     *
     * <p>Examples: {@code Computer Science}, {@code Philosophy}, {@code Economics & Finance}
     */
    MAJOR(null, "^(?=.{1,100}$)[A-Za-z&]+( [A-Za-z&]+)*$"),

    /**
     * Tag representing the resident's gender pronouns.
     * Valid content is one of {@code she/her}, {@code he/him}, or {@code they/them}.
     */
    GENDER(List.of("she/her", "he/him", "they/them"), null);

    private final List<String> allowedValues;
    private final String validationRegex;

    TagType(List<String> allowedValues, String validationRegex) {
        this.allowedValues = allowedValues;
        this.validationRegex = validationRegex;
    }

    /**
     * Validates the given tag content against this {@code TagType}'s rules.
     *
     * <p>Validation behaviour:
     * <ul>
     *   <li>If {@code allowedValues} is defined, the content must match one of the values exactly.</li>
     *   <li>Otherwise, the content must match the defined regular expression.</li>
     * </ul>
     *
     * @param tagContent the tag content to validate (must not be {@code null})
     * @return {@code true} if the content is valid for this tag type, {@code false} otherwise
     * @throws IllegalStateException if neither validation strategy is defined
     */
    public boolean isValidTagContent(String tagContent) {
        requireNonNull(tagContent);

        // Case 1: Use allowed values (closed set)
        if (allowedValues != null) {
            return allowedValues.contains(tagContent);
        }

        // Defensive check: if allowedValues is null, validationRegex must be non-null
        if (validationRegex == null) {
            throw new IllegalStateException("TagType must have either allowedValues or validationRegex defined.");
        }

        // Case 2: Use regex (open set)
        return tagContent.matches(validationRegex);
    }

    /**
     * Returns the allowed values for this tag type, if defined.
     *
     * @return an {@link Optional} containing an immutable copy of the allowed values,
     *         or {@link Optional#empty()} if this tag type uses regex validation
     */
    public Optional<List<String>> getAllowedValues() {
        return allowedValues == null
                ? Optional.empty()
                : Optional.of(List.copyOf(allowedValues)); // immutable copy
    }
}
