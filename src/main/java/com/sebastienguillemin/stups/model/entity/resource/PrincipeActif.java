package com.sebastienguillemin.stups.model.entity.resource;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

import com.sebastienguillemin.stups.repository.RDFRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "principe_actif")
@ToString
public class PrincipeActif extends Composant {
    
    @ManyToOne
    @JoinColumn(name = "id_forme_chimique")
    private FormeChimique formeChimique;


    // @Column(name = "is_cbd")
    // private boolean isCbd;

    // @Column(name = "is_cbn")
    // private boolean isCbn;

    // @Column(name = "taux_cbd")
    // private String tauxCbd;

    // @Column(name = "trace_cbd")
    // private String traceCbd;

    // @Column(name = "taux_cbn")
    // private String tauxCbn;

    // @Column(name = "trace_cbn")
    // private String traceCbn;

    @Override
    public Resource getResource(Model model) {
        Resource resource = super.getPartielResource(model);
        Property aFormeChimique = model.createProperty(RDFRepository.PREFIX + "aFormeChimique");

        resource.addProperty(aFormeChimique, this.formeChimique.getResource(model));
        resource.addProperty(RDF.type, model.getResource(RDFRepository.PREFIX + "PrincipeActif"));
                
        return resource;
    }
}
