package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s name matches any keyword using word-level fuzzy matching.
 *
 * <p>The person's name is split into words, then each word is checked against the keyword set via
 * {@link StringUtil#fuzzyMatchesAnyIgnoreCase(String, Set)}.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns the name keywords used by this predicate.
     */
    public Set<String> getKeywords() {
        return Set.copyOf(keywords);
    }

    @Override
    public boolean test(Person person) {
        Set<String> keywordsSet = Set.copyOf(keywords);
        Set<String> nameWordsSet = StringUtil.splitSentenceIntoWords(person.getName().fullName);

        if (keywordsSet.isEmpty()) {
            return true;
        }

        if (nameWordsSet.isEmpty()) {
            return false;
        }

        return nameWordsSet.stream()
                .anyMatch(nameWord -> StringUtil.fuzzyMatchesAnyIgnoreCase(nameWord, keywordsSet));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate)) {
            return false;
        }

        // Compare keywords as sets to ignore order and duplicates
        Set<String> keywordsSet = Set.copyOf(keywords);
        Set<String> otherKeywordsSet = Set.copyOf(otherNameContainsKeywordsPredicate.keywords);
        return keywordsSet.equals(otherKeywordsSet);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
