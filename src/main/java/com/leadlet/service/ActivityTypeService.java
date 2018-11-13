package com.leadlet.service;

import com.leadlet.service.dto.ActivityTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActivityTypeService {

    /**
     * Save a activityType.
     *
     * @param activityTypeDTO the entity to save
     * @return the persisted entity
     */
    ActivityTypeDTO save(ActivityTypeDTO activityTypeDTO);

    /**
     * Update a activityType.
     *
     * @param activityTypeDTO the entity to update
     * @return the persisted entity
     */
    ActivityTypeDTO update(ActivityTypeDTO activityTypeDTO);

    /**
     * Get all the activityTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ActivityTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" activityType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ActivityTypeDTO findOne(Long id);

    /**
     * Delete the "id" activityType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
