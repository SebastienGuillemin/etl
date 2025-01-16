package com.sebastienguillemin.stups.model.entity.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

import com.sebastienguillemin.stups.model.BaseEntity;
import com.sebastienguillemin.stups.model.entity.base.Composition;
import com.sebastienguillemin.stups.model.entity.base.Description;
import com.sebastienguillemin.stups.model.entity.base.LotEchantillon;
import com.sebastienguillemin.stups.model.entity.base.Propriete;
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
@Table(name = "echantillon")
@ToString
public class Echantillon extends BaseEntity implements ResourceEntity {
    // List of samples to not fetch
    private final static ArrayList<Integer> TO_NOT_FETCH_IDS = new ArrayList<>(Arrays.asList(965, 2431, 2433, 2441, 2468, 2470, 2510, 2511, 2512, 2546, 4408, 4847, 4848, 4849, 4949, 4950, 6195, 6196, 6197, 6198, 6199, 8039, 8040, 8041, 8042, 9194, 9195, 9196, 9197, 9274, 9275, 9276, 9277, 10266, 10267, 10268, 10703, 10704, 10705, 10706, 10707, 10764));


    @Column(name = "num_echantillon")
    private String num;

    @ManyToOne
    @JoinColumn(name = "id_scelle")
    private Scelle scelle;

    @ManyToOne
    @JoinColumn(name = "id_composition")
    private Composition composition;

    public List<Resource> getNeighborsResources(Model model) {
        List<Resource> resources = new ArrayList<>();

        for (LotEchantillon lot : this.composition.getLotsTete()) {
            if (lot.getTypeLien().getLibelle().equals("Macroscopique") || lot.getTypeLien().getLibelle().equals("Composition atypique")) {
                for (Echantillon echantillon : lot.getComposition1().getEchantillons())
                    if (!TO_NOT_FETCH_IDS.contains(echantillon.getId()) && echantillon.id != this.id)
                        resources.add(echantillon.getResource(model));

                for (Echantillon echantillon : lot.getComposition2().getEchantillons())
                    if (!TO_NOT_FETCH_IDS.contains(echantillon.getId()) && echantillon.id != this.id)
                        resources.add(echantillon.getResource(model));
            }
        }

        return resources;
    }


    public List<Resource> getChemicalNeighborsResources(Model model) {
        List<Resource> resources = new ArrayList<>();

        for (LotEchantillon lot : this.composition.getLotsTete()) {
            if (lot.getTypeLien().getLibelle().equals("Profilage")) {
                
                for (Echantillon echantillon : lot.getComposition1().getEchantillons())
                    if (!TO_NOT_FETCH_IDS.contains(echantillon.getId()) && echantillon.id != this.id)
                        resources.add(echantillon.getResource(model));

                for (Echantillon echantillon : lot.getComposition2().getEchantillons())
                    if (!TO_NOT_FETCH_IDS.contains(echantillon.getId()) && echantillon.id != this.id)
                        resources.add(echantillon.getResource(model));
            }
        }

        return resources;
    }

    public String getProperty(String propertyName) {
        Propriete property = null;
        
        for (Description description  : this.composition.getDescriptions()) {
            property = description.getPropriete();

            if (property.getLibelle().equals(propertyName))
                if (description.getValeur() != null)
                    return description.getValeur();
                else if (description.getValeurPropriete() != null)
                    return description.getValeurPropriete().getLibelle();
                else return "";
        }

        return "";
    }

    @Override
    public Resource getResource(Model model) {
        // Creating sample resource
        Resource resource = model.createResource(RDFRepository.PREFIX + this.getResourceName());

        // Creating (or retrieving if alrezady created) properties
        Property idProperty = model.createProperty(RDFRepository.PREFIX + "id");
        Property typeDrogue = model.createProperty(RDFRepository.PREFIX + "typeDrogue");
        Property aPrincipeActif = model.createProperty(RDFRepository.PREFIX + "aPrincipeActif");
        Property aProduitCoupage = model.createProperty(RDFRepository.PREFIX + "aProduitCoupage");
        Property aAspectExterne = model.createProperty(RDFRepository.PREFIX + "aAspectExterne");
        Property numeroEchantillon = model.createProperty(RDFRepository.PREFIX + "numeroEchantillon");
        Property provientDe = model.createProperty(RDFRepository.PREFIX + "provientDe");
        Property forme = model.createProperty(RDFRepository.PREFIX + "forme");
        
        // Add ID
        resource.addLiteral(idProperty, this.id + "");
        
        // Add drug type and active principal resources
        for (PrincipeActif principeActif : this.composition.getPrincipeActifs()) {
            resource.addProperty(typeDrogue, principeActif.getSubstance().getType().getLibelle());
            resource.addProperty(aPrincipeActif, principeActif.getResource(model));
        }

        // Add cutting product resources
        for (ProduitCoupage produitCoupage : this.composition.getProduitCoupages())
            resource.addProperty(aProduitCoupage, produitCoupage.getResource(model));

        Aspect aspectInterne = this.composition.getAspectInterne();
        if (aspectInterne != null) {
            Property aAspectInterne = model.createProperty(RDFRepository.PREFIX + "aAspectInterne");
            resource.addProperty(aAspectInterne, aspectInterne.getResource(model));
        }

        // Add external aspect resource
        resource.addProperty(aAspectExterne, this.composition.getAspect().getResource(model));

        // Add sample number
        resource.addProperty(numeroEchantillon, this.num);

        // Add scelle resource
        resource.addProperty(provientDe, this.scelle.getResource(model));

        // Add forme resource
        String formeValue = this.getProperty("forme");
        if (formeValue != null && !formeValue.equals(""))
            resource.addProperty(forme, this.getProperty("forme"));

        // Add comment
        String commentaire = this.composition.getCommentaire();
        if (commentaire != null) {
            Property commmentaire = model.createProperty(RDFRepository.PREFIX + "commentaire");
            resource.addProperty(commmentaire, commentaire);
        }

        // Add description properties
        Property descriptionProperty;
        String propertyName, value;
        for (Description description : this.composition.getDescriptions()) {
            value = null;

            propertyName = description.getPropriete().getLibelle();
            descriptionProperty = model.createProperty(RDFRepository.PREFIX + description.getPropriete().getLibelle());

            // Tabular data
            if (description.getValeurPropriete() != null)
                resource.addLiteral(descriptionProperty, description.getValeurPropriete().getLibelle());
                
            // Free data
            else if (description.getValeur() != null && !description.getValeur().equals("")) {
                value = description.getValeur();
                
                if (Propriete.BOOLEAN_PROPERTIES.contains(propertyName)) {
                    if (value.equalsIgnoreCase("non") || value.equalsIgnoreCase("false") || value.equalsIgnoreCase("0"))
                        resource.addLiteral(descriptionProperty, false);

                    else if (value.equalsIgnoreCase("oui") || value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1"))
                        resource.addLiteral(descriptionProperty, true);
                }

                else if (Propriete.FLOAT_PROPERTIES.contains(propertyName)) {
                    resource.addLiteral(descriptionProperty, Float.parseFloat(value.replace(',', '.')));
                }
                
                else
                    resource.addLiteral(descriptionProperty, value);

            }
        }

        resource.addProperty(RDF.type, model.getResource(RDFRepository.PREFIX + "Echantillon"));

        return resource;
    }
}
