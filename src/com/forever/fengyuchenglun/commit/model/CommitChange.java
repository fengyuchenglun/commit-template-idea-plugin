package com.forever.fengyuchenglun.commit.model;

/**
 * The type Commit change.
 *
 * @author duanledexianxianxian
 * @data 2019 /2/17
 */
public class CommitChange {
    /**
     * The Change type.
     */
    private ChangeType changeType;
    /**
     * The Change scope.
     */
    private ChangeScope changeScope;
    /**
     * The Short description.
     */
    private String shortDescription;
    /**
     * The Long description.
     */
    private String longDescription;
    /**
     * The Breaking changes.
     */
    private String breakingChanges;
    /**
     * The Closed issues.
     */
    private String closedIssues;

    public ChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }

    public ChangeScope getChangeScope() {
        return changeScope;
    }

    public void setChangeScope(ChangeScope changeScope) {
        this.changeScope = changeScope;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getBreakingChanges() {
        return breakingChanges;
    }

    public void setBreakingChanges(String breakingChanges) {
        this.breakingChanges = breakingChanges;
    }

    public String getClosedIssues() {
        return closedIssues;
    }

    public void setClosedIssues(String closedIssues) {
        this.closedIssues = closedIssues;
    }

    @Override
    public String toString() {
        return "CommitChange{" +
                "changeType=" + changeType +
                ", changeScope=" + changeScope +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", breakingChanges='" + breakingChanges + '\'' +
                ", closedIssues='" + closedIssues + '\'' +
                '}';
    }
}
