package com.sebastienguillemin.stups.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "echantillon")
@ToString
public class Echantillon {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_scelle")
    private Scelle scelle;

    @OneToOne
    @JoinColumn(name = "id_composition")
    private Composition composition;
}
