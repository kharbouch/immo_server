package com.immo.data.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import com.immo.bean.jpa.ClientEntity;
import com.immo.bean.jpa.DossierClientEntity;
import com.immo.bean.jpa.DossierEntity;


/**
 * Repository : DossierClient.
 */
public interface DossierClientJpaRepository extends PagingAndSortingRepository<DossierClientEntity, Long> {

	@Async
    @Query("SELECT t.client FROM DossierClientEntity t where t.dossier = :dossier") 
    List<ClientEntity> findClientByDossier(@Param("dossier") DossierEntity dossier);
}
