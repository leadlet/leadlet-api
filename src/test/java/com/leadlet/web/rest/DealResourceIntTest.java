package com.leadlet.web.rest;

import com.leadlet.LeadletApiApp;

import com.leadlet.domain.AppAccount;
import com.leadlet.domain.Deal;
import com.leadlet.repository.AppAccountRepository;
import com.leadlet.repository.DealRepository;
import com.leadlet.service.DealService;
import com.leadlet.service.dto.DealDTO;
import com.leadlet.service.mapper.DealMapper;
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
 * Test class for the DealResource REST controller.
 *
 * @see DealResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
public class DealResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final Double DEFAULT_POTENTIAL_VALUE = 1D;
    private static final Double UPDATED_POTENTIAL_VALUE = 2D;

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private AppAccountRepository appAccountRepository;

    @Autowired
    private DealMapper dealMapper;

    @Autowired
    private DealService dealService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDealMockMvc;

    private Deal deal;
    private AppAccount xCompanyAppAccount;
    private AppAccount yCompanyAppAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
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

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deal createEntity(EntityManager em) {
        Deal deal = new Deal()
            .name(DEFAULT_NAME)
            .order(DEFAULT_ORDER)
            .potentialValue(DEFAULT_POTENTIAL_VALUE);
        return deal;
    }

    @Before
    public void initTest() {
        deal = createEntity(em);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser")
    public void createDeal() throws Exception {
        int databaseSizeBeforeCreate = dealRepository.findAll().size();

        // Create the Deal
        DealDTO dealDTO = dealMapper.toDto(deal);
        restDealMockMvc.perform(post("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dealDTO)))
            .andExpect(status().isCreated());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeCreate + 1);
        Deal testDeal = dealList.get(dealList.size() - 1);
        assertThat(testDeal.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeal.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testDeal.getPotentialValue()).isEqualTo(DEFAULT_POTENTIAL_VALUE);
        assertThat(testDeal.getAppAccount()).isEqualTo(xCompanyAppAccount);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser")
    public void createDealWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dealRepository.findAll().size();

        // Create the Deal with an existing ID
        deal.setId(1L);
        DealDTO dealDTO = dealMapper.toDto(deal);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDealMockMvc.perform(post("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dealDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser")
    public void getAllDeals() throws Exception {

        Deal dealX1 = new Deal();
        dealX1.setName("dealX1");
        dealX1.setOrder(1);
        dealX1.setPotentialValue(DEFAULT_POTENTIAL_VALUE);
        dealX1.setAppAccount(xCompanyAppAccount);
        dealX1 = dealRepository.saveAndFlush(dealX1);

        Deal dealX2 = new Deal();
        dealX2.setName("dealX2");
        dealX2.setOrder(2);
        dealX2.setPotentialValue(DEFAULT_POTENTIAL_VALUE);
        dealX2.setAppAccount(xCompanyAppAccount);
        dealX2 = dealRepository.saveAndFlush(dealX2);

        Deal dealY1 = new Deal();
        dealY1.setName("dealY1");
        dealY1.setOrder(3);
        dealY1.setPotentialValue(DEFAULT_POTENTIAL_VALUE);
        dealY1.setAppAccount(yCompanyAppAccount);
        dealY1 = dealRepository.saveAndFlush(dealY1);


        // Get all the dealList
        restDealMockMvc.perform(get("/api/deals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$.[0].id").value(dealX2.getId()))
            .andExpect(jsonPath("$.[0].name").value("dealX2"))
            .andExpect(jsonPath("$.[0].order").value(2))
            .andExpect(jsonPath("$.[0].potentialValue").value(DEFAULT_POTENTIAL_VALUE))
            .andExpect(jsonPath("$.[1].id").value(dealX1.getId()))
            .andExpect(jsonPath("$.[1].name").value("dealX1"))
            .andExpect(jsonPath("$.[1].order").value(1))
            .andExpect(jsonPath("$.[1].potentialValue").value(DEFAULT_POTENTIAL_VALUE));
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser")
    public void getDeal() throws Exception {

        Deal dealX1 = new Deal();
        dealX1.setName("dealX1");
        dealX1.setOrder(1);
        dealX1.setPotentialValue(DEFAULT_POTENTIAL_VALUE);
        dealX1.setAppAccount(xCompanyAppAccount);
        dealX1 = dealRepository.saveAndFlush(dealX1);

        // Get the deal
        restDealMockMvc.perform(get("/api/deals/{id}", deal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dealX1.getId()))
            .andExpect(jsonPath("$.name").value(dealX1.getName()))
            .andExpect(jsonPath("$.order").value(dealX1.getOrder()))
            .andExpect(jsonPath("$.potentialValue").value(dealX1.getPotentialValue()));
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser")
    public void getDealForOtherAccount() throws Exception {

        Deal dealY1 = new Deal();
        dealY1.setName("dealY1");
        dealY1.setOrder(3);
        dealY1.setPotentialValue(DEFAULT_POTENTIAL_VALUE);
        dealY1.setAppAccount(yCompanyAppAccount);
        dealY1 = dealRepository.saveAndFlush(dealY1);

        restDealMockMvc.perform(get("/api/deals/{id}", dealY1.getId()))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser")
    public void getNonExistingDeal() throws Exception {
        // Get the deal
        restDealMockMvc.perform(get("/api/deals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser")
    public void updateDeal() throws Exception {
        Deal dealX1 = new Deal();
        dealX1.setName("dealX1");
        dealX1.setOrder(1);
        dealX1.setPotentialValue(DEFAULT_POTENTIAL_VALUE);
        dealX1.setAppAccount(xCompanyAppAccount);
        dealX1 = dealRepository.saveAndFlush(dealX1);

        int databaseSizeBeforeUpdate = dealRepository.findAll().size();

        // Update the deal
        Deal updatedDeal = dealRepository.findOne(dealX1.getId());
        updatedDeal
            .name(UPDATED_NAME)
            .order(UPDATED_ORDER)
            .potentialValue(UPDATED_POTENTIAL_VALUE);
        DealDTO dealDTO = dealMapper.toDto(updatedDeal);

        restDealMockMvc.perform(put("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dealDTO)))
            .andExpect(status().isOk());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeUpdate);
        Deal testDeal = dealList.get(dealList.size() - 1);
        assertThat(testDeal.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeal.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testDeal.getPotentialValue()).isEqualTo(UPDATED_POTENTIAL_VALUE);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser")
    public void updateDealForOtherAccount() throws Exception {

        Deal dealY1 = new Deal();
        dealY1.setName("dealY1");
        dealY1.setOrder(3);
        dealY1.setPotentialValue(DEFAULT_POTENTIAL_VALUE);
        dealY1.setAppAccount(yCompanyAppAccount);
        dealY1 = dealRepository.saveAndFlush(dealY1);

        int databaseSizeBeforeUpdate = dealRepository.findAll().size();

        Deal updatedDeal = dealRepository.findOne(dealY1.getId());
        updatedDeal
            .name(UPDATED_NAME)
            .order(UPDATED_ORDER)
            .potentialValue(UPDATED_POTENTIAL_VALUE);

        // Create the Deal
        DealDTO dealDTO = dealMapper.toDto(updatedDeal);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDealMockMvc.perform(put("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dealDTO)))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser")
    public void updateNonExistingDeal() throws Exception {
        int databaseSizeBeforeUpdate = dealRepository.findAll().size();

        // Create the Deal
        DealDTO dealDTO = dealMapper.toDto(deal);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDealMockMvc.perform(put("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dealDTO)))
            .andExpect(status().isNotFound());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser")
    public void deleteDeal() throws Exception {
        Deal dealX1 = new Deal();
        dealX1.setName("dealX1");
        dealX1.setOrder(1);
        dealX1.setPotentialValue(DEFAULT_POTENTIAL_VALUE);
        dealX1.setAppAccount(xCompanyAppAccount);
        dealX1 = dealRepository.saveAndFlush(dealX1);

        int databaseSizeBeforeDelete = dealRepository.findAll().size();

        // Get the deal
        restDealMockMvc.perform(delete("/api/deals/{id}", dealX1.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    @WithUserDetails("ycompanyadminuser")
    public void deleteDealForOtherAccount() throws Exception {

        Deal dealX1 = new Deal();
        dealX1.setName("dealX1");
        dealX1.setOrder(1);
        dealX1.setPotentialValue(DEFAULT_POTENTIAL_VALUE);
        dealX1.setAppAccount(xCompanyAppAccount);
        dealX1 = dealRepository.saveAndFlush(dealX1);

        int databaseSizeBeforeDelete = dealRepository.findAll().size();

        // Get the deal
        restDealMockMvc.perform(delete("/api/deals/{id}", dealX1.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Deal.class);
        Deal deal1 = new Deal();
        deal1.setId(1L);
        Deal deal2 = new Deal();
        deal2.setId(deal1.getId());
        assertThat(deal1).isEqualTo(deal2);
        deal2.setId(2L);
        assertThat(deal1).isNotEqualTo(deal2);
        deal1.setId(null);
        assertThat(deal1).isNotEqualTo(deal2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DealDTO.class);
        DealDTO dealDTO1 = new DealDTO();
        dealDTO1.setId(1L);
        DealDTO dealDTO2 = new DealDTO();
        assertThat(dealDTO1).isNotEqualTo(dealDTO2);
        dealDTO2.setId(dealDTO1.getId());
        assertThat(dealDTO1).isEqualTo(dealDTO2);
        dealDTO2.setId(2L);
        assertThat(dealDTO1).isNotEqualTo(dealDTO2);
        dealDTO1.setId(null);
        assertThat(dealDTO1).isNotEqualTo(dealDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dealMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dealMapper.fromId(null)).isNull();
    }
}
