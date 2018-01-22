package com.leadlet.service;

import com.leadlet.service.dto.ActivityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
    ActivityDTO save(ActivityDTO activityDTO);

    /**
     * Update a activity.
     *
     * @param activityDTO the entity to save
     * @return the persisted entity
     */
    ActivityDTO update(ActivityDTO activityDTO);

    /**
     *  Get all the activities.
     *
     *  @return the list of entities
     */
    List<ActivityDTO> findAll();

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

    /**
     *  Get all the activities.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ActivityDTO> findByPersonId(Long personId, Pageable pageable);
}
