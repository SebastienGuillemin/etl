package com.sebastienguillemin.stups.model.entity.resource;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.datatypes.xsd.XSDDateTime;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

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
    public static final HashMap<String, Resource> allEntitiesResources = new HashMap<>();

    @Column(name = "date_reception")
    private String dateReception;

    @Column(name = "date_saisie")
    private String dateSaisine;

    @ManyToOne
    @JoinColumn(name = "id_service")
    public Service service;

    @Override
    public Resource getResource(Model model) {
        // Creating sample resource
        String resourceName = RDFRepository.PREFIX + this.getResourceName();
        if (Saisine.allEntitiesResources.containsKey(resourceName))
            return Saisine.allEntitiesResources.get(resourceName);
        
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());
        Saisine.allEntitiesResources.put(resourceName, resource);
        
        Property idProperty = model.createProperty(RDFRepository.PREFIX + "id");
        resource.addLiteral(idProperty, BigInteger.valueOf(this.id));

        Property dateProperty = model.createProperty(RDFRepository.PREFIX + "date");
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);

            if (this.dateSaisine != null)
                cal.setTime(sdf.parse(this.dateSaisine));
            else if (this.dateReception != null)
                cal.setTime(sdf.parse(this.dateReception));

            // Use this to avoid automatic '-2 hours' performed by XSDDateTime constructor
            if (cal.get(Calendar.HOUR_OF_DAY) < 2)
                cal.set(Calendar.HOUR_OF_DAY, 2);

            XSDDateTime xsdDateTime = new XSDDateTime(cal);
            xsdDateTime.narrowType(XSDDatatype.XSDdate);
            resource.addLiteral(dateProperty, xsdDateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        resource.addProperty(RDF.type, model.getResource(RDFRepository.PREFIX + "Saisine"));
                
        return resource;
    }
}
