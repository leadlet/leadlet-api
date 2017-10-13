package com.leadlet.web.rest;

import com.leadlet.LeadletApiApp;

import com.leadlet.domain.AppAccount;
import com.leadlet.domain.ContactPhone;
import com.leadlet.repository.AppAccountRepository;
import com.leadlet.repository.ContactPhoneRepository;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.ContactPhoneService;
import com.leadlet.service.dto.ContactPhoneDTO;
import com.leadlet.service.mapper.ContactPhoneMapper;
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
import org.springframework.security.test.context.support.WithUserDetails;
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

import com.leadlet.domain.enumeration.PhoneType;
/**
 * Test class for the ContactPhoneResource REST controller.
 *
 * @see ContactPhoneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
public class ContactPhoneResourceIntTest {

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final PhoneType DEFAULT_TYPE = PhoneType.HOME;
    private static final PhoneType UPDATED_TYPE = PhoneType.WORK;

    @Autowired
    private ContactPhoneRepository contactPhoneRepository;

    @Autowired
    private AppAccountRepository appAccountRepository;

    @Autowired
    private ContactPhoneMapper contactPhoneMapper;

    @Autowired
    private ContactPhoneService contactPhoneService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContactPhoneMockMvc;

    private ContactPhone contactPhone;
    private AppAccount xCompanyAppAccount;
    private AppAccount yCompanyAppAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContactPhoneResource contactPhoneResource = new ContactPhoneResource(contactPhoneService);
        this.restContactPhoneMockMvc = MockMvcBuilders.standaloneSetup(contactPhoneResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void setAppAccountsUsers() {
        this.xCompanyAppAccount = this.appAccountRepository.findOneByName("CompanyX").get();
        this.yCompanyAppAccount = this.appAccountRepository.findOneByName("CompanyY").get();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactPhone createEntity(EntityManager em) {
        ContactPhone contactPhone = new ContactPhone()
            .phone(DEFAULT_PHONE)
            .type(DEFAULT_TYPE);
        return contactPhone;
    }

    @Before
    public void initTest() {
        contactPhone = createEntity(em);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void createContactPhone() throws Exception {
        int databaseSizeBeforeCreate = contactPhoneRepository.findAll().size();

        // Create the ContactPhone
        ContactPhoneDTO contactPhoneDTO = contactPhoneMapper.toDto(contactPhone);
        restContactPhoneMockMvc.perform(post("/api/contact-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactPhoneDTO)))
            .andExpect(status().isCreated());

        // Validate the ContactPhone in the database
        List<ContactPhone> contactPhoneList = contactPhoneRepository.findAll();
        assertThat(contactPhoneList).hasSize(databaseSizeBeforeCreate + 1);
        ContactPhone testContactPhone = contactPhoneList.get(contactPhoneList.size() - 1);
        assertThat(testContactPhone.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testContactPhone.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testContactPhone.getAppAccount()).isEqualTo(xCompanyAppAccount);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void createContactPhoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactPhoneRepository.findAll().size();

        // Create the ContactPhone with an existing ID
        contactPhone.setId(1L);
        ContactPhoneDTO contactPhoneDTO = contactPhoneMapper.toDto(contactPhone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactPhoneMockMvc.perform(post("/api/contact-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactPhoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ContactPhone> contactPhoneList = contactPhoneRepository.findAll();
        assertThat(contactPhoneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void getAllContactPhones() throws Exception {

        ContactPhone contactPhoneX1 = new ContactPhone();
        contactPhoneX1.setPhone("contactPhoneX1");
        contactPhoneX1.setType(DEFAULT_TYPE);
        contactPhoneX1.setAppAccount(xCompanyAppAccount);
        contactPhoneX1 = contactPhoneRepository.saveAndFlush(contactPhoneX1);

        ContactPhone contactPhoneX2 = new ContactPhone();
        contactPhoneX2.setPhone("contactPhoneX2");
        contactPhoneX2.setType(DEFAULT_TYPE);
        contactPhoneX2.setAppAccount(xCompanyAppAccount);
        contactPhoneX2 = contactPhoneRepository.saveAndFlush(contactPhoneX2);


        // Get all the contactPhoneList
        restContactPhoneMockMvc.perform(get("/api/contact-phones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[0].id").value(contactPhoneX2.getId()))
            .andExpect(jsonPath("$.[0].phone").value(contactPhoneX2.getPhone()))
            .andExpect(jsonPath("$.[0].type").value(contactPhoneX2.getType().name().toString()))
            .andExpect(jsonPath("$.[1].id").value(contactPhoneX1.getId()))
            .andExpect(jsonPath("$.[1].phone").value(contactPhoneX1.getPhone()))
            .andExpect(jsonPath("$.[1].type").value(contactPhoneX1.getType().name().toString()));
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void getContactPhone() throws Exception {

        ContactPhone contactPhoneX1 = new ContactPhone();
        contactPhoneX1.setPhone("contactPhoneX1");
        contactPhoneX1.setType(DEFAULT_TYPE);
        contactPhoneX1.setAppAccount(xCompanyAppAccount);
        contactPhoneX1 = contactPhoneRepository.saveAndFlush(contactPhoneX1);

        // Get the contactPhone
        restContactPhoneMockMvc.perform(get("/api/contact-phones/{id}", contactPhoneX1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contactPhoneX1.getId()))
            .andExpect(jsonPath("$.phone").value(contactPhoneX1.getPhone()))
            .andExpect(jsonPath("$.type").value(contactPhoneX1.getType().name().toString()));
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void getContactPhoneForOtherAccount() throws Exception {

        ContactPhone contactPhoneY1 = new ContactPhone();
        contactPhoneY1.setPhone("contactPhoneX1");
        contactPhoneY1.setType(DEFAULT_TYPE);
        contactPhoneY1.setAppAccount(yCompanyAppAccount);
        contactPhoneY1 = contactPhoneRepository.saveAndFlush(contactPhoneY1);

        restContactPhoneMockMvc.perform(get("/api/contact-phones/{id}", contactPhoneY1.getId()))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void getNonExistingContactPhone() throws Exception {
        // Get the contactPhone
        restContactPhoneMockMvc.perform(get("/api/contact-phones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void updateContactPhone() throws Exception {

        ContactPhone contactPhoneX1 = new ContactPhone();
        contactPhoneX1.setPhone("contactPhoneX1");
        contactPhoneX1.setType(DEFAULT_TYPE);
        contactPhoneX1.setAppAccount(xCompanyAppAccount);
        contactPhoneX1 = contactPhoneRepository.saveAndFlush(contactPhoneX1);

        int databaseSizeBeforeUpdate = contactPhoneRepository.findAll().size();

        // Update the contactPhone
        ContactPhone updatedContactPhone = contactPhoneRepository.findOne(contactPhoneX1.getId());
        updatedContactPhone
            .phone(UPDATED_PHONE)
            .type(UPDATED_TYPE);
        ContactPhoneDTO contactPhoneDTO = contactPhoneMapper.toDto(updatedContactPhone);

        restContactPhoneMockMvc.perform(put("/api/contact-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactPhoneDTO)))
            .andExpect(status().isOk());

        // Validate the ContactPhone in the database
        List<ContactPhone> contactPhoneList = contactPhoneRepository.findAll();
        assertThat(contactPhoneList).hasSize(databaseSizeBeforeUpdate);
        ContactPhone testContactPhone = contactPhoneList.get(contactPhoneList.size() - 1);
        assertThat(testContactPhone.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testContactPhone.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void updateContactPhoneForOtherAccount() throws Exception {

        ContactPhone contactPhoneY1 = new ContactPhone();
        contactPhoneY1.setPhone("contactPhoneX1");
        contactPhoneY1.setType(DEFAULT_TYPE);
        contactPhoneY1.setAppAccount(yCompanyAppAccount);
        contactPhoneY1 = contactPhoneRepository.saveAndFlush(contactPhoneY1);

        int databaseSizeBeforeUpdate = contactPhoneRepository.findAll().size();

        // Update the contactPhone
        ContactPhone updatedContactPhone = contactPhoneRepository.findOne(contactPhoneY1.getId());
        updatedContactPhone
            .phone(UPDATED_PHONE)
            .type(UPDATED_TYPE);
        ContactPhoneDTO contactPhoneDTO = contactPhoneMapper.toDto(updatedContactPhone);

        restContactPhoneMockMvc.perform(put("/api/contact-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactPhoneDTO)))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void updateNonExistingContactPhone() throws Exception {
        int databaseSizeBeforeUpdate = contactPhoneRepository.findAll().size();

        // Create the ContactPhone
        ContactPhoneDTO contactPhoneDTO = contactPhoneMapper.toDto(contactPhone);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContactPhoneMockMvc.perform(put("/api/contact-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactPhoneDTO)))
            .andExpect(status().isNotFound());

        // Validate the ContactPhone in the database
        List<ContactPhone> contactPhoneList = contactPhoneRepository.findAll();
        assertThat(contactPhoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void deleteContactPhone() throws Exception {

        ContactPhone contactPhoneX1 = new ContactPhone();
        contactPhoneX1.setPhone("contactPhoneX1");
        contactPhoneX1.setType(DEFAULT_TYPE);
        contactPhoneX1.setAppAccount(xCompanyAppAccount);
        contactPhoneX1 = contactPhoneRepository.saveAndFlush(contactPhoneX1);

        int databaseSizeBeforeDelete = contactPhoneRepository.findAll().size();

        // Get the contactPhone
        restContactPhoneMockMvc.perform(delete("/api/contact-phones/{id}", contactPhoneX1.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContactPhone> contactPhoneList = contactPhoneRepository.findAll();
        assertThat(contactPhoneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    @WithUserDetails("ycompanyadminuser@spacex.com")
    public void deleteContactPhoneForOtherAccount() throws Exception {

        ContactPhone contactPhoneX1 = new ContactPhone();
        contactPhoneX1.setPhone("contactPhoneX1");
        contactPhoneX1.setType(DEFAULT_TYPE);
        contactPhoneX1.setAppAccount(xCompanyAppAccount);
        contactPhoneX1 = contactPhoneRepository.saveAndFlush(contactPhoneX1);

        int databaseSizeBeforeDelete = contactPhoneRepository.findAll().size();

        // Get the contactPhone
        restContactPhoneMockMvc.perform(delete("/api/contact-phones/{id}", contactPhoneX1.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactPhone.class);
        ContactPhone contactPhone1 = new ContactPhone();
        contactPhone1.setId(1L);
        ContactPhone contactPhone2 = new ContactPhone();
        contactPhone2.setId(contactPhone1.getId());
        assertThat(contactPhone1).isEqualTo(contactPhone2);
        contactPhone2.setId(2L);
        assertThat(contactPhone1).isNotEqualTo(contactPhone2);
        contactPhone1.setId(null);
        assertThat(contactPhone1).isNotEqualTo(contactPhone2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactPhoneDTO.class);
        ContactPhoneDTO contactPhoneDTO1 = new ContactPhoneDTO();
        contactPhoneDTO1.setId(1L);
        ContactPhoneDTO contactPhoneDTO2 = new ContactPhoneDTO();
        assertThat(contactPhoneDTO1).isNotEqualTo(contactPhoneDTO2);
        contactPhoneDTO2.setId(contactPhoneDTO1.getId());
        assertThat(contactPhoneDTO1).isEqualTo(contactPhoneDTO2);
        contactPhoneDTO2.setId(2L);
        assertThat(contactPhoneDTO1).isNotEqualTo(contactPhoneDTO2);
        contactPhoneDTO1.setId(null);
        assertThat(contactPhoneDTO1).isNotEqualTo(contactPhoneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contactPhoneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contactPhoneMapper.fromId(null)).isNull();
    }
}
