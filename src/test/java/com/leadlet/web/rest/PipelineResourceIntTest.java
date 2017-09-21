package com.leadlet.web.rest;

import com.leadlet.LeadletApiApp;

import com.leadlet.domain.Pipeline;
import com.leadlet.repository.PipelineRepository;
import com.leadlet.service.PipelineService;
import com.leadlet.service.dto.PipelineDTO;
import com.leadlet.service.mapper.PipelineMapper;
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
 * Test class for the PipelineResource REST controller.
 *
 * @see PipelineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
public class PipelineResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    @Autowired
    private PipelineRepository pipelineRepository;

    @Autowired
    private PipelineMapper pipelineMapper;

    @Autowired
    private PipelineService pipelineService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPipelineMockMvc;

    private Pipeline pipeline;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PipelineResource pipelineResource = new PipelineResource(pipelineService);
        this.restPipelineMockMvc = MockMvcBuilders.standaloneSetup(pipelineResource)
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
    public static Pipeline createEntity(EntityManager em) {
        Pipeline pipeline = new Pipeline()
            .name(DEFAULT_NAME)
            .order(DEFAULT_ORDER);
        return pipeline;
    }

    @Before
    public void initTest() {
        pipeline = createEntity(em);
    }

    @Test
    @Transactional
    public void createPipeline() throws Exception {
        int databaseSizeBeforeCreate = pipelineRepository.findAll().size();

        // Create the Pipeline
        PipelineDTO pipelineDTO = pipelineMapper.toDto(pipeline);
        restPipelineMockMvc.perform(post("/api/pipelines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pipelineDTO)))
            .andExpect(status().isCreated());

        // Validate the Pipeline in the database
        List<Pipeline> pipelineList = pipelineRepository.findAll();
        assertThat(pipelineList).hasSize(databaseSizeBeforeCreate + 1);
        Pipeline testPipeline = pipelineList.get(pipelineList.size() - 1);
        assertThat(testPipeline.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPipeline.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void createPipelineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pipelineRepository.findAll().size();

        // Create the Pipeline with an existing ID
        pipeline.setId(1L);
        PipelineDTO pipelineDTO = pipelineMapper.toDto(pipeline);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPipelineMockMvc.perform(post("/api/pipelines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pipelineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Pipeline> pipelineList = pipelineRepository.findAll();
        assertThat(pipelineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPipelines() throws Exception {
        // Initialize the database
        pipelineRepository.saveAndFlush(pipeline);

        // Get all the pipelineList
        restPipelineMockMvc.perform(get("/api/pipelines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pipeline.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }

    @Test
    @Transactional
    public void getPipeline() throws Exception {
        // Initialize the database
        pipelineRepository.saveAndFlush(pipeline);

        // Get the pipeline
        restPipelineMockMvc.perform(get("/api/pipelines/{id}", pipeline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pipeline.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingPipeline() throws Exception {
        // Get the pipeline
        restPipelineMockMvc.perform(get("/api/pipelines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePipeline() throws Exception {
        // Initialize the database
        pipelineRepository.saveAndFlush(pipeline);
        int databaseSizeBeforeUpdate = pipelineRepository.findAll().size();

        // Update the pipeline
        Pipeline updatedPipeline = pipelineRepository.findOne(pipeline.getId());
        updatedPipeline
            .name(UPDATED_NAME)
            .order(UPDATED_ORDER);
        PipelineDTO pipelineDTO = pipelineMapper.toDto(updatedPipeline);

        restPipelineMockMvc.perform(put("/api/pipelines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pipelineDTO)))
            .andExpect(status().isOk());

        // Validate the Pipeline in the database
        List<Pipeline> pipelineList = pipelineRepository.findAll();
        assertThat(pipelineList).hasSize(databaseSizeBeforeUpdate);
        Pipeline testPipeline = pipelineList.get(pipelineList.size() - 1);
        assertThat(testPipeline.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPipeline.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingPipeline() throws Exception {
        int databaseSizeBeforeUpdate = pipelineRepository.findAll().size();

        // Create the Pipeline
        PipelineDTO pipelineDTO = pipelineMapper.toDto(pipeline);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPipelineMockMvc.perform(put("/api/pipelines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pipelineDTO)))
            .andExpect(status().isCreated());

        // Validate the Pipeline in the database
        List<Pipeline> pipelineList = pipelineRepository.findAll();
        assertThat(pipelineList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePipeline() throws Exception {
        // Initialize the database
        pipelineRepository.saveAndFlush(pipeline);
        int databaseSizeBeforeDelete = pipelineRepository.findAll().size();

        // Get the pipeline
        restPipelineMockMvc.perform(delete("/api/pipelines/{id}", pipeline.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pipeline> pipelineList = pipelineRepository.findAll();
        assertThat(pipelineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pipeline.class);
        Pipeline pipeline1 = new Pipeline();
        pipeline1.setId(1L);
        Pipeline pipeline2 = new Pipeline();
        pipeline2.setId(pipeline1.getId());
        assertThat(pipeline1).isEqualTo(pipeline2);
        pipeline2.setId(2L);
        assertThat(pipeline1).isNotEqualTo(pipeline2);
        pipeline1.setId(null);
        assertThat(pipeline1).isNotEqualTo(pipeline2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PipelineDTO.class);
        PipelineDTO pipelineDTO1 = new PipelineDTO();
        pipelineDTO1.setId(1L);
        PipelineDTO pipelineDTO2 = new PipelineDTO();
        assertThat(pipelineDTO1).isNotEqualTo(pipelineDTO2);
        pipelineDTO2.setId(pipelineDTO1.getId());
        assertThat(pipelineDTO1).isEqualTo(pipelineDTO2);
        pipelineDTO2.setId(2L);
        assertThat(pipelineDTO1).isNotEqualTo(pipelineDTO2);
        pipelineDTO1.setId(null);
        assertThat(pipelineDTO1).isNotEqualTo(pipelineDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pipelineMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pipelineMapper.fromId(null)).isNull();
    }
}
