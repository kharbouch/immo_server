package com.immo.data.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import com.immo.bean.jpa.BienEntity;
import com.immo.bean.jpa.ProjetEntity;
import com.immo.bean.jpa.TrancheEntity;

/**
 * Repository : Bien.
 */
public interface BienJpaRepository extends PagingAndSortingRepository<BienEntity, Long> {

	@Async
    @Query("SELECT b FROM BienEntity b, TrancheEntity t, ProjetEntity p WHERE  b.tranche=t and t.projet = p and p = :projet") 
    List<BienEntity> findBienByProjet(@Param("projet") ProjetEntity projet);
	
	@Async
    @Query("SELECT b FROM BienEntity b, TrancheEntity t WHERE b.tranche=t and t = :tranche") 
    List<BienEntity> findBienByTranche(@Param("tranche") TrancheEntity tranche);
	
	@Async
    @Query(value = "   SELECT  T.ID, T.LIBELLE, nvl(( select COUNT (bien.id) AS val"
    		+ "   FROM   BIEN, TRANCHE, DOSSIER, RECETTE"
    		+ "   WHERE       BIEN.REF_TRANCHE = TRANCHE.ID"
    		+ "           AND BIEN.ID = DOSSIER.REF_BIEN"
    		+ "           AND DOSSIER.ID = RECETTE.REF_DOSSIER "
    		+ "           AND DOSSIER.ETAT = 'actif' "
    		+ "           AND RECETTE.ETAT IS NULL"
    		+ "           AND RECETTE.DATE_DEPOT = trunc(sysdate)"
    		+ "           AND T.ID = TRANCHE.ID ),0)BIENVJ,"
    		+ "    nvl((SELECT   COUNT (bien.id) AS val"
    		+ "    FROM   BIEN, TRANCHE, DOSSIER, RECETTE"
    		+ "   WHERE       BIEN.REF_TRANCHE = TRANCHE.ID"
    		+ "          AND BIEN.ID = DOSSIER.REF_BIEN"
    		+ "           AND DOSSIER.ID = RECETTE.REF_DOSSIER"
    		+ "          AND DOSSIER.ETAT = 'actif'"
    		+ "          AND RECETTE.ETAT IS NULL"
    		+ "          AND T.ID = TRANCHE.ID),0) BIENV,"
    		+ "  nvl((SELECT   COUNT (bien.id) AS val"
    		+ "   FROM   BIEN, TRANCHE, DOSSIER "
    		+ "  WHERE       BIEN.REF_TRANCHE = TRANCHE.ID"
    		+ "           AND BIEN.ID = DOSSIER.REF_BIEN   "
    		+ "           AND DOSSIER.ETAT = 'actif'"
    		+ "          AND DOSSIER.ID NOT IN(select distinct RECETTE.REF_DOSSIER"
    		+ "           from RECETTE where RECETTE.ETAT IS NULL)"
    		+ "           AND 3 = TRANCHE.ID),0) BIENRES,"
    		+ "   nvl((select COUNT (bien.id) AS val"
    		+ "        FROM   BIEN, TRANCHE "
    		+ "   where  BIEN.ETAT='libre'"
    		+ "           AND  BIEN.REF_TRANCHE = TRANCHE.ID "
    		+ "           AND T.ID = TRANCHE.ID),0) BIENLIB, "
    		+ "   nvl((select COUNT (bien.id) AS val"
    		+ "      FROM   BIEN, TRANCHE "
    		+ "    where  BIEN.ETAT='bloque'"
    		+ "          AND  BIEN.REF_TRANCHE = TRANCHE.ID"
    		+ "            AND T.ID = TRANCHE.ID),0) BIENBLOQ,"
    		+ "    P.ID AS PROJET, P.LIBELLE PROJETDES  "
    		+ "    FROM TRANCHE T, PROJET P"
    		+ "    WHERE P.ID= T.REF_PROJET", nativeQuery = true) 
    List<Object> etatBien();
	
}
