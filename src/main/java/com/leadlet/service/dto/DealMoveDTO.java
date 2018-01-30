package com.leadlet.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Deal entity.
 */
public class DealMoveDTO implements Serializable {

    private Long id;

    private Long newStageId;

    private Long prevDealId;

    private Long nextDealId;

    public Long getId() {
        return id;
    }

    public DealMoveDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getNewStageId() {
        return newStageId;
    }

    public DealMoveDTO setNewStageId(Long newStageId) {
        this.newStageId = newStageId;
        return this;
    }

    public Long getPrevDealId() {
        return prevDealId;
    }

    public DealMoveDTO setPrevDealId(Long prevDealId) {
        this.prevDealId = prevDealId;
        return this;
    }

    public Long getNextDealId() {
        return nextDealId;
    }

    public DealMoveDTO setNextDealId(Long nextDealId) {
        this.nextDealId = nextDealId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DealMoveDTO)) return false;
        DealMoveDTO that = (DealMoveDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(prevDealId, that.prevDealId) &&
            Objects.equals(nextDealId, that.nextDealId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, prevDealId, nextDealId);
    }
}
