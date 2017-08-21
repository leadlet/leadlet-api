package com.leadlet.web.rest;

import com.leadlet.LeadletApiApp;

import com.leadlet.domain.EmailTemplates;
import com.leadlet.repository.EmailTemplatesRepository;
import com.leadlet.service.EmailTemplatesService;
import com.leadlet.service.dto.EmailTemplatesDTO;
import com.leadlet.service.mapper.EmailTemplatesMapper;
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
 * Test class for the EmailTemplatesResource REST controller.
 *
 * @see EmailTemplatesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
public class EmailTemplatesResourceIntTest {

    private static final String DEFAULT_TEMPLATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE = "BBBBBBBBBB";

    @Autowired
    private EmailTemplatesRepository emailTemplatesRepository;

    @Autowired
    private EmailTemplatesMapper emailTemplatesMapper;

    @Autowired
    private EmailTemplatesService emailTemplatesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmailTemplatesMockMvc;

    private EmailTemplates emailTemplates;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmailTemplatesResource emailTemplatesResource = new EmailTemplatesResource(emailTemplatesService);
        this.restEmailTemplatesMockMvc = MockMvcBuilders.standaloneSetup(emailTemplatesResource)
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
    public static EmailTemplates createEntity(EntityManager em) {
        EmailTemplates emailTemplates = new EmailTemplates()
            .templateName(DEFAULT_TEMPLATE_NAME)
            .template(DEFAULT_TEMPLATE);
        return emailTemplates;
    }

