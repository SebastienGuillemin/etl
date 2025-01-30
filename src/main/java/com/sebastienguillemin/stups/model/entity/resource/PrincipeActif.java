package com.sebastienguillemin.stups.model.entity.resource;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

import com.sebastienguillemin.stups.repository.RDFRepository;

import jakarta.persistence.Column;
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

    @Column(name = "taux_cbd")
    private String tauxCBD;

    @Column(name = "trace_cbd")
    private String traceCBD;

    @Column(name = "taux_cbn")
    private String tauxCBN;

    @Column(name = "trace_cbn")
    private String traceCBN;

    @Override
    public Resource getResource(Model model) {
        Resource resource = super.getPartielResource(model);

        // If a Cannabis sample
        if (this.substance.getType().getLibelle().equals("Cannabis")) {
            float tauxCBDLiteral = this.getTauxCBD();
            if (tauxCBDLiteral != -1.0f) {
                Property tauxCBD = model.createProperty(RDFRepository.PREFIX + "tauxCBD");
                resource.addLiteral(tauxCBD, tauxCBDLiteral);
            }

            float tauxCBNLiteral = this.getTauxCBN();
            if (tauxCBNLiteral != -1.0f) {
                Property tauxCBN = model.createProperty(RDFRepository.PREFIX + "tauxCBN");
                resource.addLiteral(tauxCBN, tauxCBNLiteral);
            }

            float tauxTHCLiteral = this.getDosage();
            if (this.substance.getLibelle().equals("Delta9-Tétrahydrocannabinol (THC)") && tauxTHCLiteral != -1.0f) {
                Property tauxTHC = model.createProperty(RDFRepository.PREFIX + "tauxTHC");
                resource.addLiteral(tauxTHC, tauxTHCLiteral);
            }
        }

        Property aFormeChimique = model.createProperty(RDFRepository.PREFIX + "aFormeChimique");

        resource.addProperty(aFormeChimique, this.formeChimique.getResource(model));
        resource.addProperty(RDF.type, model.getResource(RDFRepository.PREFIX + "PrincipeActif"));

        return resource;
    }

    private float getTauxCBD() {
        return (this.tauxCBD != null) ? Float.valueOf(this.tauxCBD) : (this.traceCBD != null && this.traceCBD.equals("t")) ? 0.0f : -1.0f;
    }

    private float getTauxCBN() {
        return (this.tauxCBN != null) ? Float.valueOf(this.tauxCBN) : (this.traceCBN != null && this.traceCBN.equals("t")) ? 0.0f : -1.0f;
    }
}
