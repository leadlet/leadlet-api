package com.leadlet.service;

import com.leadlet.LeadletApiApp;
import com.leadlet.domain.AppAccount;
import com.leadlet.domain.StoragePreference;
import com.leadlet.domain.enumeration.StorageType;
import com.leadlet.service.dto.AppAccountDTO;
import com.leadlet.service.dto.StoragePreferenceDTO;
import com.leadlet.service.impl.GoogleStorageService;
import com.leadlet.service.mapper.AppAccountMapper;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Autowired
    private AppAccountService appAccountService;

    @Autowired
    private AppAccountMapper appAccountMapper;
    private MockMultipartFile multipartFile;
    private MockMultipartFile multipartFileBig;


    @Before
    public void setUp() throws IOException, SQLException {
        multipartFile = createMockMultiPartFile("leadlet-demo-3310b024322a.json");
        multipartFileBig = createMockMultiPartFile("bigPdfFile.pdf");


    }

    private MockMultipartFile createMockMultiPartFile(String resourceName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(resourceName);

        String name = resourceName;
        String originalFileName = resourceName;
        String contentType = "text/plain";
        byte[] content = IOUtils.toByteArray(inputStream);

        MockMultipartFile file = new MockMultipartFile(name,
            originalFileName, contentType, content);

        return  file;
    }

    @Test
    public void saveAppAccountWithStoragePreference() throws IOException, SQLException {

        StoragePreferenceDTO storagePreferenceDTO = new StoragePreferenceDTO();
        storagePreferenceDTO.setType(StorageType.Values.GOOGLE_STORAGE);
        storagePreferenceDTO.setGsBucketName("test-bucket-name");

        AppAccountDTO appAccountDTO = new AppAccountDTO();
        appAccountDTO.setName("test-app-account");
        appAccountDTO.setStoragePreference(storagePreferenceDTO);

        AppAccount savedAppAccount = appAccountService.save(appAccountDTO,multipartFile);

        assertThat(savedAppAccount.getStoragePreference()).isNotNull();
        assertThat(savedAppAccount.getStoragePreference().getType()).isEqualTo(StorageType.GOOGLE_STORAGE);
        assertThat(savedAppAccount.getStoragePreference().getGsBucketName()).isEqualTo("test-bucket-name");
        assertThat(IOUtils.contentEquals(savedAppAccount.getStoragePreference().getGsKeyFile().getBinaryStream(),multipartFile.getInputStream())).isTrue();
        assertThat(savedAppAccount.getStoragePreference().getGsKeyFileName()).isEqualTo("leadlet-demo-3310b024322a.json");

    }

    @Test
    public void updateAppAccountWithStoragePreference() throws IOException, SQLException {

        StoragePreferenceDTO storagePreferenceDTO = new StoragePreferenceDTO();
        storagePreferenceDTO.setType(StorageType.Values.GOOGLE_STORAGE);
        storagePreferenceDTO.setGsBucketName("test-bucket-name");

        AppAccountDTO appAccountDTO = new AppAccountDTO();
        appAccountDTO.setName("test-app-account");
        appAccountDTO.setStoragePreference(storagePreferenceDTO);

        AppAccount savedAppAccount = appAccountService.save(appAccountDTO,multipartFile);

        assertThat(savedAppAccount.getStoragePreference()).isNotNull();
        assertThat(savedAppAccount.getStoragePreference().getType()).isEqualTo(StorageType.GOOGLE_STORAGE);
        assertThat(savedAppAccount.getStoragePreference().getGsBucketName()).isEqualTo("test-bucket-name");
        assertThat(IOUtils.contentEquals(savedAppAccount.getStoragePreference().getGsKeyFile().getBinaryStream(),multipartFile.getInputStream())).isTrue();
        assertThat(savedAppAccount.getStoragePreference().getGsKeyFileName()).isEqualTo("leadlet-demo-3310b024322a.json");
        assertThat(savedAppAccount.getStoragePreference().getId()).isEqualTo(1);

        savedAppAccount = appAccountService.save(appAccountMapper.toDto(savedAppAccount),multipartFile);
        assertThat(savedAppAccount.getStoragePreference().getId()).isEqualTo(1);

    }

    @Test
    public void failedToSaveAppAccountWithBigGsKeyFile() throws IOException, SQLException {

        StoragePreferenceDTO storagePreferenceDTO = new StoragePreferenceDTO();
        storagePreferenceDTO.setType(StorageType.Values.GOOGLE_STORAGE);
        storagePreferenceDTO.setGsBucketName("test-bucket-name-big");

        AppAccountDTO appAccountDTO = new AppAccountDTO();
        appAccountDTO.setName("test-app-account");
        appAccountDTO.setStoragePreference(storagePreferenceDTO);

        assertThatThrownBy(() -> appAccountService.save(appAccountDTO,multipartFileBig) )
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Key file too big");
    }

    @Test
    public void mapAppAccountWithoutGsKeyFile() throws IOException, SQLException {

        StoragePreferenceDTO storagePreferenceDTO = new StoragePreferenceDTO();
        storagePreferenceDTO.setType(StorageType.Values.GOOGLE_STORAGE);
        storagePreferenceDTO.setGsBucketName("test-bucket-name");

        AppAccountDTO appAccountDTO = new AppAccountDTO();
        appAccountDTO.setName("test-app-account");
        appAccountDTO.setStoragePreference(storagePreferenceDTO);

        AppAccount savedAppAccount = appAccountService.save(appAccountDTO,null);

        AppAccountDTO savedAppAccountDTO = appAccountMapper.toDto(savedAppAccount);

        assertThat(savedAppAccountDTO.getStoragePreference().getGsBucketName()).isEqualTo("test-bucket-name");
        assertThat(savedAppAccountDTO.getStoragePreference().getType()).isEqualTo(StorageType.Values.GOOGLE_STORAGE);


    }


}
