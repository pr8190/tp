package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.FilterDetails;

/**
 * Tests whether a {@code Person} matches the details specified in a {@link FilterDetails}.
 */
public class PersonMatchesDetailsPredicate implements Predicate<Person> {

    /**
     * The filter details to match against.
     */
    private final FilterDetails filterDetails;

    /**
     * Creates a {@code PersonMatchesDetailsPredicate} with the given {@code FilterDetails}.
     */
    public PersonMatchesDetailsPredicate(FilterDetails filterDetails) {
        this.filterDetails = requireNonNull(filterDetails);
    }

    /**
     * Returns a snapshot of the filter details used by this predicate.
     */
    public FilterDetails filterDetails() {
        return new FilterDetails(filterDetails);
    }

    @Override
    public boolean test(Person person) {
        String personNameString = person.getName().fullName;
        String personEmailString = person.getEmail().value;
        String personPhoneString = person.getPhone().value;
        String personRoomNumberString = person.getRoomNumber().value;
        String personStudentIdString = person.getStudentId().value;
        String personEmergencyContactString = person.getEmergencyContact().value;

        // Get the person's tag values as strings, or empty strings if the tags are not present
        String personYearString = person.getYear().map(tag -> tag.getTagContent()).orElse("");
        String personMajorString = person.getMajor().map(tag -> tag.getTagContent()).orElse("");
        String personGenderString = person.getGender().map(tag -> tag.getTagContent()).orElse("");

        return isFuzzyMatch(personNameString, filterDetails.getNameKeywords())
                && isFuzzyMatch(personEmailString, filterDetails.getEmailKeywords())
                && isFuzzyMatch(personPhoneString, filterDetails.getPhoneNumberKeywords())
                && isFuzzyMatch(personRoomNumberString, filterDetails.getRoomNumberKeywords())
                && isExactMatch(personStudentIdString, filterDetails.getStudentIdKeywords())
                && isFuzzyMatch(personEmergencyContactString, filterDetails.getEmergencyContactKeywords())
                && isExactMatch(personYearString, filterDetails.getTagYearKeywords())
                && isFuzzyMatch(personMajorString, filterDetails.getTagMajorKeywords())
                && isExactMatch(personGenderString, filterDetails.getTagGenderKeywords());
    }

    /**
     * Checks if the given {@code fieldValue} matches any of the {@code keywords} via fuzzy matching defined in
     * {@link StringUtil#fuzzyMatchesAnyIgnoreCase(String, Set)}.
     * <p>
     * If the field value is empty, it will only match if the keywords are also empty (meaning non-existent).
     */
    private boolean isFuzzyMatch(String fieldValue, Set<String> keywords) {
        requireNonNull(keywords);
        requireNonNull(fieldValue);

        if (keywords.isEmpty()) {
            return true;
        }
        if (fieldValue.isEmpty()) {
            return false;
        }

        return StringUtil.fuzzyMatchesAnyIgnoreCase(fieldValue, keywords);
    }

    /**
     * Checks if any of the person's tags exactly match any of the {@code keywords} as defined in
     * {@link StringUtil#equalsAnyIgnoreCase(String, Set)}.
     * <p>
     * If the field value is empty, it will only match if the keywords are also empty (meaning non-existent).
     */
    private boolean isExactMatch(String fieldValue, Set<String> keywords) {
        requireNonNull(fieldValue);
        requireNonNull(keywords);

        if (keywords.isEmpty()) {
            return true;
        }
        if (fieldValue.isEmpty()) {
            return false;
        }

        return StringUtil.equalsAnyIgnoreCase(fieldValue, keywords);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PersonMatchesDetailsPredicate otherPredicate)) {
            return false;
        }
        return Objects.equals(this.filterDetails, otherPredicate.filterDetails);
    }

    @Override
    public String toString() {
        return new ToStringBuilder("")
                .add("filterDetails", filterDetails)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(filterDetails);
    }
}
