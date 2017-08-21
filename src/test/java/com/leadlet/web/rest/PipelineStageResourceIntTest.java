package com.leadlet.web.rest;

import com.leadlet.LeadletApiApp;

import com.leadlet.domain.PipelineStage;
import com.leadlet.repository.PipelineStageRepository;
import com.leadlet.service.PipelineStageService;
import com.leadlet.service.dto.PipelineStageDTO;
import com.leadlet.service.mapper.PipelineStageMapper;
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
 * Test class for the PipelineStageResource REST controller.
 *
 * @see PipelineStageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
public class PipelineStageResourceIntTest {

    private static final Integer DEFAULT_STAGE_ORDER = 1;
    private static final Integer UPDATED_STAGE_ORDER = 2;

    @Autowired
    private PipelineStageRepository pipelineStageRepository;

    @Autowired
    private PipelineStageMapper pipelineStageMapper;

    @Autowired
    private PipelineStageService pipelineStageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPipelineStageMockMvc;

    private PipelineStage pipelineStage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PipelineStageResource pipelineStageResource = new PipelineStageResource(pipelineStageService);
        this.restPipelineStageMockMvc = MockMvcBuilders.standaloneSetup(pipelineStageResource)
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
    public static PipelineStage createEntity(EntityManager em) {
        PipelineStage pipelineStage = new PipelineStage()
            .stageOrder(DEFAULT_STAGE_ORDER);
        return pipelineStage;
    }

    @Before
    public void initTest() {
        pipelineStage = createEntity(em);
    }

    @Test
    @Transactional
    public void createPipelineStage() throws Exception {
        int databaseSizeBeforeCreate = pipelineStageRepository.findAll().size();

        // Create the PipelineStage
        PipelineStageDTO pipelineStageDTO = pipelineStageMapper.toDto(pipelineStage);
        restPipelineStageMockMvc.perform(post("/api/pipeline-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pipelineStageDTO)))
            .andExpect(status().isCreated());

        // Validate the PipelineStage in the database
        List<PipelineStage> pipelineStageList = pipelineStageRepository.findAll();
        assertThat(pipelineStageList).hasSize(databaseSizeBeforeCreate + 1);
        PipelineStage testPipelineStage = pipelineStageList.get(pipelineStageList.size() - 1);
        assertThat(testPipelineStage.getStageOrder()).isEqualTo(DEFAULT_STAGE_ORDER);
    }

    @Test
    @Transactional
    public void createPipelineStageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pipelineStageRepository.findAll().size();

        // Create the PipelineStage with an existing ID
        pipelineStage.setId(1L);
        PipelineStageDTO pipelineStageDTO = pipelineStageMapper.toDto(pipelineStage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPipelineStageMockMvc.perform(post("/api/pipeline-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pipelineStageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PipelineStage> pipelineStageList = pipelineStageRepository.findAll();
        assertThat(pipelineStageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPipelineStages() throws Exception {
        // Initialize the database
        pipelineStageRepository.saveAndFlush(pipelineStage);

        // Get all the pipelineStageList
        restPipelineStageMockMvc.perform(get("/api/pipeline-stages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pipelineStage.getId().intValue())))
            .andExpect(jsonPath("$.[*].stageOrder").value(hasItem(DEFAULT_STAGE_ORDER)));
    }

    @Test
    @Transactional
    public void getPipelineStage() throws Exception {
        // Initialize the database
        pipelineStageRepository.saveAndFlush(pipelineStage);

        // Get the pipelineStage
        restPipelineStageMockMvc.perform(get("/api/pipeline-stages/{id}", pipelineStage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pipelineStage.getId().intValue()))
            .andExpect(jsonPath("$.stageOrder").value(DEFAULT_STAGE_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingPipelineStage() throws Exception {
        // Get the pipelineStage
        restPipelineStageMockMvc.perform(get("/api/pipeline-stages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePipelineStage() throws Exception {
        // Initialize the database
        pipelineStageRepository.saveAndFlush(pipelineStage);
        int databaseSizeBeforeUpdate = pipelineStageRepository.findAll().size();

        // Update the pipelineStage
        PipelineStage updatedPipelineStage = pipelineStageRepository.findOne(pipelineStage.getId());
        updatedPipelineStage
            .stageOrder(UPDATED_STAGE_ORDER);
        PipelineStageDTO pipelineStageDTO = pipelineStageMapper.toDto(updatedPipelineStage);

        restPipelineStageMockMvc.perform(put("/api/pipeline-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pipelineStageDTO)))
            .andExpect(status().isOk());

        // Validate the PipelineStage in the database
        List<PipelineStage> pipelineStageList = pipelineStageRepository.findAll();
        assertThat(pipelineStageList).hasSize(databaseSizeBeforeUpdate);
        PipelineStage testPipelineStage = pipelineStageList.get(pipelineStageList.size() - 1);
        assertThat(testPipelineStage.getStageOrder()).isEqualTo(UPDATED_STAGE_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingPipelineStage() throws Exception {
        int databaseSizeBeforeUpdate = pipelineStageRepository.findAll().size();

        // Create the PipelineStage
        PipelineStageDTO pipelineStageDTO = pipelineStageMapper.toDto(pipelineStage);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPipelineStageMockMvc.perform(put("/api/pipeline-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pipelineStageDTO)))
            .andExpect(status().isCreated());

        // Validate the PipelineStage in the database
        List<PipelineStage> pipelineStageList = pipelineStageRepository.findAll();
        assertThat(pipelineStageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePipelineStage() throws Exception {
        // Initialize the database
        pipelineStageRepository.saveAndFlush(pipelineStage);
        int databaseSizeBeforeDelete = pipelineStageRepository.findAll().size();

        // Get the pipelineStage
        restPipelineStageMockMvc.perform(delete("/api/pipeline-stages/{id}", pipelineStage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PipelineStage> pipelineStageList = pipelineStageRepository.findAll();
        assertThat(pipelineStageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PipelineStage.class);
        PipelineStage pipelineStage1 = new PipelineStage();
        pipelineStage1.setId(1L);
        PipelineStage pipelineStage2 = new PipelineStage();
        pipelineStage2.setId(pipelineStage1.getId());
        assertThat(pipelineStage1).isEqualTo(pipelineStage2);
        pipelineStage2.setId(2L);
        assertThat(pipelineStage1).isNotEqualTo(pipelineStage2);
        pipelineStage1.setId(null);
        assertThat(pipelineStage1).isNotEqualTo(pipelineStage2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PipelineStageDTO.class);
        PipelineStageDTO pipelineStageDTO1 = new PipelineStageDTO();
        pipelineStageDTO1.setId(1L);
        PipelineStageDTO pipelineStageDTO2 = new PipelineStageDTO();
        assertThat(pipelineStageDTO1).isNotEqualTo(pipelineStageDTO2);
        pipelineStageDTO2.setId(pipelineStageDTO1.getId());
        assertThat(pipelineStageDTO1).isEqualTo(pipelineStageDTO2);
        pipelineStageDTO2.setId(2L);
        assertThat(pipelineStageDTO1).isNotEqualTo(pipelineStageDTO2);
        pipelineStageDTO1.setId(null);
        assertThat(pipelineStageDTO1).isNotEqualTo(pipelineStageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pipelineStageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pipelineStageMapper.fromId(null)).isNull();
    }
}
