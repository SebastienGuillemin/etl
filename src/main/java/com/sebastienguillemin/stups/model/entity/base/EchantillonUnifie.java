package com.sebastienguillemin.stups.model.entity.base;

import com.sebastienguillemin.stups.model.BaseEntity;
import com.sebastienguillemin.stups.model.entity.resource.Echantillon;
import com.sebastienguillemin.stups.model.entity.resource.Scelle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "echantillon_unifie")
@ToString
public class EchantillonUnifie extends BaseEntity {
    @Column(name = "num_echantillon")
    private String num;

    // @ManyToOne
    // @JoinColumn(name = "id_scelle")
    // private Scelle scelle;

    private boolean logo;
    private String nomLogo;
    private boolean etiquette;
    private String presentation;
    private boolean ovule;
    private boolean abime;
    private boolean visqueux;
    private float hauteur;
    private float largeur;
    private float diametre;
    private float longueur;
    private float epaisseur;
    private String forme;
    private String couleur;
    private String couleurInterieure;
    private boolean empreinte;
    private float masse;
    private String secabiliteRecto;
    private String secabiliteVerso;
    private float taux;
    private float volume;
    // private float principeActif;
    // private float produitCoupage;
    private String aspect;
    private String formeChimique;
    private String service;
    private String substance;
    private String numeroEchantillon;
    // private String numeroScelle;
    private boolean trace;
    private String typeDrogue;
    private float quantite;

    public EchantillonUnifie (Echantillon echantillon) {
        this.num = echantillon.getNum();
    }
}
