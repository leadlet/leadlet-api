package com.leadlet.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Deal entity.
 */
public class DealMoveDTO implements Serializable {

    private Long id;

    private Integer newOrder;

    private Long newStageId;

    public DealMoveDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNewOrder() {
        return newOrder;
    }

    public void setNewOrder(Integer newOrder) {
        this.newOrder = newOrder;
    }

    public Long getNewStageId() {
        return newStageId;
    }

    public void setNewStageId(Long newStageId) {
        this.newStageId = newStageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DealMoveDTO that = (DealMoveDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (newOrder != null ? !newOrder.equals(that.newOrder) : that.newOrder != null) return false;
        return newStageId != null ? newStageId.equals(that.newStageId) : that.newStageId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (newOrder != null ? newOrder.hashCode() : 0);
        result = 31 * result + (newStageId != null ? newStageId.hashCode() : 0);
        return result;
    }
}
