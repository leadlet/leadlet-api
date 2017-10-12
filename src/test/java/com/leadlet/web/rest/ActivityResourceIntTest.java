package com.leadlet.web.rest;

import com.leadlet.LeadletApiApp;

import com.leadlet.domain.Activity;
import com.leadlet.domain.AppAccount;
import com.leadlet.domain.User;
import com.leadlet.repository.ActivityRepository;
import com.leadlet.repository.AppAccountRepository;
import com.leadlet.repository.UserRepository;
import com.leadlet.service.ActivityService;
import com.leadlet.service.dto.ActivityDTO;
import com.leadlet.service.mapper.ActivityMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ActivityResource REST controller.
 *
 * @see ActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
public class ActivityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final String DEFAULT_MEMO = "AAAAAAAAAA";
    private static final String UPDATED_MEMO = "BBBBBBBBBB";

    private static final Double DEFAULT_POTENTIAL_VALUE = 1D;
    private static final Double UPDATED_POTENTIAL_VALUE = 2D;

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AppAccountRepository appAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActivityMockMvc;

    private Activity activity;

    private User user;
    private static User xcompanyadminuser;
    private static User ycompanyadminuser;
    private AppAccount xCompanyAppAccount;
    private AppAccount yCompanyAppAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ActivityResource activityResource = new ActivityResource(activityService);
        this.restActivityMockMvc = MockMvcBuilders.standaloneSetup(activityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void setAppAccountsUsers() {
        this.xCompanyAppAccount = this.appAccountRepository.findOneByName("CompanyX").get();
        this.yCompanyAppAccount = this.appAccountRepository.findOneByName("CompanyY").get();

        this.xcompanyadminuser = this.userRepository.findOneByLogin("xcompanyadminuser").get();
        this.ycompanyadminuser = this.userRepository.findOneByLogin("ycompanyadminuser").get();

    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activity createEntity(EntityManager em) {
        Activity activity = new Activity()
            .name(DEFAULT_NAME)
            .order(DEFAULT_ORDER)
            .memo(DEFAULT_MEMO)
            .potentialValue(DEFAULT_POTENTIAL_VALUE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return activity;
    }

    @Before
    public void initTest() {
        activity = createEntity(em);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void createActivity() throws Exception {
        int databaseSizeBeforeCreate = activityRepository.findAll().size();

        // Create the Activity
        ActivityDTO activityDTO = activityMapper.toDto(activity);
        restActivityMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityDTO)))
            .andExpect(status().isCreated());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate + 1);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testActivity.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testActivity.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testActivity.getPotentialValue()).isEqualTo(DEFAULT_POTENTIAL_VALUE);
        assertThat(testActivity.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testActivity.getEndDate()).isEqualTo(DEFAULT_END_DATE);

        assertThat(testActivity.getAppAccount()).isEqualTo(xCompanyAppAccount);
    }

    //TODO: burada verilen accounta ozel test eklenmeli
    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void createActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activityRepository.findAll().size();

        // Create the Activity with an existing ID
        activity.setId(1L);
        ActivityDTO activityDTO = activityMapper.toDto(activity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void getAllActivities() throws Exception {
        // Initialize the database

        Activity activityX1 = new Activity();
        activityX1.setName("activityX1");
        activityX1.setOrder(1);
        activityX1.setMemo("activityMemoX1");
        activityX1.setPotentialValue(12.2);
        activityX1.setStartDate(DEFAULT_START_DATE);
        activityX1.setEndDate(DEFAULT_END_DATE);
        activityX1.setUser(xcompanyadminuser);
        activityX1.setAppAccount(xCompanyAppAccount);
        activityX1 = activityRepository.saveAndFlush(activityX1);

        Activity activityX2 = new Activity();
        activityX2.setName("activityX2");
        activityX2.setOrder(2);
        activityX2.setMemo("activityMemoX2");
        activityX2.setPotentialValue(14.0);
        activityX2.setStartDate(DEFAULT_START_DATE);
        activityX2.setEndDate(DEFAULT_END_DATE);
        activityX2.setUser(xcompanyadminuser);
        activityX2.setAppAccount(xCompanyAppAccount);
        activityX2 = activityRepository.saveAndFlush(activityX2);

        Activity activityY1 = new Activity();
        activityY1.setName("activityY1");
        activityY1.setOrder(2);
        activityY1.setMemo("activityMemoY1");
        activityY1.setPotentialValue(14.0);
        activityY1.setStartDate(DEFAULT_START_DATE);
        activityY1.setEndDate(DEFAULT_END_DATE);
        activityY1.setUser(ycompanyadminuser);
        activityY1.setAppAccount(yCompanyAppAccount);
        activityY1 = activityRepository.saveAndFlush(activityY1);

        // Get all the activityList
        restActivityMockMvc.perform(get("/api/activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$.[0].id").value(activityX2.getId()))
            .andExpect(jsonPath("$.[0].name").value("activityX2"))
            .andExpect(jsonPath("$.[0].order").value(2))
            .andExpect(jsonPath("$.[1].id").value(activityX1.getId()))
            .andExpect(jsonPath("$.[1].name").value("activityX1"))
            .andExpect(jsonPath("$.[1].order").value(1));
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void getActivity() throws Exception {
        // Initialize the database
        Activity activityX1 = new Activity();
        activityX1.setName("activityX1");
        activityX1.setOrder(1);
        activityX1.setMemo("activityMemoX1");
        activityX1.setPotentialValue(12.2);
        activityX1.setStartDate(DEFAULT_START_DATE);
        activityX1.setEndDate(DEFAULT_END_DATE);
        activityX1.setUser(xcompanyadminuser);
        activityX1.setAppAccount(xCompanyAppAccount);
        activityX1 = activityRepository.saveAndFlush(activityX1);

        // Get the activity
        restActivityMockMvc.perform(get("/api/activities/{id}", activityX1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activityX1.getId().intValue()))
            .andExpect(jsonPath("$.name").value(activityX1.getName()))
            .andExpect(jsonPath("$.order").value(activityX1.getOrder()))
            .andExpect(jsonPath("$.memo").value(activityX1.getMemo()))
            .andExpect(jsonPath("$.potentialValue").value(activityX1.getPotentialValue()));
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void getActivityForOtherAccount() throws Exception {

        Activity activityY1 = new Activity();
        activityY1.setName("activityY1");
        activityY1.setOrder(1);
        activityY1.setAppAccount(yCompanyAppAccount);
        activityY1 = activityRepository.saveAndFlush(activityY1);

        //Get the activity
        restActivityMockMvc.perform(get("/api/activities/{id}", activityY1.getId()))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void getNonExistingActivity() throws Exception {
        // Get the activity
        restActivityMockMvc.perform(get("/api/activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void updateActivity() throws Exception {
        // Initialize the database
        Activity activityX1 = new Activity();
        activityX1.setName("activityX1");
        activityX1.setOrder(1);
        activityX1.setAppAccount(xCompanyAppAccount);
        activityX1 = activityRepository.saveAndFlush(activityX1);

        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Update the activity
        Activity updatedActivity = activityRepository.findOne(activityX1.getId());

        updatedActivity
            .name(UPDATED_NAME)
            .order(UPDATED_ORDER)
            .memo(UPDATED_MEMO)
            .potentialValue(UPDATED_POTENTIAL_VALUE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        ActivityDTO activityDTO = activityMapper.toDto(updatedActivity);

        restActivityMockMvc.perform(put("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityDTO)))
            .andExpect(status().isOk());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testActivity.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testActivity.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testActivity.getPotentialValue()).isEqualTo(UPDATED_POTENTIAL_VALUE);
        assertThat(testActivity.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testActivity.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void updateActivityForOtherAccount() throws Exception {

        // Initialize the database
        Activity activityY1 = new Activity();
        activityY1.setName("activityY1");
        activityY1.setOrder(1);
        activityY1.setAppAccount(yCompanyAppAccount);
        activityY1 = activityRepository.saveAndFlush(activityY1);

        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        //update the activity
        Activity updatedActivity = activityRepository.findOne(activityY1.getId());
        updatedActivity
            .name(UPDATED_NAME)
            .order(UPDATED_ORDER);
        ActivityDTO activityDTO = activityMapper.toDto(updatedActivity);

        restActivityMockMvc.perform(put("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityDTO)))
            .andExpect(status().isNotFound());

    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void updateNonExistingActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Create the Activity
        ActivityDTO activityDTO = activityMapper.toDto(activity);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActivityMockMvc.perform(put("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityDTO)))
            .andExpect(status().isNotFound());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void deleteActivity() throws Exception {
        // Initialize the database
        Activity activityX1 = new Activity();
        activityX1.setName("activityX1");
        activityX1.setOrder(1);
        activityX1.setAppAccount(xCompanyAppAccount);
        activityX1 = activityRepository.saveAndFlush(activityX1);

        int databaseSizeBeforeDelete = activityRepository.findAll().size();

        // Get the activity
        restActivityMockMvc.perform(delete("/api/activities/{id}", activityX1.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void deleteActivityForOtherAccount() throws Exception {
        // Initialize the database
        Activity activityY1 = new Activity();
        activityY1.setName("activityY1");
        activityY1.setOrder(1);
        activityY1.setAppAccount(yCompanyAppAccount);
        activityY1 = activityRepository.saveAndFlush(activityY1);

        int databaseSizeBeforeDelete = activityRepository.findAll().size();

        // Get the activity
        restActivityMockMvc.perform(delete("/api/activities/{id}", activityY1.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Activity.class);
        Activity activity1 = new Activity();
        activity1.setId(1L);
        Activity activity2 = new Activity();
        activity2.setId(activity1.getId());
        assertThat(activity1).isEqualTo(activity2);
        activity2.setId(2L);
        assertThat(activity1).isNotEqualTo(activity2);
        activity1.setId(null);
        assertThat(activity1).isNotEqualTo(activity2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityDTO.class);
        ActivityDTO activityDTO1 = new ActivityDTO();
        activityDTO1.setId(1L);
        ActivityDTO activityDTO2 = new ActivityDTO();
        assertThat(activityDTO1).isNotEqualTo(activityDTO2);
        activityDTO2.setId(activityDTO1.getId());
        assertThat(activityDTO1).isEqualTo(activityDTO2);
        activityDTO2.setId(2L);
        assertThat(activityDTO1).isNotEqualTo(activityDTO2);
        activityDTO1.setId(null);
        assertThat(activityDTO1).isNotEqualTo(activityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(activityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(activityMapper.fromId(null)).isNull();
    }
}
