package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_PREFIX;
import static seedu.address.logic.commands.TagCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_YEAR;

import java.util.HashMap;
import java.util.Map;

import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.StudentId;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagType;

/**
 * Parses input arguments and creates a {@link TagCommand} object.
 *
 * <p>Expects a student ID prefix and at least one tag prefix. Recognised prefixes are:
 * <ul>
 *   <li>{@code PREFIX_STUDENT_ID} — identifies the target resident</li>
 *   <li>{@code PREFIX_TAG_GENDER} — gender pronouns tag</li>
 *   <li>{@code PREFIX_TAG_MAJOR} — academic major tag</li>
 *   <li>{@code PREFIX_TAG_YEAR} — year of study tag</li>
 * </ul>
 */
public class TagCommandParser implements Parser<TagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns a TagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     * or student ID is missing or invalid.
     */
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        checkForUnknownPrefixes(args);

        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_STUDENT_ID,
                        CliSyntax.PREFIX_TAG_GENDER,
                        CliSyntax.PREFIX_TAG_MAJOR,
                        CliSyntax.PREFIX_TAG_YEAR);

        argumentMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENT_ID,
                PREFIX_TAG_GENDER, PREFIX_TAG_MAJOR, PREFIX_TAG_YEAR);

        if (argumentMultimap.getValue(PREFIX_STUDENT_ID).isEmpty() || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        StudentId studentId = ParserUtil.parseStudentId(argumentMultimap.getValue(PREFIX_STUDENT_ID).get());
        Map<TagType, Tag> tags = parseTags(argumentMultimap);

        return new TagCommand(studentId, tags);
    }

    /**
     * Parses the tags from the {@code ArgumentMultimap} and returns a Map of TagType to Tag.
     *
     * @param argumentMultimap the ArgumentMultimap containing the parsed arguments.
     * @return a Map of TagType to Tag containing the parsed tags.
     * @throws ParseException if any of the tag values are invalid or if no tags are provided.
     */
    private Map<TagType, Tag> parseTags(ArgumentMultimap argumentMultimap) throws ParseException {
        Map<TagType, Tag> tags = new HashMap<>();
        try {
            argumentMultimap.getValue(CliSyntax.PREFIX_TAG_GENDER)
                    .ifPresent(gender -> tags.put(TagType.GENDER, new Tag(TagType.GENDER, gender)));
            argumentMultimap.getValue(CliSyntax.PREFIX_TAG_MAJOR)
                    .ifPresent(major -> tags.put(TagType.MAJOR, new Tag(TagType.MAJOR, major)));
            argumentMultimap.getValue(CliSyntax.PREFIX_TAG_YEAR)
                    .ifPresent(year -> tags.put(TagType.YEAR, new Tag(TagType.YEAR, year)));
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }

        if (tags.isEmpty()) {
            throw new ParseException(TagCommand.MESSAGE_TAG_NOT_ADDED);
        }
        return tags;
    }

    private void checkForUnknownPrefixes(String args) throws ParseException {
        String unknownPrefix = ArgumentTokenizer.checkForUnknownPrefixes(args, PREFIX_STUDENT_ID,
                CliSyntax.PREFIX_TAG_GENDER,
                CliSyntax.PREFIX_TAG_MAJOR,
                CliSyntax.PREFIX_TAG_YEAR);

        if (!unknownPrefix.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_UNKNOWN_PREFIX, unknownPrefix)
                    + "\n" + TagCommand.MESSAGE_USAGE);
        }
    }
}
