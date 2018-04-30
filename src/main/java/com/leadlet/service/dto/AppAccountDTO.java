package com.leadlet.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AppAccount entity.
 */
public class AppAccountDTO implements Serializable {

    private Long id;

    private String name;

    private String address;

    private StoragePreferenceDTO storagePreference;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public StoragePreferenceDTO getStoragePreference() {
        return storagePreference;
    }

    public AppAccountDTO setStoragePreference(StoragePreferenceDTO storagePreference) {
        this.storagePreference = storagePreference;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppAccountDTO appAccountDTO = (AppAccountDTO) o;
        if(appAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppAccountDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
