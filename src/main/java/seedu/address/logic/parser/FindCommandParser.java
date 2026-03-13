package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_ARGUMENT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_YEAR;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.FilterDetails;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Tokenize the arguments
        ArgumentTokenizer tokenizer = new ArgumentTokenizer();
        ArgumentMultimap argMultimap = tokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ROOM_NUMBER, PREFIX_STUDENT_ID,
                PREFIX_EMERGENCY_CONTACT, PREFIX_TAG, PREFIX_TAG_YEAR, PREFIX_TAG_MAJOR, PREFIX_TAG_GENDER)
                .removeEmptyValuesAndPrefix();

        // Preamble and prefixes are both empty -> Output empty argument message
        if (argMultimap.getPreamble().isEmpty() && argMultimap.hasEmptyPrefixArguments()) {
            throw new ParseException(String.format(MESSAGE_EMPTY_ARGUMENT, FindCommand.MESSAGE_USAGE));
        }

        // Both preamble and prefixes exist -> Output invalid command format message
        if (!argMultimap.getPreamble().isEmpty() && !argMultimap.hasEmptyPrefixArguments()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Preamble exists, prefixes are empty -> FindCommand search by name
        if (!argMultimap.getPreamble().isEmpty() && argMultimap.hasEmptyPrefixArguments()) {
            List<String> nameKeywords = getNameKeywords(args);
            return new FindCommand(new NameContainsKeywordsPredicate(nameKeywords));
        }

        // Preamble is empty, prefixes exist -> FindCommand by prefixes
        if (argMultimap.getPreamble().isEmpty() && !argMultimap.hasEmptyPrefixArguments()) {
            FilterDetails filterDetails = buildFilterDetails(argMultimap);
            // TODO: once PersonMatchesDetailsPredicate is ready, plug it here instead of empty predicate
            return new FindCommand(new NameContainsKeywordsPredicate(List.of()));
        }

        // Should not reach here because all cases are covered above
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    /**
     * Builds a {@link FilterDetails} instance from the values in {@code argMultimap}.
     * All values for a given prefix are collected with {@code getAllValues} and converted
     * into {@link java.util.Set}s to remove duplicates.
     */
    private FilterDetails buildFilterDetails(ArgumentMultimap argMultimap) {
        // Build all keyword sets from ArgumentMultimap
        Set<String> nameKeywords = toSet(argMultimap.getAllValues(PREFIX_NAME));
        Set<String> emailKeywords = toSet(argMultimap.getAllValues(PREFIX_EMAIL));
        Set<String> phoneNumberKeywords = toSet(argMultimap.getAllValues(PREFIX_PHONE));
        Set<String> roomNumberKeywords = toSet(argMultimap.getAllValues(PREFIX_ROOM_NUMBER));
        Set<String> studentIdKeywords = toSet(argMultimap.getAllValues(PREFIX_STUDENT_ID));
        Set<String> emergencyContactKeywords = toSet(argMultimap.getAllValues(PREFIX_EMERGENCY_CONTACT));
        Set<String> tagKeywords = toSet(argMultimap.getAllValues(PREFIX_TAG));
        Set<String> tagYearKeywords = toSet(argMultimap.getAllValues(PREFIX_TAG_YEAR));
        Set<String> tagMajorKeywords = toSet(argMultimap.getAllValues(PREFIX_TAG_MAJOR));
        Set<String> tagGenderKeywords = toSet(argMultimap.getAllValues(PREFIX_TAG_GENDER));

        // Populate FilterDetails using those sets
        FilterDetails filterDetails = new FilterDetails();
        filterDetails.setNameKeywords(nameKeywords);
        filterDetails.setEmailKeywords(emailKeywords);
        filterDetails.setPhoneNumberKeywords(phoneNumberKeywords);
        filterDetails.setRoomNumberKeywords(roomNumberKeywords);
        filterDetails.setStudentIdKeywords(studentIdKeywords);
        filterDetails.setEmergencyContactKeywords(emergencyContactKeywords);
        filterDetails.setTagKeywords(tagKeywords);
        filterDetails.setTagYearKeywords(tagYearKeywords);
        filterDetails.setTagMajorKeywords(tagMajorKeywords);
        filterDetails.setTagGenderKeywords(tagGenderKeywords);

        return filterDetails;
    }

    private static Set<String> toSet(List<String> values) {
        return new HashSet<>(values);
    }

    /**
     * Extract name keywords from the preamble. Name keywords are separated by whitespaces.
     *
     * @param args the preamble
     * @return a list of name keywords
     * @throws ParseException if the preamble is empty or contains only whitespaces
     */
    private static List<String> getNameKeywords(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return Arrays.stream(trimmedArgs.split("\\s+"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}
