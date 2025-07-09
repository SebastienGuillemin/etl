package com.sebastienguillemin.stups.model.entity.resource;

import java.util.Hashtable;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

import com.sebastienguillemin.stups.model.BaseEntity;
import com.sebastienguillemin.stups.repository.RDFRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "forme_chimique")
@ToString
public class FormeChimique extends BaseEntity implements ResourceEntity {
    @Transient
    private static Hashtable<String, Resource> individuals = new Hashtable<>(); // Libellé forme chimique -> individu ({Base , BaseEtHCL , Chlorhydrate , HCL , Indeterminee , Phosphate , Sulfate})

    private String libelle;

    @Override
    public Resource getResource(Model model) {
        if (!FormeChimique.individuals.containsKey(this.getResourceName())) {
            System.out.println("Create new Forme Chimique individual: " + this.libelle);
            FormeChimique.individuals.put(this.getResourceName(), model.createResource(RDFRepository.PREFIX + this.getResourceName()));
        }

        return FormeChimique.individuals.get(this.getResourceName());
    }

    @Override
    public String getResourceName() {
        switch (this.libelle) {
            // Need to rewrite these two labels
            case "Base & HCl":
                return "BaseEtHCL";

            case "Indéterminée":
                return "Indeterminee";

            default:
                return this.libelle;
        }
    }
}
