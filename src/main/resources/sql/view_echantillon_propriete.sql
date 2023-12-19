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
            VALUES
                ('Numero echantillon'),
                ('Etiquette'),
                ('Secabilite verso'),
                ('Secabilite recto'),
                ('Diametre'),
                ('Epaisseur'),
                ('Description de l'' Objet '),
                ('Couleur exterieur 1'),
                ('Couleur exterieur 2'),
                ('Logo'),
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
        ) AS res (
            id int4,
            num_echantillon VARCHAR(255),
            etiquette VARCHAR(255),
            secabilite_verso VARCHAR(255),
            secabilite_recto VARCHAR(255),
            diametre VARCHAR(255),
            epaisseur VARCHAR(255),
            description_de_objet VARCHAR(255),
            couleur_exterieur_1 VARCHAR(255),
            couleur_exterieur_2 VARCHAR(255),
            logo VARCHAR(255),
            longueur VARCHAR(255),
            largeur VARCHAR(255),
            couleur_interieur_comprime VARCHAR(255),
            forme VARCHAR(255),
            visqueux VARCHAR(255),
            presentation VARCHAR(255),
            couleur VARCHAR(255),
            couleur_exterieur_comprime VARCHAR(255),
            masse_resine VARCHAR(255),
            nom_de_logo VARCHAR(255),
            masse_comprime VARCHAR(255),
            autre_resine VARCHAR(255),
            unite_taux VARCHAR(255),
            ovule VARCHAR(255),
            abime VARCHAR(255),
            hauteur VARCHAR(255),
            type_drogue VARCHAR (255)
        )
);