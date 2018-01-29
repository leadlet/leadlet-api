package com.leadlet.web.rest.vm;

import com.leadlet.service.dto.DealDTO;
import com.leadlet.service.dto.PipelineDTO;
import com.leadlet.service.dto.StageDTO;
import com.leadlet.service.dto.StageWithDealDTO;

import java.util.List;

public class BoardVM {

    private PipelineDTO pipeline;
    private List<StageWithDealDTO> stages;

    public PipelineDTO getPipeline() {
        return pipeline;
    }

    public BoardVM setPipeline(PipelineDTO pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    public List<StageWithDealDTO> getStages() {
        return stages;
    }

    public BoardVM setStages(List<StageWithDealDTO> stages) {
        this.stages = stages;
        return this;
    }
}
