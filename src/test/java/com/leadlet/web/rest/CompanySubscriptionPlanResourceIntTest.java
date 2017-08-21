package com.leadlet.web.rest;

import com.leadlet.LeadletApiApp;

import com.leadlet.domain.CompanySubscriptionPlan;
import com.leadlet.repository.CompanySubscriptionPlanRepository;
import com.leadlet.service.CompanySubscriptionPlanService;
import com.leadlet.service.dto.CompanySubscriptionPlanDTO;
import com.leadlet.service.mapper.CompanySubscriptionPlanMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.leadlet.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CompanySubscriptionPlanResource REST controller.
 *
 * @see CompanySubscriptionPlanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
public class CompanySubscriptionPlanResourceIntTest {

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CompanySubscriptionPlanRepository companySubscriptionPlanRepository;

    @Autowired
    private CompanySubscriptionPlanMapper companySubscriptionPlanMapper;

    @Autowired
    private CompanySubscriptionPlanService companySubscriptionPlanService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanySubscriptionPlanMockMvc;

    private CompanySubscriptionPlan companySubscriptionPlan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CompanySubscriptionPlanResource companySubscriptionPlanResource = new CompanySubscriptionPlanResource(companySubscriptionPlanService);
        this.restCompanySubscriptionPlanMockMvc = MockMvcBuilders.standaloneSetup(companySubscriptionPlanResource)
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
    public static CompanySubscriptionPlan createEntity(EntityManager em) {
        CompanySubscriptionPlan companySubscriptionPlan = new CompanySubscriptionPlan()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return companySubscriptionPlan;
    }

