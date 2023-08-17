package com.sebastienguillemin.stups.model.entity.base;

import com.sebastienguillemin.stups.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Unite extends BaseEntity {
    public String type;
    public String libelle;

    @Column(name = "lib_short")
    public String libShort;
}
