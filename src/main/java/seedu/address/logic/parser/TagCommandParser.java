package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.TagCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import java.util.HashMap;
import java.util.Map;

import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.StudentId;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagType;

/**
 * Parses input arguments and creates a TagCommand object.
 */
public class TagCommandParser implements Parser<TagCommand> {

    private static final Prefix[] ALL_PREFIXES = {
        CliSyntax.PREFIX_STUDENT_ID,
        CliSyntax.PREFIX_TAG_GENDER,
        CliSyntax.PREFIX_TAG_MAJOR,
        CliSyntax.PREFIX_TAG_YEAR
    };

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns an TagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ParserUtil.checkForUnknownPrefixes(args, MESSAGE_USAGE, PREFIX_STUDENT_ID,
                CliSyntax.PREFIX_TAG_GENDER,
                CliSyntax.PREFIX_TAG_MAJOR,
                CliSyntax.PREFIX_TAG_YEAR);

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, ALL_PREFIXES);

        argumentMultimap.verifyNoDuplicatePrefixesFor(ALL_PREFIXES);

        if (argumentMultimap.getValue(PREFIX_STUDENT_ID).isEmpty() || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        StudentId studentId = ParserUtil.parseStudentId(argumentMultimap.getValue(PREFIX_STUDENT_ID).get());
        Map<TagType, Tag> tags = parseTags(argumentMultimap);

        return new TagCommand(studentId, tags);
    }

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
}
