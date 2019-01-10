package com.leadlet.service.dto;


import com.leadlet.domain.enumeration.ActivityStatus;
import com.leadlet.domain.enumeration.DealStatus;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Deal entity.
 */
public class DealDTO implements Serializable {

    private Long id;

    private String title;

    private Long stage_id;

    private Long pipeline_id;

    private Long contact_id;

    private Long agent_id;

    private Instant created_date;

    private Instant last_modified_date;

    private ZonedDateTime possible_close_date;

    private DealValueDTO deal_value;

    private Long deal_source_id;

    private Long deal_channel_id;

    private Long lost_reason_id;

    private Set<Long> product_ids;

    private Set<Long> activity_ids;

    private ActivityStatus activity_status;

    private DealStatus deal_status;

    public DealDTO() {
    }

    public Long getId() {
        return id;
    }

    public DealDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public DealDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public Long getStage_id() {
        return stage_id;
    }

    public DealDTO setStage_id(Long stage_id) {
        this.stage_id = stage_id;
        return this;
    }

    public Long getPipeline_id() {
        return pipeline_id;
    }

    public DealDTO setPipeline_id(Long pipeline_id) {
        this.pipeline_id = pipeline_id;
        return this;
    }

    public Long getContact_id() {
        return contact_id;
    }

    public DealDTO setContact_id(Long contact_id) {
        this.contact_id = contact_id;
        return this;
    }

    public Long getAgent_id() {
        return agent_id;
    }

    public DealDTO setAgent_id(Long agent_id) {
        this.agent_id = agent_id;
        return this;
    }

    public Instant getCreated_date() {
        return created_date;
    }

    public DealDTO setCreated_date(Instant created_date) {
        this.created_date = created_date;
        return this;
    }

    public Instant getLast_modified_date() {
        return last_modified_date;
    }

    public DealDTO setLast_modified_date(Instant last_modified_date) {
        this.last_modified_date = last_modified_date;
        return this;
    }

    public ZonedDateTime getPossible_close_date() {
        return possible_close_date;
    }

    public DealDTO setPossible_close_date(ZonedDateTime possible_close_date) {
        this.possible_close_date = possible_close_date;
        return this;
    }

    public DealValueDTO getDeal_value() {
        return deal_value;
    }

    public DealDTO setDeal_value(DealValueDTO deal_value) {
        this.deal_value = deal_value;
        return this;
    }

    public Long getDeal_source_id() {
        return deal_source_id;
    }

    public DealDTO setDeal_source_id(Long deal_source_id) {
        this.deal_source_id = deal_source_id;
        return this;
    }

    public Long getDeal_channel_id() {
        return deal_channel_id;
    }

    public DealDTO setDeal_channel_id(Long deal_channel_id) {
        this.deal_channel_id = deal_channel_id;
        return this;
    }

    public Long getLost_reason_id() {
        return lost_reason_id;
    }

    public DealDTO setLost_reason_id(Long lost_reason_id) {
        this.lost_reason_id = lost_reason_id;
        return this;
    }

    public Set<Long> getProduct_ids() {
        return product_ids;
    }

    public DealDTO setProduct_ids(Set<Long> product_ids) {
        this.product_ids = product_ids;
        return this;
    }

    public Set<Long> getActivity_ids() {
        return activity_ids;
    }

    public DealDTO setActivity_ids(Set<Long> activity_ids) {
        this.activity_ids = activity_ids;
        return this;
    }

    public ActivityStatus getActivity_status() {
        return activity_status;
    }

    public DealDTO setActivity_status(ActivityStatus activity_status) {
        this.activity_status = activity_status;
        return this;
    }

    public DealStatus getDeal_status() {
        return deal_status;
    }

    public DealDTO setDeal_status(DealStatus deal_status) {
        this.deal_status = deal_status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DealDTO)) return false;
        DealDTO dealDTO = (DealDTO) o;
        return Objects.equals(id, dealDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
