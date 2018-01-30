package com.leadlet.service;

import com.leadlet.web.rest.vm.BoardVM;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Deal.
 */
public interface BoardService {

    BoardVM get(Long pipelineId, Pageable page);

}
