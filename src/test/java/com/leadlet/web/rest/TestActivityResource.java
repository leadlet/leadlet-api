package com.leadlet.web.rest;

import com.leadlet.LeadletApiApp;
import com.leadlet.service.ActivityService;
import com.leadlet.service.dto.ActivityDTO;
import com.leadlet.service.dto.DetailedDealDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadletApiApp.class)
public class TestActivityResource {

    private MockMvc mockMvc;

    @Autowired
    private HttpMessageConverter[] httpMessageConverters;

    @Mock
    private ActivityService activityService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ActivityResource activityResource = new ActivityResource(activityService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(activityResource)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .setMessageConverters(httpMessageConverters)
            .build();
    }

    @Test
    public void getActivities() throws Exception {

        DetailedDealDTO dealDTO = new DetailedDealDTO();
        dealDTO.setId(1L);
        dealDTO.setTitle("test deal");

        ActivityDTO activity1 = new ActivityDTO();
        activity1.setId(1L);
        activity1.setMemo("test memo");
        activity1.setDeal(dealDTO);

        ActivityDTO activity2 = new ActivityDTO();
        activity2.setId(2L);
        activity2.setMemo("test2 memo");
        activity2.setDeal(dealDTO);

        Page<ActivityDTO> activities = new PageImpl<>(Arrays.asList(activity1, activity2));

        when(activityService.search(anyString(), any())).thenReturn(activities);

        mockMvc.perform(get("/api/activities")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].memo", is("test memo")))
            .andExpect(jsonPath("$[0].deal.id", is(1)))
            .andExpect(jsonPath("$[0].deal.title", is("test deal")))
            .andExpect(jsonPath("$[1].id", is(2)))
            .andExpect(jsonPath("$[1].memo", is("test2 memo")))
            .andExpect(jsonPath("$[1].deal.id", is(1)))
            .andExpect(jsonPath("$[1].deal.title", is("test deal")));
    }

}
