package com.leadlet.web.rest;

import com.leadlet.LeadletApiApp;

import com.leadlet.domain.AppAccount;
import com.leadlet.domain.Team;
import com.leadlet.repository.AppAccountRepository;
import com.leadlet.repository.TeamRepository;
import com.leadlet.service.TeamService;
import com.leadlet.service.dto.TeamDTO;
import com.leadlet.service.mapper.TeamMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TeamResource REST controller.
 *
 * @see TeamResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
public class TeamResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private AppAccountRepository appAccountRepository;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private TeamService teamService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTeamMockMvc;

    private Team team;
    private AppAccount xCompanyAppAccount;
    private AppAccount yCompanyAppAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TeamResource teamResource = new TeamResource(teamService);
        this.restTeamMockMvc = MockMvcBuilders.standaloneSetup(teamResource)
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
    public static Team createEntity(EntityManager em) {
        Team team = new Team()
            .name(DEFAULT_NAME);
        return team;
    }

    @Before
    public void initTest() {
        team = createEntity(em);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void createTeam() throws Exception {
        int databaseSizeBeforeCreate = teamRepository.findAll().size();

        // Create the Team
        TeamDTO teamDTO = teamMapper.toDto(team);
        restTeamMockMvc.perform(post("/api/teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamDTO)))
            .andExpect(status().isCreated());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeCreate + 1);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTeam.getAppAccount()).isEqualTo(xCompanyAppAccount);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void createTeamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teamRepository.findAll().size();

        // Create the Team with an existing ID
        team.setId(1L);
        TeamDTO teamDTO = teamMapper.toDto(team);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamMockMvc.perform(post("/api/teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void getAllTeams() throws Exception {

        Team teamX1 = new Team();
        teamX1.setName("teamX1");
        teamX1.setAppAccount(xCompanyAppAccount);
        teamX1 = teamRepository.saveAndFlush(teamX1);

        Team teamX2 = new Team();
        teamX2.setName("teamX2");
        teamX2.setAppAccount(xCompanyAppAccount);
        teamX2 = teamRepository.saveAndFlush(teamX2);

        Team teamY1 = new Team();
        teamY1.setName("teamY1");
        teamY1.setAppAccount(yCompanyAppAccount);
        teamY1 = teamRepository.saveAndFlush(teamY1);

        // Get all the teamList
        restTeamMockMvc.perform(get("/api/teams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[0].id").value(teamX2.getId()))
            .andExpect(jsonPath("$.[0].name").value(teamX2.getName()))
            .andExpect(jsonPath("$.[1].id").value(teamX1.getId()))
            .andExpect(jsonPath("$.[1].name").value(teamX1.getName()));
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void getTeam() throws Exception {

        Team teamX1 = new Team();
        teamX1.setName("teamX1");
        teamX1.setAppAccount(xCompanyAppAccount);
        teamX1 = teamRepository.saveAndFlush(teamX1);

        // Get the team
        restTeamMockMvc.perform(get("/api/teams/{id}", teamX1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(teamX1.getId()))
            .andExpect(jsonPath("$.name").value(teamX1.getName()));
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void getTeamForOtherAccount() throws Exception {

        Team teamY1 = new Team();
        teamY1.setName("teamY1");
        teamY1.setAppAccount(yCompanyAppAccount);
        teamY1 = teamRepository.saveAndFlush(teamY1);

        restTeamMockMvc.perform(get("/api/teams/{id}", teamY1.getId()))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void getNonExistingTeam() throws Exception {
        // Get the team
        restTeamMockMvc.perform(get("/api/teams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void updateTeam() throws Exception {

        Team teamX1 = new Team();
        teamX1.setName("teamX1");
        teamX1.setAppAccount(xCompanyAppAccount);
        teamX1 = teamRepository.saveAndFlush(teamX1);

        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Update the team
        Team updatedTeam = teamRepository.findOne(teamX1.getId());
        updatedTeam
            .name(UPDATED_NAME);
        TeamDTO teamDTO = teamMapper.toDto(updatedTeam);

        restTeamMockMvc.perform(put("/api/teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamDTO)))
            .andExpect(status().isOk());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void updateStageForOtherAccount() throws Exception {

        Team teamY1 = new Team();
        teamY1.setName("teamY1");
        teamY1.setAppAccount(yCompanyAppAccount);
        teamY1 = teamRepository.saveAndFlush(teamY1);

        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Update the team
        Team updatedTeam = teamRepository.findOne(teamY1.getId());
        updatedTeam
            .name(UPDATED_NAME);
        TeamDTO teamDTO = teamMapper.toDto(updatedTeam);

        restTeamMockMvc.perform(put("/api/teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamDTO)))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void updateNonExistingTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Create the Team
        TeamDTO teamDTO = teamMapper.toDto(team);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTeamMockMvc.perform(put("/api/teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamDTO)))
            .andExpect(status().isNotFound());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    @WithUserDetails("xcompanyadminuser@spacex.com")
    public void deleteTeam() throws Exception {

        Team teamX1 = new Team();
        teamX1.setName("teamX1");
        teamX1.setAppAccount(xCompanyAppAccount);
        teamX1 = teamRepository.saveAndFlush(teamX1);

        int databaseSizeBeforeDelete = teamRepository.findAll().size();

        // Get the team
        restTeamMockMvc.perform(delete("/api/teams/{id}", teamX1.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Team.class);
        Team team1 = new Team();
        team1.setId(1L);
        Team team2 = new Team();
        team2.setId(team1.getId());
        assertThat(team1).isEqualTo(team2);
        team2.setId(2L);
        assertThat(team1).isNotEqualTo(team2);
        team1.setId(null);
        assertThat(team1).isNotEqualTo(team2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamDTO.class);
        TeamDTO teamDTO1 = new TeamDTO();
        teamDTO1.setId(1L);
        TeamDTO teamDTO2 = new TeamDTO();
        assertThat(teamDTO1).isNotEqualTo(teamDTO2);
        teamDTO2.setId(teamDTO1.getId());
        assertThat(teamDTO1).isEqualTo(teamDTO2);
        teamDTO2.setId(2L);
        assertThat(teamDTO1).isNotEqualTo(teamDTO2);
        teamDTO1.setId(null);
        assertThat(teamDTO1).isNotEqualTo(teamDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(teamMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(teamMapper.fromId(null)).isNull();
    }
}
