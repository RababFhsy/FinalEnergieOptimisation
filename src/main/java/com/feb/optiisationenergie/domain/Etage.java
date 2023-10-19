package com.feb.optiisationenergie.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Etage.
 */
@Entity
@Table(name = "etage")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Etage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "etage_numero")
    private Integer etageNumero;

    @ManyToOne
    private Batiment batiment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Etage id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEtageNumero() {
        return this.etageNumero;
    }

    public Etage etageNumero(Integer etageNumero) {
        this.setEtageNumero(etageNumero);
        return this;
    }

    public void setEtageNumero(Integer etageNumero) {
        this.etageNumero = etageNumero;
    }

    public Batiment getBatiment() {
        return this.batiment;
    }

    public void setBatiment(Batiment batiment) {
        this.batiment = batiment;
    }

    public Etage batiment(Batiment batiment) {
        this.setBatiment(batiment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Etage)) {
            return false;
        }
        return id != null && id.equals(((Etage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Etage{" +
            "id=" + getId() +
            ", etageNumero=" + getEtageNumero() +
            "}";
    }
}
