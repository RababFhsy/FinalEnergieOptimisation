package com.feb.optiisationenergie.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Capteur.
 */
@Entity
@Table(name = "capteur")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Capteur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "capteur_reference")
    private String capteurReference;

    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @Column(name = "valeur_min")
    private Double valeurMin;

    @Column(name = "valeur_max")
    private Double valeurMax;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Capteur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCapteurReference() {
        return this.capteurReference;
    }

    public Capteur capteurReference(String capteurReference) {
        this.setCapteurReference(capteurReference);
        return this;
    }

    public void setCapteurReference(String capteurReference) {
        this.capteurReference = capteurReference;
    }

    public String getType() {
        return this.type;
    }

    public Capteur type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Capteur photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Capteur photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Double getValeurMin() {
        return this.valeurMin;
    }

    public Capteur valeurMin(Double valeurMin) {
        this.setValeurMin(valeurMin);
        return this;
    }

    public void setValeurMin(Double valeurMin) {
        this.valeurMin = valeurMin;
    }

    public Double getValeurMax() {
        return this.valeurMax;
    }

    public Capteur valeurMax(Double valeurMax) {
        this.setValeurMax(valeurMax);
        return this;
    }

    public void setValeurMax(Double valeurMax) {
        this.valeurMax = valeurMax;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Capteur)) {
            return false;
        }
        return id != null && id.equals(((Capteur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Capteur{" +
            "id=" + getId() +
            ", capteurReference='" + getCapteurReference() + "'" +
            ", type='" + getType() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", valeurMin=" + getValeurMin() +
            ", valeurMax=" + getValeurMax() +
            "}";
    }
}
