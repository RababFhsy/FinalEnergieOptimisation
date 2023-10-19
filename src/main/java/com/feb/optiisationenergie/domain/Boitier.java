package com.feb.optiisationenergie.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Boitier.
 */
@Entity
@Table(name = "boitier")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Boitier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "boitier_reference")
    private String boitierReference;

    @Column(name = "type")
    private String type;

    @Column(name = "nbr_branche")
    private Integer nbrBranche;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Boitier id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBoitierReference() {
        return this.boitierReference;
    }

    public Boitier boitierReference(String boitierReference) {
        this.setBoitierReference(boitierReference);
        return this;
    }

    public void setBoitierReference(String boitierReference) {
        this.boitierReference = boitierReference;
    }

    public String getType() {
        return this.type;
    }

    public Boitier type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNbrBranche() {
        return this.nbrBranche;
    }

    public Boitier nbrBranche(Integer nbrBranche) {
        this.setNbrBranche(nbrBranche);
        return this;
    }

    public void setNbrBranche(Integer nbrBranche) {
        this.nbrBranche = nbrBranche;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Boitier)) {
            return false;
        }
        return id != null && id.equals(((Boitier) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Boitier{" +
            "id=" + getId() +
            ", boitierReference='" + getBoitierReference() + "'" +
            ", type='" + getType() + "'" +
            ", nbrBranche=" + getNbrBranche() +
            "}";
    }
}
