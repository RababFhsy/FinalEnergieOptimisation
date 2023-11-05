package com.feb.optiisationenergie.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Batiment.
 */
@Entity
@Table(name = "batiment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Batiment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "batiment_nom")
    private String batimentNom;

    public Batiment(){}

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Batiment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Batiment adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getBatimentNom() {
        return this.batimentNom;
    }

    public Batiment batimentNom(String batimentNom) {
        this.setBatimentNom(batimentNom);
        return this;
    }

    public void setBatimentNom(String batimentNom) {
        this.batimentNom = batimentNom;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Batiment)) {
            return false;
        }
        return id != null && id.equals(((Batiment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Batiment{" +
            "id=" + getId() +
            ", adresse='" + getAdresse() + "'" +
            ", batimentNom='" + getBatimentNom() + "'" +
            "}";
    }
}
