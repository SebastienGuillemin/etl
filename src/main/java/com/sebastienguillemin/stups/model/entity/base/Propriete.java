package com.sebastienguillemin.stups.model.entity.base;

import java.util.Arrays;
import java.util.List;

import com.sebastienguillemin.stups.model.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Propriete extends BaseEntity {
    public static final List<String> BOOLEAN_PROPERTIES = Arrays.asList("abime", "ovule", "logo", "trace", "visqueux");
    public static final List<String> FLOAT_PROPERTIES = Arrays.asList("longueur", "largeur", "taux", "quantite", "epaisseur", "volume", "diametre", "masse", "hauteur");

    private String libelle;

    @OneToMany(mappedBy = "propriete")
    private List<Description> descriptions;

    @OneToMany(mappedBy = "propriete")
    private List<ValeurPropriete> valeurs;


    /**
     * 
     * The label of the property must be rewritten in order to remove the special characters
     * 
     */
    public String getLibelle() {
        switch (this.libelle) {
            case "Présentation":
                return "presentation";

            case "Abîmé":
                return "abime";

            case "Diamètre":
                return "diametre";

            case "Unité taux":
                return "unite";

            case "Nom de logo":
                return "nomLogo";

            case "Couleur extérieur 1":
            case "Couleur extérieur 2":
            case "Couleur extérieur (comprimé)":
                return "couleur";

            case "Couleur intérieur (comprimé)":
                return "couleurInterieur";

            case "Masse (résine)":
            case "Masse (comprimé)":
                return "masse";

            case "Sécabilité recto":
                return "secabiliteRecto";

            case "Sécabilité verso":
                return "secabiliteVerso";

            case "Description de l'Objet":
                return "description";

            case "Autre (résine)":
                return "autre";

            default:
                return libelle.toLowerCase();
        }
    }
}
