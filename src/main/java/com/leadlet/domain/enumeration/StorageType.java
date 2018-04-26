package com.leadlet.domain.enumeration;

/**
 * The StorageType enumeration.
 */
public enum StorageType {
    GOOGLE_STORAGE(Values.GOOGLE_STORAGE),
    AMAZON_S3(Values.AMAZON_S3),
    UNDEFINED(Values.UNDEFINED);

    String value;

    StorageType(String value){
        this.value = value;
    }

    public static class Values {
        public static final String GOOGLE_STORAGE = "GOOGLE_STORAGE";
        public static final String AMAZON_S3 = "AMAZON_S3";
        public static final String UNDEFINED = "UNDEFINED";

    }
}
