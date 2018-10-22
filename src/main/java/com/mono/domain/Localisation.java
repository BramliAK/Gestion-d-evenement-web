package com.mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mono.domain.enumeration.Emplacement;

/**
 * A Localisation.
 */
@Entity
@Table(name = "localisation")
public class Localisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nomemplacement", nullable = false)
    private String nomemplacement;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "emplacement", nullable = false)
    private Emplacement emplacement;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Float longitude;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Float latitude;

    @ManyToMany(mappedBy = "localisations")
    @JsonIgnore
    private Set<Evenement> evenements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomemplacement() {
        return nomemplacement;
    }

    public Localisation nomemplacement(String nomemplacement) {
        this.nomemplacement = nomemplacement;
        return this;
    }

    public void setNomemplacement(String nomemplacement) {
        this.nomemplacement = nomemplacement;
    }

    public Emplacement getEmplacement() {
        return emplacement;
    }

    public Localisation emplacement(Emplacement emplacement) {
        this.emplacement = emplacement;
        return this;
    }

    public void setEmplacement(Emplacement emplacement) {
        this.emplacement = emplacement;
    }

    public Float getLongitude() {
        return longitude;
    }

    public Localisation longitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Localisation latitude(Float latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Set<Evenement> getEvenements() {
        return evenements;
    }

    public Localisation evenements(Set<Evenement> evenements) {
        this.evenements = evenements;
        return this;
    }

    public Localisation addEvenement(Evenement evenement) {
        this.evenements.add(evenement);
        evenement.getLocalisations().add(this);
        return this;
    }

    public Localisation removeEvenement(Evenement evenement) {
        this.evenements.remove(evenement);
        evenement.getLocalisations().remove(this);
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
        Localisation localisation = (Localisation) o;
        if (localisation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), localisation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Localisation{" +
            "id=" + getId() +
            ", nomemplacement='" + getNomemplacement() + "'" +
            ", emplacement='" + getEmplacement() + "'" +
            ", longitude=" + getLongitude() +
            ", latitude=" + getLatitude() +
            "}";
    }
}
