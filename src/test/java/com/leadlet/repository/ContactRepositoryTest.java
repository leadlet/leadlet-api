package com.leadlet.repository;


import com.leadlet.LeadletApiApp;
import com.leadlet.domain.AppAccount;
import com.leadlet.domain.Contact;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.jpa.domain.Specifications.where;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
@Transactional
public class ContactRepositoryTest {
    @Autowired
    ContactRepository contactRepository;

    @Autowired
    AppAccountRepository appAccountRepository;

    private AppAccount appAccount1, appAccount2;
    private Contact contact1, contact2, contact3, contact4;

    @Before
    public void setup() {
        appAccount1 = new AppAccount();
        appAccount1.setName("appAccount1");
        appAccount1 = appAccountRepository.save(appAccount1);

        appAccount2 = new AppAccount();
        appAccount2.setName("appAccount2");
        appAccount2 = appAccountRepository.save(appAccount2);

        contact1 = new Contact();
        contact1.setName("contact1");
        contact1.setEmail("contact1@gmail.com");
        contact1.setAppAccount(appAccount1);
        contact1 = contactRepository.save(contact1);

        contact2 = new Contact();
        contact2.setName("contact2");
        contact2.setEmail("contact2@gmail.com");
        contact2.setAppAccount(appAccount2);

        contact2 = contactRepository.save(contact2);

        contact3 = new Contact();
        contact3.setName("contact3");
        contact3.setEmail("contact3@gmail.com");
        contact3.setAppAccount(appAccount2);
        contact3 = contactRepository.save(contact3);

        contact4 = new Contact();
        contact4.setName("contact4");
        contact4.setEmail("contact4@gmail.com");
        contact4.setAppAccount(appAccount2);

        contact4 = contactRepository.save(contact4);
    }

    @Test
    public void findBySpecification(){


        Specification<Contact> isNameContact1 = new Specification<Contact>() {
            @Override
            public Predicate toPredicate(Root<Contact> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("name"),"contact1");
            }
        };

        Contact contact = contactRepository.findOne(where(isNameContact1));

        assertThat(contact.getId()).isEqualTo(contact1.getId());

    }

}
