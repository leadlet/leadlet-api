package com.leadlet.service.mapper;


import com.leadlet.LeadletApiApp;
import com.leadlet.domain.Deal;
import com.leadlet.domain.Product;
import com.leadlet.domain.enumeration.SyncStatus;
import com.leadlet.service.dto.DealDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
@Transactional
public class DealMapperTest {

    @Autowired
    DealMapper dealMapper;

    Deal deal;

    @Before
    public void setup() {

        deal = new Deal();
        deal.setId(1L);
        deal.setSyncStatus(SyncStatus.NOT_SYNCED);

        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("PRODUCT 1");

        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("PRODUCT 2");

        Set<Product> productSet = new HashSet<>();
        productSet.add(p1);
        productSet.add(p2);

        deal.setProducts(productSet);
    }

    @Test
    public void mapDealToDealDto(){

        DealDTO dealDTO = dealMapper.toDto(deal);

        assertThat(dealDTO.getId()).isEqualTo(1L);
        assertThat(dealDTO.getProduct_ids().size()).isEqualTo(2);

    }

}
