package com.leadlet.service.impl;

import com.leadlet.domain.Deal;
import com.leadlet.domain.Pipeline;
import com.leadlet.repository.DealRepository;
import com.leadlet.repository.PipelineRepository;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.BoardService;
import com.leadlet.service.DealService;
import com.leadlet.service.PipelineService;
import com.leadlet.service.StageService;
import com.leadlet.service.dto.*;
import com.leadlet.service.mapper.DealMapper;
import com.leadlet.web.rest.vm.BoardVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

            stageWithDeal.setDealList(dealService.findByStageId(stageDto.getId(),page));

            stageWithDealList.add(stageWithDeal);
        }

        boardVM.setPipeline(pipelineDTO);
        boardVM.setStages(stageWithDealList);

        return boardVM;
    }
}
