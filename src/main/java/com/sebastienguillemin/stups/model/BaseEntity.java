package com.sebastienguillemin.stups.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
    @Transient
    protected String simpleName;

    @Id
    protected int id;

    public String getResourceName() {
        return this.simpleName + "_" + this.id;
    }
}
