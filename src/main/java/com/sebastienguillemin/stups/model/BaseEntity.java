package com.sebastienguillemin.stups.model;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

import com.sebastienguillemin.stups.util.StringConverter;

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

    public BaseEntity() {
        this.simpleName = StringConverter.toSnakeCase(this.getClass().getSimpleName());
    }

    public String getResourceName() {
        return this.simpleName + "_" + this.id;
    }

    public abstract Resource getResource(Model model);
}
