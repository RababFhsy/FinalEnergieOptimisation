package com.feb.optiisationenergie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Locale.
 */
@Entity
@Table(name = "locale")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Locale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "type_local")
    private String typeLocal;

    @ManyToOne
    @JsonIgnoreProperties(value = { "batiment" }, allowSetters = true)
    private Etage etage;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Locale id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public Locale numero(Integer numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getTypeLocal() {
        return this.typeLocal;
    }

    public Locale typeLocal(String typeLocal) {
        this.setTypeLocal(typeLocal);
        return this;
    }

    public void setTypeLocal(String typeLocal) {
        this.typeLocal = typeLocal;
    }

    public Etage getEtage() {
        return this.etage;
    }

    public void setEtage(Etage etage) {
        this.etage = etage;
    }

    public Locale etage(Etage etage) {
        this.setEtage(etage);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Locale)) {
            return false;
        }
        return id != null && id.equals(((Locale) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Locale{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", typeLocal='" + getTypeLocal() + "'" +
            "}";
    }
}
