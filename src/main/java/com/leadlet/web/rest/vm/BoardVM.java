package com.leadlet.web.rest.vm;

import com.leadlet.service.dto.DealDTO;
import com.leadlet.service.dto.StageDTO;

import java.util.List;

public class BoardVM {

    private Long pipelineId;
    private List<StageDTO> stages;
    private List<DealDTO> deals;

    public Long getPipelineId() {
        return pipelineId;
    }

    public BoardVM setPipelineId(Long pipelineId) {
        this.pipelineId = pipelineId;
        return this;
    }

    public List<StageDTO> getStages() {
        return stages;
    }

    public BoardVM setStages(List<StageDTO> stages) {
        this.stages = stages;
        return this;
    }

    public List<DealDTO> getDeals() {
        return deals;
    }

    public BoardVM setDeals(List<DealDTO> deals) {
        this.deals = deals;
        return this;
    }
}