    @Before
    public void initTest() {
        emailTemplates = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailTemplates() throws Exception {
        int databaseSizeBeforeCreate = emailTemplatesRepository.findAll().size();

        // Create the EmailTemplates
        EmailTemplatesDTO emailTemplatesDTO = emailTemplatesMapper.toDto(emailTemplates);
        restEmailTemplatesMockMvc.perform(post("/api/email-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailTemplatesDTO)))
            .andExpect(status().isCreated());

        // Validate the EmailTemplates in the database
        List<EmailTemplates> emailTemplatesList = emailTemplatesRepository.findAll();
        assertThat(emailTemplatesList).hasSize(databaseSizeBeforeCreate + 1);
        EmailTemplates testEmailTemplates = emailTemplatesList.get(emailTemplatesList.size() - 1);
        assertThat(testEmailTemplates.getTemplateName()).isEqualTo(DEFAULT_TEMPLATE_NAME);
        assertThat(testEmailTemplates.getTemplate()).isEqualTo(DEFAULT_TEMPLATE);
    }

    @Test
    @Transactional
    public void createEmailTemplatesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailTemplatesRepository.findAll().size();

        // Create the EmailTemplates with an existing ID
        emailTemplates.setId(1L);
        EmailTemplatesDTO emailTemplatesDTO = emailTemplatesMapper.toDto(emailTemplates);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailTemplatesMockMvc.perform(post("/api/email-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailTemplatesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EmailTemplates> emailTemplatesList = emailTemplatesRepository.findAll();
        assertThat(emailTemplatesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmailTemplates() throws Exception {
        // Initialize the database
        emailTemplatesRepository.saveAndFlush(emailTemplates);

        // Get all the emailTemplatesList
        restEmailTemplatesMockMvc.perform(get("/api/email-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailTemplates.getId().intValue())))
            .andExpect(jsonPath("$.[*].templateName").value(hasItem(DEFAULT_TEMPLATE_NAME.toString())))
            .andExpect(jsonPath("$.[*].template").value(hasItem(DEFAULT_TEMPLATE.toString())));
    }

    @Test
    @Transactional
    public void getEmailTemplates() throws Exception {
        // Initialize the database
        emailTemplatesRepository.saveAndFlush(emailTemplates);

        // Get the emailTemplates
        restEmailTemplatesMockMvc.perform(get("/api/email-templates/{id}", emailTemplates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emailTemplates.getId().intValue()))
            .andExpect(jsonPath("$.templateName").value(DEFAULT_TEMPLATE_NAME.toString()))
            .andExpect(jsonPath("$.template").value(DEFAULT_TEMPLATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmailTemplates() throws Exception {
        // Get the emailTemplates
        restEmailTemplatesMockMvc.perform(get("/api/email-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailTemplates() throws Exception {
        // Initialize the database
        emailTemplatesRepository.saveAndFlush(emailTemplates);
        int databaseSizeBeforeUpdate = emailTemplatesRepository.findAll().size();

        // Update the emailTemplates
        EmailTemplates updatedEmailTemplates = emailTemplatesRepository.findOne(emailTemplates.getId());
        updatedEmailTemplates
            .templateName(UPDATED_TEMPLATE_NAME)
            .template(UPDATED_TEMPLATE);
        EmailTemplatesDTO emailTemplatesDTO = emailTemplatesMapper.toDto(updatedEmailTemplates);

        restEmailTemplatesMockMvc.perform(put("/api/email-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailTemplatesDTO)))
            .andExpect(status().isOk());

        // Validate the EmailTemplates in the database
        List<EmailTemplates> emailTemplatesList = emailTemplatesRepository.findAll();
        assertThat(emailTemplatesList).hasSize(databaseSizeBeforeUpdate);
        EmailTemplates testEmailTemplates = emailTemplatesList.get(emailTemplatesList.size() - 1);
        assertThat(testEmailTemplates.getTemplateName()).isEqualTo(UPDATED_TEMPLATE_NAME);
        assertThat(testEmailTemplates.getTemplate()).isEqualTo(UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmailTemplates() throws Exception {
        int databaseSizeBeforeUpdate = emailTemplatesRepository.findAll().size();

        // Create the EmailTemplates
        EmailTemplatesDTO emailTemplatesDTO = emailTemplatesMapper.toDto(emailTemplates);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmailTemplatesMockMvc.perform(put("/api/email-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailTemplatesDTO)))
            .andExpect(status().isCreated());

        // Validate the EmailTemplates in the database
        List<EmailTemplates> emailTemplatesList = emailTemplatesRepository.findAll();
        assertThat(emailTemplatesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmailTemplates() throws Exception {
        // Initialize the database
        emailTemplatesRepository.saveAndFlush(emailTemplates);
        int databaseSizeBeforeDelete = emailTemplatesRepository.findAll().size();

        // Get the emailTemplates
        restEmailTemplatesMockMvc.perform(delete("/api/email-templates/{id}", emailTemplates.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmailTemplates> emailTemplatesList = emailTemplatesRepository.findAll();
        assertThat(emailTemplatesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailTemplates.class);
        EmailTemplates emailTemplates1 = new EmailTemplates();
        emailTemplates1.setId(1L);
        EmailTemplates emailTemplates2 = new EmailTemplates();
        emailTemplates2.setId(emailTemplates1.getId());
        assertThat(emailTemplates1).isEqualTo(emailTemplates2);
        emailTemplates2.setId(2L);
        assertThat(emailTemplates1).isNotEqualTo(emailTemplates2);
        emailTemplates1.setId(null);
        assertThat(emailTemplates1).isNotEqualTo(emailTemplates2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailTemplatesDTO.class);
        EmailTemplatesDTO emailTemplatesDTO1 = new EmailTemplatesDTO();
        emailTemplatesDTO1.setId(1L);
        EmailTemplatesDTO emailTemplatesDTO2 = new EmailTemplatesDTO();
        assertThat(emailTemplatesDTO1).isNotEqualTo(emailTemplatesDTO2);
        emailTemplatesDTO2.setId(emailTemplatesDTO1.getId());
        assertThat(emailTemplatesDTO1).isEqualTo(emailTemplatesDTO2);
        emailTemplatesDTO2.setId(2L);
        assertThat(emailTemplatesDTO1).isNotEqualTo(emailTemplatesDTO2);
        emailTemplatesDTO1.setId(null);
        assertThat(emailTemplatesDTO1).isNotEqualTo(emailTemplatesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(emailTemplatesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(emailTemplatesMapper.fromId(null)).isNull();
    }
}
