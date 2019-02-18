package com.forever.fengyuchenglun.commit.model;


/**
 * header scope
 *
 * @author 44902
 */
public class ChangeScope {


    /**
     * header scope
     */
    private String scope;
    /**
     * 描述
     */
    private String description;

    /**
     * Instantiates a new Change type.
     *
     * @param scope       the scope
     * @param description the description
     */
    public ChangeScope(String scope, String description) {
        this.scope = scope;
        this.description = description;
    }

    /**
     * Gets scope.
     *
     * @return the scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * Sets scope.
     *
     * @param scope the scope
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", this.scope, this.description);
    }
}
