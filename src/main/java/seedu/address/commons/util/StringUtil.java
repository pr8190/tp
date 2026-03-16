package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.text.similarity.LevenshteinDistance;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Checks if a word is an exact match for any word in a given set, ignoring case.
     * <br>
     * Examples:
     * <pre>
     *      equalsAnyIgnoreCase("abc", Set.of("abc", "xyz")) == true // exact match found
     *      equalsAnyIgnoreCase("abc", Set.of("ABC", "xyz")) == true // exact match found, ignoring case
     *      equalsAnyIgnoreCase("abc", Set.of("ab", "xyz")) == false // no exact match found
     * </pre>
     *
     * @param word    The word to check. Cannot be null or empty.
     * @param wordSet The set of words to compare against. Cannot be null or empty.
     * @return true if an exact match is found.
     */
    public static boolean equalsAnyIgnoreCase(String word, Set<String> wordSet) {
        requireNonNull(word);
        requireNonNull(wordSet);

        String preppedWord = word.toLowerCase().trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(!wordSet.isEmpty(), "Word set cannot be empty");

        return wordSet.stream()
                .map(k -> k.toLowerCase().trim())
                .anyMatch(preppedWord::equals);
    }

    /**
     * Checks if word fuzzy matches any word in the word set, ignoring case.
     * <br>
     * Fuzzy matching is defined as an algorithm in {@link #fuzzyMatchesIgnoresCase(String, String)}.
     * <br>
     * Examples:
     * <pre>
     *      fuzzyMatchesAnyIgnoreCase("abc", Set.of("abc", "xyz")) == true
     *              // "abc" fuzzy matches "abc"
     *      fuzzyMatchesAnyIgnoreCase("abc", Set.of("ABCxyz")) == true
     *              // "abc" is a 3 character-long substring of "ABCxyz", so it is considered a fuzzy match
     *      fuzzyMatchesAnyIgnoreCase("abc", Set.of("ab", "xyz")) == true
     *              // "abc" is a 1 edit distance away from "ab", so it is considered a fuzzy match
     * </pre>
     */
    public static boolean fuzzyMatchesAnyIgnoreCase(String word, Set<String> wordSet) {
        requireNonNull(word);
        requireNonNull(wordSet);

        String preppedWord = word.toLowerCase().trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(!wordSet.isEmpty(), "Word set cannot be empty");

        return wordSet.stream()
                .map(k -> k.toLowerCase().trim())
                .anyMatch(k -> fuzzyMatchesIgnoresCase(preppedWord, k));
    }

    /**
     * Checks if two strings are similar enough to be considered a fuzzy match.
     * <p>
     * The matching rules are applied after trimming and converting to lower case:
     * <ol>
     *     <li>Exact matches are always true.</li>
     *     <li>If either string is 2 characters or shorter, only exact matches return true (fuzzy logic disabled).</li>
     *     <li>If the {@code query} is a substring of the {@code target}, returns true.</li>
     *     <li>If the Levenshtein distance is 2 or less, returns true (tolerating small typos).</li>
     * </ol>
     *
     * <br>Examples:
     * <pre>
     *    fuzzyMatchesIgnoresCase("ABc", "abc") == true  // exact match
     *    fuzzyMatchesIgnoresCase("abc", "acd") == true  // 2 edits (b->c, c->d) -> match
     *    fuzzyMatchesIgnoresCase("abc", "abcde") == true  // {@code query} is a substring of {@code target} -> match
     *    fuzzyMatchesIgnoresCase("ab", "abcde")  == false // too short for fuzzy matching
     * </pre>
     *
     * @param query The string to search for. Cannot be null or empty.
     * @param target The string to compare against. Cannot be null or empty.
     * @return true if the strings match exactly or fall within the fuzzy threshold.
     */
    public static boolean fuzzyMatchesIgnoresCase(String query, String target) {
        requireNonNull(query);
        requireNonNull(target);

        checkArgument(!query.isEmpty(), "Query parameter cannot be empty");
        checkArgument(!target.isEmpty(), "Target parameter cannot be empty");

        String queryProcessed = query.toLowerCase().trim();
        String targetProcessed = target.toLowerCase().trim();

        if (queryProcessed.equals(targetProcessed)) {
            return true;
        }

        if (queryProcessed.length() <= 2 || targetProcessed.length() <= 2) {
            return false;
        }

        if (targetProcessed.contains(queryProcessed)) {
            return true;
        }

        LevenshteinDistance levenshtein = new LevenshteinDistance(2);
        Integer distance = levenshtein.apply(queryProcessed, targetProcessed);

        return distance != null && distance != -1;
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     *
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Splits a given sentence into words and returns a set of the words in the sentence.
     *
     * @param sentence sentence to be split into words. Cannot be null.
     * @return a {@code Set<String>} of the words in the sentence.
     */
    public static Set<String> splitSentenceIntoWords(String sentence) {
        requireNonNull(sentence);
        String trimmedSentence = sentence.trim();
        if (trimmedSentence.isEmpty()) {
            return new HashSet<>();
        }
        String[] wordsInSentence = trimmedSentence.split("\\s+");
        return new HashSet<>(Arrays.asList(wordsInSentence));
    }
}
