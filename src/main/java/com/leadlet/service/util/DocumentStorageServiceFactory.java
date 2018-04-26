package com.leadlet.service.util;

import com.leadlet.domain.StoragePreference;
import com.leadlet.domain.enumeration.StorageType;
import com.leadlet.service.DocumentStorageService;
import com.leadlet.service.impl.GoogleStorageService;

import javax.el.MethodNotFoundException;

public class DocumentStorageServiceFactory {

    public static DocumentStorageService getService(StoragePreference preference){
        if( preference.getType().equals(StorageType.GOOGLE_STORAGE)){
            return new GoogleStorageService(preference);
        }else if( preference.getType().equals(StorageType.AMAZON_S3)){
            throw new MethodNotFoundException();
        }

        return null;
    }

}
