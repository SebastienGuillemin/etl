package com.sebastienguillemin.stups.model.entity.base;

import com.sebastienguillemin.stups.model.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "type")
@ToString
public class Type extends BaseEntity {
    private String code;
    private String categorie;
    private String libelle;
    private int rang;    
}
