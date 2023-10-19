package com.feb.optiisationenergie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Energie.
 */
@Entity
@Table(name = "energie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Energie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_system_energitique")
    private String nomSystemEnergitique;

    @OneToMany(mappedBy = "energie")
    @JsonIgnoreProperties(value = { "locale", "energie" }, allowSetters = true)
    private Set<Consommation> consommations = new HashSet<>();

    @OneToMany(mappedBy = "energie")
    @JsonIgnoreProperties(value = { "locale", "energie" }, allowSetters = true)
    private Set<Anomalie> anomalies = new HashSet<>();

    @OneToMany(mappedBy = "energie")
    @JsonIgnoreProperties(value = { "locale", "energie" }, allowSetters = true)
    private Set<Prediction> predictions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Energie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomSystemEnergitique() {
        return this.nomSystemEnergitique;
    }

    public Energie nomSystemEnergitique(String nomSystemEnergitique) {
        this.setNomSystemEnergitique(nomSystemEnergitique);
        return this;
    }

    public void setNomSystemEnergitique(String nomSystemEnergitique) {
        this.nomSystemEnergitique = nomSystemEnergitique;
    }

    public Set<Consommation> getConsommations() {
        return this.consommations;
    }

    public void setConsommations(Set<Consommation> consommations) {
        if (this.consommations != null) {
            this.consommations.forEach(i -> i.setEnergie(null));
        }
        if (consommations != null) {
            consommations.forEach(i -> i.setEnergie(this));
        }
        this.consommations = consommations;
    }

    public Energie consommations(Set<Consommation> consommations) {
        this.setConsommations(consommations);
        return this;
    }

    public Energie addConsommations(Consommation consommation) {
        this.consommations.add(consommation);
        consommation.setEnergie(this);
        return this;
    }

    public Energie removeConsommations(Consommation consommation) {
        this.consommations.remove(consommation);
        consommation.setEnergie(null);
        return this;
    }

    public Set<Anomalie> getAnomalies() {
        return this.anomalies;
    }

    public void setAnomalies(Set<Anomalie> anomalies) {
        if (this.anomalies != null) {
            this.anomalies.forEach(i -> i.setEnergie(null));
        }
        if (anomalies != null) {
            anomalies.forEach(i -> i.setEnergie(this));
        }
        this.anomalies = anomalies;
    }

    public Energie anomalies(Set<Anomalie> anomalies) {
        this.setAnomalies(anomalies);
        return this;
    }

    public Energie addAnomalies(Anomalie anomalie) {
        this.anomalies.add(anomalie);
        anomalie.setEnergie(this);
        return this;
    }

    public Energie removeAnomalies(Anomalie anomalie) {
        this.anomalies.remove(anomalie);
        anomalie.setEnergie(null);
        return this;
    }

    public Set<Prediction> getPredictions() {
        return this.predictions;
    }

    public void setPredictions(Set<Prediction> predictions) {
        if (this.predictions != null) {
            this.predictions.forEach(i -> i.setEnergie(null));
        }
        if (predictions != null) {
            predictions.forEach(i -> i.setEnergie(this));
        }
        this.predictions = predictions;
    }

    public Energie predictions(Set<Prediction> predictions) {
        this.setPredictions(predictions);
        return this;
    }

    public Energie addPredictions(Prediction prediction) {
        this.predictions.add(prediction);
        prediction.setEnergie(this);
        return this;
    }

    public Energie removePredictions(Prediction prediction) {
        this.predictions.remove(prediction);
        prediction.setEnergie(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Energie)) {
            return false;
        }
        return id != null && id.equals(((Energie) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Energie{" +
            "id=" + getId() +
            ", nomSystemEnergitique='" + getNomSystemEnergitique() + "'" +
            "}";
    }
}
