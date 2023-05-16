package com.sebastienguillemin.stups.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "echantillon")
public class Echantillon {
    @Id
    private int id;
    private Scelle scelle;
}
