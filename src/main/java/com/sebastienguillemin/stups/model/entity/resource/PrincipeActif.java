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

    @Column(name = "is_cbd")
    private boolean isCBD;

    @Column(name = "taux_cbd")
    private String tauxCBD;

    @Column(name = "trace_cbd")
    private boolean traceCBD;

    @Column(name = "is_cbn")
    private boolean isCBN;

    @Column(name = "taux_cbn")
    private String tauxCBN;

    @Column(name = "trace_cbn")
    private boolean traceCBN;

    public void setIsCBD(String value) {
        this.isCBD = value.equals("t");
    }

    public void traceCBD(String value) {
        this.traceCBD = value.equals("t");
    }

    public void setIsCBN(String value) {
        this.isCBN = value.equals("t");
    }

    public void traceCBN(String value) {
        this.traceCBN = value.equals("t");
    }

    @Override
    public Resource getResource(Model model) {
        if (this.resource != null)
            return this.resource;

        this.resource = super.getPartielResource(model);

        Property aFormeChimique = model.createProperty(RDFRepository.PREFIX + "aFormeChimique");

        resource.addProperty(aFormeChimique, this.formeChimique.getResource(model));
        resource.addProperty(RDF.type, model.getResource(RDFRepository.PREFIX + "PrincipeActif"));

        return resource;
    }

    public float getTauxCBD() {
        if (!this.isCBD)
            return Composant.MISSING_RATE_IMPUTATION_VALUE;
        
        return (this.tauxCBD != null) ? Float.valueOf(this.tauxCBD) : (this.traceCBD) ? 0.0f : Composant.MISSING_RATE_IMPUTATION_VALUE;
    }

    public float getTauxCBN() {
        if (!this.isCBN)
            return Composant.MISSING_RATE_IMPUTATION_VALUE;

        return (this.tauxCBN != null) ? Float.valueOf(this.tauxCBN) : (this.traceCBN) ? 0.0f : Composant.MISSING_RATE_IMPUTATION_VALUE;
    }
}
