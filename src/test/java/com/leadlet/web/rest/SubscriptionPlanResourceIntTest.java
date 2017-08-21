package com.leadlet.web.rest;

import com.leadlet.LeadletApiApp;

import com.leadlet.domain.SubscriptionPlan;
import com.leadlet.repository.SubscriptionPlanRepository;
import com.leadlet.service.SubscriptionPlanService;
import com.leadlet.service.dto.SubscriptionPlanDTO;
import com.leadlet.service.mapper.SubscriptionPlanMapper;
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

import com.leadlet.domain.enumeration.PlanName;
/**
 * Test class for the SubscriptionPlanResource REST controller.
 *
 * @see SubscriptionPlanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
public class SubscriptionPlanResourceIntTest {

    private static final PlanName DEFAULT_PLAN_NAME = PlanName.TRIAL;
    private static final PlanName UPDATED_PLAN_NAME = PlanName.BASIC;

    private static final String DEFAULT_ALLOWED_FEATURES = "AAAAAAAAAA";
    private static final String UPDATED_ALLOWED_FEATURES = "BBBBBBBBBB";

    @Autowired
    private SubscriptionPlanRepository subscriptionPlanRepository;

    @Autowired
    private SubscriptionPlanMapper subscriptionPlanMapper;

    @Autowired
    private SubscriptionPlanService subscriptionPlanService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSubscriptionPlanMockMvc;

    private SubscriptionPlan subscriptionPlan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubscriptionPlanResource subscriptionPlanResource = new SubscriptionPlanResource(subscriptionPlanService);
        this.restSubscriptionPlanMockMvc = MockMvcBuilders.standaloneSetup(subscriptionPlanResource)
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
    public static SubscriptionPlan createEntity(EntityManager em) {
        SubscriptionPlan subscriptionPlan = new SubscriptionPlan()
            .planName(DEFAULT_PLAN_NAME)
            .allowedFeatures(DEFAULT_ALLOWED_FEATURES);
        return subscriptionPlan;
    }

    @Before
    public void initTest() {
        subscriptionPlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubscriptionPlan() throws Exception {
        int databaseSizeBeforeCreate = subscriptionPlanRepository.findAll().size();

        // Create the SubscriptionPlan
        SubscriptionPlanDTO subscriptionPlanDTO = subscriptionPlanMapper.toDto(subscriptionPlan);
        restSubscriptionPlanMockMvc.perform(post("/api/subscription-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionPlanDTO)))
            .andExpect(status().isCreated());

        // Validate the SubscriptionPlan in the database
        List<SubscriptionPlan> subscriptionPlanList = subscriptionPlanRepository.findAll();
        assertThat(subscriptionPlanList).hasSize(databaseSizeBeforeCreate + 1);
        SubscriptionPlan testSubscriptionPlan = subscriptionPlanList.get(subscriptionPlanList.size() - 1);
        assertThat(testSubscriptionPlan.getPlanName()).isEqualTo(DEFAULT_PLAN_NAME);
        assertThat(testSubscriptionPlan.getAllowedFeatures()).isEqualTo(DEFAULT_ALLOWED_FEATURES);
    }

    @Test
    @Transactional
    public void createSubscriptionPlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subscriptionPlanRepository.findAll().size();

        // Create the SubscriptionPlan with an existing ID
        subscriptionPlan.setId(1L);
        SubscriptionPlanDTO subscriptionPlanDTO = subscriptionPlanMapper.toDto(subscriptionPlan);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubscriptionPlanMockMvc.perform(post("/api/subscription-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SubscriptionPlan> subscriptionPlanList = subscriptionPlanRepository.findAll();
        assertThat(subscriptionPlanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlans() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList
        restSubscriptionPlanMockMvc.perform(get("/api/subscription-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subscriptionPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].planName").value(hasItem(DEFAULT_PLAN_NAME.toString())))
            .andExpect(jsonPath("$.[*].allowedFeatures").value(hasItem(DEFAULT_ALLOWED_FEATURES.toString())));
    }

    @Test
    @Transactional
    public void getSubscriptionPlan() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get the subscriptionPlan
        restSubscriptionPlanMockMvc.perform(get("/api/subscription-plans/{id}", subscriptionPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subscriptionPlan.getId().intValue()))
            .andExpect(jsonPath("$.planName").value(DEFAULT_PLAN_NAME.toString()))
            .andExpect(jsonPath("$.allowedFeatures").value(DEFAULT_ALLOWED_FEATURES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSubscriptionPlan() throws Exception {
        // Get the subscriptionPlan
        restSubscriptionPlanMockMvc.perform(get("/api/subscription-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubscriptionPlan() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);
        int databaseSizeBeforeUpdate = subscriptionPlanRepository.findAll().size();

        // Update the subscriptionPlan
        SubscriptionPlan updatedSubscriptionPlan = subscriptionPlanRepository.findOne(subscriptionPlan.getId());
        updatedSubscriptionPlan
            .planName(UPDATED_PLAN_NAME)
            .allowedFeatures(UPDATED_ALLOWED_FEATURES);
        SubscriptionPlanDTO subscriptionPlanDTO = subscriptionPlanMapper.toDto(updatedSubscriptionPlan);

        restSubscriptionPlanMockMvc.perform(put("/api/subscription-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionPlanDTO)))
            .andExpect(status().isOk());

        // Validate the SubscriptionPlan in the database
        List<SubscriptionPlan> subscriptionPlanList = subscriptionPlanRepository.findAll();
        assertThat(subscriptionPlanList).hasSize(databaseSizeBeforeUpdate);
        SubscriptionPlan testSubscriptionPlan = subscriptionPlanList.get(subscriptionPlanList.size() - 1);
        assertThat(testSubscriptionPlan.getPlanName()).isEqualTo(UPDATED_PLAN_NAME);
        assertThat(testSubscriptionPlan.getAllowedFeatures()).isEqualTo(UPDATED_ALLOWED_FEATURES);
    }

    @Test
    @Transactional
    public void updateNonExistingSubscriptionPlan() throws Exception {
        int databaseSizeBeforeUpdate = subscriptionPlanRepository.findAll().size();

        // Create the SubscriptionPlan
        SubscriptionPlanDTO subscriptionPlanDTO = subscriptionPlanMapper.toDto(subscriptionPlan);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSubscriptionPlanMockMvc.perform(put("/api/subscription-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionPlanDTO)))
            .andExpect(status().isCreated());

        // Validate the SubscriptionPlan in the database
        List<SubscriptionPlan> subscriptionPlanList = subscriptionPlanRepository.findAll();
        assertThat(subscriptionPlanList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSubscriptionPlan() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);
        int databaseSizeBeforeDelete = subscriptionPlanRepository.findAll().size();

        // Get the subscriptionPlan
        restSubscriptionPlanMockMvc.perform(delete("/api/subscription-plans/{id}", subscriptionPlan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SubscriptionPlan> subscriptionPlanList = subscriptionPlanRepository.findAll();
        assertThat(subscriptionPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubscriptionPlan.class);
        SubscriptionPlan subscriptionPlan1 = new SubscriptionPlan();
        subscriptionPlan1.setId(1L);
        SubscriptionPlan subscriptionPlan2 = new SubscriptionPlan();
        subscriptionPlan2.setId(subscriptionPlan1.getId());
        assertThat(subscriptionPlan1).isEqualTo(subscriptionPlan2);
        subscriptionPlan2.setId(2L);
        assertThat(subscriptionPlan1).isNotEqualTo(subscriptionPlan2);
        subscriptionPlan1.setId(null);
        assertThat(subscriptionPlan1).isNotEqualTo(subscriptionPlan2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubscriptionPlanDTO.class);
        SubscriptionPlanDTO subscriptionPlanDTO1 = new SubscriptionPlanDTO();
        subscriptionPlanDTO1.setId(1L);
        SubscriptionPlanDTO subscriptionPlanDTO2 = new SubscriptionPlanDTO();
        assertThat(subscriptionPlanDTO1).isNotEqualTo(subscriptionPlanDTO2);
        subscriptionPlanDTO2.setId(subscriptionPlanDTO1.getId());
        assertThat(subscriptionPlanDTO1).isEqualTo(subscriptionPlanDTO2);
        subscriptionPlanDTO2.setId(2L);
        assertThat(subscriptionPlanDTO1).isNotEqualTo(subscriptionPlanDTO2);
        subscriptionPlanDTO1.setId(null);
        assertThat(subscriptionPlanDTO1).isNotEqualTo(subscriptionPlanDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(subscriptionPlanMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(subscriptionPlanMapper.fromId(null)).isNull();
    }
}
