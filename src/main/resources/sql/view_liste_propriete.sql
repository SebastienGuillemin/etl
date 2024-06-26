CREATE MATERIALIZED VIEW liste_propriete AS (
    (
        SELECT -- Valeur de la propriété dans la table 'valeur_propriete' ('Unité taux' n'est pas sélectionnée).
            e.id,
            CASE
                p.libelle
                WHEN 'Couleur extérieur (comprimé)' THEN 'Couleur extérieur'
                WHEN 'Couleur intérieur (comprimé)' THEN 'Couleur intérieur'
                ELSE p.libelle
            END AS propriete,
            vp.libelle AS valeur,
            0 as valeur_num
        FROM echantillon e,
            composition c,
            description d,
            propriete p,
            valeur_propriete vp
        WHERE e.id_composition = c.id
            AND c.id = d.id_composition
            AND p.id = d.id_propriete
            AND d.id_valeur_propriete = vp.id
            AND p.libelle != 'Unité taux'
    )
    UNION
    (
        SELECT -- Valeur de la propriété dans la table 'description' (Autre (résine) n'est pas sélectionnée).
            e.id,
            CASE
                p.libelle
                WHEN 'Masse (résine)' THEN 'Masse'
                WHEN 'Masse (comprimé)' THEN 'Masse'
                WHEN 'Autre (résine)' THEN 'Autre'
                WHEN 'Couleur extérieur (comprimé)' THEN 'Couleur extérieur'
                WHEN 'Couleur intérieur (comprimé)' THEN 'Couleur intérieur'
                ELSE p.libelle
            END AS propriete,
            d.valeur AS valeur,
            0 as valeur_num
        FROM echantillon e,
            composition c,
            description d,
            propriete p
        WHERE e.id_composition = c.id
            AND c.id = d.id_composition
            AND p.id = d.id_propriete
            AND d.valeur IS NOT NULL
            AND p.libelle NOT IN (
                'Largeur',
                'Longueur',
                'Diamètre',
                'Hauteur',
                'Epaisseur',
                'Masse',
                'Masse (résine)',
                'Masse (comprimé)',
                'Autre (résine)'
            )
    )
    UNION
    (
        SELECT -- Valeur de la propriété dans la table 'description'.
            e.id,
            CASE
                p.libelle
                WHEN 'Masse (résine)' THEN 'Masse'
                WHEN 'Masse (comprimé)' THEN 'Masse'
                ELSE p.libelle
            END AS propriete,
            null AS valeur,
            cast (replace (d.valeur, ',', '.') as DECIMAL) as valeur_num
        FROM echantillon e,
            composition c,
            description d,
            propriete p
        WHERE e.id_composition = c.id
            AND c.id = d.id_composition
            AND p.id = d.id_propriete
            AND d.valeur IS NOT NULL
            AND p.libelle IN (
                'Largeur',
                'Longueur',
                'Diamètre',
                'Hauteur',
                'Epaisseur',
                'Masse',
                'Masse (résine)',
                'Masse (comprimé)'
            )
    )
    UNION
    (
        SELECT -- Récupération du type de drogue.
            e.id,
            'Type drogue' AS propriete,
            t.libelle AS valeur,
            0 as valeur_num
        FROM echantillon e,
            principe_actif pa,
            composition c,
            substance s,
            "type" t
        WHERE e.id_composition = c.id
            AND pa.id_composition = c.id
            AND pa.id_substance = s.id
            AND t.id = s.id_categORie
    )
    UNION
    (
        SELECT -- Récupération du numéro d'échantillon.
            e.id,
            'Numero echantillon' AS propriete,
            e.num_echantillon AS valeur,
            0 as valeur_num
        FROM echantillon e,
            composition c
        WHERE e.id_composition = c.id
    )
    UNION
    (
        SELECT -- Récupération principes actifs à taux non nuls
            e.id,
            s.libelle AS "Propriete",
            null AS "Valeur",
            pa.taux as valeur_num
        FROM echantillon e
            INNER JOIN composition c ON c.id = e.id_composition
            INNER JOIN principe_actif pa ON pa.id_composition = c.id
            INNER JOIN substance s ON s.id = pa.id_substance
        WHERE taux IS NOT NULL
    )
    UNION
    (
        SELECT -- Récupération principes actifs présents en trace (i.e. avec un taux nul)
            e.id,
            s.libelle AS "Propriete",
            null AS "Valeur",
            0.0 as valeur_num
        FROM echantillon e
            INNER JOIN composition c ON c.id = e.id_composition
            INNER JOIN principe_actif pa ON pa.id_composition = c.id
            INNER JOIN substance s ON s.id = pa.id_substance
        WHERE trace
            AND (
                taux IS NULL
                OR taux = 0
            )
    )
    UNION
    (
        SELECT -- Récupération produits de coupage à taux non nuls
            e.id,
            s.libelle AS "Propriete",
            null AS "Valeur",
            co.taux as valeur_num
        FROM echantillon e
            INNER JOIN composition c ON c.id = e.id_composition
            INNER JOIN constituant co ON co.id_composition = c.id
            INNER JOIN substance s ON s.id = co.id_substance
        WHERE taux IS NOT NULL
    )
    UNION
    (
        SELECT -- Récupération produits de coupage présents en trace (i.e. avec un taux nul)
            e.id,
            s.libelle AS "Propriete",
            null AS "Valeur",
            0.0 as valeur_num
        FROM echantillon e
            INNER JOIN composition c ON c.id = e.id_composition
            INNER JOIN constituant co ON co.id_composition = c.id
            INNER JOIN substance s ON s.id = co.id_substance
        WHERE trace
            AND (
                taux IS NULL
                OR taux = 0
            )
    )
    UNION
    (
        SELECT -- Récupération is_cbd
            e.id,
            'CBD' AS "Propriete",
            '1' AS "Valeur",
            0.0 as valeur_num
        FROM echantillon e
            INNER JOIN composition c ON c.id = e.id_composition
            INNER JOIN principe_actif pa on c.id = pa.id_composition
        WHERE pa.is_cbd
    )
    UNION
    (
        SELECT -- Récupération is_cbn
            e.id,
            'CBN' AS "Propriete",
            '1' AS "Valeur",
            0.0 as valeur_num
        FROM echantillon e
            INNER JOIN composition c ON c.id = e.id_composition
            INNER JOIN principe_actif pa on c.id = pa.id_composition
        WHERE pa.is_cbn
    )
    UNION
    (
        SELECT -- Récupération taux cbd
            e.id,
            'Taux cbd' AS "Propriete",
            null AS "Valeur",
            CASE
                WHEN pa.taux_cbd IS NOT NULL THEN pa.taux_cbd
                ELSE 0.0
            END as valeur_num
        FROM echantillon e
            INNER JOIN composition c ON c.id = e.id_composition
            INNER JOIN principe_actif pa on c.id = pa.id_composition
        WHERE pa.is_cbd
    )
    UNION
    (
        SELECT -- Récupération taux cbn
            e.id,
            'Taux cbn' AS "Propriete",
            null AS "Valeur",
            CASE
                WHEN pa.taux_cbn IS NOT NULL THEN pa.taux_cbn
                ELSE 0.0
            END as valeur_num
        FROM echantillon e
            INNER JOIN composition c ON c.id = e.id_composition
            INNER JOIN principe_actif pa on c.id = pa.id_composition
        WHERE pa.is_cbn
    )
)