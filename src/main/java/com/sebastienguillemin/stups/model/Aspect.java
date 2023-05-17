package com.sebastienguillemin.stups.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Aspect {
    @Id
    public int id;

    public String libelle;
}
