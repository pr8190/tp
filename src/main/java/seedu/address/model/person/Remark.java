package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Remark in the address book.
 * Guarantees: immutable
 */
public class Remark {

    public final String remark;

    /**
     * Constructs a {@code Remark}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);

        this.remark = remark;
    }

    @Override
    public String toString() {
        return remark;
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof Remark
                && remark.equals(((Remark) other).remark));
    }

    @Override
    public int hashCode() {
        return remark.hashCode();
    }

}
