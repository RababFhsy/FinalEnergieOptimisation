package com.feb.optiisationenergie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Preference.
 */
@Entity
@Table(name = "preference")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Preference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "temp_min_value")
    private Double tempMinValue;

    @Column(name = "temp_max_value")
    private Double tempMaxValue;

    @Column(name = "plage_horaire")
    private Double plageHoraire;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "consommations", "anomalies", "predictions" }, allowSetters = true)
    private Energie energie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Preference id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTempMinValue() {
        return this.tempMinValue;
    }

    public Preference tempMinValue(Double tempMinValue) {
        this.setTempMinValue(tempMinValue);
        return this;
    }

    public void setTempMinValue(Double tempMinValue) {
        this.tempMinValue = tempMinValue;
    }

    public Double getTempMaxValue() {
        return this.tempMaxValue;
    }

    public Preference tempMaxValue(Double tempMaxValue) {
        this.setTempMaxValue(tempMaxValue);
        return this;
    }

    public void setTempMaxValue(Double tempMaxValue) {
        this.tempMaxValue = tempMaxValue;
    }

    public Double getPlageHoraire() {
        return this.plageHoraire;
    }

    public Preference plageHoraire(Double plageHoraire) {
        this.setPlageHoraire(plageHoraire);
        return this;
    }

    public void setPlageHoraire(Double plageHoraire) {
        this.plageHoraire = plageHoraire;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Preference user(User user) {
        this.setUser(user);
        return this;
    }

    public Energie getEnergie() {
        return this.energie;
    }

    public void setEnergie(Energie energie) {
        this.energie = energie;
    }

    public Preference energie(Energie energie) {
        this.setEnergie(energie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Preference)) {
            return false;
        }
        return id != null && id.equals(((Preference) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Preference{" +
            "id=" + getId() +
            ", tempMinValue=" + getTempMinValue() +
            ", tempMaxValue=" + getTempMaxValue() +
            ", plageHoraire=" + getPlageHoraire() +
            "}";
    }
}
