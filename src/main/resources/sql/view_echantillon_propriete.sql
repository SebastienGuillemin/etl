SELECT
    *
FROM
    crosstab(
        $$
        select
            id,
            propriete,
            valeur
        from
            echantillon_unifie
        $$,
        $$
        values
            ('Etiquette'),
            ('Secabilite verso'),
            ('Secabilite recto'),
            ('Diametre'),
            ('Epaisseur'),
            ('Description de l'' Objet '),
            ('Couleur exterieur 2'),
            ('Logo'),
            ('Couleur exterieur 1'),
            ('Longueur'),
            ('Largeur'),
            ('Couleur interieur comprime'),
            ('Forme'),
            ('Visqueux'),
            ('Presentation'),
            ('Couleur'),
            ('Couleur exterieur comprime'),
            ('Masse resine'),
            ('Nom de logo'),
            ('Masse comprime'),
            ('Autre (resine)'),
            ('Unite taux'),
            ('Ovule'),
            ('Abîme'),
            ('Hauteur'),
            ('Type drogue')
        $$
    ) as res (
        id int4,
        etiquette varchar(255),
        secabilite_verso varchar(255),
        secabilite_recto varchar(255),
        diametre varchar(255),
        epaisseur varchar(255),
        description_de_objet varchar(255),
        couleur_exterieur_2 varchar(255),
        logo varchar(255),
        couleur_exterieur_1 varchar(255),
        longueur varchar(255),
        largeur varchar(255),
        couleur_interieur_comprime varchar(255),
        forme varchar(255),
        visqueux varchar(255),
        presentation varchar(255),
        couleur varchar(255),
        couleur_exterieur_comprime varchar(255),
        masse_resine varchar(255),
        nom_de_logo varchar(255),
        masse_comprime varchar(255),
        autre_resine varchar(255),
        unite_taux varchar(255),
        ovule varchar(255),
        abime varchar(255),
        hauteur varchar(255),
        type_drogue varchar (255)
    );