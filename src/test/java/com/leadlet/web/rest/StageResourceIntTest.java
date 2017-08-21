package com.leadlet.web.rest;

import com.leadlet.LeadletApiApp;

import com.leadlet.domain.Stage;
import com.leadlet.repository.StageRepository;
import com.leadlet.service.StageService;
import com.leadlet.service.dto.StageDTO;
import com.leadlet.service.mapper.StageMapper;
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
 * Test class for the StageResource REST controller.
 *
 * @see StageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
public class StageResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private StageMapper stageMapper;

    @Autowired
    private StageService stageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStageMockMvc;

    private Stage stage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StageResource stageResource = new StageResource(stageService);
        this.restStageMockMvc = MockMvcBuilders.standaloneSetup(stageResource)
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
    public static Stage createEntity(EntityManager em) {
        Stage stage = new Stage()
            .name(DEFAULT_NAME);
        return stage;
    }

    @Before
    public void initTest() {
        stage = createEntity(em);
    }

    @Test
    @Transactional
    public void createStage() throws Exception {
        int databaseSizeBeforeCreate = stageRepository.findAll().size();

        // Create the Stage
        StageDTO stageDTO = stageMapper.toDto(stage);
        restStageMockMvc.perform(post("/api/stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stageDTO)))
            .andExpect(status().isCreated());

        // Validate the Stage in the database
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeCreate + 1);
        Stage testStage = stageList.get(stageList.size() - 1);
        assertThat(testStage.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createStageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stageRepository.findAll().size();

        // Create the Stage with an existing ID
        stage.setId(1L);
        StageDTO stageDTO = stageMapper.toDto(stage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStageMockMvc.perform(post("/api/stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStages() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

        // Get all the stageList
        restStageMockMvc.perform(get("/api/stages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getStage() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

        // Get the stage
        restStageMockMvc.perform(get("/api/stages/{id}", stage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStage() throws Exception {
        // Get the stage
        restStageMockMvc.perform(get("/api/stages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStage() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);
        int databaseSizeBeforeUpdate = stageRepository.findAll().size();

        // Update the stage
        Stage updatedStage = stageRepository.findOne(stage.getId());
        updatedStage
            .name(UPDATED_NAME);
        StageDTO stageDTO = stageMapper.toDto(updatedStage);

        restStageMockMvc.perform(put("/api/stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stageDTO)))
            .andExpect(status().isOk());

        // Validate the Stage in the database
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeUpdate);
        Stage testStage = stageList.get(stageList.size() - 1);
        assertThat(testStage.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingStage() throws Exception {
        int databaseSizeBeforeUpdate = stageRepository.findAll().size();

        // Create the Stage
        StageDTO stageDTO = stageMapper.toDto(stage);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStageMockMvc.perform(put("/api/stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stageDTO)))
            .andExpect(status().isCreated());

        // Validate the Stage in the database
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStage() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);
        int databaseSizeBeforeDelete = stageRepository.findAll().size();

        // Get the stage
        restStageMockMvc.perform(delete("/api/stages/{id}", stage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stage.class);
        Stage stage1 = new Stage();
        stage1.setId(1L);
        Stage stage2 = new Stage();
        stage2.setId(stage1.getId());
        assertThat(stage1).isEqualTo(stage2);
        stage2.setId(2L);
        assertThat(stage1).isNotEqualTo(stage2);
        stage1.setId(null);
        assertThat(stage1).isNotEqualTo(stage2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StageDTO.class);
        StageDTO stageDTO1 = new StageDTO();
        stageDTO1.setId(1L);
        StageDTO stageDTO2 = new StageDTO();
        assertThat(stageDTO1).isNotEqualTo(stageDTO2);
        stageDTO2.setId(stageDTO1.getId());
        assertThat(stageDTO1).isEqualTo(stageDTO2);
        stageDTO2.setId(2L);
        assertThat(stageDTO1).isNotEqualTo(stageDTO2);
        stageDTO1.setId(null);
        assertThat(stageDTO1).isNotEqualTo(stageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stageMapper.fromId(null)).isNull();
    }
}
