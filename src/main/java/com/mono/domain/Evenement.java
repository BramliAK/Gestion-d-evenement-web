package com.mono.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mono.domain.enumeration.Typeevent;

/**
 * A Evenement.
 */
@Entity
@Table(name = "evenement")
public class Evenement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    
    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "typeevnet", nullable = false)
    private Typeevent typeevnet;

    
    @Lob
    @Column(name = "image", nullable = false)
    private byte[] image;

    @Column(name = "image_content_type", nullable = false)
    private String imageContentType;

    @Column(name = "prix")
    private Float prix;

    @ManyToMany
    @JoinTable(name = "evenement_dateevent",
               joinColumns = @JoinColumn(name = "evenements_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "dateevents_id", referencedColumnName = "id"))
    private Set<Dateevent> dateevents = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "evenement_localisation",
               joinColumns = @JoinColumn(name = "evenements_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "localisations_id", referencedColumnName = "id"))
    private Set<Localisation> localisations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Evenement nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public Evenement description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Typeevent getTypeevnet() {
        return typeevnet;
    }

    public Evenement typeevnet(Typeevent typeevnet) {
        this.typeevnet = typeevnet;
        return this;
    }

    public void setTypeevnet(Typeevent typeevnet) {
        this.typeevnet = typeevnet;
    }

    public byte[] getImage() {
        return image;
    }

    public Evenement image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Evenement imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Float getPrix() {
        return prix;
    }

    public Evenement prix(Float prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public Set<Dateevent> getDateevents() {
        return dateevents;
    }

    public Evenement dateevents(Set<Dateevent> dateevents) {
        this.dateevents = dateevents;
        return this;
    }

    public Evenement addDateevent(Dateevent dateevent) {
        this.dateevents.add(dateevent);
        dateevent.getEvenements().add(this);
        return this;
    }

    public Evenement removeDateevent(Dateevent dateevent) {
        this.dateevents.remove(dateevent);
        dateevent.getEvenements().remove(this);
        return this;
    }

    public void setDateevents(Set<Dateevent> dateevents) {
        this.dateevents = dateevents;
    }

    public Set<Localisation> getLocalisations() {
        return localisations;
    }

    public Evenement localisations(Set<Localisation> localisations) {
        this.localisations = localisations;
        return this;
    }

    public Evenement addLocalisation(Localisation localisation) {
        this.localisations.add(localisation);
        localisation.getEvenements().add(this);
        return this;
    }

    public Evenement removeLocalisation(Localisation localisation) {
        this.localisations.remove(localisation);
        localisation.getEvenements().remove(this);
        return this;
    }

    public void setLocalisations(Set<Localisation> localisations) {
        this.localisations = localisations;
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
        Evenement evenement = (Evenement) o;
        if (evenement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), evenement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Evenement{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", typeevnet='" + getTypeevnet() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", prix=" + getPrix() +
            "}";
    }
}
