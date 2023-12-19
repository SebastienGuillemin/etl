CREATE MATERIALIZED VIEW liste_propriete AS (
    (
        SELECT -- Valeur de la propriété dans la table "valeur_propriete".
            e.id,
            c.id AS id_composition,
            p.libelle AS propriete,
            vp.libelle AS valeur
        FROM
            echantillon e,
            composition c,
            description d,
            propriete p,
            valeur_propriete vp
        WHERE
            e.id_composition = c.id
            AND c.id = d.id_composition
            AND p.id = d.id_propriete
            AND d.id_valeur_propriete = vp.id
    )
    UNION
    (
        SELECT -- Valeur de la propriété dans la table "description".
            e.id,
            c.id AS id_composition,
            CASE p.libelle
                WHEN 'Masse (résine)' THEN 'Masse'
                WHEN 'Masse (comprimé)' THEN 'Masse'
                ELSE P.libelle
            END AS propriete,
            d.valeur AS valeur
        FROM
            echantillon e,
            composition c,
            description d,
            propriete p
        WHERE
            e.id_composition = c.id
            AND c.id = d.id_composition
            AND p.id = d.id_propriete
            AND d.valeur is not null
    )
    UNION
    (
        SELECT -- Récupération du type de drogue.
            e.id,
            c.id AS id_composition,
            'Type drogue' AS propriete,
            t.libelle AS valeur
        FROM
            echantillon e,
            principe_actif pa,
            composition c,
            substance s,
            "type" t
        WHERE
            e.id_composition = c.id
            AND pa.id_composition = c.id
            AND pa.id_substance = s.id
            AND t.id = s.id_categorie
    )
    UNION
    (
        SELECT -- Récupération du numéro d'échantillon.
            e.id,
            c.id AS id_composition,
            'Numero echantillon' AS propriete,
            e.num_echantillon AS valeur
        FROM
            echantillon e,
            composition c
        WHERE
            e.id_composition = c.id
    )
)