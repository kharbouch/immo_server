package com.immo.data.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import com.immo.bean.jpa.ProjetEntity;
import com.immo.bean.jpa.TrancheEntity;
import com.immo.bean.jpa.UtilisateurEntity;
import com.immo.bean.jpa.UtilisateurTrancheEntity;

/**
 * Repository : UtilisateurTranche.
 */
public interface UtilisateurTrancheJpaRepository extends PagingAndSortingRepository<UtilisateurTrancheEntity, Long> {

	@Async
    @Query("SELECT u.tranche FROM UtilisateurTrancheEntity u where u.utilisateur = :user") 
    List<TrancheEntity> findTrancheByUser(@Param("user") UtilisateurEntity user);
	
	@Async
    @Query("SELECT u.tranche.projet FROM UtilisateurTrancheEntity u where u.utilisateur = :user") 
    List<ProjetEntity> findProjetByUser(@Param("user") UtilisateurEntity user);
	
}
