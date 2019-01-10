package com.leadlet.service.mapper;


import com.leadlet.LeadletApiApp;
import com.leadlet.domain.*;
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

        Pipeline pipeline1 = new Pipeline();
        pipeline1.setId(10L);
        pipeline1.setName("Pipeline 1");

        deal.setPipeline(pipeline1);

        Stage s1 = new Stage();
        s1.setId(20L);
        s1.setName("Stage 1");
        s1.setPipeline(pipeline1);

        deal.setStage(s1);

        Activity a1 = new Activity();
        a1.setId(30L);
        a1.setTitle("Activity 1");

        Activity a2 = new Activity();
        a2.setId(40L);
        a2.setTitle("Activity 2");

        Set<Activity> activitySet = new HashSet<>();
        activitySet.add(a1);
        activitySet.add(a2);

        deal.setActivities(activitySet);


    }

    @Test
    public void mapDealToDealDto(){

        DealDTO dealDTO = dealMapper.toDto(deal);

        assertThat(dealDTO.getId()).isEqualTo(1L);
        assertThat(dealDTO.getProduct_ids().size()).isEqualTo(2);
        assertThat(dealDTO.getPipeline_id()).isEqualTo(10L);
        assertThat(dealDTO.getStage_id()).isEqualTo(20L);
        assertThat(dealDTO.getActivity_ids().size()).isEqualTo(2);

    }

}
