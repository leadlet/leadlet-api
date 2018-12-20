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
    private Contact contact;
    private Pipeline pipeline;
    private Stage stage;

    @Before
    public void setup() {

        appAccount = new AppAccount();
        appAccount.setName("appAccount");
        entityManager.persist(appAccount);
        entityManager.flush();

        contact = new Contact();
        contact.setName("contact");
        contact.setEmail("contact1@gmail.com");
        contact.setAppAccount(appAccount);
        entityManager.persist(contact);
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
        deal.setContact(contact);
        deal.setPriority(100);
        deal.setAppAccount(appAccount);

        Deal saved = dealRepository.save(deal);
        assertThat(saved.getId()).isNotNull();

    }

}
