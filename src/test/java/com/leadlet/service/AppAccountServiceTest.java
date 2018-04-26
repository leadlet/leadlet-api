package com.leadlet.service;

import com.leadlet.LeadletApiApp;
import com.leadlet.domain.enumeration.StorageType;
import com.leadlet.service.dto.AppAccountDTO;
import com.leadlet.service.dto.StoragePreferenceDTO;
import com.leadlet.service.mapper.AppAccountMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the AppAccountService.
 *
 * @see AppAccountService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
@Transactional
public class AppAccountServiceTest {

    @Autowired
    private AppAccountService appAccountService;

    @Autowired
    private AppAccountMapper appAccountMapper;



    @Test
    public void saveAppAccountWithStoragePreference() throws IOException, SQLException {

        StoragePreferenceDTO storagePreferenceDTO = new StoragePreferenceDTO();
        storagePreferenceDTO.setType(StorageType.Values.GOOGLE_STORAGE);

        AppAccountDTO appAccountDTO = new AppAccountDTO();
        appAccountDTO.setName("test-app-account");
        appAccountDTO.setStoragePreference(storagePreferenceDTO);

        AppAccountDTO savedAppAccountDTO = appAccountService.save(appAccountDTO);

        assertThat(savedAppAccountDTO.getStoragePreference()).isNotNull();
        assertThat(savedAppAccountDTO.getStoragePreference().getType()).isEqualTo(StorageType.Values.GOOGLE_STORAGE);


    }


}
