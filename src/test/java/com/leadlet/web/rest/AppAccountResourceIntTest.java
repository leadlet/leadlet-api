package com.leadlet.web.rest;

import com.leadlet.LeadletApiApp;

import com.leadlet.domain.AppAccount;
import com.leadlet.repository.AppAccountRepository;
import com.leadlet.service.AppAccountService;
import com.leadlet.service.dto.AppAccountDTO;
import com.leadlet.service.mapper.AppAccountMapper;
import com.leadlet.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AppAccountResource REST controller.
 *
 * @see AppAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
public class AppAccountResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private AppAccountRepository appAccountRepository;

    @Autowired
    private AppAccountMapper appAccountMapper;

    @Autowired
    private AppAccountService appAccountService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAppAccountMockMvc;

    private AppAccount appAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AppAccountResource appAccountResource = new AppAccountResource(appAccountService);
        this.restAppAccountMockMvc = MockMvcBuilders.standaloneSetup(appAccountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppAccount createEntity(EntityManager em) {
        AppAccount appAccount = new AppAccount()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS);
        return appAccount;
    }

    @Before
    public void initTest() {
        appAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppAccount() throws Exception {
        int databaseSizeBeforeCreate = appAccountRepository.findAll().size();

        // Create the AppAccount
        AppAccountDTO appAccountDTO = appAccountMapper.toDto(appAccount);
        restAppAccountMockMvc.perform(post("/api/app-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the AppAccount in the database
        List<AppAccount> appAccountList = appAccountRepository.findAll();
        assertThat(appAccountList).hasSize(databaseSizeBeforeCreate + 1);
        AppAccount testAppAccount = appAccountList.get(appAccountList.size() - 1);
        assertThat(testAppAccount.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAppAccount.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createAppAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appAccountRepository.findAll().size();

        // Create the AppAccount with an existing ID
        appAccount.setId(1L);
        AppAccountDTO appAccountDTO = appAccountMapper.toDto(appAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppAccountMockMvc.perform(post("/api/app-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AppAccount> appAccountList = appAccountRepository.findAll();
        assertThat(appAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAppAccounts() throws Exception {
        // Initialize the database
        appAccountRepository.saveAndFlush(appAccount);

        // Get all the appAccountList
        restAppAccountMockMvc.perform(get("/api/app-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())));
    }

    @Test
    @Transactional
    public void getAppAccount() throws Exception {
        // Initialize the database
        appAccountRepository.saveAndFlush(appAccount);

        // Get the appAccount
        restAppAccountMockMvc.perform(get("/api/app-accounts/{id}", appAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appAccount.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAppAccount() throws Exception {
        // Get the appAccount
        restAppAccountMockMvc.perform(get("/api/app-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppAccount() throws Exception {
        // Initialize the database
        appAccountRepository.saveAndFlush(appAccount);
        int databaseSizeBeforeUpdate = appAccountRepository.findAll().size();

        // Update the appAccount
        AppAccount updatedAppAccount = appAccountRepository.findOne(appAccount.getId());
        updatedAppAccount
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS);
        AppAccountDTO appAccountDTO = appAccountMapper.toDto(updatedAppAccount);

        restAppAccountMockMvc.perform(put("/api/app-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appAccountDTO)))
            .andExpect(status().isOk());

        // Validate the AppAccount in the database
        List<AppAccount> appAccountList = appAccountRepository.findAll();
        assertThat(appAccountList).hasSize(databaseSizeBeforeUpdate);
        AppAccount testAppAccount = appAccountList.get(appAccountList.size() - 1);
        assertThat(testAppAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppAccount.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingAppAccount() throws Exception {
        int databaseSizeBeforeUpdate = appAccountRepository.findAll().size();

        // Create the AppAccount
        AppAccountDTO appAccountDTO = appAccountMapper.toDto(appAccount);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAppAccountMockMvc.perform(put("/api/app-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the AppAccount in the database
        List<AppAccount> appAccountList = appAccountRepository.findAll();
        assertThat(appAccountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAppAccount() throws Exception {
        // Initialize the database
        appAccountRepository.saveAndFlush(appAccount);
        int databaseSizeBeforeDelete = appAccountRepository.findAll().size();

        // Get the appAccount
        restAppAccountMockMvc.perform(delete("/api/app-accounts/{id}", appAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AppAccount> appAccountList = appAccountRepository.findAll();
        assertThat(appAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppAccount.class);
        AppAccount appAccount1 = new AppAccount();
        appAccount1.setId(1L);
        AppAccount appAccount2 = new AppAccount();
        appAccount2.setId(appAccount1.getId());
        assertThat(appAccount1).isEqualTo(appAccount2);
        appAccount2.setId(2L);
        assertThat(appAccount1).isNotEqualTo(appAccount2);
        appAccount1.setId(null);
        assertThat(appAccount1).isNotEqualTo(appAccount2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppAccountDTO.class);
        AppAccountDTO appAccountDTO1 = new AppAccountDTO();
        appAccountDTO1.setId(1L);
        AppAccountDTO appAccountDTO2 = new AppAccountDTO();
        assertThat(appAccountDTO1).isNotEqualTo(appAccountDTO2);
        appAccountDTO2.setId(appAccountDTO1.getId());
        assertThat(appAccountDTO1).isEqualTo(appAccountDTO2);
        appAccountDTO2.setId(2L);
        assertThat(appAccountDTO1).isNotEqualTo(appAccountDTO2);
        appAccountDTO1.setId(null);
        assertThat(appAccountDTO1).isNotEqualTo(appAccountDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(appAccountMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(appAccountMapper.fromId(null)).isNull();
    }
}
