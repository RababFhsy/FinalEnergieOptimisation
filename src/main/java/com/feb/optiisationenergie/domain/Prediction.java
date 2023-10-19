package com.feb.optiisationenergie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A Prediction.
 */
@Entity
@Table(name = "prediction")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Prediction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "consommation_predit")
    private Double consommationPredit;

    @Column(name = "jhi_precision")
    private String precision;

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

    public Prediction id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDebut() {
        return this.dateDebut;
    }

    public Prediction dateDebut(LocalDate dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return this.dateFin;
    }

    public Prediction dateFin(LocalDate dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Double getConsommationPredit() {
        return this.consommationPredit;
    }

    public Prediction consommationPredit(Double consommationPredit) {
        this.setConsommationPredit(consommationPredit);
        return this;
    }

    public void setConsommationPredit(Double consommationPredit) {
        this.consommationPredit = consommationPredit;
    }

    public String getPrecision() {
        return this.precision;
    }

    public Prediction precision(String precision) {
        this.setPrecision(precision);
        return this;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Prediction locale(Locale locale) {
        this.setLocale(locale);
        return this;
    }

    public Energie getEnergie() {
        return this.energie;
    }

    public void setEnergie(Energie energie) {
        this.energie = energie;
    }

    public Prediction energie(Energie energie) {
        this.setEnergie(energie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prediction)) {
            return false;
        }
        return id != null && id.equals(((Prediction) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Prediction{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", consommationPredit=" + getConsommationPredit() +
            ", precision='" + getPrecision() + "'" +
            "}";
    }
}
