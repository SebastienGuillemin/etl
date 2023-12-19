CREATE MATERIALIZED VIEW liste_propriete AS (
    (
        SELECT
            -- Valeur de la propriété dans la table "valeur_propriete".
            e.id,
            CASE p.libelle
                WHEN 'Couleur extérieur (comprimé)' THEN 'Couleur extérieur'
                WHEN 'Couleur intérieur (comprimé)' THEN 'Couleur intérieur'
                ELSE p.libelle
            END AS propriete,
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
        SELECT
            -- Valeur de la propriété dans la table "description".
            e.id,
            CASE p.libelle
                WHEN 'Masse (résine)' THEN 'Masse'
                WHEN 'Masse (comprimé)' THEN 'Masse'
                WHEN 'Autre (résine)' THEN 'Autre'
                WHEN 'Couleur extérieur (comprimé)' THEN 'Couleur extérieur'
                WHEN 'Couleur intérieur (comprimé)' THEN 'Couleur intérieur'
                ELSE p.libelle
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
            AND d.valeur IS NOT NULL
    )
    UNION
    (
        SELECT
            -- Récupération du type de drogue.
            e.id,
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
            AND t.id = s.id_categORie
    )
    UNION
    (
        SELECT
            -- Récupération du numéro d'échantillon.
            e.id,
            'Numero echantillon' AS propriete,
            e.num_echantillon AS valeur
        FROM
            echantillon e,
            composition c
        WHERE
            e.id_composition = c.id
    )
    UNION
    (
        SELECT
            -- Récupération constituants à taux non nuls
            e.id,
            s.libelle AS "Propriete",
            CAST(pa.taux as varchar) AS "Valeur"
        FROM
            echantillon e
            INNER JOIN composition c ON c.id = e.id_composition
            INNER JOIN principe_actif pa ON pa.id_composition = c.id
            INNER JOIN substance s ON s.id = pa.id_substance
        WHERE
            taux IS NOT NULL
    )
    UNION
    (
        SELECT
            -- Récupération constituants présents en trace (i.e. avec un taux nul)
            e.id,
            s.libelle AS "Propriete",
            '0' AS "Valeur"
        FROM
            echantillon e
            INNER JOIN composition c ON c.id = e.id_composition
            INNER JOIN principe_actif pa ON pa.id_composition = c.id
            INNER JOIN substance s ON s.id = pa.id_substance
        WHERE
            trace
            AND (
                taux IS NULL
                OR taux = 0
            )
    )
)