    @Before
    public void initTest() {
        companySubscriptionPlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanySubscriptionPlan() throws Exception {
        int databaseSizeBeforeCreate = companySubscriptionPlanRepository.findAll().size();

        // Create the CompanySubscriptionPlan
        CompanySubscriptionPlanDTO companySubscriptionPlanDTO = companySubscriptionPlanMapper.toDto(companySubscriptionPlan);
        restCompanySubscriptionPlanMockMvc.perform(post("/api/company-subscription-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companySubscriptionPlanDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanySubscriptionPlan in the database
        List<CompanySubscriptionPlan> companySubscriptionPlanList = companySubscriptionPlanRepository.findAll();
        assertThat(companySubscriptionPlanList).hasSize(databaseSizeBeforeCreate + 1);
        CompanySubscriptionPlan testCompanySubscriptionPlan = companySubscriptionPlanList.get(companySubscriptionPlanList.size() - 1);
        assertThat(testCompanySubscriptionPlan.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testCompanySubscriptionPlan.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createCompanySubscriptionPlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companySubscriptionPlanRepository.findAll().size();

        // Create the CompanySubscriptionPlan with an existing ID
        companySubscriptionPlan.setId(1L);
        CompanySubscriptionPlanDTO companySubscriptionPlanDTO = companySubscriptionPlanMapper.toDto(companySubscriptionPlan);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanySubscriptionPlanMockMvc.perform(post("/api/company-subscription-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companySubscriptionPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CompanySubscriptionPlan> companySubscriptionPlanList = companySubscriptionPlanRepository.findAll();
        assertThat(companySubscriptionPlanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanySubscriptionPlans() throws Exception {
        // Initialize the database
        companySubscriptionPlanRepository.saveAndFlush(companySubscriptionPlan);

        // Get all the companySubscriptionPlanList
        restCompanySubscriptionPlanMockMvc.perform(get("/api/company-subscription-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companySubscriptionPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))));
    }

    @Test
    @Transactional
    public void getCompanySubscriptionPlan() throws Exception {
        // Initialize the database
        companySubscriptionPlanRepository.saveAndFlush(companySubscriptionPlan);

        // Get the companySubscriptionPlan
        restCompanySubscriptionPlanMockMvc.perform(get("/api/company-subscription-plans/{id}", companySubscriptionPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companySubscriptionPlan.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingCompanySubscriptionPlan() throws Exception {
        // Get the companySubscriptionPlan
        restCompanySubscriptionPlanMockMvc.perform(get("/api/company-subscription-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanySubscriptionPlan() throws Exception {
        // Initialize the database
        companySubscriptionPlanRepository.saveAndFlush(companySubscriptionPlan);
        int databaseSizeBeforeUpdate = companySubscriptionPlanRepository.findAll().size();

        // Update the companySubscriptionPlan
        CompanySubscriptionPlan updatedCompanySubscriptionPlan = companySubscriptionPlanRepository.findOne(companySubscriptionPlan.getId());
        updatedCompanySubscriptionPlan
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        CompanySubscriptionPlanDTO companySubscriptionPlanDTO = companySubscriptionPlanMapper.toDto(updatedCompanySubscriptionPlan);

        restCompanySubscriptionPlanMockMvc.perform(put("/api/company-subscription-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companySubscriptionPlanDTO)))
            .andExpect(status().isOk());

        // Validate the CompanySubscriptionPlan in the database
        List<CompanySubscriptionPlan> companySubscriptionPlanList = companySubscriptionPlanRepository.findAll();
        assertThat(companySubscriptionPlanList).hasSize(databaseSizeBeforeUpdate);
        CompanySubscriptionPlan testCompanySubscriptionPlan = companySubscriptionPlanList.get(companySubscriptionPlanList.size() - 1);
        assertThat(testCompanySubscriptionPlan.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testCompanySubscriptionPlan.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanySubscriptionPlan() throws Exception {
        int databaseSizeBeforeUpdate = companySubscriptionPlanRepository.findAll().size();

        // Create the CompanySubscriptionPlan
        CompanySubscriptionPlanDTO companySubscriptionPlanDTO = companySubscriptionPlanMapper.toDto(companySubscriptionPlan);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanySubscriptionPlanMockMvc.perform(put("/api/company-subscription-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companySubscriptionPlanDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanySubscriptionPlan in the database
        List<CompanySubscriptionPlan> companySubscriptionPlanList = companySubscriptionPlanRepository.findAll();
        assertThat(companySubscriptionPlanList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanySubscriptionPlan() throws Exception {
        // Initialize the database
        companySubscriptionPlanRepository.saveAndFlush(companySubscriptionPlan);
        int databaseSizeBeforeDelete = companySubscriptionPlanRepository.findAll().size();

        // Get the companySubscriptionPlan
        restCompanySubscriptionPlanMockMvc.perform(delete("/api/company-subscription-plans/{id}", companySubscriptionPlan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanySubscriptionPlan> companySubscriptionPlanList = companySubscriptionPlanRepository.findAll();
        assertThat(companySubscriptionPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanySubscriptionPlan.class);
        CompanySubscriptionPlan companySubscriptionPlan1 = new CompanySubscriptionPlan();
        companySubscriptionPlan1.setId(1L);
        CompanySubscriptionPlan companySubscriptionPlan2 = new CompanySubscriptionPlan();
        companySubscriptionPlan2.setId(companySubscriptionPlan1.getId());
        assertThat(companySubscriptionPlan1).isEqualTo(companySubscriptionPlan2);
        companySubscriptionPlan2.setId(2L);
        assertThat(companySubscriptionPlan1).isNotEqualTo(companySubscriptionPlan2);
        companySubscriptionPlan1.setId(null);
        assertThat(companySubscriptionPlan1).isNotEqualTo(companySubscriptionPlan2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanySubscriptionPlanDTO.class);
        CompanySubscriptionPlanDTO companySubscriptionPlanDTO1 = new CompanySubscriptionPlanDTO();
        companySubscriptionPlanDTO1.setId(1L);
        CompanySubscriptionPlanDTO companySubscriptionPlanDTO2 = new CompanySubscriptionPlanDTO();
        assertThat(companySubscriptionPlanDTO1).isNotEqualTo(companySubscriptionPlanDTO2);
        companySubscriptionPlanDTO2.setId(companySubscriptionPlanDTO1.getId());
        assertThat(companySubscriptionPlanDTO1).isEqualTo(companySubscriptionPlanDTO2);
        companySubscriptionPlanDTO2.setId(2L);
        assertThat(companySubscriptionPlanDTO1).isNotEqualTo(companySubscriptionPlanDTO2);
        companySubscriptionPlanDTO1.setId(null);
        assertThat(companySubscriptionPlanDTO1).isNotEqualTo(companySubscriptionPlanDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(companySubscriptionPlanMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(companySubscriptionPlanMapper.fromId(null)).isNull();
    }
}
