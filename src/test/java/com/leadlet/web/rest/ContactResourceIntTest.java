package com.leadlet.web.rest;

import com.leadlet.LeadletApiApp;

import com.leadlet.domain.AppAccount;
import com.leadlet.domain.Contact;
import com.leadlet.repository.AppAccountRepository;
import com.leadlet.repository.ContactRepository;
import com.leadlet.repository.util.SearchCriteria;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.ContactService;
import com.leadlet.service.dto.ContactDTO;
import com.leadlet.service.mapper.ContactMapper;
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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.leadlet.domain.enumeration.ContactType;
/**
 * Test class for the ContactResource REST controller.
 *
 * @see ContactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
public class ContactResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final ContactType DEFAULT_TYPE = ContactType.PERSON;
    private static final ContactType UPDATED_TYPE = ContactType.ORGANIZATION;

    private static final Boolean DEFAULT_IS_CONTACT_PERSON = false;
    private static final Boolean UPDATED_IS_CONTACT_PERSON = true;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AppAccountRepository appAccountRepository;

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private ContactService contactService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContactMockMvc;

    private Contact contact;
    private AppAccount xCompanyAppAccount;
    private AppAccount yCompanyAppAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContactResource contactResource = new ContactResource(contactService);
        this.restContactMockMvc = MockMvcBuilders.standaloneSetup(contactResource)
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
    public static Contact createEntity(EntityManager em) {
        Contact contact = new Contact()
            .name(DEFAULT_NAME)
            .location(DEFAULT_LOCATION)
            .type(DEFAULT_TYPE)
            .isContactPerson(DEFAULT_IS_CONTACT_PERSON);
        return contact;
    }

    @Before
    public void initTest() {
        contact = createEntity(em);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void createContact() throws Exception {
        int databaseSizeBeforeCreate = contactRepository.findAll().size();

        // Create the Contact
        ContactDTO contactDTO = contactMapper.toDto(contact);
        restContactMockMvc.perform(post("/api/contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isCreated());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeCreate + 1);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContact.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testContact.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testContact.isIsContactPerson()).isEqualTo(DEFAULT_IS_CONTACT_PERSON);
        assertThat(testContact.getAppAccount()).isEqualTo(xCompanyAppAccount);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void createContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactRepository.findAll().size();

        // Create the Contact with an existing ID
        contact.setId(1L);
        ContactDTO contactDTO = contactMapper.toDto(contact);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactMockMvc.perform(post("/api/contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void getAllContacts() throws Exception {

        Contact contactX1 = new Contact();
        contactX1.setName("contactX1");
        contactX1.setLocation("contactX1Location");
        contactX1.setType(DEFAULT_TYPE);
        contactX1.setIsContactPerson(DEFAULT_IS_CONTACT_PERSON);
        contactX1.setAppAccount(xCompanyAppAccount);
        contactX1 = contactRepository.saveAndFlush(contactX1);

        Contact contactX2 = new Contact();
        contactX2.setName("contactX2");
        contactX2.setLocation("contactX2Location");
        contactX2.setType(DEFAULT_TYPE);
        contactX2.setIsContactPerson(DEFAULT_IS_CONTACT_PERSON);
        contactX2.setAppAccount(xCompanyAppAccount);
        contactX2 = contactRepository.saveAndFlush(contactX2);

        Contact contactY1 = new Contact();
        contactY1.setName("contactY1");
        contactY1.setLocation("contactY1Location");
        contactY1.setType(DEFAULT_TYPE);
        contactY1.setIsContactPerson(DEFAULT_IS_CONTACT_PERSON);
        contactY1.setAppAccount(yCompanyAppAccount);
        contactY1 = contactRepository.saveAndFlush(contactY1);

        // Get all the contactList
        restContactMockMvc.perform(get("/api/contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[0].id").value(contactX2.getId()))
            .andExpect(jsonPath("$.[0].name").value(contactX2.getName()))
            .andExpect(jsonPath("$.[0].location").value(contactX2.getLocation()))
            .andExpect(jsonPath("$.[0].type").value(contactX2.getType().name().toString()))
            .andExpect(jsonPath("$.[0].isContactPerson").value(contactX2.getContactPerson().toString()))
            .andExpect(jsonPath("$.[1].id").value(contactX1.getId()))
            .andExpect(jsonPath("$.[1].name").value(contactX1.getName()))
            .andExpect(jsonPath("$.[1].location").value(contactX1.getLocation()))
            .andExpect(jsonPath("$.[1].type").value(contactX1.getType().name().toString()))
            .andExpect(jsonPath("$.[1].isContactPerson").value(contactX1.getContactPerson().toString()));
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void getAllContactsWithFilter() throws Exception {

        // Get all the contactList
        restContactMockMvc.perform(get("/api/contacts?sort=id,desc&filter=name:Jay%20Milburne,type:PERSON"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[0].name").value("Jay Milburne"))
        ;

    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void getContact() throws Exception {

        Contact contactX1 = new Contact();
        contactX1.setName("contactX1");
        contactX1.setLocation("contactX1Location");
        contactX1.setType(DEFAULT_TYPE);
        contactX1.setIsContactPerson(DEFAULT_IS_CONTACT_PERSON);
        contactX1.setAppAccount(xCompanyAppAccount);
        contactX1 = contactRepository.saveAndFlush(contactX1);

        // Get the contact
        restContactMockMvc.perform(get("/api/contacts/{id}", contactX1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contactX1.getId()))
            .andExpect(jsonPath("$.name").value(contactX1.getName()))
            .andExpect(jsonPath("$.location").value(contactX1.getLocation()))
            .andExpect(jsonPath("$.type").value(contactX1.getType().name().toString()))
            .andExpect(jsonPath("$.isContactPerson").value(contactX1.getContactPerson().toString()));
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void getContactForOtherAccount() throws Exception {

        Contact contactY1 = new Contact();
        contactY1.setName("contactY1");
        contactY1.setLocation("contactY1Location");
        contactY1.setType(DEFAULT_TYPE);
        contactY1.setIsContactPerson(DEFAULT_IS_CONTACT_PERSON);
        contactY1.setAppAccount(yCompanyAppAccount);
        contactY1 = contactRepository.saveAndFlush(contactY1);

        restContactMockMvc.perform(get("/api/contacts/{id}", contactY1.getId()))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void getNonExistingContact() throws Exception {
        // Get the contact
        restContactMockMvc.perform(get("/api/contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void updateContact() throws Exception {

        Contact contactX1 = new Contact();
        contactX1.setName("contactX1");
        contactX1.setLocation("contactX1Location");
        contactX1.setType(DEFAULT_TYPE);
        contactX1.setIsContactPerson(DEFAULT_IS_CONTACT_PERSON);
        contactX1.setAppAccount(xCompanyAppAccount);
        contactX1 = contactRepository.saveAndFlush(contactX1);

        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // Update the contact
        Contact updatedContact = contactRepository.findOne(contactX1.getId());
        updatedContact
            .name(UPDATED_NAME)
            .location(UPDATED_LOCATION)
            .type(UPDATED_TYPE)
            .isContactPerson(UPDATED_IS_CONTACT_PERSON);
        ContactDTO contactDTO = contactMapper.toDto(updatedContact);

        restContactMockMvc.perform(put("/api/contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isOk());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContact.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testContact.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testContact.isIsContactPerson()).isEqualTo(UPDATED_IS_CONTACT_PERSON);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void updateContactForOtherAccount() throws Exception {

        Contact contactY1 = new Contact();
        contactY1.setName("contactY1");
        contactY1.setLocation("contactY1Location");
        contactY1.setType(DEFAULT_TYPE);
        contactY1.setIsContactPerson(DEFAULT_IS_CONTACT_PERSON);
        contactY1.setAppAccount(yCompanyAppAccount);
        contactY1 = contactRepository.saveAndFlush(contactY1);

        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // Update the contact
        Contact updatedContact = contactRepository.findOne(contactY1.getId());
        updatedContact
            .name(UPDATED_NAME)
            .location(UPDATED_LOCATION)
            .type(UPDATED_TYPE)
            .isContactPerson(UPDATED_IS_CONTACT_PERSON);
        ContactDTO contactDTO = contactMapper.toDto(updatedContact);

        restContactMockMvc.perform(put("/api/contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void updateNonExistingContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // Create the Contact
        ContactDTO contactDTO = contactMapper.toDto(contact);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContactMockMvc.perform(put("/api/contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isNotFound());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void deleteContact() throws Exception {
        Contact contactX1 = new Contact();
        contactX1.setName("contactX1");
        contactX1.setLocation("contactX1Location");
        contactX1.setType(DEFAULT_TYPE);
        contactX1.setIsContactPerson(DEFAULT_IS_CONTACT_PERSON);
        contactX1.setAppAccount(xCompanyAppAccount);
        contactX1 = contactRepository.saveAndFlush(contactX1);

        int databaseSizeBeforeDelete = contactRepository.findAll().size();

        // Get the contact
        restContactMockMvc.perform(delete("/api/contacts/{id}", contactX1.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void deleteContactForOtherAccount() throws Exception {

        Contact contactY1 = new Contact();
        contactY1.setName("contactY1");
        contactY1.setLocation("contactY1Location");
        contactY1.setType(DEFAULT_TYPE);
        contactY1.setIsContactPerson(DEFAULT_IS_CONTACT_PERSON);
        contactY1.setAppAccount(yCompanyAppAccount);
        contactY1 = contactRepository.saveAndFlush(contactY1);

        int databaseSizeBeforeDelete = contactRepository.findAll().size();

        // Get the contact
        restContactMockMvc.perform(delete("/api/contacts/{id}", contactY1.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNotFound());
    }


    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contact.class);
        Contact contact1 = new Contact();
        contact1.setId(1L);
        Contact contact2 = new Contact();
        contact2.setId(contact1.getId());
        assertThat(contact1).isEqualTo(contact2);
        contact2.setId(2L);
        assertThat(contact1).isNotEqualTo(contact2);
        contact1.setId(null);
        assertThat(contact1).isNotEqualTo(contact2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactDTO.class);
        ContactDTO contactDTO1 = new ContactDTO();
        contactDTO1.setId(1L);
        ContactDTO contactDTO2 = new ContactDTO();
        assertThat(contactDTO1).isNotEqualTo(contactDTO2);
        contactDTO2.setId(contactDTO1.getId());
        assertThat(contactDTO1).isEqualTo(contactDTO2);
        contactDTO2.setId(2L);
        assertThat(contactDTO1).isNotEqualTo(contactDTO2);
        contactDTO1.setId(null);
        assertThat(contactDTO1).isNotEqualTo(contactDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contactMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contactMapper.fromId(null)).isNull();
    }
}
