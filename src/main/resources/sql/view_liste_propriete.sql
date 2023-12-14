CREATE OR REPLACE MATERIALIZED VIEW liste_propriete as (
    select
        e.id,
        c.id as id_composition,
        p.libelle as propriete,
        vp.libelle as valeur
    from
        echantillon e,
        composition c,
        description d,
        propriete p,
        valeur_propriete vp
    where
        e.id_composition = c.id
        and c.id = d.id_composition
        and p.id = d.id_propriete
        and d.id_valeur_propriete = vp.id
)
UNION
(
    select
        e.id,
        c.id as id_composition,
        p.libelle as propriete,
        d.valeur as valeur
    from
        echantillon e,
        composition c,
        description d,
        propriete p
    where
        e.id_composition = c.id
        and c.id = d.id_composition
        and p.id = d.id_propriete
        and d.valeur is not null
)
UNION
(
    select
        e.id,
        c.id as id_composition,
        'Type drogue' as propriete,
        t.libelle as valeur
    from
        echantillon e,
        principe_actif pa,
        composition c,
        substance s,
        "type" t
    where
        e.id_composition = c.id
        and pa.id_composition = c.id
        and pa.id_substance = s.id
        and t.id = s.id_categorie
)
UNION
(
    select
        e.id,
        c.id as id_composition,
        'Numero echantillon' as propriete,
        e.num_echantillon as valeur
    from
        echantillon e,
        composition c
    where
        e.id_composition = c.id
)