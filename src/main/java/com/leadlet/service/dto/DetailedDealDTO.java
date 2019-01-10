package com.leadlet.service.dto;


import com.leadlet.domain.enumeration.ActivityStatus;
import com.leadlet.domain.enumeration.DealStatus;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * A DTO for the Deal entity.
 */
public class DetailedDealDTO implements Serializable {

    private Long id;

    private String title;

    private Integer priority;

    private StageDTO stage;

    private PipelineDTO pipeline;

    private ContactDTO contact;

    private UserDTO agent;

    private Instant created_date;

    private Instant last_modified_date;

    private ZonedDateTime possible_close_date;

    private DealValueDTO deal_value;

    private SourceDTO deal_source;

    private ChannelDTO deal_channel;

    private Set<ProductDTO> products;

    private ActivityStatus activity_status;

    private DealStatus deal_status;

    private LostReasonDTO lost_reason;

    public DetailedDealDTO() {
    }

    public Long getId() {
        return id;
    }

    public DetailedDealDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public DetailedDealDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getPriority() {
        return priority;
    }

    public DetailedDealDTO setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public StageDTO getStage() {
        return stage;
    }

    public DetailedDealDTO setStage(StageDTO stage) {
        this.stage = stage;
        return this;
    }

    public PipelineDTO getPipeline() {
        return pipeline;
    }

    public DetailedDealDTO setPipeline(PipelineDTO pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    public ContactDTO getContact() {
        return contact;
    }

    public DetailedDealDTO setContact(ContactDTO contact) {
        this.contact = contact;
        return this;
    }

    public UserDTO getAgent() {
        return agent;
    }

    public DetailedDealDTO setAgent(UserDTO agent) {
        this.agent = agent;
        return this;
    }

    public Instant getCreated_date() {
        return created_date;
    }

    public DetailedDealDTO setCreated_date(Instant created_date) {
        this.created_date = created_date;
        return this;
    }

    public Instant getLast_modified_date() {
        return last_modified_date;
    }

    public DetailedDealDTO setLast_modified_date(Instant last_modified_date) {
        this.last_modified_date = last_modified_date;
        return this;
    }

    public ZonedDateTime getPossible_close_date() {
        return possible_close_date;
    }

    public DetailedDealDTO setPossible_close_date(ZonedDateTime possible_close_date) {
        this.possible_close_date = possible_close_date;
        return this;
    }

    public DealValueDTO getDeal_value() {
        return deal_value;
    }

    public DetailedDealDTO setDeal_value(DealValueDTO deal_value) {
        this.deal_value = deal_value;
        return this;
    }

    public SourceDTO getDeal_source() {
        return deal_source;
    }

    public DetailedDealDTO setDeal_source(SourceDTO deal_source) {
        this.deal_source = deal_source;
        return this;
    }

    public ChannelDTO getDeal_channel() {
        return deal_channel;
    }

    public DetailedDealDTO setDeal_channel(ChannelDTO deal_channel) {
        this.deal_channel = deal_channel;
        return this;
    }

    public Set<ProductDTO> getProducts() {
        return products;
    }

    public DetailedDealDTO setProducts(Set<ProductDTO> products) {
        this.products = products;
        return this;
    }

    public ActivityStatus getActivity_status() {
        return activity_status;
    }

    public DetailedDealDTO setActivity_status(ActivityStatus activity_status) {
        this.activity_status = activity_status;
        return this;
    }

    public DealStatus getDeal_status() {
        return deal_status;
    }

    public DetailedDealDTO setDeal_status(DealStatus deal_status) {
        this.deal_status = deal_status;
        return this;
    }

    public LostReasonDTO getLost_reason() {
        return lost_reason;
    }

    public DetailedDealDTO setLost_reason(LostReasonDTO lost_reason) {
        this.lost_reason = lost_reason;
        return this;
    }
}
