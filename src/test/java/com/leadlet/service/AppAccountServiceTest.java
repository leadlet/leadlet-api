package com.leadlet.service;

import com.leadlet.LeadletApiApp;
import com.leadlet.service.mapper.AppAccountMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test class for the AppAccountService.
 *
 * @see AppAccountService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
@Transactional
public class AppAccountServiceTest {

    @Autowired
    private AppAccountService appAccountService;

    @Autowired
    private AppAccountMapper appAccountMapper;

}
