package com.immo.data.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import com.immo.bean.jpa.DossierEntity;
import com.immo.bean.jpa.RecetteEntity;
import com.immo.bean.jpa.UtilisateurEntity;

/**
 * Repository : Recette.
 */
public interface RecetteJpaRepository extends PagingAndSortingRepository<RecetteEntity, Long> {
	
	@Async
    @Query("SELECT t FROM RecetteEntity t where t.etat is null and t.dossier = :idDossier") 
    List<RecetteEntity> findByDossier(@Param("idDossier") DossierEntity idDossier);
	
	@Async
    @Query("SELECT t FROM RecetteEntity t where t.numRecette = :numero and t.banque = :banque") 
    List<RecetteEntity> findByNumAndBanque(@Param("numero") String numero, @Param("banque") String banque);

}
