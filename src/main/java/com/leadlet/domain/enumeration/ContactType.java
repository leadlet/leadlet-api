package com.leadlet.domain.enumeration;

/**
 * The ContactType enumeration.
 */
public enum ContactType {

    PERSON("PERSON"),
    ORGANIZATION("ORGANIZATION");
    public String key;

    private ContactType(String key) {
        this.key = key;
    }
    public String toString() {
        return this.key;
    }

    public String getKey() {
        return key;
    }
}
