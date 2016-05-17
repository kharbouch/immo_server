package com.immo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.immo.bean.jpa.ProfilEntity;

/**
 * Repository : Profil.
 */
public interface ProfilJpaRepository extends PagingAndSortingRepository<ProfilEntity, Long> {

}
