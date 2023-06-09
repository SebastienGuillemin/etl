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
public abstract class BaseEntity implements Comparable<BaseEntity> {
    @Id
    protected int id;
    
    @Transient
    protected String simpleName;

    public BaseEntity() {
        this.simpleName = StringConverter.toSnakeCase(this.getClass().getSimpleName());
    }

    public String getResourceName() {
        return this.simpleName + "_" + this.id;
    }

    @Override
    public int compareTo(BaseEntity entity) {
        return Integer.compare(this.id, entity.getId());
    }

    public abstract Resource getResource(Model model);
}
