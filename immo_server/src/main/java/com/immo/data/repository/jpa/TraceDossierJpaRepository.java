package com.immo.data.repository.jpa;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.immo.bean.jpa.TraceDossierEntity;

/**
 * Repository : TraceDossier.
 */
public interface TraceDossierJpaRepository extends PagingAndSortingRepository<TraceDossierEntity, Long> {

}
