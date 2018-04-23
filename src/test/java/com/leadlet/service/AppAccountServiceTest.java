package com.leadlet.service;

import com.leadlet.LeadletApiApp;
import com.leadlet.config.Constants;
import com.leadlet.domain.AppAccount;
import com.leadlet.domain.StoragePreference;
import com.leadlet.domain.User;
import com.leadlet.domain.enumeration.StorageType;
import com.leadlet.repository.AppAccountRepository;
import com.leadlet.repository.UserRepository;
import com.leadlet.service.dto.AppAccountDTO;
import com.leadlet.service.dto.StoragePreferenceDTO;
import com.leadlet.service.dto.UserDTO;
import com.leadlet.service.impl.AppAccountServiceImpl;
import com.leadlet.service.mapper.AppAccountMapper;
import com.leadlet.service.mapper.AppAccountMapperImpl;
import com.leadlet.service.mapper.StoragePreferenceMapper;
import com.leadlet.service.mapper.StoragePreferenceMapperImpl;
import com.leadlet.service.util.RandomUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

/**
 * Test class for the AppAccountService.
 *
 * @see AppAccountService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
@Transactional
public class AppAccountServiceTest {

    private AppAccountRepository appAccountRepositoryMock;

    private AppAccountService appAccountService;

    @Autowired
    private AppAccountMapper appAccountMapper;

    @Before
    public void setUp() {
        appAccountRepositoryMock = mock(AppAccountRepository.class);
        appAccountService = new AppAccountServiceImpl(appAccountRepositoryMock,appAccountMapper);
    }


    @Test
    public void saveAccountWithStorageInfo() {

        AppAccountDTO appAccountDTO = new AppAccountDTO();
        appAccountDTO.setName("Company X");
        appAccountDTO.setAddress("Company X Address");
        appAccountDTO.setStoragePreference(new StoragePreferenceDTO(StorageType.GOOGLE_STORAGE.toString(),"google-storage-api-key-1"));

        AppAccount savedAppAccount = new AppAccount();
        savedAppAccount.setName("Saved Company X");
        savedAppAccount.setAddress("Saved Company X Address");
        savedAppAccount.setStoragePreference(new StoragePreference(StorageType.GOOGLE_STORAGE,"saved-google-storage-api-key-1"));
        savedAppAccount.setId(10000l);

        Mockito.when(appAccountRepositoryMock.save(any(AppAccount.class))).thenReturn(savedAppAccount);

        AppAccountDTO savedAppAccountDTO = appAccountService.save(appAccountDTO);

        assertThat(savedAppAccountDTO.getId()).isEqualTo(10000l);
        assertThat(savedAppAccountDTO.getName()).isEqualTo("Saved Company X");
        assertThat(savedAppAccountDTO.getStoragePreference().getApiKey()).isEqualTo("saved-google-storage-api-key-1");


    }


}
