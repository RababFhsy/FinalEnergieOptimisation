package com.feb.optiisationenergie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A Anomalie.
 */
@Entity
@Table(name = "anomalie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Anomalie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "zone_normale_min")
    private Double zoneNormaleMin;

    @Column(name = "zone_normale_max")
    private Double zoneNormaleMax;

    @Column(name = "date_anomalie")
    private LocalDate dateAnomalie;

    @Column(name = "description_anomalie")
    private String descriptionAnomalie;

    @ManyToOne
    @JsonIgnoreProperties(value = { "etage" }, allowSetters = true)
    private Locale locale;

    @ManyToOne
    @JsonIgnoreProperties(value = { "consommations", "anomalies", "predictions" }, allowSetters = true)
    private Energie energie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Anomalie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getZoneNormaleMin() {
        return this.zoneNormaleMin;
    }

    public Anomalie zoneNormaleMin(Double zoneNormaleMin) {
        this.setZoneNormaleMin(zoneNormaleMin);
        return this;
    }

    public void setZoneNormaleMin(Double zoneNormaleMin) {
        this.zoneNormaleMin = zoneNormaleMin;
    }

    public Double getZoneNormaleMax() {
        return this.zoneNormaleMax;
    }

    public Anomalie zoneNormaleMax(Double zoneNormaleMax) {
        this.setZoneNormaleMax(zoneNormaleMax);
        return this;
    }

    public void setZoneNormaleMax(Double zoneNormaleMax) {
        this.zoneNormaleMax = zoneNormaleMax;
    }

    public LocalDate getDateAnomalie() {
        return this.dateAnomalie;
    }

    public Anomalie dateAnomalie(LocalDate dateAnomalie) {
        this.setDateAnomalie(dateAnomalie);
        return this;
    }

    public void setDateAnomalie(LocalDate dateAnomalie) {
        this.dateAnomalie = dateAnomalie;
    }

    public String getDescriptionAnomalie() {
        return this.descriptionAnomalie;
    }

    public Anomalie descriptionAnomalie(String descriptionAnomalie) {
        this.setDescriptionAnomalie(descriptionAnomalie);
        return this;
    }

    public void setDescriptionAnomalie(String descriptionAnomalie) {
        this.descriptionAnomalie = descriptionAnomalie;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Anomalie locale(Locale locale) {
        this.setLocale(locale);
        return this;
    }

    public Energie getEnergie() {
        return this.energie;
    }

    public void setEnergie(Energie energie) {
        this.energie = energie;
    }

    public Anomalie energie(Energie energie) {
        this.setEnergie(energie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Anomalie)) {
            return false;
        }
        return id != null && id.equals(((Anomalie) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Anomalie{" +
            "id=" + getId() +
            ", zoneNormaleMin=" + getZoneNormaleMin() +
            ", zoneNormaleMax=" + getZoneNormaleMax() +
            ", dateAnomalie='" + getDateAnomalie() + "'" +
            ", descriptionAnomalie='" + getDescriptionAnomalie() + "'" +
            "}";
    }
}
