package com.sebastienguillemin.stups.model.entity.resource;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.apache.jena.datatypes.xsd.XSDDateTime;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import com.sebastienguillemin.stups.model.BaseEntity;
import com.sebastienguillemin.stups.repository.RDFRepository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Saisine extends BaseEntity implements ResourceEntity {
    @Column(name = "date_reception")
    private String dateReception;

    @ManyToOne
    @JoinColumn(name = "id_service")
    public Service service;

    @Override
    public Resource getResource(Model model) {
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());
        
        Property id = model.createProperty(RDFRepository.PREFIX + "id");
        resource.addProperty(id, this.id + "");

        if (this.dateReception != null) {
            try {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);
                cal.setTime(sdf.parse(this.dateReception));
    
                Property date = model.createProperty(RDFRepository.PREFIX + "date");
                resource.addLiteral(date, new XSDDateTime(cal));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
                
        return resource;
    }
}
