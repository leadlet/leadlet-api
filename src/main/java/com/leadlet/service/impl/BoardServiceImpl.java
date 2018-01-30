package com.leadlet.service.impl;

import com.leadlet.service.BoardService;
import com.leadlet.service.DealService;
import com.leadlet.service.PipelineService;
import com.leadlet.service.StageService;
import com.leadlet.service.dto.DealDTO;
import com.leadlet.service.dto.PipelineDTO;
import com.leadlet.service.dto.StageDTO;
import com.leadlet.service.dto.StageWithDealDTO;
import com.leadlet.web.rest.vm.BoardVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Service Implementation for managing Deal.
 */
@Service
@Transactional
public class BoardServiceImpl implements BoardService {

    private final Logger log = LoggerFactory.getLogger(BoardServiceImpl.class);

    private final DealService dealService;

    private final PipelineService pipelineService;

    private final StageService stageService;

    public BoardServiceImpl(DealService dealService, PipelineService pipelineService, StageService stageService) {
        this.dealService = dealService;
        this.pipelineService = pipelineService;
        this.stageService = stageService;
    }

    @Override
    public BoardVM get(Long pipelineId, Pageable page) {

        BoardVM boardVM = new BoardVM();

        PipelineDTO pipelineDTO = pipelineService.findOne(pipelineId);

        List<StageDTO> stageList = stageService.findAllByPipelineId(pipelineId);

        List<StageWithDealDTO> stageWithDealList = new ArrayList<>();

        for (StageDTO stageDto: stageList) {
            StageWithDealDTO stageWithDeal = new StageWithDealDTO(stageDto);

            Page<DealDTO> dealList = dealService.findAllByStageId(stageDto.getId(),page);

            stageWithDeal.setDealList(dealList.getContent());
            stageWithDeal.setDealTotalCount(dealList.getTotalElements());
            stageWithDeal.setDealPageCount(dealList.getTotalPages());
            stageWithDeal.setDealTotalPotential(dealService.getDealTotalByStage(stageDto.getId()));

            stageWithDealList.add(stageWithDeal);
        }

        boardVM.setPipeline(pipelineDTO);
        boardVM.setStages(stageWithDealList);

        return boardVM;
    }
}
