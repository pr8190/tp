package seedu.address.model.person;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.FilterDetails;

/**
 * Tests whether a {@code Person} matches the details specified in a {@link FilterDetails}.
 */
public class PersonMatchesDetailsPredicate implements Predicate<Person> {

    private final FilterDetails filterDetails;

    public PersonMatchesDetailsPredicate(FilterDetails filterDetails) {
        this.filterDetails = Objects.requireNonNull(filterDetails);
    }

    @Override
    public boolean test(Person person) {
        return isNameMatch(person)
                || isFuzzyMatch(person.getEmail().value, filterDetails.getEmailKeywords())
                || isFuzzyMatch(person.getPhone().value, filterDetails.getPhoneNumberKeywords())
                || isExactMatch(person.getRoomNumber().value, filterDetails.getRoomNumberKeywords())
                || isFuzzyMatch(person.getStudentId().value, filterDetails.getStudentIdKeywords())
                || isExactMatch(person.getEmergencyContact().value,
                        filterDetails.getEmergencyContactKeywords())
                || matchesFuzzyTags(person, filterDetails.getTagKeywords())
                || matchesExactTags(person, filterDetails.getTagYearKeywords())
                || matchesFuzzyTags(person, filterDetails.getTagMajorKeywords())
                || matchesExactTags(person, filterDetails.getTagGenderKeywords());
    }

    // Name matching
    private boolean isNameMatch(Person person) {
        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(filterDetails.getNameKeywords().stream().toList());
        return predicate.test(person);
    }

    // Exact string matching (not case-sensitive) for room and studentId
    private boolean isExactMatch(String fieldValue, Set<String> keywords) {
        assert keywords != null : "keywords set should be non-null";
        if (keywords.isEmpty()) {
            return false;
        }
        String lowerField = fieldValue.toLowerCase();
        return keywords.stream()
                .map(k -> k.toLowerCase())
                .anyMatch(lowerField::contains);
    }

    // Fuzzy string matching (not case-sensitive)
    // TODO: Implement fuzzy Match
    private boolean isFuzzyMatch(String fieldValue, Set<String> keywords) {
        assert keywords != null : "keywords set should be non-null";
        return isExactMatch(fieldValue, keywords);
    }

    // Substring matching (not case-sensitive)
    // TODO: Implement fuzzy Match
    private boolean isSubstringMatch(String fieldValue, Set<String> keywords) {
        return isExactMatch(fieldValue, keywords);
    }

    // === Tag helpers without BiPredicate ===
    private boolean matchesFuzzyTags(Person person, Set<String> keywords) {
        assert keywords != null : "tag keyword set should be non-null";
        if (keywords.isEmpty()) {
            return false;
        }
        return person.getTags().stream().anyMatch(tag -> {
            String lowerTag = tag.tagName.toLowerCase(Locale.ROOT);
            return keywords.stream()
                    .map(k -> k.toLowerCase(Locale.ROOT))
                    .anyMatch(lowerTag::contains);
        });
    }

    private boolean matchesExactTags(Person person, Set<String> keywords) {
        assert keywords != null : "tag keyword set should be non-null";
        if (keywords.isEmpty()) {
            return false;
        }
        return person.getTags().stream().anyMatch(tag ->
                keywords.stream().anyMatch(keyword -> tag.tagName.equalsIgnoreCase(keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonMatchesDetailsPredicate)) {
            return false;
        }

        PersonMatchesDetailsPredicate otherPredicate = (PersonMatchesDetailsPredicate) other;
        return Objects.equals(this.filterDetails, otherPredicate.filterDetails);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("filterDetails", filterDetails)
                .toString();
    }
}
