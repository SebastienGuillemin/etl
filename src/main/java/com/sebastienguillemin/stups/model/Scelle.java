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
public class Scelle {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_saisine")
    public Saisine saisine;
}
