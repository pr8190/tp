package seedu.address.model.person;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.FilterDetails;

/**
 * Tests whether a {@code Person} matches the details specified in a {@link FilterDetails}.
 */
public record PersonMatchesDetailsPredicate(FilterDetails filterDetails) implements Predicate<Person> {

    /**
     * Creates a {@code PersonMatchesDetailsPredicate} with the given {@code FilterDetails}.
     */
    public PersonMatchesDetailsPredicate(FilterDetails filterDetails) {
        this.filterDetails = Objects.requireNonNull(filterDetails);
    }

    /**
     * Returns a snapshot of the filter details used by this predicate.
     */
    @Override
    public FilterDetails filterDetails() {
        return new FilterDetails(filterDetails);
    }

    @Override
    public boolean test(Person person) {
        return isNameMatch(person)
                & isFuzzyMatch(person.getEmail().value, filterDetails.getEmailKeywords())
                & isFuzzyMatch(person.getPhone().value, filterDetails.getPhoneNumberKeywords())
                & isExactMatch(person.getRoomNumber().value, filterDetails.getRoomNumberKeywords())
                & isFuzzyMatch(person.getStudentId().value, filterDetails.getStudentIdKeywords())
                & isExactMatch(person.getEmergencyContact().value, filterDetails.getEmergencyContactKeywords())
                & matchesExactTags(person, filterDetails.getTagYearKeywords())
                & matchesFuzzyTags(person, filterDetails.getTagMajorKeywords())
                & matchesExactTags(person, filterDetails.getTagGenderKeywords());
    }

    /**
     * Checks if the person's name matches any of the keywords specified in {@code FilterDetails}.
     * Name matching is done using {@link NameContainsKeywordsPredicate#test(Person)}.
     */
    private boolean isNameMatch(Person person) {
        if (filterDetails.getNameKeywords().isEmpty()) {
            return true;
        }
        List<String> listOfKeywords = filterDetails.getNameKeywords().stream().toList();
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(listOfKeywords);
        return predicate.test(person);
    }

    /**
     * Checks if the given {@code fieldValue} exactly matches any of the {@code keywords} (case-insensitive).
     */
    private boolean isExactMatch(String fieldValue, Set<String> keywords) {
        assert keywords != null : "keywords set should be non-null";
        if (keywords.isEmpty()) {
            return true;
        }
        if (fieldValue.isEmpty()) {
            return false;
        }
        String lower = fieldValue.toLowerCase(Locale.ROOT);
        return keywords.stream().map(k -> k.toLowerCase(Locale.ROOT)).anyMatch(lower::equals);
    }

    /**
     * Checks if the given {@code fieldValue} matches any of the {@code keywords} via substring matching
     * (case-insensitive).
     */
    private boolean isFuzzyMatch(String fieldValue, Set<String> keywords) {
        assert keywords != null : "keywords set should be non-null";
        if (keywords.isEmpty()) {
            return true;
        }
        if (fieldValue.isEmpty()) {
            return false;
        }
        String lower = fieldValue.toLowerCase(Locale.ROOT);
        return keywords.stream().map(k -> k.toLowerCase(Locale.ROOT)).anyMatch(lower::contains);
    }

    /**
     * Checks if any of the person's tags match any of the {@code keywords} via substring matching
     * (case-insensitive). The keyword must be a substring of the tag name, not the other way around,
     * to avoid e.g. "cs" matching "statistics".
     */
    private boolean matchesFuzzyTags(Person person, Set<String> keywords) {
        assert keywords != null : "tag keyword set should be non-null";
        if (keywords.isEmpty()) {
            return true;
        }
        return person.getTags().values().stream().anyMatch(tag -> {
            String lowerTag = tag.tagName.toLowerCase(Locale.ROOT);
            return keywords.stream()
                    .map(k -> k.toLowerCase(Locale.ROOT))
                    .anyMatch(k -> lowerTag.contains(k) && lowerTag.length() <= k.length() + 3);
        });
    }

    /**
     * Checks if any of the person's tags exactly match any of the {@code keywords} (case-insensitive).
     */
    private boolean matchesExactTags(Person person, Set<String> keywords) {
        assert keywords != null : "tag keyword set should be non-null";
        if (keywords.isEmpty()) {
            return true;
        }
        return person.getTags().values().stream()
                .anyMatch(tag -> keywords.stream()
                        .anyMatch(keyword -> tag.tagName.equalsIgnoreCase(keyword)));
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
        return new ToStringBuilder(this)
                .add("filterDetails", filterDetails)
                .toString();
    }
}
