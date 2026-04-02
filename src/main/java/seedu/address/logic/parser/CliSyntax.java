package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n=");
    public static final Prefix PREFIX_PHONE = new Prefix("p=");
    public static final Prefix PREFIX_EMAIL = new Prefix("e=");
    public static final Prefix PREFIX_STUDENT_ID = new Prefix("i=");
    public static final Prefix PREFIX_ROOM_NUMBER = new Prefix("r=");
    public static final Prefix PREFIX_EMERGENCY_CONTACT = new Prefix("ec=");
    public static final Prefix PREFIX_TAG_YEAR = new Prefix("y=");
    public static final Prefix PREFIX_TAG_MAJOR = new Prefix("m=");
    public static final Prefix PREFIX_TAG_GENDER = new Prefix("g=");
    public static final Prefix PREFIX_DEMERIT_INDEX = new Prefix("di=");
    public static final Prefix PREFIX_REMARK = new Prefix("rm=");
}
