package com.sebastienguillemin.stups.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Embeddable
public class Scelle {
    @Id
    private int id;

}
