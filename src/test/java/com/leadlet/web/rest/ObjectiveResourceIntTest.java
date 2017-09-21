package com.leadlet.web.rest;

import com.leadlet.LeadletApiApp;

import com.leadlet.domain.Objective;
import com.leadlet.repository.ObjectiveRepository;
import com.leadlet.service.ObjectiveService;
import com.leadlet.service.dto.ObjectiveDTO;
import com.leadlet.service.mapper.ObjectiveMapper;
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

import com.leadlet.domain.enumeration.ObjectiveSourceType;
/**
 * Test class for the ObjectiveResource REST controller.
 *
 * @see ObjectiveResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
public class ObjectiveResourceIntTest {

    private static final Long DEFAULT_SOURCE_ID = 1L;
    private static final Long UPDATED_SOURCE_ID = 2L;

    private static final ObjectiveSourceType DEFAULT_SOURCE_TYPE = ObjectiveSourceType.USER;
    private static final ObjectiveSourceType UPDATED_SOURCE_TYPE = ObjectiveSourceType.TEAM;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DUE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DUE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_TARGET_AMOUNT = 1L;
    private static final Long UPDATED_TARGET_AMOUNT = 2L;

    private static final Long DEFAULT_CURRENT_AMOUNT = 1L;
    private static final Long UPDATED_CURRENT_AMOUNT = 2L;

    @Autowired
    private ObjectiveRepository objectiveRepository;

    @Autowired
    private ObjectiveMapper objectiveMapper;

    @Autowired
    private ObjectiveService objectiveService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restObjectiveMockMvc;

    private Objective objective;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ObjectiveResource objectiveResource = new ObjectiveResource(objectiveService);
        this.restObjectiveMockMvc = MockMvcBuilders.standaloneSetup(objectiveResource)
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
    public static Objective createEntity(EntityManager em) {
        Objective objective = new Objective()
            .sourceId(DEFAULT_SOURCE_ID)
            .sourceType(DEFAULT_SOURCE_TYPE)
            .name(DEFAULT_NAME)
            .dueDate(DEFAULT_DUE_DATE)
            .targetAmount(DEFAULT_TARGET_AMOUNT)
            .currentAmount(DEFAULT_CURRENT_AMOUNT);
        return objective;
    }

    @Before
    public void initTest() {
        objective = createEntity(em);
    }

    @Test
    @Transactional
    public void createObjective() throws Exception {
        int databaseSizeBeforeCreate = objectiveRepository.findAll().size();

        // Create the Objective
        ObjectiveDTO objectiveDTO = objectiveMapper.toDto(objective);
        restObjectiveMockMvc.perform(post("/api/objectives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(objectiveDTO)))
            .andExpect(status().isCreated());

        // Validate the Objective in the database
        List<Objective> objectiveList = objectiveRepository.findAll();
        assertThat(objectiveList).hasSize(databaseSizeBeforeCreate + 1);
        Objective testObjective = objectiveList.get(objectiveList.size() - 1);
        assertThat(testObjective.getSourceId()).isEqualTo(DEFAULT_SOURCE_ID);
        assertThat(testObjective.getSourceType()).isEqualTo(DEFAULT_SOURCE_TYPE);
        assertThat(testObjective.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testObjective.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testObjective.getTargetAmount()).isEqualTo(DEFAULT_TARGET_AMOUNT);
        assertThat(testObjective.getCurrentAmount()).isEqualTo(DEFAULT_CURRENT_AMOUNT);
    }

    @Test
    @Transactional
    public void createObjectiveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = objectiveRepository.findAll().size();

        // Create the Objective with an existing ID
        objective.setId(1L);
        ObjectiveDTO objectiveDTO = objectiveMapper.toDto(objective);

        // An entity with an existing ID cannot be created, so this API call must fail
        restObjectiveMockMvc.perform(post("/api/objectives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(objectiveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Objective> objectiveList = objectiveRepository.findAll();
        assertThat(objectiveList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllObjectives() throws Exception {
        // Initialize the database
        objectiveRepository.saveAndFlush(objective);

        // Get all the objectiveList
        restObjectiveMockMvc.perform(get("/api/objectives?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(objective.getId().intValue())))
            .andExpect(jsonPath("$.[*].sourceId").value(hasItem(DEFAULT_SOURCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].sourceType").value(hasItem(DEFAULT_SOURCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(sameInstant(DEFAULT_DUE_DATE))))
            .andExpect(jsonPath("$.[*].targetAmount").value(hasItem(DEFAULT_TARGET_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].currentAmount").value(hasItem(DEFAULT_CURRENT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getObjective() throws Exception {
        // Initialize the database
        objectiveRepository.saveAndFlush(objective);

        // Get the objective
        restObjectiveMockMvc.perform(get("/api/objectives/{id}", objective.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(objective.getId().intValue()))
            .andExpect(jsonPath("$.sourceId").value(DEFAULT_SOURCE_ID.intValue()))
            .andExpect(jsonPath("$.sourceType").value(DEFAULT_SOURCE_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.dueDate").value(sameInstant(DEFAULT_DUE_DATE)))
            .andExpect(jsonPath("$.targetAmount").value(DEFAULT_TARGET_AMOUNT.intValue()))
            .andExpect(jsonPath("$.currentAmount").value(DEFAULT_CURRENT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingObjective() throws Exception {
        // Get the objective
        restObjectiveMockMvc.perform(get("/api/objectives/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateObjective() throws Exception {
        // Initialize the database
        objectiveRepository.saveAndFlush(objective);
        int databaseSizeBeforeUpdate = objectiveRepository.findAll().size();

        // Update the objective
        Objective updatedObjective = objectiveRepository.findOne(objective.getId());
        updatedObjective
            .sourceId(UPDATED_SOURCE_ID)
            .sourceType(UPDATED_SOURCE_TYPE)
            .name(UPDATED_NAME)
            .dueDate(UPDATED_DUE_DATE)
            .targetAmount(UPDATED_TARGET_AMOUNT)
            .currentAmount(UPDATED_CURRENT_AMOUNT);
        ObjectiveDTO objectiveDTO = objectiveMapper.toDto(updatedObjective);

        restObjectiveMockMvc.perform(put("/api/objectives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(objectiveDTO)))
            .andExpect(status().isOk());

        // Validate the Objective in the database
        List<Objective> objectiveList = objectiveRepository.findAll();
        assertThat(objectiveList).hasSize(databaseSizeBeforeUpdate);
        Objective testObjective = objectiveList.get(objectiveList.size() - 1);
        assertThat(testObjective.getSourceId()).isEqualTo(UPDATED_SOURCE_ID);
        assertThat(testObjective.getSourceType()).isEqualTo(UPDATED_SOURCE_TYPE);
        assertThat(testObjective.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testObjective.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testObjective.getTargetAmount()).isEqualTo(UPDATED_TARGET_AMOUNT);
        assertThat(testObjective.getCurrentAmount()).isEqualTo(UPDATED_CURRENT_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingObjective() throws Exception {
        int databaseSizeBeforeUpdate = objectiveRepository.findAll().size();

        // Create the Objective
        ObjectiveDTO objectiveDTO = objectiveMapper.toDto(objective);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restObjectiveMockMvc.perform(put("/api/objectives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(objectiveDTO)))
            .andExpect(status().isCreated());

        // Validate the Objective in the database
        List<Objective> objectiveList = objectiveRepository.findAll();
        assertThat(objectiveList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteObjective() throws Exception {
        // Initialize the database
        objectiveRepository.saveAndFlush(objective);
        int databaseSizeBeforeDelete = objectiveRepository.findAll().size();

        // Get the objective
        restObjectiveMockMvc.perform(delete("/api/objectives/{id}", objective.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Objective> objectiveList = objectiveRepository.findAll();
        assertThat(objectiveList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Objective.class);
        Objective objective1 = new Objective();
        objective1.setId(1L);
        Objective objective2 = new Objective();
        objective2.setId(objective1.getId());
        assertThat(objective1).isEqualTo(objective2);
        objective2.setId(2L);
        assertThat(objective1).isNotEqualTo(objective2);
        objective1.setId(null);
        assertThat(objective1).isNotEqualTo(objective2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObjectiveDTO.class);
        ObjectiveDTO objectiveDTO1 = new ObjectiveDTO();
        objectiveDTO1.setId(1L);
        ObjectiveDTO objectiveDTO2 = new ObjectiveDTO();
        assertThat(objectiveDTO1).isNotEqualTo(objectiveDTO2);
        objectiveDTO2.setId(objectiveDTO1.getId());
        assertThat(objectiveDTO1).isEqualTo(objectiveDTO2);
        objectiveDTO2.setId(2L);
        assertThat(objectiveDTO1).isNotEqualTo(objectiveDTO2);
        objectiveDTO1.setId(null);
        assertThat(objectiveDTO1).isNotEqualTo(objectiveDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(objectiveMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(objectiveMapper.fromId(null)).isNull();
    }
}
