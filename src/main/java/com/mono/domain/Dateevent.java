package com.mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Dateevent.
 */
@Entity
@Table(name = "dateevent")
public class Dateevent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private Instant date;

    @ManyToMany(mappedBy = "dateevents")
    @JsonIgnore
    private Set<Evenement> evenements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public Dateevent date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Set<Evenement> getEvenements() {
        return evenements;
    }

    public Dateevent evenements(Set<Evenement> evenements) {
        this.evenements = evenements;
        return this;
    }

    public Dateevent addEvenement(Evenement evenement) {
        this.evenements.add(evenement);
        evenement.getDateevents().add(this);
        return this;
    }

    public Dateevent removeEvenement(Evenement evenement) {
        this.evenements.remove(evenement);
        evenement.getDateevents().remove(this);
        return this;
    }

    public void setEvenements(Set<Evenement> evenements) {
        this.evenements = evenements;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dateevent dateevent = (Dateevent) o;
        if (dateevent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dateevent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dateevent{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
