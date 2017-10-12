package com.leadlet.web.rest;

import com.leadlet.LeadletApiApp;

import com.leadlet.domain.AppAccount;
import com.leadlet.domain.ContactEmail;
import com.leadlet.repository.AppAccountRepository;
import com.leadlet.repository.ContactEmailRepository;
import com.leadlet.service.ContactEmailService;
import com.leadlet.service.dto.ContactEmailDTO;
import com.leadlet.service.mapper.ContactEmailMapper;
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

import com.leadlet.domain.enumeration.EmailType;
/**
 * Test class for the ContactEmailResource REST controller.
 *
 * @see ContactEmailResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
public class ContactEmailResourceIntTest {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final EmailType DEFAULT_TYPE = EmailType.HOME;
    private static final EmailType UPDATED_TYPE = EmailType.WORK;

    @Autowired
    private ContactEmailRepository contactEmailRepository;

    @Autowired
    private AppAccountRepository appAccountRepository;

    @Autowired
    private ContactEmailMapper contactEmailMapper;

    @Autowired
    private ContactEmailService contactEmailService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContactEmailMockMvc;

    private ContactEmail contactEmail;
    private AppAccount xCompanyAppAccount;
    private AppAccount yCompanyAppAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContactEmailResource contactEmailResource = new ContactEmailResource(contactEmailService);
        this.restContactEmailMockMvc = MockMvcBuilders.standaloneSetup(contactEmailResource)
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
    public static ContactEmail createEntity(EntityManager em) {
        ContactEmail contactEmail = new ContactEmail()
            .email(DEFAULT_EMAIL)
            .type(DEFAULT_TYPE);
        return contactEmail;
    }

    @Before
    public void initTest() {
        contactEmail = createEntity(em);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser")
    public void createContactEmail() throws Exception {
        int databaseSizeBeforeCreate = contactEmailRepository.findAll().size();

        // Create the ContactEmail
        ContactEmailDTO contactEmailDTO = contactEmailMapper.toDto(contactEmail);
        restContactEmailMockMvc.perform(post("/api/contact-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactEmailDTO)))
            .andExpect(status().isCreated());

        // Validate the ContactEmail in the database
        List<ContactEmail> contactEmailList = contactEmailRepository.findAll();
        assertThat(contactEmailList).hasSize(databaseSizeBeforeCreate + 1);
        ContactEmail testContactEmail = contactEmailList.get(contactEmailList.size() - 1);
        assertThat(testContactEmail.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContactEmail.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testContactEmail.getAppAccount()).isEqualTo(xCompanyAppAccount);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser")
    public void createContactEmailWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactEmailRepository.findAll().size();

        // Create the ContactEmail with an existing ID
        contactEmail.setId(1L);
        ContactEmailDTO contactEmailDTO = contactEmailMapper.toDto(contactEmail);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactEmailMockMvc.perform(post("/api/contact-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactEmailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ContactEmail> contactEmailList = contactEmailRepository.findAll();
        assertThat(contactEmailList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser")
    public void getAllContactEmails() throws Exception {

        ContactEmail contactEmailX1 = new ContactEmail();
        contactEmailX1.setEmail("contactEmailX1");
        contactEmailX1.setType(DEFAULT_TYPE);
        contactEmailX1.setAppAccount(xCompanyAppAccount);
        contactEmailX1 = contactEmailRepository.saveAndFlush(contactEmailX1);

        ContactEmail contactEmailX2 = new ContactEmail();
        contactEmailX2.setEmail("contactEmailX2");
        contactEmailX2.setType(DEFAULT_TYPE);
        contactEmailX2.setAppAccount(xCompanyAppAccount);
        contactEmailX2 = contactEmailRepository.saveAndFlush(contactEmailX2);

        ContactEmail contactEmailY1 = new ContactEmail();
        contactEmailY1.setEmail("contactEmailY1");
        contactEmailY1.setType(DEFAULT_TYPE);
        contactEmailY1.setAppAccount(yCompanyAppAccount);
        contactEmailY1 = contactEmailRepository.saveAndFlush(contactEmailY1);

        // Get all the contactEmailList
        restContactEmailMockMvc.perform(get("/api/contact-emails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[0].id").value(contactEmailX2.getId()))
            .andExpect(jsonPath("$.[0].email").value(contactEmailX2.getEmail()))
            .andExpect(jsonPath("$.[0].type").value(contactEmailX2.getType()))
            .andExpect(jsonPath("$.[1].id").value(contactEmailX1.getId()))
            .andExpect(jsonPath("$.[1].email").value(contactEmailX1.getEmail()))
            .andExpect(jsonPath("$.[1].type").value(contactEmailX1.getType()));
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser")
    public void getContactEmail() throws Exception {

        ContactEmail contactEmailX1 = new ContactEmail();
        contactEmailX1.setEmail("contactEmailX1");
        contactEmailX1.setType(DEFAULT_TYPE);
        contactEmailX1.setAppAccount(xCompanyAppAccount);
        contactEmailX1 = contactEmailRepository.saveAndFlush(contactEmailX1);

        // Get the contactEmail
        restContactEmailMockMvc.perform(get("/api/contact-emails/{id}", contactEmailX1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contactEmail.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContactEmail() throws Exception {
        // Get the contactEmail
        restContactEmailMockMvc.perform(get("/api/contact-emails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactEmail() throws Exception {
        // Initialize the database
        contactEmailRepository.saveAndFlush(contactEmail);
        int databaseSizeBeforeUpdate = contactEmailRepository.findAll().size();

        // Update the contactEmail
        ContactEmail updatedContactEmail = contactEmailRepository.findOne(contactEmail.getId());
        updatedContactEmail
            .email(UPDATED_EMAIL)
            .type(UPDATED_TYPE);
        ContactEmailDTO contactEmailDTO = contactEmailMapper.toDto(updatedContactEmail);

        restContactEmailMockMvc.perform(put("/api/contact-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactEmailDTO)))
            .andExpect(status().isOk());

        // Validate the ContactEmail in the database
        List<ContactEmail> contactEmailList = contactEmailRepository.findAll();
        assertThat(contactEmailList).hasSize(databaseSizeBeforeUpdate);
        ContactEmail testContactEmail = contactEmailList.get(contactEmailList.size() - 1);
        assertThat(testContactEmail.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContactEmail.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingContactEmail() throws Exception {
        int databaseSizeBeforeUpdate = contactEmailRepository.findAll().size();

        // Create the ContactEmail
        ContactEmailDTO contactEmailDTO = contactEmailMapper.toDto(contactEmail);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContactEmailMockMvc.perform(put("/api/contact-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactEmailDTO)))
            .andExpect(status().isCreated());

        // Validate the ContactEmail in the database
        List<ContactEmail> contactEmailList = contactEmailRepository.findAll();
        assertThat(contactEmailList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContactEmail() throws Exception {
        // Initialize the database
        contactEmailRepository.saveAndFlush(contactEmail);
        int databaseSizeBeforeDelete = contactEmailRepository.findAll().size();

        // Get the contactEmail
        restContactEmailMockMvc.perform(delete("/api/contact-emails/{id}", contactEmail.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContactEmail> contactEmailList = contactEmailRepository.findAll();
        assertThat(contactEmailList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactEmail.class);
        ContactEmail contactEmail1 = new ContactEmail();
        contactEmail1.setId(1L);
        ContactEmail contactEmail2 = new ContactEmail();
        contactEmail2.setId(contactEmail1.getId());
        assertThat(contactEmail1).isEqualTo(contactEmail2);
        contactEmail2.setId(2L);
        assertThat(contactEmail1).isNotEqualTo(contactEmail2);
        contactEmail1.setId(null);
        assertThat(contactEmail1).isNotEqualTo(contactEmail2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactEmailDTO.class);
        ContactEmailDTO contactEmailDTO1 = new ContactEmailDTO();
        contactEmailDTO1.setId(1L);
        ContactEmailDTO contactEmailDTO2 = new ContactEmailDTO();
        assertThat(contactEmailDTO1).isNotEqualTo(contactEmailDTO2);
        contactEmailDTO2.setId(contactEmailDTO1.getId());
        assertThat(contactEmailDTO1).isEqualTo(contactEmailDTO2);
        contactEmailDTO2.setId(2L);
        assertThat(contactEmailDTO1).isNotEqualTo(contactEmailDTO2);
        contactEmailDTO1.setId(null);
        assertThat(contactEmailDTO1).isNotEqualTo(contactEmailDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contactEmailMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contactEmailMapper.fromId(null)).isNull();
    }
}
