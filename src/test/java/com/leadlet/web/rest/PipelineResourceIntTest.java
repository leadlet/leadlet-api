package com.leadlet.web.rest;

import com.leadlet.LeadletApiApp;

import com.leadlet.domain.AppAccount;
import com.leadlet.domain.Pipeline;
import com.leadlet.repository.AppAccountRepository;
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
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
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
    private AppAccountRepository appAccountRepository;

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
    private AppAccount xCompanyAppAccount;
    private AppAccount yCompanyAppAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PipelineResource pipelineResource = new PipelineResource(pipelineService);
        this.restPipelineMockMvc = MockMvcBuilders.standaloneSetup(pipelineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void setAppAccountsUsers(){
        this.xCompanyAppAccount = this.appAccountRepository.findOneByName("CompanyX").get();
        this.yCompanyAppAccount = this.appAccountRepository.findOneByName("CompanyY").get();
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
    @WithUserDetails("xcompanyadminuser")
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

        assertThat(testPipeline.getAppAccount()).isEqualTo(xCompanyAppAccount);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser")
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
    @WithUserDetails("xcompanyadminuser")
    public void getAllPipelines() throws Exception {
        // Initialize the database
        Pipeline pipelineX1 = new Pipeline();
        pipelineX1.setName("pipelineX1");
        pipelineX1.setOrder(1);
        pipelineX1.setAppAccount(xCompanyAppAccount);
        pipelineX1 = pipelineRepository.saveAndFlush(pipelineX1);

        Pipeline pipelineX2 = new Pipeline();
        pipelineX2.setName("pipelineX2");
        pipelineX2.setOrder(2);
        pipelineX2.setAppAccount(xCompanyAppAccount);
        pipelineX2 = pipelineRepository.saveAndFlush(pipelineX2);

        Pipeline pipelineY1 = new Pipeline();
        pipelineY1.setName("pipelineY1");
        pipelineY1.setOrder(1);
        pipelineY1.setAppAccount(yCompanyAppAccount);
        pipelineY1 = pipelineRepository.saveAndFlush(pipelineY1);

        // Get all the pipelineList
        restPipelineMockMvc.perform(get("/api/pipelines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$",hasSize(2)))
            .andExpect(jsonPath("$.[0].id").value(pipelineX2.getId()))
            .andExpect(jsonPath("$.[0].name").value("pipelineX2"))
            .andExpect(jsonPath("$.[0].order").value(2))
            .andExpect(jsonPath("$.[1].id").value(pipelineX1.getId()))
            .andExpect(jsonPath("$.[1].name").value("pipelineX1"))
            .andExpect(jsonPath("$.[1].order").value(1));
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser")
    public void getPipeline() throws Exception {

        Pipeline pipelineX1 = new Pipeline();
        pipelineX1.setName("pipelineX1");
        pipelineX1.setOrder(1);
        pipelineX1.setAppAccount(xCompanyAppAccount);
        pipelineX1 = pipelineRepository.saveAndFlush(pipelineX1);

        // Get the pipeline
        restPipelineMockMvc.perform(get("/api/pipelines/{id}", pipelineX1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pipelineX1.getId().intValue()))
            .andExpect(jsonPath("$.name").value(pipelineX1.getName()))
            .andExpect(jsonPath("$.order").value(pipelineX1.getOrder()));
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser")
    public void getPipelineForOtherAccount() throws Exception {

        Pipeline pipelineY1 = new Pipeline();
        pipelineY1.setName("pipelineY1");
        pipelineY1.setOrder(1);
        pipelineY1.setAppAccount(yCompanyAppAccount);
        pipelineY1 = pipelineRepository.saveAndFlush(pipelineY1);

        // Get the pipeline
        restPipelineMockMvc.perform(get("/api/pipelines/{id}", pipelineY1.getId()))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser")
    public void getNonExistingPipeline() throws Exception {
        // Get the pipeline
        restPipelineMockMvc.perform(get("/api/pipelines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser")
    public void updatePipeline() throws Exception {
        // Initialize the database
        Pipeline pipelineX = new Pipeline();
        pipelineX.setName("pipelineX1");
        pipelineX.setOrder(1);
        pipelineX.setAppAccount(xCompanyAppAccount);
        pipelineX = pipelineRepository.saveAndFlush(pipelineX);

        pipelineX = pipelineRepository.saveAndFlush(pipelineX);
        int databaseSizeBeforeUpdate = pipelineRepository.findAll().size();

        // Update the pipeline
        Pipeline updatedPipeline = pipelineRepository.findOne(pipelineX.getId());
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
    @WithUserDetails("xcompanyadminuser")
    public void updatePipelineForOtherAccount() throws Exception {
        // Initialize the database
        Pipeline pipelineY = new Pipeline();
        pipelineY.setName("pipelineY");
        pipelineY.setOrder(1);
        pipelineY.setAppAccount(yCompanyAppAccount);
        pipelineY = pipelineRepository.saveAndFlush(pipelineY);

        pipelineY = pipelineRepository.saveAndFlush(pipelineY);
        int databaseSizeBeforeUpdate = pipelineRepository.findAll().size();

        // Update the pipeline
        Pipeline updatedPipeline = pipelineRepository.findOne(pipelineY.getId());
        updatedPipeline
            .name(UPDATED_NAME)
            .order(UPDATED_ORDER);
        PipelineDTO pipelineDTO = pipelineMapper.toDto(updatedPipeline);

        restPipelineMockMvc.perform(put("/api/pipelines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pipelineDTO)))
            .andExpect(status().isBadRequest());

    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser")
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
    @WithUserDetails("xcompanyadminuser")
    public void deletePipeline() throws Exception {
        // Initialize the database
        Pipeline pipelineX = new Pipeline();
        pipelineX.setName("pipelineX");
        pipelineX.setOrder(1);
        pipelineX.setAppAccount(xCompanyAppAccount);
        pipelineX = pipelineRepository.saveAndFlush(pipelineX);

        int databaseSizeBeforeDelete = pipelineRepository.findAll().size();

        // Get the pipeline
        restPipelineMockMvc.perform(delete("/api/pipelines/{id}", pipelineX.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pipeline> pipelineList = pipelineRepository.findAll();
        assertThat(pipelineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser")
    public void deletePipelineForOtherAccount() throws Exception {
        // Initialize the database
        Pipeline pipelineY = new Pipeline();
        pipelineY.setName("pipelineY");
        pipelineY.setOrder(1);
        pipelineY.setAppAccount(yCompanyAppAccount);
        pipelineY = pipelineRepository.saveAndFlush(pipelineY);

        int databaseSizeBeforeDelete = pipelineRepository.findAll().size();

        // Get the pipeline
        restPipelineMockMvc.perform(delete("/api/pipelines/{id}", pipelineY.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isBadRequest());
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
