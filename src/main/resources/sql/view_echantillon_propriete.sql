CREATE MATERIALIZED VIEW echantillon_propriete AS (
    SELECT
        *
    FROM
        crosstab(
            $$
                SELECT
                    id,
                    propriete,
                    COALESCE(valeur, cast(valeur_num as varchar))
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
            _3_4_methylenedioxyethylamphetamine decimal,
            _3_4_methylene_dioxy_methylamphetamine decimal,
            _5F_ADB decimal,
            _5F_MDMB_PICA decimal,
            Abime varchar(255),
            Acide_4_hydroxybutanoique decimal,
            ADB_BUTINACA decimal,
            Amphetamine decimal,
            Buprenorphine decimal,
            Cafeine decimal,
            Cannabidiol decimal,
            Cannabinol decimal,
            Clonazepam decimal,
            Cocaine decimal,
            Couleur varchar(255),
            Couleur_exterieur varchar(255),
            Couleur_exterieur_1 varchar(255),
            Couleur_exterieur_2 varchar(255),
            Couleur_interieur varchar(255),
            Delta8_Tetrahydrocannabinol decimal,
            Delta9_Tetrahydrocannabinol decimal,
            Description_de_l_Objet varchar(255),
            Diametre decimal,
            Epaisseur decimal,
            Etiquette varchar(255),
            Forme varchar(255),
            FUB_AMB decimal,
            Gammabutyrolactone decimal,
            Hauteur decimal,
            Heroine decimal,
            Ketamine decimal,
            Largeur decimal,
            Levamisole decimal,
            Lidocaine decimal,
            Logo varchar(255),
            Longueur decimal,
            Masse decimal,
            MDMB_4en_PINACA decimal,
            Methamphetamine decimal,
            Morphine decimal,
            Nom_de_logo varchar(255),
            Noscapine decimal,
            num_echantillon varchar(255),
            O6_Monoacetylmorphine decimal,
            Ovule varchar(255),
            Paracetamol decimal,
            Phenacetine decimal,
            Presentation varchar(255),
            Procaine decimal,
            Secabilite_recto varchar(255),
            Secabilite_verso varchar(255),
            Type_drogue varchar(255),
            Visqueux varchar(255)
        )
);