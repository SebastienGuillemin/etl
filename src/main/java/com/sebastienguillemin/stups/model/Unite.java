package com.sebastienguillemin.stups.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Unite {
    @Id
    public int id;

    public String type;
    public String libelle;

    @Column(name = "lib_short")
    public String libShort;
}
