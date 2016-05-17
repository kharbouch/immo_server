package com.immo.data.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import com.immo.bean.jpa.ClientEntity;
import com.immo.bean.jpa.DossierEntity;
import com.immo.bean.jpa.EcheanceEntity;

/**
 * Repository : Echeance.
 */
public interface EcheanceJpaRepository extends PagingAndSortingRepository<EcheanceEntity, Long> {

	@Async
    @Query("SELECT t FROM EcheanceEntity t where t.dossier = :dossier") 
    List<EcheanceEntity> findEcheanceByDossier(@Param("dossier") DossierEntity dossier);
}
