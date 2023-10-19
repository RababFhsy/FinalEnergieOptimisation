package com.feb.optiisationenergie.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A CapteurBoitier.
 */
@Entity
@Table(name = "capteur_boitier")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CapteurBoitier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "branche")
    private String branche;

    @ManyToOne
    private Capteur capteur;

    @ManyToOne
    private Boitier boitier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CapteurBoitier id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranche() {
        return this.branche;
    }

    public CapteurBoitier branche(String branche) {
        this.setBranche(branche);
        return this;
    }

    public void setBranche(String branche) {
        this.branche = branche;
    }

    public Capteur getCapteur() {
        return this.capteur;
    }

    public void setCapteur(Capteur capteur) {
        this.capteur = capteur;
    }

    public CapteurBoitier capteur(Capteur capteur) {
        this.setCapteur(capteur);
        return this;
    }

    public Boitier getBoitier() {
        return this.boitier;
    }

    public void setBoitier(Boitier boitier) {
        this.boitier = boitier;
    }

    public CapteurBoitier boitier(Boitier boitier) {
        this.setBoitier(boitier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CapteurBoitier)) {
            return false;
        }
        return id != null && id.equals(((CapteurBoitier) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CapteurBoitier{" +
            "id=" + getId() +
            ", branche='" + getBranche() + "'" +
            "}";
    }
}
