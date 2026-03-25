package seedu.address.model.demerit;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents one applied demerit incident on a resident.
 */
public class DemeritIncident {

    private final int ruleIndex;
    private final String ruleTitle;
    private final int offenceNumber;
    private final int pointsApplied;
    private final String remark;

    /**
     * Creates a demerit incident.
     */
    public DemeritIncident(int ruleIndex, String ruleTitle, int offenceNumber,
                           int pointsApplied, String remark) {
        requireNonNull(ruleTitle);
        requireNonNull(remark);
        this.ruleIndex = ruleIndex;
        this.ruleTitle = ruleTitle.trim();
        this.offenceNumber = offenceNumber;
        this.pointsApplied = pointsApplied;
        this.remark = remark.trim();
    }

    public int getRuleIndex() {
        return ruleIndex;
    }

    public String getRuleTitle() {
        return ruleTitle;
    }

    public int getOffenceNumber() {
        return offenceNumber;
    }

    public int getPointsApplied() {
        return pointsApplied;
    }

    public String getRemark() {
        return remark;
    }

    @Override
    public String toString() {
        if (remark.isEmpty()) {
            return String.format("[%d] %s (offence %d, +%d)",
                    ruleIndex, ruleTitle, offenceNumber, pointsApplied);
        }
        return String.format("[%d] %s (offence %d, +%d) - %s",
                ruleIndex, ruleTitle, offenceNumber, pointsApplied, remark);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DemeritIncident)) {
            return false;
        }
        DemeritIncident otherIncident = (DemeritIncident) other;
        return ruleIndex == otherIncident.ruleIndex
                && offenceNumber == otherIncident.offenceNumber
                && pointsApplied == otherIncident.pointsApplied
                && ruleTitle.equals(otherIncident.ruleTitle)
                && remark.equals(otherIncident.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruleIndex, ruleTitle, offenceNumber, pointsApplied, remark);
    }
}
