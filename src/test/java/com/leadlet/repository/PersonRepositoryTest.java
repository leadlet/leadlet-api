package com.leadlet.repository;


import com.leadlet.LeadletApiApp;
import com.leadlet.domain.AppAccount;
import com.leadlet.domain.Person;
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
public class PersonRepositoryTest {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    AppAccountRepository appAccountRepository;

    private AppAccount appAccount1, appAccount2;
    private Person person1, person2, person3, person4;

    @Before
    public void setup() {
        appAccount1 = new AppAccount();
        appAccount1.setName("appAccount1");
        appAccount1 = appAccountRepository.save(appAccount1);

        appAccount2 = new AppAccount();
        appAccount2.setName("appAccount2");
        appAccount2 = appAccountRepository.save(appAccount2);

        person1 = new Person();
        person1.setName("person1");
        person1.setEmail("person1@gmail.com");
        person1.setAppAccount(appAccount1);
        person1 = personRepository.save(person1);

        person2 = new Person();
        person2.setName("person2");
        person2.setEmail("person2@gmail.com");
        person2.setAppAccount(appAccount2);

        person2 = personRepository.save(person2);

        person3 = new Person();
        person3.setName("person3");
        person3.setEmail("person3@gmail.com");
        person3.setAppAccount(appAccount2);
        person3 = personRepository.save(person3);

        person4 = new Person();
        person4.setName("person4");
        person4.setEmail("person4@gmail.com");
        person4.setAppAccount(appAccount2);

        person4 = personRepository.save(person4);
    }

    @Test
    public void findBySpecification(){


        Specification<Person> isNamePerson1 = new Specification<Person>() {
            @Override
            public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("name"),"person1");
            }
        };

        Person person = personRepository.findOne(where(isNamePerson1));

        assertThat(person.getId()).isEqualTo(person1.getId());

    }

}
