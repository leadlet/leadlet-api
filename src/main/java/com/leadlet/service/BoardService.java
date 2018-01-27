package com.leadlet.service;

import com.leadlet.service.dto.DealDTO;
import com.leadlet.service.dto.DealMoveDTO;
import com.leadlet.web.rest.vm.BoardVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Deal.
 */
public interface BoardService {


    BoardVM get(Long pipelineId);

}
