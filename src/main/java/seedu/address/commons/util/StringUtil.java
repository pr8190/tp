package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import org.apache.commons.text.similarity.LevenshteinDistance;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case.
     *   A full exact match is not needed. Fuzzy matching is done through {@link #fuzzyMatch(String, String)}.
     *
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "ABc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "ab") == true // (L-distance = 1)
     *       containsWordIgnoreCase("ABc def", "ax") == false // (L-distance = 3)
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean matchesWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim().toLowerCase();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence.toLowerCase();
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(w -> fuzzyMatch(w, preppedWord));
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
     * Returns {@code true} if the two strings are similar enough to be considered a match.
     * <br>
     * Exact matches are always returned as true.
     * For strings longer than 2 characters, a Levenshtein distance of up to 2 edits is allowed for small typos.
     * Read more about Levenshtein distance <a href="https://en.wikipedia.org/wiki/Levenshtein_distance">here</a>
     *
     * @param s1 String to compare
     * @param s2 String to compare
     * @return true if the strings match exactly or fall within the typo threshold
     */
    private static boolean fuzzyMatch(String s1, String s2) {
        if (s1.equals(s2)) {
            return true;
        }

        if (s1.length() <= 2 || s2.length() <= 2) {
            return false;
        }

        LevenshteinDistance levenshtein = new LevenshteinDistance(2);
        Integer distance = levenshtein.apply(s1, s2);

        return distance != null && distance != -1;
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
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
}
