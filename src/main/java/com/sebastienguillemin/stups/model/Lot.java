package com.sebastienguillemin.stups.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
/**
 * Cette classe permet de représenter une instance du concept Lot de l'ontologie.
 * La classe {@link LotEchantillon} permet de charger les données depuis la base.
 */
public class Lot {
    private int id;
    private List<Echantillon> echantillons;

    public void addEchantillon(Echantillon e) {
        if (!this.echantillons.contains(e))
            this.echantillons.add(e);
    }

    public int size() {
        return this.echantillons.size();
    }
}
