package com.immo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.immo.bean.jpa.ProjetEntity;

/**
 * Repository : Projet.
 */
public interface ProjetJpaRepository extends PagingAndSortingRepository<ProjetEntity, Long> {

}
