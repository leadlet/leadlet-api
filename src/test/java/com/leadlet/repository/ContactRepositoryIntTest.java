package com.leadlet.repository;

import com.leadlet.LeadletApiApp;
import com.leadlet.domain.Contact;
import com.leadlet.domain.enumeration.ContactType;

import com.leadlet.repository.util.SearchCriteria;
import com.leadlet.repository.util.SearchSpecification;
import com.leadlet.repository.util.SpecificationsBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the CustomAuditEventRepository customAuditEventRepository class.
 *
 * @see CustomAuditEventRepository
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
@Transactional
public class ContactRepositoryIntTest {

    @Autowired
    private ContactRepository contactRepository;

    @Test
    public void givenTitle_whenGettingListOfUsers_thenCorrect() {
        SearchSpecification<Contact> spec =
            new SearchSpecification(new SearchCriteria("title", ":", "developer"));

        List<Contact> results = contactRepository.findAll(spec);

        assertThat(results.size()).isEqualTo(2);
    }

    @Test
    public void givenTypePerson_whenGettingListOfContacts_thenCorrect() {
        SearchSpecification<Contact> spec =
            new SearchSpecification(new SearchCriteria("type", ":", ContactType.PERSON));

        List<Contact> result = contactRepository.findAll(spec);

        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void givenNameAndType_whenGettingListOfContacts_thenCorrect(){

        SpecificationsBuilder builder = new SpecificationsBuilder();

        builder.with(new SearchCriteria("name",":","Jay Milburne"));
        builder.with(new SearchCriteria("type",":", "PERSON"));

        Specification<Contact> spec = builder.build();
        List<Contact> result = contactRepository.findAll(spec);

        assertThat(result.size()).isEqualTo(1);

    }
}
