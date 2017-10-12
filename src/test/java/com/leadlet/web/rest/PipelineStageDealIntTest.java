package com.leadlet.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leadlet.LeadletApiApp;
import com.leadlet.domain.AppAccount;
import com.leadlet.domain.Deal;
import com.leadlet.domain.Pipeline;
import com.leadlet.domain.Stage;
import com.leadlet.repository.AppAccountRepository;
import com.leadlet.repository.DealRepository;
import com.leadlet.repository.PipelineRepository;
import com.leadlet.repository.StageRepository;
import com.leadlet.service.DealService;
import com.leadlet.service.PipelineService;
import com.leadlet.service.StageService;
import com.leadlet.service.dto.DealDTO;
import com.leadlet.service.dto.PipelineDTO;
import com.leadlet.service.mapper.DealMapper;
import com.leadlet.service.mapper.PipelineMapper;
import com.leadlet.service.mapper.StageMapper;
import com.leadlet.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
public class PipelineStageDealIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final Double UPDATED_POTENTIAL_VALUE = 2D;

    @Autowired
    private PipelineService pipelineService;

    @Autowired
    private PipelineRepository pipelineRepository;

    @Autowired
    private PipelineMapper pipelineMapper;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private AppAccountRepository appAccountRepository;

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private StageMapper stageMapper;

    @Autowired
    private StageService stageService;

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private DealMapper dealMapper;

    @Autowired
    private DealService dealService;

    @Autowired
    private EntityManager em;

    private MockMvc restPipelineMockMvc;

    private Pipeline pipeline;

    private MockMvc restStageMockMvc;

    private Stage stage;

    private MockMvc restDealMockMvc;

    private Deal deal;

    private AppAccount xCompanyAppAccount;
    private AppAccount yCompanyAppAccount;

    List<Pipeline> pipelineList;
    Pipeline testPipeline;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        PipelineResource pipelineResource = new PipelineResource(pipelineService);
        this.restPipelineMockMvc = MockMvcBuilders.standaloneSetup(pipelineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();

        StageResource stageResource = new StageResource(stageService);
        this.restStageMockMvc = MockMvcBuilders.standaloneSetup(stageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();

        DealResource dealResource = new DealResource(dealService);
        this.restDealMockMvc = MockMvcBuilders.standaloneSetup(dealResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void setAppAccountsUsers() {
        this.xCompanyAppAccount = this.appAccountRepository.findOneByName("CompanyX").get();
        this.yCompanyAppAccount = this.appAccountRepository.findOneByName("CompanyY").get();
    }

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
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void createPipelineAddStagesAndDeals() throws Exception {

        //---------- For the Pipeline --------
        int databaseSizeBeforeCreateForPipeline = pipelineRepository.findAll().size();

        // Create the Pipeline
        PipelineDTO pipelineDTO = pipelineMapper.toDto(pipeline);
        restPipelineMockMvc.perform(post("/api/pipelines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pipelineDTO)))
            .andExpect(status().isCreated());

        // Validate the Pipeline in the database
        pipelineList = pipelineRepository.findAll();
        assertThat(pipelineList).hasSize(databaseSizeBeforeCreateForPipeline + 1);
        testPipeline = pipelineList.get(pipelineList.size() - 1);
        assertThat(testPipeline.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPipeline.getOrder()).isEqualTo(DEFAULT_ORDER);

        assertThat(testPipeline.getAppAccount()).isEqualTo(xCompanyAppAccount);

        //---------- For the Stage ---------

        //Create 2 stages and add to the pipeline
        Stage stageX1 = new Stage();
        stageX1.setName("stageX1");
        stageX1.setPipeline(testPipeline);
        stageX1.setAppAccount(xCompanyAppAccount);
        stageX1 = stageRepository.saveAndFlush(stageX1);

        Stage stageX2 = new Stage();
        stageX2.setName("stageX2");
        stageX2.setPipeline(testPipeline);
        stageX2.setAppAccount(xCompanyAppAccount);
        stageX2 = stageRepository.saveAndFlush(stageX2);

        Stage stageX3 = new Stage();
        stageX3.setName("stageX3");
        stageX3.setPipeline(testPipeline);
        stageX3.setAppAccount(xCompanyAppAccount);
        stageX3 = stageRepository.saveAndFlush(stageX3);

        //------------ For the Deal -----------

        //Create 2 deals and add to the Stages
        Deal dealX1 = new Deal();
        dealX1.setAppAccount(xCompanyAppAccount);
        dealX1.setName("dealX1");
        dealX1.setOrder(1);
        dealX1.setPotentialValue(10.0);
        dealX1.setStage(stageX1);
        //dealX1.setStage(stageX2); TODO: test edilmeli.. bir deal birden fazla stage de yer alabilir mi?
        dealX1 = dealRepository.saveAndFlush(dealX1);

        Deal dealX2 = new Deal();
        dealX2.setAppAccount(xCompanyAppAccount);
        dealX2.setName("dealX2");
        dealX2.setOrder(2);
        dealX2.setPotentialValue(10.0);
        dealX2.setStage(stageX1);
        dealX2 = dealRepository.saveAndFlush(dealX2);

        //--------- update Deals Stage --------------

        // Initialize the database
        dealRepository.saveAndFlush(dealX1);

        // Update the deal
        ObjectMapper mapper = new ObjectMapper();
        MvcResult mvcResult = restDealMockMvc.perform(get("/api/deals/{id}", dealX1.getId())).andReturn();
        DealDTO updatedDeal = mapper.readValue(mvcResult.getResponse().getContentAsByteArray(), DealDTO.class);

        updatedDeal.setStageId(stageX3.getId());

        restDealMockMvc.perform(put("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeal)))
            .andExpect(status().isOk());

        // Validate the Deal in the database
        Deal testDeal = dealRepository.getOne(dealX1.getId());
        assertThat(testDeal.getName()).isEqualTo("dealX1");
        assertThat(testDeal.getOrder()).isEqualTo(1);
        assertThat(testDeal.getPotentialValue()).isEqualTo(10.0);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void createPipelineAddStagesAndDealsForOtherAccount() throws Exception {

        //Create 2 stages and add to the pipeline
        Stage stageX1 = new Stage();
        stageX1.setName("stageX1");
        stageX1.setPipeline(testPipeline);
        stageX1.setAppAccount(yCompanyAppAccount);
        stageX1 = stageRepository.saveAndFlush(stageX1);

        Deal dealX1 = new Deal();
        dealX1.setAppAccount(yCompanyAppAccount);
        dealX1.setName("dealX1");
        dealX1.setOrder(1);
        dealX1.setPotentialValue(10.0);
        dealX1.setStage(stageX1);
        dealX1 = dealRepository.saveAndFlush(dealX1);

        restDealMockMvc.perform(get("/api/deals/{id}", dealX1.getId()))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void createPipelineAddStagesAndDealsForOtherAccount2() throws Exception {

        //Create 2 stages and add to the pipeline
        Stage stageX1 = new Stage();
        stageX1.setName("stageX1");
        stageX1.setPipeline(testPipeline);
        stageX1.setAppAccount(yCompanyAppAccount);
        stageX1 = stageRepository.saveAndFlush(stageX1);

        Deal dealX1 = new Deal();
        dealX1.setAppAccount(yCompanyAppAccount);
        dealX1.setName("dealX1");
        dealX1.setOrder(1);
        dealX1.setPotentialValue(10.0);
        dealX1.setStage(stageX1);
        dealX1 = dealRepository.saveAndFlush(dealX1);

        Deal updatedDeal = dealRepository.findOne(dealX1.getId());
        updatedDeal.setStage(stageX1);
        DealDTO dealDTO = dealMapper.toDto(updatedDeal);

        restDealMockMvc.perform(put("/api/deals")
        .contentType(TestUtil.APPLICATION_JSON_UTF8)
        .content(TestUtil.convertObjectToJsonBytes(dealDTO)))
        .andExpect(status().isNotFound());  //TODO: Spring JPA find and update.. DealResource'da update fonk. guncellenmeli
    }
}
