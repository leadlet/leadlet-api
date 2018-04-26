package com.leadlet.service.impl;

import com.leadlet.domain.StoragePreference;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Test class for the AppAccountService.
 *
 * @see GoogleStorageServiceTest
 */
@RunWith(SpringRunner.class)
public class GoogleStorageServiceTest {

    private GoogleStorageService googleStorageService;

    private StoragePreference storagePreferenceMock;

    private MultipartFile multipartFile;
    private Blob googleJsonKeyBlob;


    @Before
    public void setUp() throws IOException, SQLException {

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("logback.xml");

        String name = "logback.xml";
        String originalFileName = "logback.xml";
        String contentType = "text/plain";
        byte[] content = IOUtils.toByteArray(inputStream);

        multipartFile = new MockMultipartFile(name,
            originalFileName, contentType, content);

        InputStream blobInputStream = classLoader.getResourceAsStream("leadlet-demo-3310b024322a.json");

        googleJsonKeyBlob = new SerialBlob(StreamUtils.copyToByteArray(blobInputStream));

        storagePreferenceMock = mock(StoragePreference.class);
        googleStorageService = new GoogleStorageService(storagePreferenceMock);
        Mockito.when(storagePreferenceMock.getGsKeyFile()).thenReturn(googleJsonKeyBlob);
        Mockito.when(storagePreferenceMock.getGsBucketName()).thenReturn("leadlet-documents");

        googleStorageService.init();

    }


    @Test
    public void uploadFileToGoogleStorage() throws IOException, SQLException {
        String url = googleStorageService.upload(multipartFile);


    }

    @Test
    public void deleteFileFromGoogleStorage() throws IOException, SQLException {
        String url = googleStorageService.upload(multipartFile);

        boolean deleted = googleStorageService.delete(multipartFile.getName());

        assertThat(deleted).isTrue();

    }
}
