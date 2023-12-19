CREATE MATERIALIZED VIEW echantillon_propriete AS (
    SELECT
        *
    FROM
        crosstab(
            $$
                SELECT
                    id,
                    propriete,
                    valeur
                FROM
                    liste_propriete
            $$,
            $$
                SELECT 
                    DISTINCT lp.propriete 
                FROM 
                    liste_propriete lp
                ORDER BY lp.propriete
            $$
        ) AS res (
            id varchar(255),
            _3_4_methylenedioxyethylamphetamine varchar(255),
            _3_4_methylene_dioxy_methylamphetamine varchar(255),
            _5F_ADB varchar(255),
            _5F_MDMB_PICA varchar(255),
            Abime varchar(255),
            Acide_4_hydroxybutanoique varchar(255),
            ADB_BUTINACA varchar(255),
            Amphetamine varchar(255),
            Autre varchar(255),
            Buprenorphine varchar(255),
            Cafeine varchar(255),
            Cannabidiol varchar(255),
            Cannabinol varchar(255),
            Clonazepam varchar(255),
            Cocaine varchar(255),
            Couleur varchar(255),
            Couleur_exterieur varchar(255),
            Couleur_exterieur_1 varchar(255),
            Couleur_exterieur_2 varchar(255),
            Couleur_interieur varchar(255),
            Delta8_Tetrahydrocannabinol varchar(255),
            Delta9_Tetrahydrocannabinol varchar(255),
            Description_de_l_Objet varchar(255),
            Diametre varchar(255),
            Epaisseur varchar(255),
            Etiquette varchar(255),
            Forme varchar(255),
            FUB_AMB varchar(255),
            Gammabutyrolactone varchar(255),
            Hauteur varchar(255),
            Heroine varchar(255),
            Ketamine varchar(255),
            Largeur varchar(255),
            Levamisole varchar(255),
            Lidocaine varchar(255),
            Logo varchar(255),
            Longueur varchar(255),
            Masse varchar(255),
            MDMB_4en_PINACA varchar(255),
            Methamphetamine varchar(255),
            Morphine varchar(255),
            Nom_de_logo varchar(255),
            Noscapine varchar(255),
            num_echantillon varchar(255),
            O6_Monoacetylmorphine varchar(255),
            Ovule varchar(255),
            Paracetamol varchar(255),
            Phenacetine varchar(255),
            Presentation varchar(255),
            Procaine varchar(255),
            Secabilite_recto varchar(255),
            Secabilite_verso varchar(255),
            Type_drogue varchar(255),
            Unite_taux varchar(255),
            Visqueux varchar(255)
        )
);