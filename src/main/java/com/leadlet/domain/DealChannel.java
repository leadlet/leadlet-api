package com.leadlet.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * A Deal Channel.
 */
@Entity
@Table(name = "deal_channel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DealChannel extends AbstractAccountSpecificEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 64)
    @NotNull
    private String name;

    @OneToMany(mappedBy = "dealChannel", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Deal> deals;

    public Set<Deal> getDeals() {
        return deals;
    }

    public void setDeals(Set<Deal> deals) {
        this.deals = deals;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DealChannel)) return false;
        DealChannel channel = (DealChannel) o;
        return Objects.equals(id, channel.id) &&
            Objects.equals(name, channel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "DealChannel{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
