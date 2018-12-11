package com.leadlet.service;

import com.leadlet.service.dto.ActivityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

/**
 * Service Interface for managing Activity.
 */
public interface ActivityService {

    /**
     * Save a activity.
     *
     * @param activityDTO the entity to save
     * @return the persisted entity
     */
    ActivityDTO save(ActivityDTO activityDTO) throws IOException;

    /**
     * Update a activity.
     *
     * @param activityDTO the entity to save
     * @return the persisted entity
     */
    ActivityDTO update(ActivityDTO activityDTO) throws IOException;

    /**
     *  Get the "id" activity.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ActivityDTO findOne(Long id);

    /**
     *  Delete the "id" activity.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    Page<ActivityDTO> search(String filter, Pageable pageable) throws IOException;

}
