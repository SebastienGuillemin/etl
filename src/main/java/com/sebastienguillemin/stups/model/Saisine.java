package com.sebastienguillemin.stups.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Saisine {
    @Id
    public int id;

    @ManyToOne
    @JoinColumn(name = "id_service_requerant")
    public ServiceRequerant serviceRequerant;

    @ManyToOne
    @JoinColumn(name = "id_service")
    public Service service;

    @ManyToOne
    @JoinColumn(name = "id_service_capteur")
    public ServiceRequerant serviceCapteur;
}
