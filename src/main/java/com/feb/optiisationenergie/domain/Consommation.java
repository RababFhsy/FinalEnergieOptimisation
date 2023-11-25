package com.feb.optiisationenergie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A Consommation.
 */
@Entity
@Table(name = "consommation")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Consommation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "energie_consommation")
    private String energieConsommation;

    @Column(name = "date_consommation")
    private LocalDate dateConsommation;

    @ManyToOne
    @JsonIgnoreProperties(value = { "etage" }, allowSetters = true)
    private Locale locale;

    @ManyToOne
    @JsonIgnoreProperties(value = { "consommations", "anomalies", "predictions" }, allowSetters = true)
    private Energie energie;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Consommation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnergieConsommation() {
        return this.energieConsommation;
    }

    public Consommation energieConsommation(String energieConsommation) {
        this.setEnergieConsommation(energieConsommation);
        return this;
    }

    public void setEnergieConsommation(String energieConsommation) {
        this.energieConsommation = energieConsommation;
    }

    public LocalDate getDateConsommation() {
        return this.dateConsommation;
    }

    public Consommation dateConsommation(LocalDate dateConsommation) {
        this.setDateConsommation(dateConsommation);
        return this;
    }

    public void setDateConsommation(LocalDate dateConsommation) {
        this.dateConsommation = dateConsommation;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Consommation locale(Locale locale) {
        this.setLocale(locale);
        return this;
    }

    public Energie getEnergie() {
        return this.energie;
    }

    public void setEnergie(Energie energie) {
        this.energie = energie;
    }

    public Consommation energie(Energie energie) {
        this.setEnergie(energie);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Consommation user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Consommation)) {
            return false;
        }
        return id != null && id.equals(((Consommation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Consommation{" +
            "id=" + getId() +
            ", energieConsommation='" + getEnergieConsommation() + "'" +
            ", dateConsommation='" + getDateConsommation() + "'" +
            "}";
    }
}
