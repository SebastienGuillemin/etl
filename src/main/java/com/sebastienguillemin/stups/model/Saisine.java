package com.sebastienguillemin.stups.model;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import com.sebastienguillemin.stups.repository.RDFRepository;

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
public class Saisine extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "id_service_requerant")
    public ServiceRequerant serviceRequerant;

    @ManyToOne
    @JoinColumn(name = "id_service")
    public Service service;

    @ManyToOne
    @JoinColumn(name = "id_service_capteur")
    public ServiceRequerant serviceCapteur;

    @Override
    public Resource getResource(Model model) {
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());
        
        // if (this.serviceCapteur != null) {
        //     Property aServiceCapteur = model.createProperty(RDFRepository.PREFIX + "aServiceCapteur");
        //     resource.addProperty(aServiceCapteur, this.serviceCapteur.getResource(model));
        // }

        // if (this.serviceRequerant != null) {
        //     Property aServiceRequerant = model.createProperty(RDFRepository.PREFIX + "aServiceRequerant");
        //     resource.addProperty(aServiceRequerant, this.serviceRequerant.getResource(model));
        // }
                
        return resource;
    }
}
