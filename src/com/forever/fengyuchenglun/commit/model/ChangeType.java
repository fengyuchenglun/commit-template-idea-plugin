package com.forever.fengyuchenglun.commit.model;


/**
 * header type
 *
 * @author 44902
 */
public class ChangeType {


    /**
     * header类型
     */
    private   String type;
    /**
     * 描述
     */
    private  String description;

    /**
     * Instantiates a new Change type.
     *
     * @param type        the type
     * @param description the description
     */
    public  ChangeType(String type, String description) {
        this.type = type;
        this.description = description;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getType() {
        return type;
    }

    /**
     * Sets title.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
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
        return String.format("%s - %s", this.type, this.description);
    }
}
