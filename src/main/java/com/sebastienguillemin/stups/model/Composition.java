package com.sebastienguillemin.stups.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Composition {
    @Id
    public int id;

    @ManyToOne
    @JoinColumn(name = "id_aspect")
    public Aspect aspect;

    @ManyToOne
    @JoinColumn(name = "id_aspect_interne")
    public Aspect aspectInterne;


    @ManyToOne
    @JoinColumn(name = "id_unite")
    public Unite unite;

    public Float quantite;
    public String commentaire;
}