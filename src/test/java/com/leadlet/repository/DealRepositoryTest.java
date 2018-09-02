package com.leadlet.repository;


import com.leadlet.LeadletApiApp;
import com.leadlet.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
@Transactional
public class DealRepositoryTest {
    @Autowired
    DealRepository dealRepository;

    @Autowired
    private EntityManager entityManager;

    private AppAccount appAccount;
    private Organization organization;
    private Person person;
    private Pipeline pipeline;
    private Stage stage;

    @Before
    public void setup() {

        appAccount = new AppAccount();
        appAccount.setName("appAccount");
        entityManager.persist(appAccount);
        entityManager.flush();

        organization = new Organization();
        organization.setName("organization");
        organization.setAppAccount(appAccount);

        entityManager.persist(organization);
        entityManager.flush();

        person = new Person();
        person.setName("person");
        person.setEmail("person1@gmail.com");
        person.setOrganization(organization);
        person.setAppAccount(appAccount);
        entityManager.persist(person);
        entityManager.flush();

        pipeline = new Pipeline();
        pipeline.setName("pipeline");
        pipeline.setAppAccount(appAccount);
        entityManager.persist(pipeline);
        entityManager.flush();

        stage = new Stage();
        stage.setName("stage-1");
        stage.setPipeline(pipeline);
        stage.setAppAccount(appAccount);
        entityManager.persist(stage);
        entityManager.flush();

    }

    @Test
    public void saveDealWithoutOptionalFields(){

        Deal deal = new Deal();
        deal.setTitle("deal title");
        deal.setStage(stage);
        deal.setPipeline(pipeline);
        deal.setPerson(person);
        deal.setOrganization(organization);
        deal.setPriority(100);
        deal.setAppAccount(appAccount);

        Deal saved = dealRepository.save(deal);
        assertThat(saved.getId()).isNotNull();

    }

}
